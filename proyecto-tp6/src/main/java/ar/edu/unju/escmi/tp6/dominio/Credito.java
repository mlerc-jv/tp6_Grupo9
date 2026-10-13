package ar.edu.unju.escmi.tp6.dominio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Credito {

	public static final int CANTIDAD_CUOTAS = 20;
	public static final double MONTO_MAXIMO = 2500000;
	public static final double MONTO_MAXIMO_CELULARES = 1000000;
	public static final LocalDate FIN_PROGRAMA = LocalDate.of(LocalDate.now().getYear(), 12, 22);

	private TarjetaCredito tarjetaCredito;
	private Factura factura;
	private List<Cuota> cuotas = new ArrayList<Cuota>();

	public Credito() {
	}

	public Credito(TarjetaCredito tarjetaCredito, Factura factura, List<Cuota> cuotas) {
		this.tarjetaCredito = tarjetaCredito;
		this.factura = factura;
		this.cuotas = (cuotas != null) ? cuotas : new ArrayList<Cuota>();
		validarCompra();
		generarCuotas();
		// la compra usa parte del límite de la tarjeta
		tarjetaCredito.setLimiteCompra(tarjetaCredito.getLimiteCompra() - factura.calcularTotal());
	}

	public Credito(List<Cuota> cuotas) {
		this.cuotas = (cuotas != null) ? cuotas : new ArrayList<Cuota>();
	}

	public TarjetaCredito getTarjetaCredito() {
		return tarjetaCredito;
	}

	public void setTarjetaCredito(TarjetaCredito tarjetaCredito) {
		this.tarjetaCredito = tarjetaCredito;
	}

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public List<Cuota> getCuotas() {
		return cuotas;
	}

	public void setCuotas(List<Cuota> cuotas) {
		this.cuotas = cuotas;
	}
	
	/**
	 * Verifica las reglas del programa "Ahora 20" antes de otorgar el crédito.
	 * Si alguna no se cumple, lanza una excepción y el crédito no se crea.
	 */
	public void validarCompra() {
		if (tarjetaCredito == null || factura == null) {
			throw new IllegalArgumentException("El crédito necesita una tarjeta y una factura");
		}
		if (LocalDate.now().isAfter(FIN_PROGRAMA)) {
			throw new IllegalStateException("El programa Ahora 20 estuvo vigente hasta el " + FIN_PROGRAMA);
		}

		boolean tieneCelular = false;
		for (Detalle detalle : factura.getDetalles()) {
			Producto producto = detalle.getProducto();
			if (producto != null) {
				if (!producto.esAhora20()) {
					throw new IllegalArgumentException(
							"El producto '" + producto.getDescripcion() + "' no está incluido en Ahora 20");
				}
				if (producto.esCelular()) {
					tieneCelular = true;
				}
			}
		}

		double total = factura.calcularTotal();
		double tope = tieneCelular ? MONTO_MAXIMO_CELULARES : MONTO_MAXIMO;
		if (total > tope) {
			throw new MontoExcedidoException("El total $" + total + " supera el tope de Ahora 20 ($" + tope + ")");
		}
		if (total > tarjetaCredito.getLimiteCompra()) {
			throw new MontoExcedidoException("El total $" + total + " supera el límite disponible de la tarjeta ($"
					+ tarjetaCredito.getLimiteCompra() + ")");
		}
	}

	public void generarCuotas() {
		cuotas.clear(); // así nunca quedan más de 20 cuotas
		double montoCuota = this.factura.calcularTotal() / CANTIDAD_CUOTAS;
		int nroCuota = 0;
		LocalDate currentDate = LocalDate.now();
		LocalDate auxDate = LocalDate.now();

		for (int i = 0; i < CANTIDAD_CUOTAS; i++) {
			nroCuota++;
			Cuota cuota = new Cuota();
			cuota.setMonto(montoCuota);
			cuota.setNroCuota(nroCuota);
			cuota.setFechaGeneracion(currentDate); 
			auxDate = auxDate.plusMonths(1);
			cuota.setFechaVencimiento(auxDate);
			cuotas.add(cuota);
		}

	}

	public void mostrarCredito() {
		System.out.println("Tarjeta De Credito: " + tarjetaCredito + "\n" + factura + "\nCant. Cuotas:\n");
		for(Cuota cuota: cuotas) {
			System.out.println(cuota);
		}
	}
}
