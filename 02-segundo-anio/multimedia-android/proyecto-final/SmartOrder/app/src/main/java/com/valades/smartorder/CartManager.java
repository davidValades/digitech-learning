package com.valades.smartorder;

import java.util.ArrayList;
import java.util.List;

public class CartManager {

    // 1. Creamos una estructura para guardar los datos de cada línea del carrito
    public static class CartItem {
        public String nombre;
        public double precioBase;
        public int cantidad;

        public CartItem(String nombre, double precioBase, int cantidad) {
            this.nombre = nombre;
            this.precioBase = precioBase;
            this.cantidad = cantidad;
        }
    }

    // 2. Nuestra memoria ahora guarda objetos CartItem
    public static List<CartItem> productosEnCarrito = new ArrayList<>();

    // 3. Método inteligente para añadir (Si ya existe, le suma 1 a la cantidad)
    public static void agregarProducto(String nombre, double precio) {
        for (CartItem item : productosEnCarrito) {
            if (item.nombre.equals(nombre)) {
                item.cantidad++;
                return; // Cortamos la ejecución aquí porque ya lo hemos sumado
            }
        }
        // Si el bucle termina y no lo ha encontrado, lo añade como un producto nuevo
        productosEnCarrito.add(new CartItem(nombre, precio, 1));
    }

    // 4. Método para restar cantidad o eliminar el producto si llega a 0
    public static void quitarProducto(String nombre) {
        for (int i = 0; i < productosEnCarrito.size(); i++) {
            CartItem item = productosEnCarrito.get(i);
            if (item.nombre.equals(nombre)) {
                item.cantidad--;
                if (item.cantidad <= 0) {
                    productosEnCarrito.remove(i); // Lo borra de la lista si la cantidad es 0
                }
                return;
            }
        }
    }

    // 5. Calculadora del precio total en tiempo real
    public static double obtenerPrecioTotal() {
        double total = 0.0;
        for (CartItem item : productosEnCarrito) {
            total += (item.precioBase * item.cantidad);
        }
        return total;
    }

    // 6. Calculadora del número total de platos (Para el globo rojo del carrito)
    public static int obtenerTotalItems() {
        int total = 0;
        for (CartItem item : productosEnCarrito) {
            total += item.cantidad;
        }
        return total;
    }

    // 7. Vaciar para cuando paguemos
    public static void vaciarCarrito() {
        productosEnCarrito.clear();
    }
}