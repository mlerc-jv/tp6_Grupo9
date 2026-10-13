package ar.edu.unju.escmi.tp6.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ar.edu.unju.escmi.tp6.collections.CollectionCliente;
import ar.edu.unju.escmi.tp6.collections.CollectionCredito;
import ar.edu.unju.escmi.tp6.collections.CollectionFactura;
import ar.edu.unju.escmi.tp6.collections.CollectionProducto;
import ar.edu.unju.escmi.tp6.collections.CollectionStock;
import ar.edu.unju.escmi.tp6.collections.CollectionTarjetaCredito;
import ar.edu.unju.escmi.tp6.dominio.Cliente;
import ar.edu.unju.escmi.tp6.dominio.Credito;
import ar.edu.unju.escmi.tp6.dominio.Detalle;
import ar.edu.unju.escmi.tp6.dominio.Factura;
import ar.edu.unju.escmi.tp6.dominio.Producto;
import ar.edu.unju.escmi.tp6.dominio.Stock;
import ar.edu.unju.escmi.tp6.dominio.TarjetaCredito;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        try {
            // Primero se cargan los clientes.
            CollectionCliente.precargarClientes();
            CollectionTarjetaCredito.precargarTarjetas();
            CollectionProducto.precargarProductos();
            CollectionStock.precargarStocks();

            int opcion = 0;

            do {
                System.out.println("\n====== MENU PRINCIPAL =====");
                System.out.println("1- Realizar una venta con Ahora 20");
                System.out.println("2- Ver compras realizadas por cliente");
                System.out.println("3- Mostrar lista de electrodomesticos");
                System.out.println("4- Consultar stock");
                System.out.println("5- Revisar creditos de un cliente");
                System.out.println("6- Salir");
                System.out.print("Ingrese su opcion: ");

                try {
                    opcion = Integer.parseInt(scanner.nextLine());

                    switch (opcion) {
                        case 1:
                        	realizarVenta();
                            break;

                        case 2:
                            consultarCompras();
                            break;

                        case 3:
                            mostrarProductos();
                            break;

                        case 4:
                            consultarStock();
                            break;

                        case 5:
                            consultarCreditos();
                            break;

                        case 6:
                            System.out.println("\nSaliendo del programa...");
                            break;

                        default:
                            System.out.println("\nOpcion incorrecta. Intente nuevamente.");
                            break;
                    }

                } catch (NumberFormatException e) {
                    System.out.println("\nError: debe ingresar un numero valido.");

                } catch (Exception e) {
                    System.out.println("\nOcurrio un error: " + e.getMessage());

                } finally {
                    System.out.println("\nOperacion finalizada.");
                }

            } while (opcion != 6);

        } catch (Exception e) {
            System.out.println("\nError al iniciar el programa: " + e.getMessage());

        } finally {
            scanner.close();
            System.out.println("Programa finalizado.");
        }
    }

    public static void consultarCompras() {
        System.out.print("\nIngrese el DNI del cliente: ");
        long dni = Long.parseLong(scanner.nextLine());

        Cliente cliente = CollectionCliente.buscarCliente(dni);

        if (cliente == null) {
            System.out.println("No existe un cliente con ese DNI.");
            return;
        }

        List<Factura> compras = cliente.consultarCompras();

        if (compras == null || compras.isEmpty()) {
            System.out.println("El cliente no tiene compras registradas.");
        } else {
            System.out.println("\nCompras de " + cliente.getNombre() + ":");

            for (Factura factura : compras) {
                System.out.println(factura);
                System.out.println("Total: $" + factura.calcularTotal());
            }
        }
    }

    public static void mostrarProductos() {
        System.out.println("\n====== ELECTRODOMESTICOS INCLUIDOS =====");

        if (CollectionProducto.productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }

        for (Producto producto : CollectionProducto.productos) {
            System.out.println(producto);
        }
    }

    public static void consultarStock() {
        System.out.println("\n====== CONSULTAR STOCK =====");

        if (CollectionStock.stocks.isEmpty()) {
            System.out.println("No hay stock registrado.");
            return;
        }

        for (Stock stock : CollectionStock.stocks) {
            System.out.println(
                "Producto: " + stock.getProducto().getDescripcion()
                + " | Codigo: " + stock.getProducto().getCodigo()
                + " | Cantidad disponible: " + stock.getCantidad()
            );
        }
    }

    public static void consultarCreditos() {
        System.out.print("\nIngrese el DNI del cliente: ");
        long dni = Long.parseLong(scanner.nextLine());

        Cliente cliente = CollectionCliente.buscarCliente(dni);

        if (cliente == null) {
            System.out.println("No existe un cliente con ese DNI.");
            return;
        }

        boolean encontrado = false;

        for (Credito credito : CollectionCredito.creditos) {
            if (credito.getFactura() != null
                    && credito.getFactura().getCliente() != null
                    && credito.getFactura().getCliente().getDni() == dni) {

                encontrado = true;
                System.out.println("\nCredito del cliente: " + cliente.getNombre());
                credito.mostrarCredito();
            }
        }

        if (!encontrado) {
            System.out.println("El cliente no tiene creditos registrados.");
        }
    }
    

    public static void realizarVenta() {

        System.out.println("\n====== VENTA CON AHORA 20 =====");

        System.out.print("Ingrese el DNI del cliente: ");
        long dni = Long.parseLong(scanner.nextLine());

        Cliente cliente = CollectionCliente.buscarCliente(dni);

        if (cliente == null) {
            System.out.println("Error: el cliente no existe.");
            return;
        }

        System.out.println("Cliente: " + cliente.getNombre());

        System.out.print("Ingrese el numero de tarjeta: ");
        long numeroTarjeta = Long.parseLong(scanner.nextLine());

        TarjetaCredito tarjeta =
                CollectionTarjetaCredito.buscarTarjetaCredito(numeroTarjeta);

        if (tarjeta == null) {
            System.out.println("Error: la tarjeta no existe.");
            return;
        }

        if (tarjeta.getCliente() == null
                || tarjeta.getCliente().getDni() != dni) {
            System.out.println("Error: la tarjeta no pertenece al cliente.");
            return;
        }

        if (tarjeta.getFechaCaducacion() == null
                || tarjeta.getFechaCaducacion().isBefore(LocalDate.now())) {
            System.out.println("Error: la tarjeta de credito esta vencida o no tiene fecha de caducidad.");
            return;
        }

        System.out.print("Ingrese el codigo del producto: ");
        long codigo = Long.parseLong(scanner.nextLine());

        Producto producto = CollectionProducto.buscarProducto(codigo);

        if (producto == null) {
            System.out.println("Error: el producto no existe.");
            return;
        }

        System.out.println("Producto: " + producto.getDescripcion());
        System.out.println("Precio unitario: $" + producto.getPrecioUnitario());

        System.out.print("Ingrese la cantidad: ");
        int cantidad = Integer.parseInt(scanner.nextLine());

        if (cantidad <= 0) {
            System.out.println("Error: la cantidad debe ser mayor que cero.");
            return;
        }

        Stock stock = CollectionStock.buscarStock(producto);

        if (stock == null) {
            System.out.println("Error: no se encontro el stock del producto.");
            return;
        }

        if (stock.getCantidad() < cantidad) {
            System.out.println("Error: stock insuficiente.");
            return;
        }

        double total = producto.getPrecioUnitario() * cantidad;

     Detalle detalle = new Detalle(cantidad, total, producto);

     List<Detalle> detalles = new ArrayList<>();
     detalles.add(detalle);

     long numeroFactura = System.currentTimeMillis();
     Factura factura = new Factura(
             LocalDate.now(),
             numeroFactura,
             cliente,
             detalles
     );

     double limiteAnterior = tarjeta.getLimiteCompra();
     int stockAnterior = stock.getCantidad();

     Credito credito = null;

     try {
         credito = new Credito(tarjeta, factura, new ArrayList<>());
         CollectionStock.reducirStock(stock, cantidad);
         CollectionFactura.facturas.add(factura);
         CollectionCredito.creditos.add(credito);

     } catch (RuntimeException e) {
         CollectionCredito.creditos.remove(credito);
         CollectionFactura.facturas.remove(factura);
         stock.setCantidad(stockAnterior);
         tarjeta.setLimiteCompra(limiteAnterior);
         throw e;
     }

        System.out.println("\n====== RESUMEN DE LA COMPRA =====");
        System.out.println("Cliente: " + cliente.getNombre());
        System.out.println("Producto: " + producto.getDescripcion());
        System.out.println("Cantidad: " + cantidad);
        System.out.println("Total: $" + total);

        System.out.println("\nVenta registrada correctamente con Ahora 20.");
        System.out.println("Numero de factura: " + numeroFactura);
        System.out.println("Cantidad de cuotas: " + Credito.CANTIDAD_CUOTAS);
        System.out.println("Importe de cada cuota: $" + (total / Credito.CANTIDAD_CUOTAS));
        System.out.println("Limite disponible de la tarjeta: $" + tarjeta.getLimiteCompra());
    }

}
