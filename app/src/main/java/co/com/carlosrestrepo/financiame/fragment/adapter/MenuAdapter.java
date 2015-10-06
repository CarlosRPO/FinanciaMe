package co.com.carlosrestrepo.financiame.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import co.com.carlosrestrepo.financiame.R;
import co.com.carlosrestrepo.financiame.model.OpcionMenu;

/**
 * Clase que se encarga de adaptar y mostrar las opciones de menú
 * @author  Carlos Restrepo
 * @created Octubre 05 de 2015
 */
public class MenuAdapter extends ArrayAdapter<OpcionMenu> {

    private Context context;
    private List<OpcionMenu> opciones;

    public MenuAdapter(Context context, List<OpcionMenu> objects) {
        super(context, R.layout.row_opcion_menu, objects);
        this.context = context;
        this.opciones = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        OpcionMenu opcionMenu = opciones.get(position);

        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.row_opcion_menu, parent, false);

        ImageView icono = (ImageView) row.findViewById(R.id.imgIcono);
        icono.setBackgroundResource(opcionMenu.getIcono());

        TextView titulo = (TextView) row.findViewById(R.id.lblTitulo);
        titulo.setText(context.getString(opcionMenu.getTitulo()));

        return row;
    }
}