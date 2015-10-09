package co.com.carlosrestrepo.financiame.fragment.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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

    public MovimientoAdapter(Context context, List<Movimiento> objects) {
        super(context, R.layout.row_movimiento, objects);
        this.context = context;
        this.movimientoList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Movimiento movimiento = movimientoList.get(position);

        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.row_movimiento, parent, false);

        ImageView icon = (ImageView) row.findViewById(R.id.imgIcono);
        icon.setBackgroundColor(Integer.parseInt(movimiento.getTipoMovimiento().getColor()));
        setBackground(icon);

        TextView descripcion = (TextView) row.findViewById(R.id.lblDescripcion);
        descripcion.setText(movimiento.getDescripcion());

        TextView valor = (TextView) row.findViewById(R.id.lblValor);
        valor.setText(String.valueOf(movimiento.getValor().intValue()));

        TextView fecha = (TextView) row.findViewById(R.id.lblFecha);
        fecha.setText(sdf.format(movimiento.getFecha()));

        return row;
    }

    public void setBackground(ImageView imageView) {
        //imageView.buildDrawingCache();
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        BitmapShader shader = new BitmapShader(bitmap,  Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setShader(shader);

        Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(circleBitmap);
        c.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);

        imageView.setImageBitmap(circleBitmap);
    }
}