package co.com.carlosrestrepo.financiame.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import co.com.carlosrestrepo.financiame.R;
import co.com.carlosrestrepo.financiame.model.TipoMovimiento;
import co.com.carlosrestrepo.financiame.persistence.dao.TipoMovimientoDAO;

/**
 *
 * @author  Carlos Restrepo
 * @created Septiembre 16 de 2015
 */
public class InfoTipoMovimientoFragment extends Fragment {

    private EditText nombre;
    private CheckBox requiereDeudor;

    private TipoMovimiento tipoMovimientoEdit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info_tipo_movimiento, container, false);

        nombre = (EditText) view.findViewById(R.id.nombreTipoMovimiento);
        requiereDeudor = (CheckBox) view.findViewById(R.id.requiereDeudor);

        Bundle bundle = getArguments();
        if (bundle != null) {
            tipoMovimientoEdit = new TipoMovimiento();
            tipoMovimientoEdit.setId(bundle.getLong("id"));
            tipoMovimientoEdit.setNombre(bundle.getString("nombre"));
            tipoMovimientoEdit.setDeudor(bundle.getBoolean("requiere_deudor"));
            cargarTipoMovimiento();
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
                    TipoMovimientoDAO tmDAO = new TipoMovimientoDAO(getContext());
                    try {
                        if (tipoMovimientoEdit != null) {
                            tipoMovimientoEdit.setNombre(nombre.getText().toString());
                            tipoMovimientoEdit.setDeudor(requiereDeudor.isChecked());
                            tmDAO.update(tipoMovimientoEdit);
                        } else {
                            tipoMovimientoEdit = new TipoMovimiento();
                            tipoMovimientoEdit.setNombre(nombre.getText().toString());
                            tipoMovimientoEdit.setDeudor(requiereDeudor.isChecked());
                            tmDAO.insert(tipoMovimientoEdit);
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

    private void cargarTipoMovimiento() {
        nombre.setText(tipoMovimientoEdit.getNombre());
        requiereDeudor.setChecked(tipoMovimientoEdit.hasDeudor());
    }

    private boolean validarInfo() {
        if (nombre.getText() == null || nombre.getText().toString().isEmpty()) {
            mostrarMensaje(getString(R.string.infoTipoMovimiento));
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
                .replace(R.id.content_frame, new TipoMovimientoFragment())
                .commit();
    }
}