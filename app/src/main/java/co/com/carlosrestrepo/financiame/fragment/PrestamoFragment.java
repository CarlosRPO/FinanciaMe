package co.com.carlosrestrepo.financiame.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.com.carlosrestrepo.financiame.R;
import co.com.carlosrestrepo.financiame.fragment.adapter.DeudorAdapter;
import co.com.carlosrestrepo.financiame.model.Deudor;
import co.com.carlosrestrepo.financiame.persistence.dao.DeudorDAO;
import co.com.carlosrestrepo.financiame.persistence.exception.FinanciaMeException;

/**
 *
 * @author  Carlos Restrepo
 * @created Septiembre 15 de 2015
 */
public class PrestamoFragment extends Fragment {

    private List<Deudor> prestamoList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_prestamos, container, false);
        cargarPrestamos(view);

        return view;
    }

    private void cargarPrestamos(View view) {
        DeudorDAO deudorDAO = new DeudorDAO(getContext());
        try {
            prestamoList = deudorDAO.getAllPrestamos();
            if (prestamoList == null) prestamoList = new ArrayList<>();

            DeudorAdapter adapter = new DeudorAdapter(getContext(),
                    prestamoList);

            ListView listView = (ListView) view.findViewById(R.id.listPrestamo);
            listView.setAdapter(adapter);
            listView.setEmptyView(view.findViewById(R.id.lblEmptyList));
        } catch (FinanciaMeException e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}