package co.com.carlosrestrepo.financiame.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import co.com.carlosrestrepo.financiame.R;
import co.com.carlosrestrepo.financiame.model.MedioPago;

/**
 * Clase que se encarga de adaptar y mostrar la lista de Medios de Pago
 * @author  Carlos Restrepo
 * @created Octubre 13 de 2015
 */
public class MedioPagoAdapter extends ArrayAdapter<MedioPago> {

    private Context context;
    private List<MedioPago> medioPagoList;

    public MedioPagoAdapter(Context context, List<MedioPago> objects) {
        super(context, R.layout.row_medio_pago, objects);
        this.context = context;
        this.medioPagoList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MedioPago medioPago = medioPagoList.get(position);

        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.row_medio_pago, parent, false);

        TextView nombre = (TextView) row.findViewById(R.id.lblNombre);
        nombre.setText(medioPago.getNombre());

        return row;
    }
}