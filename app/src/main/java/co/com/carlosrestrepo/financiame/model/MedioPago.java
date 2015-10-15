package co.com.carlosrestrepo.financiame.model;

import java.io.Serializable;

/**
 * Clase encargada de administrar los atributos de la tabla tbl_tipo_movimiento
 *
 * @author  Carlos Restrepo
 * @created Octubre 13 de 2015
 */
public class MedioPago implements Serializable {

    private static final long serialVersionUID = 7532564640705550431L;

    public MedioPago() {}

    public MedioPago(long id) {
        this.id = id;
    }

    /**
     * Identificador único del medio de pago
     */
    private long id;

    /**
     * Nombre del medio de pago
     */
    private String nombre;

    /**
     * Indica el porcentaje del intereses mensual generado
     */
    private Float intereses;

    /**
     * Método que se encarga de obtener el id del medio de pago
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Método que se encarga de asignar un id al medio de pago
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Método que se encarga de obtener el nombre del medio de pago
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método que se encarga de asignar un nombre al medio de pago
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Método que se encarga de obtener los intereses del medio de pago
     * @return nombre
     */
    public Float getIntereses() {
        return intereses;
    }

    /**
     * Método que se encarga de asignar los intereses al medio de pago
     * @param intereses
     */
    public void setIntereses(Float intereses) {
        this.intereses = intereses;
    }

    @Override
    public boolean equals(Object o) {
        MedioPago medioPago = (MedioPago) o;
        if (this.id == medioPago.getId()) return true;
        return false;
    }
}