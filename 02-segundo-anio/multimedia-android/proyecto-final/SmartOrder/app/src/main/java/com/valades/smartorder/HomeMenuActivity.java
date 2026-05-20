package com.valades.smartorder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HomeMenuActivity extends AppCompatActivity {

    private static final String TAG = "HomeMenuActivity";
    private String restaurantId = "";
    private String tableId = "";
    
    private TextView tvRestaurantName;
    private LinearLayout llCategoriesContainer;
    private View shimmerCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_menu);

        // 1. Recogemos los IDs del restaurante y mesa
        if (getIntent() != null) {
            restaurantId = getIntent().getStringExtra("RESTAURANT_ID");
            tableId = getIntent().getStringExtra("TABLE_ID");
        }

        // Fallback para desarrollo si no vienen IDs
        if (restaurantId == null || restaurantId.isEmpty()) {
            restaurantId = "bc50330f-8ce5-4faf-a7b9-d325a10640a9"; 
        }

        // 2. Referencias UI
        tvRestaurantName = findViewById(R.id.tvRestaurantName);
        llCategoriesContainer = findViewById(R.id.llCategoriesContainer);
        shimmerCategories = findViewById(R.id.shimmerCategories);
        
        setupNavigation();
        setupCart();
        checkActiveOrder();

        // 3. Cargar Datos
        descargarNombreRestaurante();
        descargarCategorias();
    }

    private void setupNavigation() {
        findViewById(R.id.navHome).setOnClickListener(v -> {
            Intent intent = new Intent(this, AppHomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        findViewById(R.id.navScan).setOnClickListener(v -> {
            startActivity(new Intent(this, ScanActivity.class));
        });

        findViewById(R.id.navProfile).setOnClickListener(v -> {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("RESTAURANT_ID", restaurantId);
            intent.putExtra("TABLE_ID", tableId);
            startActivity(intent);
        });
    }

    private void setupCart() {
        View btnCart = findViewById(R.id.btnHeaderCartContainer);
        if (btnCart != null) {
            btnCart.setOnClickListener(v -> {
                Intent intent = new Intent(this, CartActivity.class);
                intent.putExtra("RESTAURANT_ID", restaurantId);
                intent.putExtra("TABLE_ID", tableId);
                startActivity(intent);
            });
        }
    }

    private void checkActiveOrder() {
        LinearLayout banner = findViewById(R.id.bannerActiveOrder);
        if (banner != null && OrderManager.tienePedidoActivo()) {
            banner.setVisibility(View.VISIBLE);
            banner.setOnClickListener(v -> {
                Intent intent = new Intent(this, ConfirmationActivity.class);
                intent.putExtra("ORDER_ID", OrderManager.activeOrderId);
                startActivity(intent);
            });
        }
    }

    private void descargarNombreRestaurante() {
        String url = NetworkConfig.SUPABASE_URL + "/rest/v1/restaurants?id=eq." + restaurantId + "&select=name";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        if (response.length() > 0) {
                            tvRestaurantName.setText(response.getJSONObject(0).getString("name"));
                        }
                    } catch (JSONException e) { e.printStackTrace(); }
                },
                error -> Log.e(TAG, "Error cargando nombre")
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

    private void descargarCategorias() {
        String url = NetworkConfig.SUPABASE_URL + "/rest/v1/categories?restaurant_id=eq." + restaurantId + "&select=*&order=name.asc";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    if (shimmerCategories != null) shimmerCategories.setVisibility(View.GONE);
                    if (llCategoriesContainer != null) {
                        llCategoriesContainer.setVisibility(View.VISIBLE);
                        llCategoriesContainer.removeAllViews();
                    }

                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject cat = response.getJSONObject(i);
                            String name = cat.getString("name");
                            String id = cat.getString("id");
                            agregarFilaCategoria(name, id);
                        }
                    } catch (JSONException e) { e.printStackTrace(); }
                },
                error -> {
                    if (shimmerCategories != null) shimmerCategories.setVisibility(View.GONE);
                    Toast.makeText(this, "Error al cargar el menú", Toast.LENGTH_SHORT).show();
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

    private void agregarFilaCategoria(String nombre, String id) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_category, llCategoriesContainer, false);
        
        TextView tv = view.findViewById(R.id.tvCategoryName);
        if (tv != null) {
            tv.setText(nombre);
        }
        
        view.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProductListActivity.class);
            intent.putExtra("CATEGORY_ID", id);
            intent.putExtra("CATEGORIA", nombre);
            intent.putExtra("RESTAURANT_ID", restaurantId);
            intent.putExtra("TABLE_ID", tableId);
            startActivity(intent);
        });
        
        if (llCategoriesContainer != null) {
            llCategoriesContainer.addView(view);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizarBadgeCarrito();
    }

    private void actualizarBadgeCarrito() {
        TextView tvCartBadge = findViewById(R.id.tvCartBadge);
        if (tvCartBadge != null) {
            int total = CartManager.obtenerTotalItems();
            tvCartBadge.setVisibility(total > 0 ? View.VISIBLE : View.GONE);
            tvCartBadge.setText(String.valueOf(total));
        }
    }
}
