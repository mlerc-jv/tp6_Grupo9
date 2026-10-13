package ar.edu.unju.escmi.tp6.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ar.edu.unju.escmi.tp6.dominio.Credito;
import ar.edu.unju.escmi.tp6.dominio.Cuota;
import ar.edu.unju.escmi.tp6.dominio.MontoExcedidoException;
import ar.edu.unju.escmi.tp6.dominio.TarjetaCredito;
import ar.edu.unju.escmi.tp6.dominio.Detalle;
import ar.edu.unju.escmi.tp6.dominio.Factura;

class CreditoTest {
	
	public static final int MONTO_1 = 500000;
	public static final int MONTO_2 = 2000000;
	
	@Test
	void testCalcularTotalFactura() {
	    double montoObtenido = crearFactura().calcularTotal();

	    assertEquals(2500000.0, montoObtenido, 0.001);
	}
	@Test
    void testMontoCreditoValido() {
        Factura factura = crearFactura();
        TarjetaCredito tarjeta = crearTarjeta(3000000);

        Credito credito = new Credito(
            tarjeta,
            factura,
            new ArrayList<Cuota>()
        );

        assertNotNull(credito);
        assertEquals(500000.0, tarjeta.getLimiteCompra(), 0.001);
    }

    @Test
    void testMontoCreditoSuperaTope() {
        Factura factura = crearFactura();
        Detalle detalle = new Detalle();
        detalle.setImporte(1);

        factura.getDetalles().add(detalle);

        TarjetaCredito tarjeta = crearTarjeta(3000000);

        assertThrows(
            MontoExcedidoException.class,
            () -> new Credito(tarjeta, factura, new ArrayList<Cuota>())
        );
    }

    @Test
    void testMontoCreditoSuperaLimiteTarjeta() {
        Factura factura = crearFactura();
        TarjetaCredito tarjeta = crearTarjeta(2000000);

        assertThrows(
            MontoExcedidoException.class,
            () -> new Credito(tarjeta, factura, new ArrayList<Cuota>())
        );
    }

    private TarjetaCredito crearTarjeta(double limiteCompra) {
        TarjetaCredito tarjeta = new TarjetaCredito();
        tarjeta.setLimiteCompra(limiteCompra);
        return tarjeta;
    }
	private Factura crearFactura() {
		Factura factura = new Factura();
		factura.setDetalles(crearListaDetalles());
		return factura;
	}
	
	private List<Detalle> crearListaDetalles(){
		List<Detalle> listaDetalles = new ArrayList<Detalle>();
		Detalle detalle1 = new Detalle();
		detalle1.setImporte(MONTO_1);
		Detalle detalle2 = new Detalle();
		detalle2.setImporte(MONTO_2);
		listaDetalles.add(detalle1);
		listaDetalles.add(detalle2);
		return listaDetalles;
		
	}

}