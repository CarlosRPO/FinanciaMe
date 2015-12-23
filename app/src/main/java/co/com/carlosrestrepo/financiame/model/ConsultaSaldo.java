package co.com.carlosrestrepo.financiame.model;

import java.io.Serializable;

/**
 * Clase encargada de administrar los atributos para los movimientos
 * que son marcados para consultar el saldo
 *
 * @author  Carlos Restrepo
 * @created Diciembre 23 de 2015
 */
public class ConsultaSaldo implements Serializable {

    private static final long serialVersionUID = -6382153358006682313L;

    public ConsultaSaldo() {}

    public ConsultaSaldo(String nombre, Integer saldo, String color) {
        this.nombre = nombre;
        this.saldo = saldo;
        this.color = color;
    }

    /**
     * Nombre del tipo de movimiento
     */
    private String nombre;

    /**
     * Saldo acumulado para el tipo de movimiento
     */
    private Integer saldo;

    /**
     * Indica el color que identifica el tipo de movimiento
     */
    private String color;

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
     * Método que se encarga de obtener el saldo acumulado del tipo de movimiento
     * @return saldo
     */
    public Integer getSaldo() {
        return saldo;
    }

    /**
     * Método que se encarga de asignar el saldo acumulado al tipo de movimiento
     * @param saldo
     */
    public void setSaldo(Integer saldo) {
        this.saldo = saldo;
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
}