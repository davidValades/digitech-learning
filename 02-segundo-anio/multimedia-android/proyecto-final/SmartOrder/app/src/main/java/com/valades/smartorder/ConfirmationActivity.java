package com.valades.smartorder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ConfirmationActivity extends AppCompatActivity {

    private String orderId = "";
    private String restaurantId = "";
    private String tableId = "";

    private LinearLayout stepReceived, stepCooking, stepReady;
    private TextView tvStatusMessage, tvOrderId;

    // Motor de repetición
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable pollingRunnable;
    private final int INTERVALO_ACTUALIZACION = 5000; // 5 segundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        // Recuperar datos
        Intent intent = getIntent();
        if (intent != null) {
            orderId = intent.getStringExtra("ORDER_ID");
            restaurantId = intent.getStringExtra("RESTAURANT_ID");
            tableId = intent.getStringExtra("TABLE_ID");
        }

        // Referencias UI
        stepReceived = findViewById(R.id.stepReceived);
        stepCooking = findViewById(R.id.stepCooking);
        stepReady = findViewById(R.id.stepReady);
        tvStatusMessage = findViewById(R.id.tvStatusMessage);
        tvOrderId = findViewById(R.id.tvOrderId);
        Button btnBackToMenu = findViewById(R.id.btnBackToMenu);

        if (orderId != null && orderId.length() >= 4) {
            tvOrderId.setText("Pedido #" + orderId.substring(0, 4).toUpperCase());
        }

        // Botón para volver al menú y seguir pidiendo (ej: postres)
        btnBackToMenu.setOnClickListener(v -> volverAlMenu());

        // Iniciar el latido de consulta a la base de datos
        iniciarSeguimientoEnTiempoReal();

        getOnBackPressedDispatcher().addCallback(this, new androidx.activity.OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                volverAlMenu();
            }
        });
    }

    private void iniciarSeguimientoEnTiempoReal() {
        if (orderId == null || orderId.isEmpty()) return;

        pollingRunnable = new Runnable() {
            @Override
            public void run() {
                consultarEstadoPedido();
                // Se vuelve a ejecutar a sí mismo a los 5 segundos
                handler.postDelayed(this, INTERVALO_ACTUALIZACION);
            }
        };
        handler.post(pollingRunnable);
    }

    private void consultarEstadoPedido() {
        String url = NetworkConfig.SUPABASE_URL + "/rest/v1/orders?id=eq." + orderId + "&select=status";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        if (response.length() > 0) {
                            JSONObject order = response.getJSONObject(0);
                            String currentStatus = order.getString("status");
                            actualizarInterfaz(currentStatus);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> { /* Fallo silencioso para no molestar al usuario si pierde cobertura 1 segundo */ }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("apikey", NetworkConfig.SUPABASE_ANON_KEY);
                headers.put("Authorization", "Bearer " + NetworkConfig.SUPABASE_ANON_KEY);
                return headers;
            }
        };
        queue.add(request);
    }

    private void actualizarInterfaz(String status) {
        // Reiniciamos todas las transparencias
        stepReceived.setAlpha(0.3f);
        stepCooking.setAlpha(0.3f);
        stepReady.setAlpha(0.3f);

        // Encendemos según el estado que devuelve la cocina
        switch (status) {
            case "received":
                stepReceived.setAlpha(1.0f);
                tvStatusMessage.setText("El restaurante ha recibido tu pedido.");
                break;
            case "cooking":
                stepReceived.setAlpha(1.0f);
                stepCooking.setAlpha(1.0f);
                tvStatusMessage.setText("¡El chef está preparando tus platos!");
                break;
            case "ready":
                stepReceived.setAlpha(1.0f);
                stepCooking.setAlpha(1.0f);
                stepReady.setAlpha(1.0f);
                tvStatusMessage.setText("¡Tu pedido está listo! El camarero va de camino.");
                break;
            case "delivered":
                stepReceived.setAlpha(1.0f);
                stepCooking.setAlpha(1.0f);
                stepReady.setAlpha(1.0f);
                tvStatusMessage.setText("Pedido entregado. ¡Que aproveche!");
                detenerSeguimiento(); // Si ya se entregó, dejamos de gastar internet

                // LA CLAVE: Limpiamos la memoria global para que la app sepa que ya acabó
                OrderManager.limpiarPedido();
                break;
        }
    }

    private void volverAlMenu() {
        Intent intent = new Intent(ConfirmationActivity.this, HomeMenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("RESTAURANT_ID", restaurantId);
        intent.putExtra("TABLE_ID", tableId);

        // ¡ESTO ES NUEVO! Le mandamos de vuelta el ID a la carta
        intent.putExtra("ACTIVE_ORDER_ID", orderId);

        startActivity(intent);
        finish();
    }

    private void detenerSeguimiento() {
        if (handler != null && pollingRunnable != null) {
            handler.removeCallbacks(pollingRunnable);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Súper importante: Apagar el motor si el cliente cierra la app para no gastar batería
        detenerSeguimiento();
    }

}