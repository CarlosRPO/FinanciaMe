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
     * Tabla tbl_movimiento
     */
    public static final String MOVIMIENTO_TABLE = "tbl_movimiento";

    /**
     * Tabla tbl_deudor
     */
    public static final String DEUDOR_TABLE = "tbl_deudor";

    /**
     * Tabla tbl_movimiento_deudor
     */
    public static final String MOVIMIENTO_DEUDOR_TABLE = "tbl_movimiento_deudor";

    /**
     * Sentencias de creación de tablas
     */
    public static final List<String> DATABASE_QUERIES = new ArrayList<String>() {
        private static final long serialVersionUID = 1L;
        {
            add("create table if not exists tbl_tipo_movimiento ("
                    + "id integer primary key autoincrement, nombre text not null, "
                    + "requiere_deudor integer not null default 0);");
            add("create table if not exists tbl_deudor (id integer primary key autoincrement, "
                    + "nombre text not null, telefono text not null, total_deudas integer not null default 0);");
            add("create table if not exists tbl_movimiento (id integer primary key autoincrement, "
                    + "id_tipo_movimiento integer not null, fecha text not null, valor integer not null, "
                    + "descripcion text not null, id_deudor integer, "
                    + "foreign key(id_tipo_movimiento) references tbl_tipo_movimiento(id),"
                    + "foreign key(id_deudor) references tbl_deudor(id));");
        }
    };
}