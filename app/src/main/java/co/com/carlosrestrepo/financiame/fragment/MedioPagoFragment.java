package co.com.carlosrestrepo.financiame.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.com.carlosrestrepo.financiame.R;
import co.com.carlosrestrepo.financiame.fragment.adapter.MedioPagoAdapter;
import co.com.carlosrestrepo.financiame.fragment.widget.FloatingActionButton;
import co.com.carlosrestrepo.financiame.model.MedioPago;
import co.com.carlosrestrepo.financiame.persistence.dao.MedioPagoDAO;
import co.com.carlosrestrepo.financiame.persistence.exception.FinanciaMeException;

/**
 *
 * @author  Carlos Restrepo
 * @created Octubre 13 de 2015
 */
public class MedioPagoFragment extends Fragment implements AdapterView.OnItemClickListener,
        FloatingActionButton.OnCheckedChangeListener {

    private List<MedioPago> medioPagoList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_medio_pago, container, false);
        cargarMediosPago(view);

        FloatingActionButton actionNew = (FloatingActionButton) view.findViewById(R.id.action_new);
        actionNew.setOnCheckedChangeListener(this);
        return view;
    }

    @Override
    public void onCheckedChanged(FloatingActionButton fabView, boolean isChecked) {
        switch (fabView.getId()){
            case R.id.action_new:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, new InfoMedioPagoFragment())
                        .commit();
                break;
            default:
                break;
        }
    }

    private void cargarMediosPago(View view) {
        MedioPagoDAO medioPagoDAO = new MedioPagoDAO(getContext());
        try {
            medioPagoList = medioPagoDAO.getAll();
            if (medioPagoList == null) medioPagoList = new ArrayList<MedioPago>();

            MedioPagoAdapter adapter = new MedioPagoAdapter(getContext(),
                    medioPagoList);

            ListView listView = (ListView) view.findViewById(R.id.listMedioPago);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
            listView.setEmptyView(view.findViewById(R.id.lblEmptyList));
        } catch (FinanciaMeException e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

        if (medioPagoList.get(position) instanceof MedioPago) {

            MedioPago medioPago = (MedioPago) medioPagoList.get(position);

            Bundle bundle = new Bundle();
            bundle.putLong("id", medioPago.getId());
            bundle.putString("nombre", medioPago.getNombre());
            bundle.putFloat("interes", medioPago.getIntereses());

            InfoMedioPagoFragment fragment = new InfoMedioPagoFragment();
            fragment.setArguments(bundle);

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
        }
    }
}