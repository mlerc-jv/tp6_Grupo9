package ar.edu.unju.escmi.tp6.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import ar.edu.unju.escmi.tp6.dominio.Detalle;
import ar.edu.unju.escmi.tp6.dominio.Factura;
import ar.edu.unju.escmi.tp6.dominio.Credito;
import ar.edu.unju.escmi.tp6.dominio.Cuota;

class CuotaTest {

    @Test
    void testCrearCuota() {
        double montoEsperado = 125000.0;
        int nroCuotaEsperado = 1;
        LocalDate fechaGeneracionEsperada = LocalDate.now();
        LocalDate fechaVencimientoEsperada = fechaGeneracionEsperada.plusMonths(1);

        Cuota cuota = new Cuota(
            montoEsperado,
            nroCuotaEsperado,
            fechaGeneracionEsperada,
            fechaVencimientoEsperada
        );

        assertEquals(montoEsperado, cuota.getMonto(), 0.001);
        assertEquals(nroCuotaEsperado, cuota.getNroCuota());
        assertEquals(fechaGeneracionEsperada, cuota.getFechaGeneracion());
        assertEquals(fechaVencimientoEsperada, cuota.getFechaVencimiento());
    }

    @Test
    void testListaCuotasNoEsNull() {
        Credito credito = new Credito(new ArrayList<Cuota>());

        assertNotNull(credito.getCuotas());
    }

    @Test
    void testListaCuotasTieneVeinteCuotas() {
        List<Cuota> cuotas = new ArrayList<Cuota>();

        Credito credito = new Credito(cuotas);

        credito.setFactura(crearFacturaDePrueba());
        credito.generarCuotas();

        assertEquals(Credito.CANTIDAD_CUOTAS, credito.getCuotas().size());
    }

    @Test
    void testCantidadCuotasNoSuperaElMaximo() {
        Credito credito = new Credito(new ArrayList<Cuota>());

        credito.setFactura(crearFacturaDePrueba());
        credito.generarCuotas();

        assertTrue(
            credito.getCuotas().size() <= Credito.CANTIDAD_CUOTAS,
            "La cantidad de cuotas no debe superar las 20 permitidas"
        );
    }
    private Factura crearFacturaDePrueba() {
    Factura factura = new Factura();

    List<Detalle> detalles = new ArrayList<Detalle>();

    Detalle detalle = new Detalle();
    detalle.setImporte(2500000.0);

    detalles.add(detalle);
    factura.setDetalles(detalles);

    return factura;
    }
}