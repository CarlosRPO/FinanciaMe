package co.com.carlosrestrepo.financiame.model;

import java.io.Serializable;

/**
 * Clase encargada de administrar los atributos de la tabla tbl_tipo_movimiento
 *
 * @author  Carlos Restrepo
 * @created Septiembre 16 de 2015
 */
public class TipoMovimiento implements Serializable {

    private static final long serialVersionUID = -6382153358006682313L;

    public TipoMovimiento() {}

    public TipoMovimiento(long id) {
        this.id = id;
    }

    /**
     * Identificador único del tipo de movimiento
     */
    private long id;

    /**
     * Nombre del tipo de movimiento
     */
    private String nombre;

    /**
     * Indica si el tipo de movimiento requiere asociar un deudor
     */
    private boolean deudor;

    /**
     * Método que se encarga de obtener el id del tipo de movimiento
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Método que se encarga de asignar un id al tipo de movimiento
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Método que se encarga de obtener el nombre del tipo de movimiento
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método que se encarga de asignar un nombre al tipo de movimiento
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Método que se encarga de verificar si el tipo de movimiento requiere un deudor
     * @return deudor
     */
    public boolean hasDeudor() {
        return deudor;
    }

    /**
     * Método que se encarga de indicar si el tipo de movimiento requeriere o no un deudor
     * @param deudor
     */
    public void setDeudor(boolean deudor) {
        this.deudor = deudor;
    }
}