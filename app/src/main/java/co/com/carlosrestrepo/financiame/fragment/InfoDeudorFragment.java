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
import android.widget.TextView;
import android.widget.Toast;

import co.com.carlosrestrepo.financiame.R;
import co.com.carlosrestrepo.financiame.model.Deudor;
import co.com.carlosrestrepo.financiame.persistence.dao.DeudorDAO;

/**
 *
 * @author  Carlos Restrepo
 * @created Septiembre 15 de 2015
 */
public class InfoDeudorFragment extends Fragment {

    private EditText nombre;
    private EditText telefono;
    private TextView totalDeudas;

    private Deudor deudorEdit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info_deudor, container, false);

        nombre = (EditText) view.findViewById(R.id.nombreDeudor);
        telefono = (EditText) view.findViewById(R.id.telefonoDeudor);
        totalDeudas = (TextView) view.findViewById(R.id.totalDeudas);

        Bundle bundle = getArguments();
        if (bundle != null) {
            deudorEdit = new Deudor();
            deudorEdit.setId(bundle.getLong("id"));
            deudorEdit.setNombre(bundle.getString("nombre"));
            deudorEdit.setTelefono(bundle.getString("telefono"));
            deudorEdit.setTotalDeudas(Integer.valueOf(bundle.getInt("total_deudas")));
            cargarDeudor();
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
                    DeudorDAO deudorDAO = new DeudorDAO(getContext());
                    try {
                        if (deudorEdit != null) {
                            deudorEdit.setNombre(nombre.getText().toString());
                            deudorEdit.setTelefono(telefono.getText().toString());
                            deudorDAO.update(deudorEdit);
                        } else {
                            deudorEdit = new Deudor();
                            deudorEdit.setNombre(nombre.getText().toString());
                            deudorEdit.setTelefono(telefono.getText().toString());
                            deudorDAO.insert(deudorEdit);
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

    private void cargarDeudor() {
        nombre.setText(deudorEdit.getNombre());
        telefono.setText(deudorEdit.getTelefono());
        totalDeudas.setText(getString(R.string.totalDeudas) + " " +
                String.valueOf(deudorEdit.getTotalDeudas().intValue()));
    }

    private boolean validarInfo() {
        if (nombre.getText() == null || nombre.getText().toString().isEmpty()) {
            mostrarMensaje(getString(R.string.infoDeudorNombre));
            return false;
        } else if (telefono.getText() == null || telefono.getText().toString().isEmpty()) {
            mostrarMensaje(getString(R.string.infoDeudorTelefono));
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
                .replace(R.id.content_frame, new DeudorFragment())
                .commit();
    }
}