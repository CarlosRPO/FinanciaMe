package co.com.carlosrestrepo.financiame.persistence.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import co.com.carlosrestrepo.financiame.model.MedioPago;
import co.com.carlosrestrepo.financiame.persistence.DriverSQLite;
import co.com.carlosrestrepo.financiame.persistence.PersistenceConfiguration;
import co.com.carlosrestrepo.financiame.persistence.exception.FinanciaMeException;

/**
 * Clase encargada del acceso a datos para la tabla tbl_medio_pago
 *
 * @author  Carlos Restrepo
 * @created Octubre 13 de 2015
 */
public class MedioPagoDAO extends DriverSQLite {

    public MedioPagoDAO(Context context) {
        super(context);
    }

    /**
     * Método que se encarga de insertar un nuevo Medio de Pago
     * @param medioPago
     * @throws FinanciaMeException
     */
    public void insert(MedioPago medioPago) throws FinanciaMeException {
        try {
            openToWrite();

            ContentValues cv = new ContentValues();
            cv.put("nombre", medioPago.getNombre());
            cv.put("interes", medioPago.getIntereses());

            sqLiteDatabase.insert(PersistenceConfiguration.MEDIO_PAGO_TABLE, null, cv);
        } catch (Exception e) {
            throw new FinanciaMeException("Ocurrió un error insertando el Medio de Pago");
        } finally {
            close();
        }
    }

    /**
     * Método que se encarga de obtener un Medio de Pago consultando por Id
     * @param id
     * @return MedioPago
     * @throws FinanciaMeException
     */
    public MedioPago findById(long id) throws FinanciaMeException {
        try {
            openToRead();

            MedioPago medioPago = null;
            String[] campos = new String[]{ "id", "nombre", "interes" };
            String filtro = "id=?";
            String[] valorFiltro = new String[] { String.valueOf(id) };

            Cursor cursor = sqLiteDatabase.query(PersistenceConfiguration.MEDIO_PAGO_TABLE,
                    campos, filtro, valorFiltro, null, null, null);

            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    medioPago = new MedioPago();
                    medioPago.setId(cursor.getLong(0));
                    medioPago.setNombre(cursor.getString(1));
                    medioPago.setIntereses(cursor.getFloat(2));
                }
            }
            return medioPago;
        } catch (Exception e) {
            throw new FinanciaMeException("Ocurrió un error consultando el Medio de Pago (Id: "
                    + String.valueOf(id) + ")");
        } finally {
            close();
        }
    }

    /**
     * Método que se encarga de obtener todos los Medios de Pago en una lista
     * @return List<MedioPago>
     * @throws FinanciaMeException
     */
    public List<MedioPago> getAll() throws FinanciaMeException {
        try {
            openToRead();

            List<MedioPago> medioPagoList = null;
            String[] campos = new String[]{ "id", "nombre", "interes" };

            Cursor cursor = sqLiteDatabase.query(PersistenceConfiguration.MEDIO_PAGO_TABLE,
                    campos, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                medioPagoList = new ArrayList<MedioPago>();
                MedioPago medioPago;
                do {
                    medioPago = new MedioPago();
                    medioPago.setId(cursor.getLong(0));
                    medioPago.setNombre(cursor.getString(1));
                    medioPago.setIntereses(cursor.getFloat(2));
                    medioPagoList.add(medioPago);
                } while(cursor.moveToNext());
            }
            return medioPagoList;
        } catch (Exception e) {
            throw new FinanciaMeException(
                    "Ocurrió un error consultando todos los Medios de Pago");
        } finally {
            close();
        }
    }

    /**
     * Método que se encarga de actualizar un Medio de Pago
     * @param medioPago
     * @throws FinanciaMeException
     */
    public void update(MedioPago medioPago) throws FinanciaMeException {
        try {
            openToWrite();

            ContentValues cv = new ContentValues();
            cv.put("nombre", medioPago.getNombre());
            cv.put("interes", medioPago.getIntereses());

            String filtro = "id=?";
            String[] valorFiltro = new String[] { String.valueOf(medioPago.getId()) };

            sqLiteDatabase.update(PersistenceConfiguration.MEDIO_PAGO_TABLE, cv, filtro,
                    valorFiltro);
        } catch (Exception e) {
            throw new FinanciaMeException("Ocurrió un error actualizando el Medio de Pago " +
                    "(Id: " + String.valueOf(medioPago.getId()) + ")");
        } finally {
            close();
        }
    }
}