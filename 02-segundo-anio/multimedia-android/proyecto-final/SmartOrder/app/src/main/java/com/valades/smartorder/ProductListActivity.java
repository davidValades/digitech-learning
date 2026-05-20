package com.valades.smartorder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProductListActivity extends AppCompatActivity {

    private static final String TAG = "SMARTORDER_RED";
    private String restaurantId = "";
    private String tableId = "";
    private String categoriaSeleccionada = "";
    private String categoryId = "";

    private View cardProduct1, cardProduct2, cardProduct3;
    private TextView tvName1, tvPrice1, tvName2, tvPrice2, tvName3, tvPrice3;
    private ImageView ivProduct1, ivProduct2, ivProduct3;
    private TextView tvRestaurantName;
    private View navHome, navScan, navProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        Intent intentRecibido = getIntent();
        if (intentRecibido != null) {
            categoryId = intentRecibido.getStringExtra("CATEGORY_ID");
            restaurantId = intentRecibido.getStringExtra("RESTAURANT_ID");
            tableId = intentRecibido.getStringExtra("TABLE_ID");
            categoriaSeleccionada = intentRecibido.getStringExtra("CATEGORIA");
        }

        // Fallback para pruebas
        if (restaurantId == null || restaurantId.isEmpty()) {
            restaurantId = "bc50330f-8ce5-4faf-a7b9-d325a10640a9";
            tableId = "Mesa Virtual 1";
        }

        TextView tvCategoryTitle = findViewById(R.id.tvCategoryTitle);
        tvRestaurantName = findViewById(R.id.tvRestaurantName);
        if (tvCategoryTitle != null) tvCategoryTitle.setText(categoriaSeleccionada);

        cardProduct1 = findViewById(R.id.cardProduct1); cardProduct2 = findViewById(R.id.cardProduct2); cardProduct3 = findViewById(R.id.cardProduct3);
        tvName1 = findViewById(R.id.tvName1); tvPrice1 = findViewById(R.id.tvPrice1);
        tvName2 = findViewById(R.id.tvName2); tvPrice2 = findViewById(R.id.tvPrice2);
        tvName3 = findViewById(R.id.tvName3); tvPrice3 = findViewById(R.id.tvPrice3);
        ivProduct1 = findViewById(R.id.ivProduct1); ivProduct2 = findViewById(R.id.ivProduct2); ivProduct3 = findViewById(R.id.ivProduct3);

        cardProduct1.setVisibility(View.GONE);
        cardProduct2.setVisibility(View.GONE);
        cardProduct3.setVisibility(View.GONE);

        // --- INICIO ELASTIC HEADER ---
        final View layoutHeader = findViewById(R.id.layoutHeader);
        View productScrollView = findViewById(R.id.productScrollView);

        if (productScrollView != null && layoutHeader != null) {
            if (productScrollView instanceof android.widget.ScrollView) {
                ((android.widget.ScrollView) productScrollView).setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                    if (scrollY > 10) {
                        layoutHeader.setBackgroundResource(R.drawable.bg_header_flat);
                        layoutHeader.setElevation(8f);
                    } else {
                        layoutHeader.setBackgroundResource(R.drawable.bg_header_curved);
                        layoutHeader.setElevation(0f);
                    }
                });
            }
        }
        // --- FIN ELASTIC HEADER ---

        if (restaurantId != null && !restaurantId.isEmpty()) {
            descargarNombreRestaurante();
            descargarCartaDeSupabase();
        }

        ImageView btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) btnBack.setOnClickListener(v -> finish());

        navHome = findViewById(R.id.navHome);
        navScan = findViewById(R.id.navScan);
        navProfile = findViewById(R.id.navProfile);

        if (navHome != null) navHome.setOnClickListener(v -> {
            Intent intent = new Intent(ProductListActivity.this, HomeMenuActivity.class);
            intent.putExtra("RESTAURANT_ID", restaurantId);
            intent.putExtra("TABLE_ID", tableId);
            startActivity(intent);
        });
        if (navScan != null) navScan.setOnClickListener(v -> startActivity(new Intent(ProductListActivity.this, ScanActivity.class)));
        if (navProfile != null) navProfile.setOnClickListener(v -> startActivity(new Intent(ProductListActivity.this, ProfileActivity.class)));

        View btnHeaderCartContainer = findViewById(R.id.btnHeaderCartContainer);
        if (btnHeaderCartContainer != null) {
            btnHeaderCartContainer.setOnClickListener(v -> {
                Intent intent = new Intent(ProductListActivity.this, CartActivity.class);
                intent.putExtra("RESTAURANT_ID", restaurantId);
                intent.putExtra("TABLE_ID", tableId);
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
                            String nombreRestaurante = response.getJSONObject(0).getString("name");
                            if (tvRestaurantName != null) {
                                tvRestaurantName.setText(nombreRestaurante);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.e(TAG, "Error cargando nombre del restaurante")
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

    private void descargarCartaDeSupabase() {
        String url = NetworkConfig.SUPABASE_URL + "/rest/v1/menu_items?restaurant_id=eq." + restaurantId + "&category_id=eq." + categoryId + "&is_available=eq.true&order=created_at.desc";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject plato = response.getJSONObject(i);
                            String nombre = plato.getString("name");
                            double precio = plato.getDouble("price");
                            String urlImagen = plato.optString("image_url", "");
                            String extrasJsonArray = plato.optString("extras", "[]");

                            if (i == 0) {
                                tvName1.setText(nombre);
                                tvPrice1.setText(String.format("%.2f €", precio));
                                cardProduct1.setVisibility(View.VISIBLE);
                                cargarImagen(urlImagen, ivProduct1);
                                cardProduct1.setOnClickListener(v -> abrirDetalleProducto(nombre, precio, urlImagen, extrasJsonArray));
                            } else if (i == 1) {
                                tvName2.setText(nombre);
                                tvPrice2.setText(String.format("%.2f €", precio));
                                cardProduct2.setVisibility(View.VISIBLE);
                                cargarImagen(urlImagen, ivProduct2);
                                cardProduct2.setOnClickListener(v -> abrirDetalleProducto(nombre, precio, urlImagen, extrasJsonArray));
                            } else if (i == 2) {
                                tvName3.setText(nombre);
                                tvPrice3.setText(String.format("%.2f €", precio));
                                cardProduct3.setVisibility(View.VISIBLE);
                                cargarImagen(urlImagen, ivProduct3);
                                cardProduct3.setOnClickListener(v -> abrirDetalleProducto(nombre, precio, urlImagen, extrasJsonArray));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(this, "Error de red al cargar la carta", Toast.LENGTH_SHORT).show()
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

    private void cargarImagen(String urlImagen, ImageView imageView) {
        if (urlImagen == null || urlImagen.isEmpty() || imageView == null) return;

        if (urlImagen.startsWith("data:image")) {
            try {
                String base64Image = urlImagen.split(",")[1];
                byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imageView.setImageBitmap(decodedByte);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Picasso.get().load(urlImagen).placeholder(R.drawable.bg_header_flat).into(imageView);
        }
    }

    private void abrirDetalleProducto(String nombre, double precio, String urlImagen, String extrasJsonArray) {
        Intent intent = new Intent(ProductListActivity.this, ProductDetailActivity.class);
        intent.putExtra("PRODUCTO_NOMBRE", nombre);
        intent.putExtra("PRODUCTO_PRECIO", String.valueOf(precio));

        // --- SOLUCIÓN AL ERROR DE MEMORIA (TransactionTooLargeException) ---
        if (urlImagen != null && urlImagen.startsWith("data:image")) {
            // Si es una imagen Base64 gigante, la guardamos en la memoria global
            ImageProvider.currentBase64Image = urlImagen;
            // Y al Intent solo le pasamos una contraseña o etiqueta ligerita
            intent.putExtra("PRODUCTO_IMAGEN", "USE_PROVIDER");
        } else {
            // Si es una URL normal (https://...), es un texto corto, se puede pasar normal
            intent.putExtra("PRODUCTO_IMAGEN", urlImagen);
        }

        intent.putExtra("PRODUCTO_EXTRAS", extrasJsonArray);
        intent.putExtra("RESTAURANT_ID", restaurantId);
        intent.putExtra("TABLE_ID", tableId);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView tvCartBadge = findViewById(R.id.tvCartBadge);
        if (tvCartBadge != null) {
            int totalItems = CartManager.obtenerTotalItems();
            tvCartBadge.setVisibility(totalItems > 0 ? View.VISIBLE : View.GONE);
            tvCartBadge.setText(String.valueOf(totalItems));
        }
    }
}