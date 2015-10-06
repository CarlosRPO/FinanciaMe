package co.com.carlosrestrepo.financiame.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import co.com.carlosrestrepo.financiame.R;
import co.com.carlosrestrepo.financiame.model.Deudor;

/**
 * Clase que se encarga de adaptar y mostrar la lista de Deudores
 * @author  Carlos Restrepo
 * @created Septiembre 17 de 2015
 */
public class DeudorAdapter extends ArrayAdapter<Deudor> {

    private Context context;
    private List<Deudor> deudorList;

    public DeudorAdapter(Context context, List<Deudor> objects) {
        super(context, R.layout.row_deudor, objects);
        this.context = context;
        this.deudorList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Deudor deudor = deudorList.get(position);

        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.row_deudor, parent, false);

        TextView nombre = (TextView) row.findViewById(R.id.lblNombre);
        nombre.setText(deudor.getNombre());

        TextView telefono = (TextView) row.findViewById(R.id.lblTelefono);
        telefono.setText(deudor.getTelefono());

        TextView totalDeudas = (TextView) row.findViewById(R.id.lblTotalDeudas);
        totalDeudas.setText(context.getString(R.string.totalDeudas) + " " +
                String.valueOf(deudor.getTotalDeudas().intValue()));

        return row;
    }
}