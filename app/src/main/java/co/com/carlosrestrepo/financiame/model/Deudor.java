package co.com.carlosrestrepo.financiame.model;

import java.io.Serializable;

/**
 * Clase encargada de administrar los atributos de la tabla tbl_deudor
 *
 * @author  Carlos Restrepo
 * @created Septiembre 16 de 2015
 */
public class Deudor implements Serializable {

    public Deudor() {
        this.totalDeudas = 0;
    }

    public Deudor(long id) {
        this.id = id;
        this.totalDeudas = 0;
    }

    private static final long serialVersionUID = -4049350165124922687L;

    /**
     * Identificador único del deudor
     */
    private long id;

    /**
     * Nombre del deudor
     */
    private String nombre;

    /**
     * Teléfono del duedor
     */
    private String telefono;

    /**
     * Indica el valor del total de las deudas
     */
    private Integer totalDeudas;

    /**
     * Método que se encarga de obtener el id del deudor
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Método que se encarga de asignar un id al deudor
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Método que se encarga de obtener el nombre del deudor
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método que se encarga de asignar un nombre al deudor
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Método que se encarga de obtener el teléfono del deudor
     * @return telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Método que se encarga de asignar un teléfono al deudor
     * @param telefono
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Método que se encarga de obtener el total de las deudas
     * @return totalDeudas
     */
    public Integer getTotalDeudas() {
        return totalDeudas;
    }

    /**
     * Método que se encarga de asignar un valor al total de las deudas
     * @param totalDeudas
     */
    public void setTotalDeudas(Integer totalDeudas) {
        this.totalDeudas = totalDeudas;
    }

    @Override
    public boolean equals(Object o) {
        Deudor deudor = (Deudor) o;
        if (this.id == deudor.getId()) return true;
        return false;
    }
}