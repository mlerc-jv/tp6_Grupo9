package ar.edu.unju.escmi.tp6.dominio;

/**
 * Se lanza cuando el total de la compra supera el tope del programa "Ahora 20"
 * ($2.500.000, o $1.000.000 si es un celular) o el límite disponible de la tarjeta.
 */
public class MontoExcedidoException extends RuntimeException {

    public MontoExcedidoException(String mensaje) {
        super(mensaje);
    }
}
