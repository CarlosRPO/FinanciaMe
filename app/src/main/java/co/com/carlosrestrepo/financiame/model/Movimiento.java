package co.com.carlosrestrepo.financiame.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Clase encargada de administrar los atributos de la tabla tbl_movimiento
 *
 * @author  Carlos Restrepo
 * @created Septiembre 16 de 2015
 */
public class Movimiento implements Serializable {

    public Movimiento() {}

    public Movimiento(long id) {
        this.id = id;
    }

    private static final long serialVersionUID = -1186377326026822031L;

    /**
     * Identificador único del movimiento
     */
    private long id;

    /**
     * Tipo del movimiento
     */
    private TipoMovimiento tipoMovimiento;

    /**
     * Fecha del movimiento
     */
    private Date fecha;

    /**
     * Valor del movimiento
     */
    private Integer valor;

    /**
     * Descripción del movimiento
     */
    private String descripcion;

    /**
     * Medio de pago para cuando el movimiento lo requiera
     */
    private MedioPago medioPago;

    /**
     * Deudor para cuando el movimiento lo requiera
     */
    private Deudor deudor;

    /**
     * Método que se encarga de obtener el id del movimiento
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Método que se encarga de asignar un id al movimiento
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Método que se encarga de obtener el tipo de movimiento
     * @return TipoMovimiento
     */
    public TipoMovimiento getTipoMovimiento() {
        return tipoMovimiento;
    }

    /**
     * Método que se encarga de asignar un tipo de movimiento
     * @param tipoMovimiento
     */
    public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    /**
     * Método que se encarga de obtener la fecha del movimiento
     * @return fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Método que se encarga de asignar una fecha al movimiento
     * @param fecha
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Método que se encarga de obtener el valor del movimiento
     * @return valor
     */
    public Integer getValor() {
        return valor;
    }

    /**
     * Método que se encarga de asignar un valor al movimiento
     * @param valor
     */
    public void setValor(Integer valor) {
        this.valor = valor;
    }

    /**
     * Método que se encarga de obtener la descripción del movimiento
     * @return descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Método que se encarga de asignar una descripción al movimiento
     * @param descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Método que se encarga de obtener el medio de pago del movimiento
     * @return medioPago
     */
    public MedioPago getMedioPago() {
        return medioPago;
    }

    /**
     * Método que se encarga de asignar un medio de pago al movimiento
     * @param medioPago
     */
    public void setMedioPago(MedioPago medioPago) {
        this.medioPago = medioPago;
    }

    /**
     * Método que se encarga de obtener el deudor del movimiento
     * @return deudor
     */
    public Deudor getDeudor() {
        return deudor;
    }

    /**
     * Método que se encarga de asignar un deudor al movimiento
     * @param deudor
     */
    public void setDeudor(Deudor deudor) {
        this.deudor = deudor;
    }
}