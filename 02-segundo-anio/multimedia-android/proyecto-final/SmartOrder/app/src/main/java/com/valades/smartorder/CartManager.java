package com.valades.smartorder;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    // Variables estáticas que mantienen su valor en TODA la aplicación
    public static List<String> productosEnCarrito = new ArrayList<>();
    public static double precioTotal = 0.0;

    // Método para añadir un producto fácilmente
    public static void agregarProducto(String nombre, double precio) {
        productosEnCarrito.add(nombre);
        precioTotal += precio;
    }

    // Método para limpiar el carrito (cuando paguemos)
    public static void vaciarCarrito() {
        productosEnCarrito.clear();
        precioTotal = 0.0;
    }
}