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
     * Indica si el tipo de movimiento requiere asociar un medio de pago
     */
    private boolean medioPago;

    /**
     * Indica si el tipo de movimiento requiere asociar un deudor
     */
    private boolean deudor;

    /**
     * Indica si el tipo de movimiento requiere consulta de saldo
     */
    private boolean consultaSaldo;

    /**
     * Indica el color que identifica el tipo de movimiento
     */
    private String color;

    /**
     * Indica la acción que debe realizar el tipo de movimiento (Sumar o Restar)
     */
    private String accion;

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
     * Método que se encarga de verificar si el tipo de movimiento requiere un medio de pago
     * @return medioPago
     */
    public boolean hasMedioPago() {
        return medioPago;
    }

    /**
     * Método que se encarga de indicar si el tipo de movimiento requeriere o no un medio de pago
     * @param medioPago
     */
    public void setMedioPago(boolean medioPago) {
        this.medioPago = medioPago;
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

    /**
     * Método que se encarga de verificar si el tipo de movimiento requiere consulta de saldo
     * @return consultaSaldo
     */
    public boolean hasConsultaSaldo() {
        return consultaSaldo;
    }

    /**
     * Método que se encarga de indicar si el tipo de movimiento requeriere o no consulta de saldo
     * @param consultaSaldo
     */
    public void setConsultaSaldo(boolean consultaSaldo) {
        this.consultaSaldo = consultaSaldo;
    }

    /**
     * Método que se encarga de obtener el color del tipo de movimiento
     * @return color
     */
    public String getColor() {
        return color;
    }

    /**
     * Método que se encarga de asignar un color al tipo de movimiento
     * @param color
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Método que se encarga de obtener la acción del tipo de movimiento
     * @return accion
     */
    public String getAccion() {
        return accion;
    }

    /**
     * Método que se encarga de asignar una acción al tipo de movimiento
     * @param accion
     */
    public void setAccion(String accion) {
        this.accion = accion;
    }

    @Override
    public boolean equals(Object o) {
        TipoMovimiento tipoMovimiento = (TipoMovimiento) o;
        if (this.id == tipoMovimiento.getId()) return true;
        return false;
    }
}