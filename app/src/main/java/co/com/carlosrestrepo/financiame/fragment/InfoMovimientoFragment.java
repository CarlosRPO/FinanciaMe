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

import java.util.List;

import co.com.carlosrestrepo.financiame.R;
import co.com.carlosrestrepo.financiame.fragment.customcontrol.NumberTextWatcher;
import co.com.carlosrestrepo.financiame.model.Deudor;
import co.com.carlosrestrepo.financiame.model.MedioPago;
import co.com.carlosrestrepo.financiame.model.Movimiento;
import co.com.carlosrestrepo.financiame.model.TipoMovimiento;
import co.com.carlosrestrepo.financiame.persistence.dao.DeudorDAO;
import co.com.carlosrestrepo.financiame.persistence.dao.MedioPagoDAO;
import co.com.carlosrestrepo.financiame.persistence.dao.MovimientoDAO;
import co.com.carlosrestrepo.financiame.persistence.dao.TipoMovimientoDAO;
import co.com.carlosrestrepo.financiame.util.FinanciaMeConfiguration;

/**
 *
 * @author  Carlos Restrepo
 * @created Septiembre 15 de 2015
 */
public class InfoMovimientoFragment extends Fragment implements
        DatePickerFragment.SelectDateDialogListener {

    private Spinner tipoMovimiento;
    private EditText fecha;
    private EditText valor;
    private EditText descripcion;
    private Spinner deudor;
    private Spinner medioPago;
    private ImageButton btnFecha;

    private Movimiento movimientoEdit;

    private List<TipoMovimiento> tipoMovimientoList;
    private List<Deudor> deudorList;
    private List<MedioPago> medioPagoList;
    private TipoMovimientoDAO tmDAO;
    private DeudorDAO deudorDAO;
    private MedioPagoDAO medioPagoDAO;

    private final String ACCION_SUMAR = "+";
    private final String ACCION_RESTAR = "-";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        tmDAO = new TipoMovimientoDAO(getContext());
        deudorDAO = new DeudorDAO(getContext());
        medioPagoDAO = new MedioPagoDAO(getContext());

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info_movimiento, container, false);

        tipoMovimiento = (Spinner) view.findViewById(R.id.tipoMovimiento);
        fecha = (EditText) view.findViewById(R.id.fechaMovimiento);
        descripcion = (EditText) view.findViewById(R.id.descripcionMovimiento);
        medioPago = (Spinner) view.findViewById(R.id.medioPagoMovimiento);
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
        llenarMedioPago();
        llenarDeudor();

        Bundle bundle = getArguments();
        if (bundle != null) {
            movimientoEdit = new Movimiento();
            movimientoEdit.setId(bundle.getLong("id"));
            movimientoEdit.setTipoMovimiento(
                    new TipoMovimiento(bundle.getLong("id_tipo_movimiento")));
            try {
                movimientoEdit.setFecha(
                        FinanciaMeConfiguration.sdf.parse(bundle.getString("fecha")));
            } catch (Exception e) {
                mostrarMensaje(getString(R.string.fechaError));
            }
            movimientoEdit.setValor(Integer.valueOf(bundle.getInt("valor")));
            movimientoEdit.setDescripcion(bundle.getString("descripcion"));
            if (bundle.containsKey("id_deudor") && bundle.getLong("id_deudor") != 0)
                movimientoEdit.setDeudor(new Deudor(bundle.getLong("id_deudor")));
            if (bundle.containsKey("id_medio_pago") && bundle.getLong("id_medio_pago") != 0)
                movimientoEdit.setMedioPago(new MedioPago(bundle.getLong("id_medio_pago")));
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
                guardarMovimiento();
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
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    if (position > 0) {
                        if (tipoMovimientoList.get(position - 1).hasDeudor()) {
                            deudor.setVisibility(View.VISIBLE);
                        } else {
                            deudor.setVisibility(View.INVISIBLE);
                        }

                        if (tipoMovimientoList.get(position - 1).hasMedioPago()) {
                            medioPago.setVisibility(View.VISIBLE);
                        } else {
                            medioPago.setVisibility(View.INVISIBLE);
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    deudor.setVisibility(View.INVISIBLE);
                    medioPago.setVisibility(View.INVISIBLE);
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

    private void llenarMedioPago() {
        try {
            int size = 1;
            medioPagoList = medioPagoDAO.getAll();
            if (medioPagoList != null && !medioPagoList.isEmpty()) {
                size = medioPagoList.size() + 1;
            }

            String[] valores = new String[size];
            valores[0] = "Seleccione un Medio de Pago...";

            if (medioPagoList != null && !medioPagoList.isEmpty()) {
                for (int i = 0; i < medioPagoList.size(); i++) {
                    MedioPago medioPago = medioPagoList.get(i);
                    valores[i+1] = medioPago.getNombre();
                }
            }

            ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, valores);
            listAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
            medioPago.setAdapter(listAdapter);
        } catch (Exception e) {
            mostrarMensaje(e.getMessage());
        }
    }

    private void cargarMovimiento() {
        try {
            TipoMovimiento _tipoMovimiento = tmDAO.findById(
                    movimientoEdit.getTipoMovimiento().getId());
            tipoMovimiento.setSelection(tipoMovimientoList.indexOf(_tipoMovimiento) + 1);
            fecha.setText(FinanciaMeConfiguration.sdf.format(movimientoEdit.getFecha()));
            valor.setText(String.valueOf(movimientoEdit.getValor().intValue()));
            descripcion.setText(movimientoEdit.getDescripcion());
            if (movimientoEdit.getDeudor() != null &&
                    movimientoEdit.getDeudor().getId() != 0) {
                Deudor _deudor = deudorDAO.findById(movimientoEdit.getDeudor().getId());
                deudor.setSelection(deudorList.indexOf(_deudor) + 1);
                deudor.setVisibility(View.VISIBLE);
            } else {
                deudor.setSelection(0);
                deudor.setVisibility(View.INVISIBLE);
            }
            if (movimientoEdit.getMedioPago() != null &&
                    movimientoEdit.getMedioPago().getId() != 0) {
                MedioPago _medioPago = medioPagoDAO.findById(movimientoEdit.getMedioPago().getId());
                medioPago.setSelection(medioPagoList.indexOf(_medioPago) + 1);
                medioPago.setVisibility(View.VISIBLE);
            } else {
                medioPago.setSelection(0);
                medioPago.setVisibility(View.INVISIBLE);
            }
        } catch (Exception e) {
            mostrarMensaje(e.getMessage());
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
        try {
            if (tipoMovimientoList.get(tipoMovimiento.getSelectedItemPosition() - 1).hasDeudor()) {
                Deudor _deudor = deudorList.get(deudor.getSelectedItemPosition() - 1);
                Integer total_deudas = _deudor.getTotalDeudas();
                if (tipoMovimientoList.get(tipoMovimiento.getSelectedItemPosition() - 1)
                        .getAccion().equals(ACCION_SUMAR) &&
                        Integer.parseInt(
                                valor.getText().toString().replace(".", "")) > total_deudas) {
                    mostrarMensaje(String.format(
                            getString(R.string.valorDeudaSuperior), String.valueOf(total_deudas)));
                    return false;
                }
            }
        } catch (Exception e) {
            mostrarMensaje(e.getMessage());
            return false;
        }
        if (tipoMovimientoList.get(tipoMovimiento.getSelectedItemPosition() - 1).hasMedioPago()
                && medioPago.getSelectedItemPosition() == 0) {
            mostrarMensaje(getString(R.string.medioPagoRequerido));
            return false;
        }
        return true;
    }

    private void guardarMovimiento() {
        if (validarInfo()) {
            MovimientoDAO movimientoDAO = new MovimientoDAO(getContext());
            try {
                if (movimientoEdit != null) {
                    movimientoEdit.setTipoMovimiento(
                            tipoMovimientoList.get(
                                    tipoMovimiento.getSelectedItemPosition() - 1));
                    movimientoEdit.setFecha(
                            FinanciaMeConfiguration.sdf.parse(fecha.getText().toString()));
                    movimientoEdit.setValor(
                            Integer.parseInt(
                                    valor.getText().toString().replace(".", "")));
                    movimientoEdit.setDescripcion(descripcion.getText().toString());
                    if (movimientoEdit.getTipoMovimiento().hasDeudor()) {
                        movimientoEdit.setDeudor(
                                deudorList.get(deudor.getSelectedItemPosition() - 1));
                    } else {
                        movimientoEdit.setDeudor(null);
                    }
                    if (movimientoEdit.getTipoMovimiento().hasMedioPago()) {
                        movimientoEdit.setMedioPago(
                                medioPagoList.get(medioPago.getSelectedItemPosition() - 1));
                    } else {
                        movimientoEdit.setMedioPago(null);
                    }
                    movimientoDAO.update(movimientoEdit);
                } else {
                    movimientoEdit = new Movimiento();
                    movimientoEdit.setTipoMovimiento(
                            tipoMovimientoList.get(
                                    tipoMovimiento.getSelectedItemPosition() - 1));
                    movimientoEdit.setFecha(
                            FinanciaMeConfiguration.sdf.parse(fecha.getText().toString()));
                    movimientoEdit.setValor(
                            Integer.parseInt(
                                    valor.getText().toString().replace(".", "")));
                    movimientoEdit.setDescripcion(descripcion.getText().toString());
                    if (movimientoEdit.getTipoMovimiento().hasDeudor()) {
                        movimientoEdit.setDeudor(
                                deudorList.get(deudor.getSelectedItemPosition() - 1));
                    }
                    if (movimientoEdit.getTipoMovimiento().hasMedioPago()) {
                        movimientoEdit.setMedioPago(
                                medioPagoList.get(medioPago.getSelectedItemPosition() - 1));
                    }
                    movimientoDAO.insert(movimientoEdit);
                }
                if (movimientoEdit.getTipoMovimiento().hasDeudor()) {
                    Deudor deudor = deudorDAO.findById(movimientoEdit.getDeudor().getId());
                    Integer total_deudas = deudor.getTotalDeudas();
                    if (movimientoEdit.getTipoMovimiento().getAccion().equals(ACCION_SUMAR)) {
                        // ACCIÓN SUMAR: Suma al saldo propio pero debe restar al deudor
                        total_deudas -= movimientoEdit.getValor();
                    } else if (movimientoEdit.getTipoMovimiento().getAccion()
                            .equals(ACCION_RESTAR)) {
                        // ACCIÓN RESTAR: Resta al saldo propio pero debe sumar al deudor
                        total_deudas += movimientoEdit.getValor();
                    }
                    deudor.setTotalDeudas(total_deudas);
                    deudorDAO.update(deudor);
                }
            } catch (Exception e) {
                mostrarMensaje(e.getMessage());
            }
            mostrarMensaje(getString(R.string.guardadoExitoso));
            atras();
        }
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