package co.com.carlosrestrepo.financiame.persistence;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

/**
 *
 * @author  Carlos Restrepo
 * @created Septiembre 15 de 2015
 *
 * Clase encargada de administrar las conexiones a la Base de Datos SQLite
 */
public class DriverSQLite {

    private Context context;
    private SQLiteHelper sqLiteHelper;

    protected SQLiteDatabase sqLiteDatabase;

    public DriverSQLite(Context context) {
        this.context = context;
    }

    /**
     * Método que se encarga de crear una nueva conexión de lectura de datos
     * @throws SQLException
     */
    protected void openToRead() throws SQLException {
        this.sqLiteHelper = new SQLiteHelper(context, PersistenceConfiguration.DATABASE_NAME, null,
                PersistenceConfiguration.DATABASE_VERSION);
        this.sqLiteDatabase = this.sqLiteHelper.getReadableDatabase();
    }

    /**
     * Método que se encarga de crear una nueva conexión para escribir datos
     * @throws SQLException
     */
    protected void openToWrite() throws SQLException {
        this.sqLiteHelper = new SQLiteHelper(context, PersistenceConfiguration.DATABASE_NAME, null,
                PersistenceConfiguration.DATABASE_VERSION);
        this.sqLiteDatabase = this.sqLiteHelper.getWritableDatabase();
    }

    /**
     * Método que se encarga de cerrar la conexión actualmente abierta
     */
    protected void close() {
        if (this.sqLiteDatabase != null && this.sqLiteDatabase.isOpen()) {
            this.sqLiteDatabase.close();
        }

        if (this.sqLiteHelper != null) {
            this.sqLiteHelper.close();
        }
    }

    private class SQLiteHelper extends SQLiteOpenHelper {

        public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                            int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            List<String> queries = PersistenceConfiguration.DATABASE_QUERIES;
            for (String query : queries) {
                db.execSQL(query);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
    }
}