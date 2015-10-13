package co.com.carlosrestrepo.financiame.fragment.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import co.com.carlosrestrepo.financiame.R;
import co.com.carlosrestrepo.financiame.model.TipoMovimiento;

/**
 * Clase que se encarga de adaptar y mostrar la lista de Tipos de Movimientos
 * @author  Carlos Restrepo
 * @created Septiembre 16 de 2015
 */
public class TipoMovimientoAdapter extends ArrayAdapter<TipoMovimiento> {

    private Context context;
    private List<TipoMovimiento> tipoMovimientoList;

    public TipoMovimientoAdapter(Context context, List<TipoMovimiento> objects) {
        super(context, R.layout.row_tipo_movimiento, objects);
        this.context = context;
        this.tipoMovimientoList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TipoMovimiento tipoMovimiento = tipoMovimientoList.get(position);

        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.row_tipo_movimiento, parent, false);

        ImageView icon = (ImageView) row.findViewById(R.id.imgIcono);
        icon.getDrawable().setColorFilter(
                Integer.parseInt(tipoMovimiento.getColor()),
                PorterDuff.Mode.MULTIPLY);

        TextView nombre = (TextView) row.findViewById(R.id.lblNombre);
        nombre.setText(tipoMovimiento.getNombre());

        return row;
    }
}