package co.com.carlosrestrepo.financiame.fragment.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import co.com.carlosrestrepo.financiame.R;
import co.com.carlosrestrepo.financiame.model.Movimiento;

/**
 * Clase que se encarga de adaptar y mostrar la lista de Movimientos
 * @author  Carlos Restrepo
 * @created Septiembre 17 de 2015
 */
public class MovimientoAdapter extends ArrayAdapter<Movimiento> {

    private Context context;
    private List<Movimiento> movimientoList;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private DecimalFormat df = new DecimalFormat("#,###.##");

    public MovimientoAdapter(Context context, List<Movimiento> objects) {
        super(context, R.layout.row_movimiento, objects);
        this.context = context;
        this.movimientoList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Movimiento movimiento = movimientoList.get(position);

        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.row_movimiento, parent, false);

        ImageView icon = (ImageView) row.findViewById(R.id.imgIcono);
        icon.getDrawable().setColorFilter(
                Integer.parseInt(movimiento.getTipoMovimiento().getColor()),
                PorterDuff.Mode.MULTIPLY);

        TextView descripcion = (TextView) row.findViewById(R.id.lblDescripcion);
        descripcion.setText(movimiento.getDescripcion());

        TextView valor = (TextView) row.findViewById(R.id.lblValor);
        valor.setText("$" + df.format((long) movimiento.getValor().intValue()));

        TextView fecha = (TextView) row.findViewById(R.id.lblFecha);
        fecha.setText(sdf.format(movimiento.getFecha()));

        return row;
    }
}