package co.com.carlosrestrepo.financiame.persistence.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import co.com.carlosrestrepo.financiame.model.TipoMovimiento;
import co.com.carlosrestrepo.financiame.persistence.DriverSQLite;
import co.com.carlosrestrepo.financiame.persistence.PersistenceConfiguration;
import co.com.carlosrestrepo.financiame.persistence.exception.FinanciaMeException;

/**
 * Clase encargada del acceso a datos para la tabla tbl_tipo_movimiento
 *
 * @author  Carlos Restrepo
 * @created Septiembre 16 de 2015
 */
public class TipoMovimientoDAO extends DriverSQLite {

    public TipoMovimientoDAO(Context context) {
        super(context);
    }

    /**
     * Método que se encarga de insertar un nuevo Tipo de Movimiento
     * @param tipoMovimiento
     * @throws FinanciaMeException
     */
    public void insert(TipoMovimiento tipoMovimiento) throws FinanciaMeException {
        try {
            openToWrite();

            ContentValues cv = new ContentValues();
            cv.put("nombre", tipoMovimiento.getNombre());
            cv.put("requiere_deudor", tipoMovimiento.hasDeudor() ? 1 : 0);
            cv.put("requiere_medio_pago", tipoMovimiento.hasMedioPago() ? 1 : 0);
            cv.put("color", tipoMovimiento.getColor());
            cv.put("accion", tipoMovimiento.getAccion());

            sqLiteDatabase.insert(PersistenceConfiguration.TIPO_MOVIMIENTO_TABLE, null, cv);
        } catch (Exception e) {
            throw new FinanciaMeException("Ocurrió un error insertando el Tipo de Movimiento");
        } finally {
            close();
        }
    }

    /**
     * Método que se encarga de obtener un Tipo de Movimiento consultando por Id
     * @param id
     * @return TipoMovimiento
     * @throws FinanciaMeException
     */
    public TipoMovimiento findById(long id) throws FinanciaMeException {
        try {
            openToRead();

            TipoMovimiento tipoMovimiento = null;
            String[] campos = new String[]{
                    "id", "nombre", "requiere_deudor", "requiere_medio_pago", "color", "accion"
            };
            String filtro = "id=?";
            String[] valorFiltro = new String[] { String.valueOf(id) };

            Cursor cursor = sqLiteDatabase.query(PersistenceConfiguration.TIPO_MOVIMIENTO_TABLE,
                    campos, filtro, valorFiltro, null, null, null);

            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    tipoMovimiento = new TipoMovimiento();
                    tipoMovimiento.setId(cursor.getLong(0));
                    tipoMovimiento.setNombre(cursor.getString(1));
                    tipoMovimiento.setDeudor(cursor.getInt(2) == 1 ? true : false);
                    tipoMovimiento.setMedioPago(cursor.getInt(3) == 1 ? true : false);
                    tipoMovimiento.setColor(cursor.getString(4));
                    tipoMovimiento.setAccion(cursor.getString(5));
                }
            }
            return tipoMovimiento;
        } catch (Exception e) {
            throw new FinanciaMeException("Ocurrió un error consultando el Tipo de Movimiento (Id: "
                    + String.valueOf(id) + ")");
        } finally {
            close();
        }
    }

    /**
     * Método que se encarga de obtener todos los Tipos de Movimiento en una lista
     * @return List<TipoMovimiento>
     * @throws FinanciaMeException
     */
    public List<TipoMovimiento> getAll() throws FinanciaMeException {
        try {
            openToRead();

            List<TipoMovimiento> tipoMovimientoList = null;
            String[] campos = new String[]{
                    "id", "nombre", "requiere_deudor", "requiere_medio_pago", "color", "accion"
            };

            Cursor cursor = sqLiteDatabase.query(PersistenceConfiguration.TIPO_MOVIMIENTO_TABLE,
                    campos, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                tipoMovimientoList = new ArrayList<TipoMovimiento>();
                TipoMovimiento tipoMovimiento;
                do {
                    tipoMovimiento = new TipoMovimiento();
                    tipoMovimiento.setId(cursor.getLong(0));
                    tipoMovimiento.setNombre(cursor.getString(1));
                    tipoMovimiento.setDeudor(cursor.getInt(2) == 1 ? true : false);
                    tipoMovimiento.setMedioPago(cursor.getInt(3) == 1 ? true : false);
                    tipoMovimiento.setColor(cursor.getString(4));
                    tipoMovimiento.setAccion(cursor.getString(5));
                    tipoMovimientoList.add(tipoMovimiento);
                } while(cursor.moveToNext());
            }
            return tipoMovimientoList;
        } catch (Exception e) {
            throw new FinanciaMeException(
                    "Ocurrió un error consultando todos los Tipos de Movimiento");
        } finally {
            close();
        }
    }

    /**
     * Método que se encarga de actualizar un Tipo de Movimiento
     * @param tipoMovimiento
     * @throws FinanciaMeException
     */
    public void update(TipoMovimiento tipoMovimiento) throws FinanciaMeException {
        try {
            openToWrite();

            ContentValues cv = new ContentValues();
            cv.put("nombre", tipoMovimiento.getNombre());
            cv.put("requiere_deudor", tipoMovimiento.hasDeudor() ? 1 : 0);
            cv.put("requiere_medio_pago", tipoMovimiento.hasMedioPago() ? 1 : 0);
            cv.put("color", tipoMovimiento.getColor());
            cv.put("accion", tipoMovimiento.getAccion());

            String filtro = "id=?";
            String[] valorFiltro = new String[] { String.valueOf(tipoMovimiento.getId()) };

            sqLiteDatabase.update(PersistenceConfiguration.TIPO_MOVIMIENTO_TABLE, cv, filtro,
                    valorFiltro);
        } catch (Exception e) {
            throw new FinanciaMeException("Ocurrió un error actualizando el Tipo de Movimiento " +
                    "(Id: " + String.valueOf(tipoMovimiento.getId()) + ")");
        } finally {
            close();
        }
    }
}