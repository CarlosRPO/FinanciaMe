package co.com.carlosrestrepo.financiame.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.List;

import co.com.carlosrestrepo.financiame.R;
import co.com.carlosrestrepo.financiame.fragment.customcontrol.NumberTextWatcher;
import co.com.carlosrestrepo.financiame.model.Deudor;
import co.com.carlosrestrepo.financiame.model.Movimiento;
import co.com.carlosrestrepo.financiame.model.TipoMovimiento;
import co.com.carlosrestrepo.financiame.persistence.dao.DeudorDAO;
import co.com.carlosrestrepo.financiame.persistence.dao.MovimientoDAO;
import co.com.carlosrestrepo.financiame.persistence.dao.TipoMovimientoDAO;

/**
 *
 * @author  Carlos Restrepo
 * @created Septiembre 15 de 2015
 */
public class InfoMovimientoFragment extends Fragment implements DatePickerFragment.SelectDateDialogListener {

    private Spinner tipoMovimiento;
    private EditText fecha;
    private EditText valor;
    private EditText descripcion;
    private Spinner deudor;
    private ImageButton btnFecha;

    private Movimiento movimientoEdit;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private List<TipoMovimiento> tipoMovimientoList;
    private List<Deudor> deudorList;
    private TipoMovimientoDAO tmDAO;
    private DeudorDAO deudorDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        tmDAO = new TipoMovimientoDAO(getContext());
        deudorDAO = new DeudorDAO(getContext());

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info_movimiento, container, false);

        tipoMovimiento = (Spinner) view.findViewById(R.id.tipoMovimiento);
        fecha = (EditText) view.findViewById(R.id.fechaMovimiento);
        descripcion = (EditText) view.findViewById(R.id.descripcionMovimiento);
        deudor = (Spinner) view.findViewById(R.id.deudorMovimiento);

        valor = (EditText) view.findViewById(R.id.valorMovimiento);
        valor.addTextChangedListener(new NumberTextWatcher(valor));

        btnFecha = (ImageButton) view.findViewById(R.id.btnFecha);
        btnFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = new DatePickerFragment();
                dialog.show(getFragmentManager(), "fecha");
            }
        });

        llenarTipoMovimiento();
        llenarDeudor();

        Bundle bundle = getArguments();
        if (bundle != null) {
            movimientoEdit = new Movimiento();
            movimientoEdit.setId(bundle.getLong("id"));
            movimientoEdit.setTipoMovimiento(
                    new TipoMovimiento(bundle.getLong("id_tipo_movimiento")));
            try {
                movimientoEdit.setFecha(sdf.parse(bundle.getString("fecha")));
            } catch (Exception e) {
                mostrarMensaje(getString(R.string.fechaError));
            }
            movimientoEdit.setValor(Integer.valueOf(bundle.getInt("valor")));
            movimientoEdit.setDescripcion(bundle.getString("descripcion"));
            if (bundle.containsKey("id_deudor"))
                movimientoEdit.setDeudor(new Deudor(bundle.getLong("id_deudor")));
            cargarMovimiento();
        }
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_info_options, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_back:
                atras();
                return true;
            case R.id.action_save:
                if (validarInfo()) {
                    MovimientoDAO movimientoDAO = new MovimientoDAO(getContext());
                    try {
                        if (movimientoEdit != null) {
                            movimientoEdit.setTipoMovimiento(
                                    tipoMovimientoList.get(
                                            tipoMovimiento.getSelectedItemPosition()));
                            movimientoEdit.setFecha(sdf.parse(fecha.getText().toString()));
                            movimientoEdit.setValor(
                                    Integer.parseInt(
                                            valor.getText().toString().replace(".", "")));
                            movimientoEdit.setDescripcion(descripcion.getText().toString());
                            movimientoEdit.setDeudor(
                                    deudorList.get(deudor.getSelectedItemPosition()));
                            movimientoDAO.update(movimientoEdit);
                        } else {
                            movimientoEdit = new Movimiento();
                            movimientoEdit.setTipoMovimiento(
                                    tipoMovimientoList.get(
                                            tipoMovimiento.getSelectedItemPosition() - 1));
                            movimientoEdit.setFecha(sdf.parse(fecha.getText().toString()));
                            movimientoEdit.setValor(
                                    Integer.parseInt(
                                            valor.getText().toString().replace(".", "")));
                            movimientoEdit.setDescripcion(descripcion.getText().toString());
                            if (movimientoEdit.getTipoMovimiento().hasDeudor()) {
                                movimientoEdit.setDeudor(
                                        deudorList.get(deudor.getSelectedItemPosition() - 1));
                            }
                            movimientoDAO.insert(movimientoEdit);
                        }
                        if (movimientoEdit.getTipoMovimiento().hasDeudor()) {
                            Deudor deudor = deudorDAO.findById(movimientoEdit.getDeudor().getId());
                            Integer total_deudas = deudor.getTotalDeudas();
                            total_deudas += movimientoEdit.getValor();
                            deudor.setTotalDeudas(total_deudas);
                            deudorDAO.update(deudor);
                        }
                    } catch (Exception e) {
                        mostrarMensaje(e.getMessage());
                    }
                    mostrarMensaje(getString(R.string.guardadoExitoso));
                    atras();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void llenarTipoMovimiento() {
        try {
            int size = 1;
            tipoMovimientoList = tmDAO.getAll();
            if (tipoMovimientoList != null && !tipoMovimientoList.isEmpty()) {
                size = tipoMovimientoList.size() + 1;
            }

            String[] valores = new String[size];
            valores[0] = "Seleccione un Tipo de Movimiento...";

            if (tipoMovimientoList != null && !tipoMovimientoList.isEmpty()) {
                for (int i = 0; i < tipoMovimientoList.size(); i++) {
                    TipoMovimiento tipoMovimiento = tipoMovimientoList.get(i);
                    valores[i+1] = tipoMovimiento.getNombre();
                }
            }

            ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, valores);
            listAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
            tipoMovimiento.setAdapter(listAdapter);

            tipoMovimiento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position > 0) {
                        if (tipoMovimientoList.get(position - 1).hasDeudor()) {
                            deudor.setVisibility(View.VISIBLE);
                        } else {
                            deudor.setVisibility(View.INVISIBLE);
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    deudor.setVisibility(View.INVISIBLE);
                }
            });
        } catch (Exception e) {
            mostrarMensaje(e.getMessage());
        }
    }

    private void llenarDeudor() {
        try {
            int size = 1;
            deudorList = deudorDAO.getAll();
            if (deudorList != null && !deudorList.isEmpty()) {
                size = deudorList.size() + 1;
            }

            String[] valores = new String[size];
            valores[0] = "Seleccione un Deudor...";

            if (deudorList != null && !deudorList.isEmpty()) {
                for (int i = 0; i < deudorList.size(); i++) {
                    Deudor deudor = deudorList.get(i);
                    valores[i+1] = deudor.getNombre();
                }
            }

            ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, valores);
            listAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
            deudor.setAdapter(listAdapter);
        } catch (Exception e) {
            mostrarMensaje(e.getMessage());
        }
    }

    private void cargarMovimiento() {
        tipoMovimiento.setSelection(
                tipoMovimientoList.indexOf(movimientoEdit.getTipoMovimiento()));
        fecha.setText(sdf.format(movimientoEdit.getFecha()));
        valor.setText(String.valueOf(movimientoEdit.getValor().intValue()));
        descripcion.setText(movimientoEdit.getDescripcion());
        if (movimientoEdit.getTipoMovimiento().hasDeudor()) {
            deudor.setSelection(deudorList.indexOf(movimientoEdit.getDeudor()));
            deudor.setVisibility(View.INVISIBLE);
        }
    }

    private boolean validarInfo() {
        if (tipoMovimiento.getSelectedItemPosition() == 0) {
            mostrarMensaje(getString(R.string.tipoMovimientoRequerido));
            return false;
        }
        if (fecha.getText() == null || fecha.getText().toString().isEmpty()) {
            mostrarMensaje(getString(R.string.fechaRequerida));
            return false;
        }
        if (valor.getText() == null || valor.getText().toString().isEmpty()) {
            mostrarMensaje(getString(R.string.valorRequerido));
            return false;
        }
        if (descripcion.getText() == null || descripcion.getText().toString().isEmpty()) {
            mostrarMensaje(getString(R.string.descripcionRequerida));
            return false;
        }
        if (tipoMovimientoList.get(tipoMovimiento.getSelectedItemPosition() - 1).hasDeudor()
                && deudor.getSelectedItemPosition() == 0) {
            mostrarMensaje(getString(R.string.deudorRequerido));
            return false;
        }
        return true;
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show();
    }

    private void atras() {
        onDestroy();
        onDestroyView();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new MovimientoFragment())
                .commit();
    }

    @Override
    public void onFinishSelectDateDialog(String date) {
        fecha.setText(date);
    }
}