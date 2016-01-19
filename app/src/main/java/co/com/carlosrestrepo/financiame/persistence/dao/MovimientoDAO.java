package co.com.carlosrestrepo.financiame.persistence.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import co.com.carlosrestrepo.financiame.model.ConsultaSaldo;
import co.com.carlosrestrepo.financiame.model.Deudor;
import co.com.carlosrestrepo.financiame.model.MedioPago;
import co.com.carlosrestrepo.financiame.model.Movimiento;
import co.com.carlosrestrepo.financiame.model.TipoMovimiento;
import co.com.carlosrestrepo.financiame.persistence.DriverSQLite;
import co.com.carlosrestrepo.financiame.persistence.PersistenceConfiguration;
import co.com.carlosrestrepo.financiame.persistence.exception.FinanciaMeException;
import co.com.carlosrestrepo.financiame.util.FinanciaMeConfiguration;

/**
 * Clase encargada del acceso a datos para la tabla tbl_movimiento
 *
 * @author  Carlos Restrepo
 * @created Septiembre 16 de 2015
 */
public class MovimientoDAO extends DriverSQLite {

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
            cv.put("fecha", FinanciaMeConfiguration.sdf.format(movimiento.getFecha()));
            cv.put("valor", movimiento.getValor().intValue());
            cv.put("descripcion", movimiento.getDescripcion());
            if (movimiento.getDeudor() != null)
                cv.put("id_deudor", movimiento.getDeudor().getId());
            if (movimiento.getMedioPago() != null)
                cv.put("id_medio_pago", movimiento.getMedioPago().getId());

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
        Cursor cursor = null;
        try {
            openToRead();

            Movimiento movimiento = null;
            String[] campos = new String[]{ "id", "id_tipo_movimiento", "fecha", "valor",
                    "descripcion", "id_deudor", "id_medio_pago" };
            String filtro = "id=?";
            String[] valorFiltro = new String[] { String.valueOf(id) };

            cursor = sqLiteDatabase.query(PersistenceConfiguration.MOVIMIENTO_TABLE,
                    campos, filtro, valorFiltro, null, null, null);

            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    movimiento = new Movimiento();
                    movimiento.setId(cursor.getLong(0));
                    movimiento.setTipoMovimiento(new TipoMovimiento(cursor.getLong(1)));
                    movimiento.setFecha(FinanciaMeConfiguration.sdf.parse(cursor.getString(2)));
                    movimiento.setValor(Integer.valueOf(cursor.getInt(3)));
                    movimiento.setDescripcion(cursor.getString(4));
                    movimiento.setDeudor(new Deudor(cursor.getLong(5)));
                    movimiento.setMedioPago(new MedioPago(cursor.getLong(6)));
                }
            }
            return movimiento;
        } catch (Exception e) {
            throw new FinanciaMeException("Ocurrió un error consultando el Movimiento (Id: "
                    + String.valueOf(id) + ")");
        } finally {
            if (cursor != null && !cursor.isClosed()) cursor.close();
            close();
        }
    }

    /**
     * Método que se encarga de obtener todos los Movimientos en una lista
     * @return List<Movimiento>
     * @throws FinanciaMeException
     */
    public List<Movimiento> getAll() throws FinanciaMeException {
        Cursor cursor = null;
        try {
            openToRead();

            List<Movimiento> movimientoList = null;
            String[] campos = new String[]{ "id", "id_tipo_movimiento", "fecha", "valor",
                    "descripcion", "id_deudor", "id_medio_pago" };
            String orderBy = "id desc";

            cursor = sqLiteDatabase.query(PersistenceConfiguration.MOVIMIENTO_TABLE,
                    campos, null, null, null, null, orderBy);

            if (cursor.moveToFirst()) {
                movimientoList = new ArrayList<Movimiento>();
                Movimiento movimiento;
                do {
                    movimiento = new Movimiento();
                    movimiento.setId(cursor.getLong(0));
                    movimiento.setTipoMovimiento(new TipoMovimiento(cursor.getLong(1)));
                    movimiento.setFecha(FinanciaMeConfiguration.sdf.parse(cursor.getString(2)));
                    movimiento.setValor(Integer.valueOf(cursor.getInt(3)));
                    movimiento.setDescripcion(cursor.getString(4));
                    movimiento.setDeudor(new Deudor(cursor.getLong(5)));
                    movimiento.setMedioPago(new MedioPago(cursor.getLong(6)));
                    movimientoList.add(movimiento);
                } while(cursor.moveToNext());
            }
            return movimientoList;
        } catch (Exception e) {
            throw new FinanciaMeException(
                    "Ocurrió un error consultando todos los Movimientos");
        } finally {
            if (cursor != null && !cursor.isClosed()) cursor.close();
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
            cv.put("fecha", FinanciaMeConfiguration.sdf.format(movimiento.getFecha()));
            cv.put("valor", movimiento.getValor().intValue());
            cv.put("descripcion", movimiento.getDescripcion());
            if (movimiento.getDeudor() != null)
                cv.put("id_deudor", movimiento.getDeudor().getId());
            if (movimiento.getMedioPago() != null)
                cv.put("id_medio_pago", movimiento.getMedioPago().getId());

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

    /**
     * Método que se encarga de consultar el saldo total
     * @throws FinanciaMeException
     */
    public Integer getSaldo() throws FinanciaMeException {
        Cursor cursor = null;
        try {
            openToRead();
            Integer saldo = null;
            cursor = sqLiteDatabase.rawQuery(PersistenceConfiguration.QUERY_SALDO, null);
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        saldo = cursor.getInt(0);
                    } while (cursor.moveToNext());
                }
            }
            return saldo;
        } catch (Exception e) {
            throw new FinanciaMeException("Ocurrió un error consultando el saldo");
        } finally {
            if (cursor != null && !cursor.isClosed()) cursor.close();
            close();
        }
    }

    /**
     * Método que se encarga de consultar el total de los préstamos
     * @throws FinanciaMeException
     */
    public Integer getSaldoPrestamos() throws FinanciaMeException {
        Cursor cursor = null;
        try {
            openToRead();
            Integer saldo = null;
            cursor = sqLiteDatabase.rawQuery(PersistenceConfiguration.QUERY_SALDO_PRESTAMOS, null);
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        saldo = cursor.getInt(0);
                    } while (cursor.moveToNext());
                }
            }
            return saldo;
        } catch (Exception e) {
            throw new FinanciaMeException(
                    "Ocurrió un error consultando el saldo total de préstamos");
        } finally {
            if (cursor != null && !cursor.isClosed()) cursor.close();
            close();
        }
    }

    /**
     * Método que se encarga de consultar el saldo de los Tipos de Movimiento marcados para
     * consulta de saldo
     * @throws FinanciaMeException
     */
    public List<ConsultaSaldo> getSaldosMarcados() throws FinanciaMeException {
        Cursor cursor = null;
        try {
            openToRead();
            List<ConsultaSaldo> saldos = new ArrayList<ConsultaSaldo>();
            cursor = sqLiteDatabase.rawQuery(PersistenceConfiguration.QUERY_SALDOS_MARCADOS, null);
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        ConsultaSaldo consultaSaldo = new ConsultaSaldo();
                        consultaSaldo.setNombre(cursor.getString(0));
                        consultaSaldo.setSaldo(cursor.getInt(1));
                        consultaSaldo.setColor(cursor.getString(2));
                        saldos.add(consultaSaldo);
                    } while (cursor.moveToNext());
                }
            }
            return saldos;
        } catch (Exception e) {
            throw new FinanciaMeException(
                    "Ocurrió un error consultando los saldos marcados");
        } finally {
            if (cursor != null && !cursor.isClosed()) cursor.close();
            close();
        }
    }

//    public void updateList() throws FinanciaMeException {
//        try {
//            openToWrite();
//            String sql = "update tbl_movimiento set fecha = substr(fecha,7,4) || '-' || substr(fecha,4,2) || '-' || substr(fecha,1,2)";
//            sqLiteDatabase.execSQL(sql);
//        } catch (Exception e) {
//            throw new FinanciaMeException("Ocurrió un error actualizando los movimientos");
//        } finally {
//            close();
//        }
//    }
}