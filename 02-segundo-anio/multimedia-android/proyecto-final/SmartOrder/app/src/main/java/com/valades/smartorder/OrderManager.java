package com.valades.smartorder;

public class OrderManager {
    public static String activeOrderId = "";
    public static String restaurantId = "";
    public static String tableId = "";

    public static boolean tienePedidoActivo() {
        return activeOrderId != null && !activeOrderId.isEmpty();
    }

    public static void limpiarPedido() {
        activeOrderId = "";
        restaurantId = "";
        tableId = "";
    }
}