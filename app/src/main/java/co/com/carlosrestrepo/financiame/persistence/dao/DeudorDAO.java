package co.com.carlosrestrepo.financiame.persistence.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import co.com.carlosrestrepo.financiame.model.Deudor;
import co.com.carlosrestrepo.financiame.persistence.DriverSQLite;
import co.com.carlosrestrepo.financiame.persistence.PersistenceConfiguration;
import co.com.carlosrestrepo.financiame.persistence.exception.FinanciaMeException;

/**
 * Clase encargada del acceso a datos para la tabla tbl_deudor
 *
 * @author  Carlos Restrepo
 * @created Septiembre 16 de 2015
 */
public class DeudorDAO extends DriverSQLite {

    public DeudorDAO(Context context) {
        super(context);
    }

    /**
     * Método que se encarga de insertar un nuevo Deudor
     * @param deudor
     * @throws FinanciaMeException
     */
    public void insert(Deudor deudor) throws FinanciaMeException {
        try {
            openToWrite();

            ContentValues cv = new ContentValues();
            cv.put("nombre", deudor.getNombre());
            cv.put("telefono", deudor.getTelefono());
            cv.put("total_deudas", deudor.getTotalDeudas().intValue());

            sqLiteDatabase.insert(PersistenceConfiguration.DEUDOR_TABLE, null, cv);
        } catch (Exception e) {
            throw new FinanciaMeException("Ocurrió un error insertando el Deudor");
        } finally {
            close();
        }
    }

    /**
     * Método que se encarga de obtener un Deudor consultando por Id
     * @param id
     * @return Deudor
     * @throws FinanciaMeException
     */
    public Deudor findById(long id) throws FinanciaMeException {
        Cursor cursor = null;
        try {
            openToRead();

            Deudor deudor = null;
            String[] campos = new String[]{ "id", "nombre", "telefono", "total_deudas" };
            String filtro = "id=?";
            String[] valorFiltro = new String[] { String.valueOf(id) };

            cursor = sqLiteDatabase.query(PersistenceConfiguration.DEUDOR_TABLE,
                    campos, filtro, valorFiltro, null, null, null);

            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    deudor = new Deudor();
                    deudor.setId(cursor.getLong(0));
                    deudor.setNombre(cursor.getString(1));
                    deudor.setTelefono(cursor.getString(2));
                    deudor.setTotalDeudas(Integer.valueOf(cursor.getInt(3)));
                }
            }
            return deudor;
        } catch (Exception e) {
            throw new FinanciaMeException("Ocurrió un error consultando el Deudor (Id: "
                    + String.valueOf(id) + ")");
        } finally {
            if (cursor != null && !cursor.isClosed()) cursor.close();
            close();
        }
    }

    /**
     * Método que se encarga de obtener todos los Deudores en una lista
     * @return List<Deudor>
     * @throws FinanciaMeException
     */
    public List<Deudor> getAll() throws FinanciaMeException {
        Cursor cursor = null;
        try {
            openToRead();

            List<Deudor> deudorList = null;
            String[] campos = new String[]{ "id", "nombre", "telefono", "total_deudas" };

            cursor = sqLiteDatabase.query(PersistenceConfiguration.DEUDOR_TABLE,
                    campos, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                deudorList = new ArrayList<Deudor>();
                Deudor deudor;
                do {
                    deudor = new Deudor();
                    deudor.setId(cursor.getLong(0));
                    deudor.setNombre(cursor.getString(1));
                    deudor.setTelefono(cursor.getString(2));
                    deudor.setTotalDeudas(Integer.valueOf(cursor.getInt(3)));
                    deudorList.add(deudor);
                } while(cursor.moveToNext());
            }
            return deudorList;
        } catch (Exception e) {
            throw new FinanciaMeException(
                    "Ocurrió un error consultando todos los Deudores");
        } finally {
            if (cursor != null && !cursor.isClosed()) cursor.close();
            close();
        }
    }

    /**
     * Método que se encarga de obtener todos los Deudores que deben actualmente
     * @return List<Deudor>
     * @throws FinanciaMeException
     */
    public List<Deudor> getAllPrestamos() throws FinanciaMeException {
        Cursor cursor = null;
        try {
            openToRead();

            List<Deudor> deudorList = null;
            String[] campos = new String[]{ "id", "nombre", "telefono", "total_deudas" };
            String filtro = "total_deudas>?";
            String[] valorFiltro = new String[]{ "0" };
            String orderBy = "total_deudas desc";

            cursor = sqLiteDatabase.query(PersistenceConfiguration.DEUDOR_TABLE,
                    campos, filtro, valorFiltro, null, null, orderBy);

            if (cursor.moveToFirst()) {
                deudorList = new ArrayList<Deudor>();
                Deudor deudor;
                do {
                    deudor = new Deudor();
                    deudor.setId(cursor.getLong(0));
                    deudor.setNombre(cursor.getString(1));
                    deudor.setTelefono(cursor.getString(2));
                    deudor.setTotalDeudas(Integer.valueOf(cursor.getInt(3)));
                    deudorList.add(deudor);
                } while(cursor.moveToNext());
            }
            return deudorList;
        } catch (Exception e) {
            throw new FinanciaMeException(
                    "Ocurrió un error consultando todos los Préstamos de los Deudores");
        } finally {
            if (cursor != null && !cursor.isClosed()) cursor.close();
            close();
        }
    }

    /**
     * Método que se encarga de actualizar un Deudor
     * @param deudor
     * @throws FinanciaMeException
     */
    public void update(Deudor deudor) throws FinanciaMeException {
        try {
            openToWrite();

            ContentValues cv = new ContentValues();
            cv.put("nombre", deudor.getNombre());
            cv.put("telefono", deudor.getTelefono());
            cv.put("total_deudas", deudor.getTotalDeudas().intValue());

            String filtro = "id=?";
            String[] valorFiltro = new String[] { String.valueOf(deudor.getId()) };

            sqLiteDatabase.update(PersistenceConfiguration.DEUDOR_TABLE, cv, filtro,
                    valorFiltro);
        } catch (Exception e) {
            throw new FinanciaMeException("Ocurrió un error actualizando el Deudor " +
                    "(Id: " + String.valueOf(deudor.getId()) + ")");
        } finally {
            close();
        }
    }
}