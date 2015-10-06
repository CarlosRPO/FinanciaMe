package co.com.carlosrestrepo.financiame.persistence.exception;

/**
 * Clase encargada del administrar las excepciones ocurridas en el acceso a datos
 *
 * @author  Carlos Restrepo
 * @created Septiembre 16 de 2015
 */
public class FinanciaMeException extends Exception {

    public FinanciaMeException(String message) {
        super(message);
    }

    public FinanciaMeException(String message, Throwable throwable) {
        super(message, throwable);
    }
}