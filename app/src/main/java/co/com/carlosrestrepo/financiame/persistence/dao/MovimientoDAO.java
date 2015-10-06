package co.com.carlosrestrepo.financiame.persistence.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import co.com.carlosrestrepo.financiame.R;
import co.com.carlosrestrepo.financiame.model.Deudor;
import co.com.carlosrestrepo.financiame.model.Movimiento;
import co.com.carlosrestrepo.financiame.model.TipoMovimiento;
import co.com.carlosrestrepo.financiame.persistence.DriverSQLite;
import co.com.carlosrestrepo.financiame.persistence.PersistenceConfiguration;
import co.com.carlosrestrepo.financiame.persistence.exception.FinanciaMeException;

/**
 * Clase encargada del acceso a datos para la tabla tbl_movimiento
 *
 * @author  Carlos Restrepo
 * @created Septiembre 16 de 2015
 */
public class MovimientoDAO extends DriverSQLite {

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public MovimientoDAO(Context context) {
        super(context);
    }

    /**
     * Método que se encarga de insertar un nuevo Movimiento
     * @param movimiento
     * @throws FinanciaMeException
     */
    public void insert(Movimiento movimiento) throws FinanciaMeException {
        try {
            openToWrite();

            ContentValues cv = new ContentValues();
            cv.put("id_tipo_movimiento", movimiento.getTipoMovimiento().getId());
            cv.put("fecha", sdf.format(movimiento.getFecha()));
            cv.put("valor", movimiento.getValor().intValue());
            cv.put("descripcion", movimiento.getDescripcion());
            if (movimiento.getDeudor() != null)
                cv.put("id_deudor", movimiento.getDeudor().getId());

            sqLiteDatabase.insert(PersistenceConfiguration.MOVIMIENTO_TABLE, null, cv);
        } catch (Exception e) {
            throw new FinanciaMeException("Ocurrió un error insertando el Movimiento");
        } finally {
            close();
        }
    }

    /**
     * Método que se encarga de obtener un Movimiento consultando por Id
     * @param id
     * @return Movimiento
     * @throws FinanciaMeException
     */
    public Movimiento findById(long id) throws FinanciaMeException {
        try {
            openToRead();

            Movimiento movimiento = null;
            String[] campos = new String[]{ "id", "id_tipo_movimiento", "fecha", "valor",
                    "descripcion", "id_deudor" };
            String filtro = "id=?";
            String[] valorFiltro = new String[] { String.valueOf(id) };

            Cursor cursor = sqLiteDatabase.query(PersistenceConfiguration.MOVIMIENTO_TABLE,
                    campos, filtro, valorFiltro, null, null, null);

            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    movimiento = new Movimiento();
                    movimiento.setId(cursor.getLong(0));
                    movimiento.setTipoMovimiento(new TipoMovimiento(cursor.getLong(1)));
                    movimiento.setFecha(sdf.parse(cursor.getString(2)));
                    movimiento.setValor(Integer.valueOf(cursor.getInt(3)));
                    movimiento.setDescripcion(cursor.getString(4));
                    movimiento.setDeudor(new Deudor(cursor.getLong(5)));
                }
            }
            return movimiento;
        } catch (Exception e) {
            throw new FinanciaMeException("Ocurrió un error consultando el Movimiento (Id: "
                    + String.valueOf(id) + ")");
        } finally {
            close();
        }
    }

    /**
     * Método que se encarga de obtener todos los Movimientos en una lista
     * @return List<Movimiento>
     * @throws FinanciaMeException
     */
    public List<Movimiento> getAll() throws FinanciaMeException {
        try {
            openToRead();

            List<Movimiento> movimientoList = null;
            String[] campos = new String[]{ "id", "id_tipo_movimiento", "fecha", "valor",
                    "descripcion", "id_deudor" };

            Cursor cursor = sqLiteDatabase.query(PersistenceConfiguration.MOVIMIENTO_TABLE,
                    campos, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                movimientoList = new ArrayList<Movimiento>();
                Movimiento movimiento;
                do {
                    movimiento = new Movimiento();
                    movimiento.setId(cursor.getLong(0));
                    movimiento.setTipoMovimiento(new TipoMovimiento(cursor.getLong(1)));
                    movimiento.setFecha(sdf.parse(cursor.getString(2)));
                    movimiento.setValor(Integer.valueOf(cursor.getInt(3)));
                    movimiento.setDescripcion(cursor.getString(4));
                    movimiento.setDeudor(new Deudor(cursor.getLong(5)));
                    movimientoList.add(movimiento);
                } while(cursor.moveToNext());
            }
            return movimientoList;
        } catch (Exception e) {
            throw new FinanciaMeException(
                    "Ocurrió un error consultando todos los Movimientos");
        } finally {
            close();
        }
    }

    /**
     * Método que se encarga de actualizar un Movimiento
     * @param movimiento
     * @throws FinanciaMeException
     */
    public void update(Movimiento movimiento) throws FinanciaMeException {
        try {
            openToWrite();

            ContentValues cv = new ContentValues();
            cv.put("id_tipo_movimiento", movimiento.getTipoMovimiento().getId());
            cv.put("fecha", sdf.format(movimiento.getFecha()));
            cv.put("valor", movimiento.getValor().intValue());
            cv.put("descripcion", movimiento.getDescripcion());
            if (movimiento.getDeudor() != null)
                cv.put("id_deudor", movimiento.getDeudor().getId());

            String filtro = "id=?";
            String[] valorFiltro = new String[] { String.valueOf(movimiento.getId()) };

            sqLiteDatabase.update(PersistenceConfiguration.MOVIMIENTO_TABLE, cv, filtro,
                    valorFiltro);
        } catch (Exception e) {
            throw new FinanciaMeException("Ocurrió un error actualizando el Movimiento " +
                    "(Id: " + String.valueOf(movimiento.getId()) + ")");
        } finally {
            close();
        }
    }
}