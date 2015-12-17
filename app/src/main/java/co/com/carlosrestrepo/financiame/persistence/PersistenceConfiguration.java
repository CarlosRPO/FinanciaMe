package co.com.carlosrestrepo.financiame.persistence;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author  Carlos Restrepo
 * @created Septiembre 15 de 2015
 */
public class PersistenceConfiguration {

    /**
     * Versión de la Base de Datos
     */
    public static final int DATABASE_VERSION = 1;

    /**
     * Nombre de la Base de Datos
     */
    public static final String DATABASE_NAME = "financiame_db";

    /**
     * Tabla tbl_tipo_movimiento
     */
    public static final String TIPO_MOVIMIENTO_TABLE = "tbl_tipo_movimiento";

    /**
     * Tabla tbl_medio_pago
     */
    public static final String MEDIO_PAGO_TABLE = "tbl_medio_pago";

    /**
     * Tabla tbl_deudor
     */
    public static final String DEUDOR_TABLE = "tbl_deudor";

    /**
     * Tabla tbl_movimiento
     */
    public static final String MOVIMIENTO_TABLE = "tbl_movimiento";

    /**
     * Sentencias de creación de tablas
     */
    public static final List<String> DATABASE_QUERIES = new ArrayList<String>() {
        private static final long serialVersionUID = 1L;
        {
            add("create table if not exists tbl_tipo_movimiento ("
                    + "id integer primary key autoincrement, nombre text not null, "
                    + "requiere_deudor integer not null default 0, "
                    + "requiere_medio_pago integer not null default 0, color text not null, "
                    + "accion text not null, consulta_saldo integer not null)");
            add("create table if not exists tbl_medio_pago (id integer primary key autoincrement, "
                    + "nombre text not null, interes real)");
            add("create table if not exists tbl_deudor (id integer primary key autoincrement, "
                    + "nombre text not null, telefono text not null, "
                    + "total_deudas integer not null default 0)");
            add("create table if not exists tbl_movimiento (id integer primary key autoincrement, "
                    + "id_tipo_movimiento integer not null, id_medio_pago integer, "
                    + "id_deudor integer, fecha text not null, valor integer not null, "
                    + "descripcion text not null, "
                    + "foreign key(id_tipo_movimiento) references tbl_tipo_movimiento(id),"
                    + "foreign key(id_medio_pago) references tbl_medio_pago(id),"
                    + "foreign key(id_deudor) references tbl_deudor(id))");
        }
    };

    /**
     * Sentencias de consultas personalizadas
     */
    public static final String QUERY_SALDO = "select (select ifnull(sum(m.valor), 0) " +
            "from tbl_movimiento m " +
            "inner join tbl_tipo_movimiento tm on tm.id = m.id_tipo_movimiento " +
            "where tm.accion = '+') - " +
            "(select ifnull(sum(m.valor), 0) from tbl_movimiento m " +
            "inner join tbl_tipo_movimiento tm on tm.id = m.id_tipo_movimiento " +
            "where tm.accion = '-')";

    public static final String QUERY_SALDO_PRESTAMOS = "select ifnull(sum(total_deudas), 0) " +
            "from tbl_deudor " +
            "where total_deudas > 0";

    public static final String QUERY_SALDOS_MARCADOS = "select tm.nombre, sum(m.valor) " +
            "from tbl_movimiento m " +
            "inner join tbl_tipo_movimiento tm on m.id_tipo_movimiento = tm.id " +
            "where tm.consulta_saldo = 1";
}