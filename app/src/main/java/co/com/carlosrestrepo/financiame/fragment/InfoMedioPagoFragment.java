package co.com.carlosrestrepo.financiame.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import co.com.carlosrestrepo.financiame.R;
import co.com.carlosrestrepo.financiame.model.MedioPago;
import co.com.carlosrestrepo.financiame.persistence.dao.MedioPagoDAO;

/**
 *
 * @author  Carlos Restrepo
 * @created Octubre 13 de 2015
 */
public class InfoMedioPagoFragment extends Fragment {

    private EditText nombre;
    private EditText interes;

    private MedioPago medioPagoEdit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info_medio_pago, container, false);

        nombre = (EditText) view.findViewById(R.id.nombreMedioPago);
        interes = (EditText) view.findViewById(R.id.interesMedioPago);

        Bundle bundle = getArguments();
        if (bundle != null) {
            medioPagoEdit = new MedioPago();
            medioPagoEdit.setId(bundle.getLong("id"));
            medioPagoEdit.setNombre(bundle.getString("nombre"));
            medioPagoEdit.setIntereses(bundle.getFloat("interes"));
            cargarMedioPago();
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
                guardarMedioPago();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void cargarMedioPago() {
        nombre.setText(medioPagoEdit.getNombre());
        if (medioPagoEdit.getIntereses() != null) {
            interes.setText(String.valueOf(medioPagoEdit.getIntereses()));
        }
    }

    private boolean validarInfo() {
        if (nombre.getText() == null || nombre.getText().toString().isEmpty()) {
            mostrarMensaje(getString(R.string.infoMedioPago));
            return false;
        }
        return true;
    }

    private void guardarMedioPago() {
        if (validarInfo()) {
            MedioPagoDAO medioPagoDAO = new MedioPagoDAO(getContext());
            try {
                if (medioPagoEdit != null) {
                    medioPagoEdit.setNombre(nombre.getText().toString());
                    if (interes.getText() != null &&
                            !interes.getText().toString().isEmpty()) {
                        medioPagoEdit.setIntereses(
                                Float.parseFloat(interes.getText().toString()));
                    }
                    medioPagoDAO.update(medioPagoEdit);
                } else {
                    medioPagoEdit = new MedioPago();
                    medioPagoEdit.setNombre(nombre.getText().toString());
                    if (interes.getText() != null &&
                            !interes.getText().toString().isEmpty()) {
                        medioPagoEdit.setIntereses(
                                Float.parseFloat(interes.getText().toString()));
                    }
                    medioPagoDAO.insert(medioPagoEdit);
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
                .replace(R.id.content_frame, new MedioPagoFragment())
                .commit();
    }
}