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
import co.com.carlosrestrepo.financiame.fragment.adapter.MovimientoAdapter;
import co.com.carlosrestrepo.financiame.fragment.widget.FloatingActionButton;
import co.com.carlosrestrepo.financiame.model.Movimiento;
import co.com.carlosrestrepo.financiame.persistence.dao.MovimientoDAO;
import co.com.carlosrestrepo.financiame.persistence.dao.TipoMovimientoDAO;
import co.com.carlosrestrepo.financiame.persistence.exception.FinanciaMeException;
import co.com.carlosrestrepo.financiame.util.FinanciaMeConfiguration;

/**
 *
 * @author  Carlos Restrepo
 * @created Septiembre 15 de 2015
 */
public class MovimientoFragment extends Fragment implements AdapterView.OnItemClickListener,
        FloatingActionButton.OnCheckedChangeListener {

    private List<Movimiento> movimientoList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movimientos, container, false);
        cargarMovimientos(view);

        FloatingActionButton actionNew = (FloatingActionButton) view.findViewById(R.id.action_new);
        actionNew.setOnCheckedChangeListener(this);
        return view;
    }

    @Override
    public void onCheckedChanged(FloatingActionButton fabView, boolean isChecked) {
        switch (fabView.getId()){
            case R.id.action_new:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, new InfoMovimientoFragment())
                        .commit();
                break;
            default:
                break;
        }
    }

    private void cargarMovimientos(View view) {
        MovimientoDAO movimientoDAO = new MovimientoDAO(getContext());
        TipoMovimientoDAO tmDAO = new TipoMovimientoDAO(getContext());
        try {
            movimientoList = movimientoDAO.getAll();

            if (movimientoList != null && !movimientoList.isEmpty()) {
                for (Movimiento movimiento : movimientoList) {
                    movimiento.setTipoMovimiento(tmDAO.findById(movimiento.getTipoMovimiento().getId()));
                }
            } else if (movimientoList == null) movimientoList = new ArrayList<Movimiento>();

            MovimientoAdapter adapter = new MovimientoAdapter(getContext(),
                    movimientoList);

            ListView listView = (ListView) view.findViewById(R.id.listMovimiento);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
            listView.setEmptyView(view.findViewById(R.id.lblEmptyList));
        } catch (FinanciaMeException e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

        if (movimientoList.get(position) instanceof Movimiento) {

            Movimiento movimiento = (Movimiento) movimientoList.get(position);

            Bundle bundle = new Bundle();
            bundle.putLong("id", movimiento.getId());
            bundle.putLong("id_tipo_movimiento", movimiento.getTipoMovimiento().getId());
            bundle.putString("fecha", FinanciaMeConfiguration.sdf.format(movimiento.getFecha()));
            bundle.putInt("valor", movimiento.getValor().intValue());
            bundle.putString("descripcion", movimiento.getDescripcion());
            if (movimiento.getDeudor() != null)
                bundle.putLong("id_deudor", movimiento.getDeudor().getId());
            if (movimiento.getMedioPago() != null)
                bundle.putLong("id_medio_pago", movimiento.getMedioPago().getId());

            InfoMovimientoFragment fragment = new InfoMovimientoFragment();
            fragment.setArguments(bundle);

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
        }
    }
}