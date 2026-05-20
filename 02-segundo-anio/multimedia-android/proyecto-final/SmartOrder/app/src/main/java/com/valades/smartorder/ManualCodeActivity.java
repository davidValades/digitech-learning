package com.valades.smartorder;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManualCodeActivity extends AppCompatActivity {

    private String mesaSeleccionada = "Ninguna";
    private String uuidRestauranteEncontrado = "";

    private List<String> opcionesMesas = new ArrayList<>();
    private ArrayAdapter<String> adapterMesas;
    private RequestQueue requestQueue;

    private Button btnConfirmarMesa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_code);

        requestQueue = Volley.newRequestQueue(this);

        ImageView btnBack = findViewById(R.id.btnBack);
        btnConfirmarMesa = findViewById(R.id.btnConfirmarMesa);
        final EditText etCodigoLocal = findViewById(R.id.etCodigoLocal);
        Spinner spinnerMesas = findViewById(R.id.spinnerMesas);

        // Apagamos el botón al inicio
        actualizarEstadoBotones(false);

        // --- 1. CONFIGURACIÓN INICIAL DEL DESPLEGABLE DE MESAS ---
        opcionesMesas.add("Introduce ID local primero");
        adapterMesas = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcionesMesas);
        adapterMesas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMesas.setAdapter(adapterMesas);

        spinnerMesas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mesaSeleccionada = parent.getItemAtPosition(position).toString();

                // Validación de seguridad para que no intente enviar un texto de error como ID de mesa
                boolean esMesaReal = !mesaSeleccionada.equals("Ninguna") &&
                        !mesaSeleccionada.equals("Selecciona tu mesa...") &&
                        !mesaSeleccionada.equals("Introduce ID local primero") &&
                        !mesaSeleccionada.equals("Buscando mesas...") &&
                        !mesaSeleccionada.equals("No hay mesas creadas") &&
                        !mesaSeleccionada.contains("Error") &&
                        !mesaSeleccionada.contains("🚫") &&
                        !mesaSeleccionada.contains("❌");

                actualizarEstadoBotones(esMesaReal);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // --- 2. MAGIA EN TIEMPO REAL: LISTENER DEL TECLADO ---
        etCodigoLocal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String codigoCorto = s.toString().trim().toUpperCase();

                if (codigoCorto.length() == 6) {
                    actualizarSpinner("Buscando mesas...");
                    actualizarEstadoBotones(false);
                    buscarRestauranteYMesas(codigoCorto);
                } else {
                    uuidRestauranteEncontrado = "";
                    actualizarSpinner("Introduce ID local primero");
                    actualizarEstadoBotones(false);
                }
            }
        });

        // --- 3. ACCIONES DE BOTONES ---
        btnConfirmarMesa.setOnClickListener(v -> {
            viajarAlMenu(uuidRestauranteEncontrado, mesaSeleccionada);
        });

        btnBack.setOnClickListener(v -> finish());
    }

    // Activa visualmente el botón naranja cuando la selección es correcta
    private void actualizarEstadoBotones(boolean activar) {
        btnConfirmarMesa.setEnabled(activar);
        btnConfirmarMesa.setAlpha(activar ? 1.0f : 0.4f);
    }

    // --- 4. LÓGICA DE RED (SUPABASE API REST) ---
    private void buscarRestauranteYMesas(String codigoCorto) {
        String urlRest = NetworkConfig.SUPABASE_URL + "/rest/v1/restaurants?short_code=eq." + codigoCorto + "&select=id,is_active";

        JsonArrayRequest reqRest = new JsonArrayRequest(Request.Method.GET, urlRest, null,
                response -> {
                    try {
                        if (response.length() > 0) {
                            JSONObject rest = response.getJSONObject(0);

                            if (!rest.optBoolean("is_active", true)) {
                                actualizarSpinner("Local suspendido 🚫");
                                uuidRestauranteEncontrado = "";
                                return;
                            }

                            uuidRestauranteEncontrado = rest.getString("id");
                            descargarMesasDelLocal(uuidRestauranteEncontrado);

                        } else {
                            actualizarSpinner("Código no existe ❌");
                            uuidRestauranteEncontrado = "";
                        }
                    } catch (JSONException e) {
                        actualizarSpinner("Error de lectura");
                    }
                },
                error -> actualizarSpinner("Error de conexión")
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("apikey", NetworkConfig.SUPABASE_ANON_KEY);
                headers.put("Authorization", "Bearer " + NetworkConfig.SUPABASE_ANON_KEY);
                return headers;
            }
        };

        requestQueue.add(reqRest);
    }

    private void descargarMesasDelLocal(String uuidRestaurante) {
        String urlMesas = NetworkConfig.SUPABASE_URL + "/rest/v1/tables?restaurant_id=eq." + uuidRestaurante + "&order=table_number.asc";

        JsonArrayRequest reqMesas = new JsonArrayRequest(Request.Method.GET, urlMesas, null,
                response -> {
                    try {
                        opcionesMesas.clear();
                        if (response.length() == 0) {
                            opcionesMesas.add("No hay mesas creadas");
                        } else {
                            opcionesMesas.add("Selecciona tu mesa...");
                            for (int i = 0; i < response.length(); i++) {
                                String nombreMesa = response.getJSONObject(i).getString("table_number");
                                if (nombreMesa.matches("\\d+")) {
                                    opcionesMesas.add("Mesa " + nombreMesa);
                                } else {
                                    opcionesMesas.add(nombreMesa);
                                }
                            }
                        }
                        adapterMesas.notifyDataSetChanged();
                    } catch (JSONException e) {
                        actualizarSpinner("Error leyendo mesas");
                    }
                },
                error -> actualizarSpinner("Error descargando mesas")
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("apikey", NetworkConfig.SUPABASE_ANON_KEY);
                headers.put("Authorization", "Bearer " + NetworkConfig.SUPABASE_ANON_KEY);
                return headers;
            }
        };

        requestQueue.add(reqMesas);
    }

    private void actualizarSpinner(String mensaje) {
        opcionesMesas.clear();
        opcionesMesas.add(mensaje);
        adapterMesas.notifyDataSetChanged();
    }

    private void viajarAlMenu(String uuidReal, String mesaId) {
        Intent intent = new Intent(ManualCodeActivity.this, HomeMenuActivity.class);
        intent.putExtra("RESTAURANT_ID", uuidReal);
        intent.putExtra("TABLE_ID", mesaId);
        // Borramos el historial hacia atrás para que no pueda volver aquí accidentalmente al darle atrás en el menú
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}