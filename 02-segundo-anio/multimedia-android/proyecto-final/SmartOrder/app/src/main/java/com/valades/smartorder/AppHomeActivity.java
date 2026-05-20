package com.valades.smartorder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AppHomeActivity extends AppCompatActivity {

    private TextView tvWelcomeUser;
    private LinearLayout cardScanQR, cardManualCode;
    private LinearLayout navScan, navProfile;

    // Elementos del buscador inline
    private EditText etSearchRestaurant;
    private ImageView btnSearchIcon;
    private LinearLayout llSearchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_home);

        tvWelcomeUser = findViewById(R.id.tvWelcomeUser);
        cardScanQR = findViewById(R.id.cardScanQR);
        cardManualCode = findViewById(R.id.cardManualCode);
        navScan = findViewById(R.id.navScan);
        navProfile = findViewById(R.id.navProfile);

        etSearchRestaurant = findViewById(R.id.etSearchRestaurant);
        btnSearchIcon = findViewById(R.id.btnSearchIcon);
        llSearchResults = findViewById(R.id.llSearchResults);

        SharedPreferences prefs = getSharedPreferences("SmartOrderPrefs", Context.MODE_PRIVATE);
        String userEmail = prefs.getString("USER_EMAIL", "comensal");

        if (userEmail.contains("@")) {
            userEmail = userEmail.split("@")[0];
            userEmail = userEmail.substring(0, 1).toUpperCase() + userEmail.substring(1);
        }
        tvWelcomeUser.setText("¡Hola, " + userEmail + "! 👋");

        // Lógica de búsqueda
        etSearchRestaurant.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                buscarRestaurante();
                return true;
            }
            return false;
        });

        btnSearchIcon.setOnClickListener(v -> buscarRestaurante());

        // Lógica de botones QR y Manual
        cardScanQR.setOnClickListener(v -> {
            Intent intent = new Intent(AppHomeActivity.this, ScanActivity.class);
            intent.putExtra("START_MODE", "QR");
            startActivity(intent);
        });

        cardManualCode.setOnClickListener(v -> {
            Intent intent = new Intent(AppHomeActivity.this, ManualCodeActivity.class);
            startActivity(intent);
        });

        // Footer
        navScan.setOnClickListener(v -> startActivity(new Intent(AppHomeActivity.this, ScanActivity.class)));
        navProfile.setOnClickListener(v -> startActivity(new Intent(AppHomeActivity.this, ProfileActivity.class)));

        // (Nota: Hemos movido la lógica del Banner al método onResume)
    }

    // 🟢 NUEVO: onResume asegura que el banner se actualice siempre al volver a esta pantalla
    @Override
    protected void onResume() {
        super.onResume();

        LinearLayout bannerActiveOrder = findViewById(R.id.bannerActiveOrder);
        if (bannerActiveOrder != null) {
            if (OrderManager.tienePedidoActivo()) {
                bannerActiveOrder.setVisibility(View.VISIBLE);
                bannerActiveOrder.setOnClickListener(v -> {
                    Intent trackerIntent = new Intent(AppHomeActivity.this, ConfirmationActivity.class);
                    trackerIntent.putExtra("ORDER_ID", OrderManager.activeOrderId);
                    trackerIntent.putExtra("RESTAURANT_ID", OrderManager.restaurantId);
                    trackerIntent.putExtra("TABLE_ID", OrderManager.tableId);
                    startActivity(trackerIntent);
                });
            } else {
                bannerActiveOrder.setVisibility(View.GONE);
            }
        }
    }

    private void buscarRestaurante() {
        String query = etSearchRestaurant.getText().toString().trim();
        if (query.isEmpty()) return;

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etSearchRestaurant.getWindowToken(), 0);

        llSearchResults.removeAllViews();
        llSearchResults.setVisibility(View.VISIBLE);

        TextView tvLoading = new TextView(this);
        tvLoading.setText("Buscando locales...");
        tvLoading.setPadding(0, 16, 0, 16);
        llSearchResults.addView(tvLoading);

        String url = NetworkConfig.SUPABASE_URL + "/rest/v1/restaurants?name=ilike.*" + query + "*&select=id,name";

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    llSearchResults.removeAllViews();
                    if (response.length() == 0) {
                        TextView tvError = new TextView(this);
                        tvError.setText("No se encontró ningún restaurante.");
                        tvError.setTextColor(getResources().getColor(R.color.brand_orange));
                        llSearchResults.addView(tvError);
                    } else {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject rest = response.getJSONObject(i);
                                String id = rest.getString("id");
                                String name = rest.getString("name");
                                crearTarjetaResultado(id, name);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                error -> {
                    llSearchResults.removeAllViews();
                    Toast.makeText(this, "Error de conexión", Toast.LENGTH_SHORT).show();
                }
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

    private void crearTarjetaResultado(String restaurantId, String restaurantName) {
        float density = getResources().getDisplayMetrics().density;
        LinearLayout card = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, (int) (12 * density));
        card.setLayoutParams(params);
        card.setBackgroundResource(R.drawable.bg_card);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding((int) (16 * density), (int) (16 * density), (int) (16 * density), (int) (16 * density));
        card.setElevation(2f);

        TextView tvName = new TextView(this);
        tvName.setText("🏪 " + restaurantName);
        tvName.setTextSize(18f);
        tvName.setTypeface(null, android.graphics.Typeface.BOLD);
        tvName.setTextColor(getResources().getColor(R.color.text_primary));
        card.addView(tvName);

        card.setOnClickListener(v -> mostrarDialogoOpciones(restaurantId, restaurantName));

        llSearchResults.addView(card);
    }

    private void mostrarDialogoOpciones(String restaurantId, String restaurantName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(restaurantName);
        builder.setMessage("¿Qué deseas hacer?");

        builder.setPositiveButton("🥡 Pedir para Recoger", (dialog, which) -> {
            Intent intent = new Intent(AppHomeActivity.this, HomeMenuActivity.class);
            intent.putExtra("RESTAURANT_ID", restaurantId);
            intent.putExtra("TABLE_ID", "TAKEAWAY");
            startActivity(intent);
        });

        builder.setNegativeButton("🍽️ Comer en el local", (dialog, which) -> {
            Intent intent = new Intent(AppHomeActivity.this, ScanActivity.class);
            intent.putExtra("START_MODE", "QR");
            startActivity(intent);
        });

        builder.setNeutralButton("Cancelar", null);
        builder.show();
    }
}