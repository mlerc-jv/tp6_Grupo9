package ar.edu.unju.escmi.tp6.dominio;

/** Se lanza cuando se intenta vender más unidades de las que hay en stock. */
public class StockInsuficienteException extends RuntimeException {

    public StockInsuficienteException(String mensaje) {
        super(mensaje);
    }
}
