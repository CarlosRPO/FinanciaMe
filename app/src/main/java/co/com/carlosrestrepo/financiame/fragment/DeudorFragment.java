package co.com.carlosrestrepo.financiame.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.com.carlosrestrepo.financiame.R;
import co.com.carlosrestrepo.financiame.fragment.adapter.DeudorAdapter;
import co.com.carlosrestrepo.financiame.fragment.widget.FloatingActionButton;
import co.com.carlosrestrepo.financiame.model.Deudor;
import co.com.carlosrestrepo.financiame.persistence.dao.DeudorDAO;
import co.com.carlosrestrepo.financiame.persistence.exception.FinanciaMeException;

/**
 *
 * @author  Carlos Restrepo
 * @created Septiembre 15 de 2015
 */
public class DeudorFragment extends Fragment implements AdapterView.OnItemClickListener,
        FloatingActionButton.OnCheckedChangeListener {

    private List<Deudor> deudorList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_deudores, container, false);
        cargarDeudores(view);

        FloatingActionButton actionNew = (FloatingActionButton) view.findViewById(R.id.action_new);
        actionNew.setOnCheckedChangeListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        atras();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onCheckedChanged(FloatingActionButton fabView, boolean isChecked) {
        switch (fabView.getId()){
            case R.id.action_new:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, new InfoDeudorFragment())
                        .commit();
                break;
            default:
                break;
        }
    }

    private void cargarDeudores(View view) {
        DeudorDAO deudorDAO = new DeudorDAO(getContext());
        try {
            deudorList = deudorDAO.getAll();
            if (deudorList == null) deudorList = new ArrayList<>();

            DeudorAdapter adapter = new DeudorAdapter(getContext(),
                    deudorList);

            ListView listView = (ListView) view.findViewById(R.id.listDeudor);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
            listView.setEmptyView(view.findViewById(R.id.lblEmptyList));
        } catch (FinanciaMeException e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

        if (deudorList.get(position) instanceof Deudor) {

            Deudor deudor = (Deudor) deudorList.get(position);

            Bundle bundle = new Bundle();
            bundle.putLong("id", deudor.getId());
            bundle.putString("nombre", deudor.getNombre());
            bundle.putString("telefono", deudor.getTelefono());
            bundle.putInt("total_deudas", deudor.getTotalDeudas().intValue());

            InfoDeudorFragment fragment = new InfoDeudorFragment();
            fragment.setArguments(bundle);

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
        }
    }

    private void atras() {
        onDestroy();
        onDestroyView();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new HomeFragment())
                .commit();
    }
}