package com.valades.smartorder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity {

    private String restaurantId = "";
    private String tableId = "";
    private Button btnConfirmPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Intent intentRecibido = getIntent();
        if (intentRecibido != null) {
            restaurantId = intentRecibido.getStringExtra("RESTAURANT_ID");
            tableId = intentRecibido.getStringExtra("TABLE_ID");
        }

        if (restaurantId == null || restaurantId.isEmpty()) restaurantId = "bc50330f-8ce5-4faf-a7b9-d325a10640a9";
        if (tableId == null || tableId.isEmpty()) tableId = "Mesa Virtual 1";

        ImageView btnBack = findViewById(R.id.btnBack);
        RadioGroup rgPaymentMethods = findViewById(R.id.rgPaymentMethods);
        btnConfirmPayment = findViewById(R.id.btnConfirmPayment);

        btnBack.setOnClickListener(v -> finish());

        btnConfirmPayment.setOnClickListener(v -> {
            int selectedId = rgPaymentMethods.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(this, "Por favor, selecciona un método de pago", Toast.LENGTH_SHORT).show();
                return;
            }

            if (CartManager.productosEnCarrito.isEmpty()) {
                Toast.makeText(this, "El carrito de compras está vacío", Toast.LENGTH_SHORT).show();
                return;
            }

            btnConfirmPayment.setText("Enviando comanda a cocina...");
            btnConfirmPayment.setEnabled(false);
            btnConfirmPayment.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#FC7331")));

            insertarOrdenPrincipalEnSupabase();
        });
    }

    private void insertarOrdenPrincipalEnSupabase() {
        String url = NetworkConfig.SUPABASE_URL + "/rest/v1/orders?select=*";

        JSONObject orderBody = new JSONObject();
        try {
            orderBody.put("restaurant_id", restaurantId);
            orderBody.put("table_name", tableId.contains("Mesa") ? tableId : "Mesa " + tableId);
            orderBody.put("status", "received");
            orderBody.put("total_amount", CartManager.obtenerPrecioTotal());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue queue = Volley.newRequestQueue(this);

        // USAMOS STRING REQUEST PARA QUE NO FALLE NUNCA AL PARSEAR
        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONArray arrayRespuesta = new JSONArray(response);
                        if (arrayRespuesta.length() > 0) {
                            String idGenerado = arrayRespuesta.getJSONObject(0).getString("id");
                            insertarPlatosDeLaOrden(idGenerado);
                        } else {
                            notificarErrorServidor("El servidor no devolvió ID");
                        }
                    } catch (JSONException e) {
                        notificarErrorServidor("Fallo procesando respuesta");
                    }
                },
                error -> {
                    // DIAGNÓSTICO PROFUNDO EN CASO DE ERROR
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        String bodyError = new String(error.networkResponse.data);
                        Log.e("SMARTORDER_PAGO", "ERROR DE SUPABASE: " + bodyError);
                    }
                    notificarErrorServidor("Error conectando con base de datos");
                }
        ) {
            @Override
            public byte[] getBody() {
                return orderBody.toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("apikey", NetworkConfig.SUPABASE_ANON_KEY);
                headers.put("Authorization", "Bearer " + NetworkConfig.SUPABASE_ANON_KEY);
                headers.put("Content-Type", "application/json");
                headers.put("Prefer", "return=representation");
                return headers;
            }
        };

        queue.add(request);
    }

    private void insertarPlatosDeLaOrden(String orderId) {
        String url = NetworkConfig.SUPABASE_URL + "/rest/v1/order_items";

        JSONArray itemsArray = new JSONArray();
        for (CartManager.CartItem item : CartManager.productosEnCarrito) {
            try {
                JSONObject itemObj = new JSONObject();
                itemObj.put("order_id", orderId);
                itemObj.put("name", item.nombre);
                itemObj.put("quantity", item.cantidad);
                itemObj.put("status", "pending");
                itemsArray.put(itemObj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.POST, url,
                // Le pasamos el ID a la siguiente pantalla
                response -> finalizarFlujoConExito(orderId),
                error -> {
                    if (error.networkResponse != null && (error.networkResponse.statusCode == 201 || error.networkResponse.statusCode == 200)) {
                        finalizarFlujoConExito(orderId);
                    } else {
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            Log.e("SMARTORDER_PAGO", "ERROR ITEMS: " + new String(error.networkResponse.data));
                        }
                        notificarErrorServidor("Error inyectando platos");
                    }
                }
        ) {
            @Override
            public byte[] getBody() {
                return itemsArray.toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("apikey", NetworkConfig.SUPABASE_ANON_KEY);
                headers.put("Authorization", "Bearer " + NetworkConfig.SUPABASE_ANON_KEY);
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        queue.add(request);
    }

    private void finalizarFlujoConExito(String orderId) {
        CartManager.vaciarCarrito();

        // 💾 GUARDAMOS EL PEDIDO GLOBALMENTE
        OrderManager.activeOrderId = orderId;
        OrderManager.restaurantId = restaurantId;
        OrderManager.tableId = tableId;

        Intent intent = new Intent(PaymentActivity.this, ConfirmationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // Ya no hace falta pasar PutExtra obligatoriamente, ¡pero los dejamos por seguridad!
        intent.putExtra("ORDER_ID", orderId);
        intent.putExtra("RESTAURANT_ID", restaurantId);
        intent.putExtra("TABLE_ID", tableId);

        startActivity(intent);
        finish();
    }

    private void notificarErrorServidor(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        btnConfirmPayment.setText("Confirmar Pago y Pedido");
        btnConfirmPayment.setEnabled(true);
        btnConfirmPayment.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#6946AA")));
    }
}