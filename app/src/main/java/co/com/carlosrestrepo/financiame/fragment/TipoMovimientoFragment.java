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
import co.com.carlosrestrepo.financiame.fragment.adapter.TipoMovimientoAdapter;
import co.com.carlosrestrepo.financiame.fragment.widget.FloatingActionButton;
import co.com.carlosrestrepo.financiame.model.TipoMovimiento;
import co.com.carlosrestrepo.financiame.persistence.dao.TipoMovimientoDAO;
import co.com.carlosrestrepo.financiame.persistence.exception.FinanciaMeException;

/**
 *
 * @author  Carlos Restrepo
 * @created Septiembre 16 de 2015
 */
public class TipoMovimientoFragment extends Fragment implements AdapterView.OnItemClickListener,
        FloatingActionButton.OnCheckedChangeListener {

    private List<TipoMovimiento> tipoMovimientoList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tipo_movimiento, container, false);
        cargarTiposMovimiento(view);

        FloatingActionButton actionNew = (FloatingActionButton) view.findViewById(R.id.action_new);
        actionNew.setOnCheckedChangeListener(this);
        return view;
    }

    @Override
    public void onCheckedChanged(FloatingActionButton fabView, boolean isChecked) {
        switch (fabView.getId()){
            case R.id.action_new:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, new InfoTipoMovimientoFragment())
                        .commit();
                break;
            default:
                break;
        }
    }

    private void cargarTiposMovimiento(View view) {
        TipoMovimientoDAO tmDAO = new TipoMovimientoDAO(getContext());
        try {
            tipoMovimientoList = tmDAO.getAll();
            if (tipoMovimientoList == null) tipoMovimientoList = new ArrayList<TipoMovimiento>();

            TipoMovimientoAdapter adapter = new TipoMovimientoAdapter(getContext(),
                    tipoMovimientoList);

            ListView listView = (ListView) view.findViewById(R.id.listTipoMovimiento);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
            listView.setEmptyView(view.findViewById(R.id.lblEmptyList));
        } catch (FinanciaMeException e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

        if (tipoMovimientoList.get(position) instanceof TipoMovimiento) {

            TipoMovimiento tipoMovimiento = (TipoMovimiento) tipoMovimientoList.get(position);

            Bundle bundle = new Bundle();
            bundle.putLong("id", tipoMovimiento.getId());
            bundle.putString("nombre", tipoMovimiento.getNombre());
            bundle.putBoolean("requiere_deudor", tipoMovimiento.hasDeudor());
            bundle.putBoolean("requiere_medio_pago", tipoMovimiento.hasMedioPago());
            bundle.putString("color", tipoMovimiento.getColor());
            bundle.putString("accion", tipoMovimiento.getAccion());

            InfoTipoMovimientoFragment fragment = new InfoTipoMovimientoFragment();
            fragment.setArguments(bundle);

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
        }
    }
}