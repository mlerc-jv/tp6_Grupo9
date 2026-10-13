package ar.edu.unju.escmi.tp6.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unju.escmi.tp6.collections.CollectionStock;
import ar.edu.unju.escmi.tp6.dominio.Producto;
import ar.edu.unju.escmi.tp6.dominio.Stock;

class StockTest {

    Stock stock;

    @BeforeEach
    void setUp() {
        CollectionStock.stocks.clear();

        Producto producto = new Producto(101, "Notebook Lenovo 15''", 1060000.0, "Argentina");
        stock = new Stock(20, producto);

        CollectionStock.stocks.add(stock);
    }

    @Test
    void testDecrementoStock() {
        int expected = 13;

        CollectionStock.reducirStock(stock, 7);

        int obtenido = stock.getCantidad();

        assertEquals(expected, obtenido);
    }
}