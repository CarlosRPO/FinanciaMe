package co.com.carlosrestrepo.financiame.model;

import java.io.Serializable;

/**
 * Clase encargada de administrar los atributos de las opciones del menú
 *
 * @author  Carlos Restrepo
 * @created Octubre 05 de 2015
 */
public class OpcionMenu implements Serializable {

    private static final long serialVersionUID = 1653974816434894963L;
    /**
     * Ícono de la opción de menú
     */
    private int icono;

    /**
     * Título de la opción del menú
     */
    private int titulo;

    public OpcionMenu() {}

    public OpcionMenu(int icono, int titulo) {
        this.icono = icono;
        this.titulo = titulo;
    }

    /**
     * Método que se encarga de obtener el ícono de la opción de menú
     * @return icono
     */
    public int getIcono() {
        return icono;
    }

    /**
     * Método que se encarga de asignar un ícono a la opción de menú
     * @param icono
     */
    public void setIcono(int icono) {
        this.icono = icono;
    }

    /**
     * Método que se encarga de obtener el título de la opción de menú
     * @return titulo
     */
    public int getTitulo() {
        return titulo;
    }

    /**
     * Método que se encarga de asignar un título a la opción de menú
     * @param titulo
     */
    public void setTitulo(int titulo) {
        this.titulo = titulo;
    }
}