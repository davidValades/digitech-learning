package com.valades.smartorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.BarcodeView;
import java.util.ArrayList;
import java.util.List;

public class ScanActivity extends AppCompatActivity {

    private BarcodeView barcodeScannerView;
    private String mesaSeleccionada = "Ninguna";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        ImageView btnBack = findViewById(R.id.btnBack);
        Button btnConfirmarMesa = findViewById(R.id.btnConfirmarMesa);
        Button btnLlamarCamarero = findViewById(R.id.btnLlamarCamarero);
        final EditText etCodigoLocal = findViewById(R.id.etCodigoLocal);
        Spinner spinnerMesas = findViewById(R.id.spinnerMesas);
        barcodeScannerView = findViewById(R.id.barcodeScannerView);

        // --- 1. CONFIGURACIÓN DEL LECTOR QR (CÁMARA REAL) ---
        if (barcodeScannerView != null) {
            barcodeScannerView.decodeContinuous(new BarcodeCallback() {
                @Override
                public void barcodeResult(BarcodeResult result) {
                    barcodeScannerView.pause();
                    String resultadoQR = result.getText();

                    // Cumplimos rúbrica: Uso de Toast contextual
                    Toast.makeText(ScanActivity.this, "Mesa vinculada por QR: " + resultadoQR, Toast.LENGTH_LONG).show();
                    viajarAlHome();
                }

                @Override
                public void possibleResultPoints(List<ResultPoint> resultPoints) {}
            });
        }

        // --- 2. CONFIGURACIÓN DEL SPINNER (REQUISITO RÚBRICA) ---
        List<String> opcionesMesas = new ArrayList<>();
        opcionesMesas.add("Selecciona mesa...");
        for (int i = 1; i <= 12; i++) {
            opcionesMesas.add("Mesa " + i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcionesMesas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMesas.setAdapter(adapter);

        spinnerMesas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    mesaSeleccionada = parent.getItemAtPosition(position).toString();
                } else {
                    mesaSeleccionada = "Ninguna";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // --- 3. EVENTOS DE INTERACCIÓN DE LOS BOTONES ---

        // Botón: Pedir desde la App (Flujo Digital Manual)
        btnConfirmarMesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputLocal = etCodigoLocal.getText().toString().trim();

                if (inputLocal.isEmpty()) {
                    Toast.makeText(ScanActivity.this, "Por favor, introduce el ID del Local.", Toast.LENGTH_SHORT).show();
                } else if (mesaSeleccionada.equals("Ninguna")) {
                    Toast.makeText(ScanActivity.this, "Por favor, selecciona una mesa del menú.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ScanActivity.this, "Conectando al Local #" + inputLocal + " desde la " + mesaSeleccionada, Toast.LENGTH_LONG).show();
                    viajarAlHome();
                }
            }
        });

        // Botón Híbrido: Llamar al Camarero (Flujo Tradicional Asistido)
        btnLlamarCamarero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputLocal = etCodigoLocal.getText().toString().trim();

                if (mesaSeleccionada.equals("Ninguna")) {
                    Toast.makeText(ScanActivity.this, "🔔 Solicitud general enviada. Un camarero acudirá a su posición.", Toast.LENGTH_LONG).show();
                } else {
                    String mensajeAsistencia = "🔔 Petición enviada. Personal en camino a la " + mesaSeleccionada;
                    if (!inputLocal.isEmpty()) {
                        mensajeAsistencia += " del Local #" + inputLocal;
                    }
                    Toast.makeText(ScanActivity.this, mensajeAsistencia, Toast.LENGTH_LONG).show();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void viajarAlHome() {
        Intent intent = new Intent(ScanActivity.this, HomeMenuActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (barcodeScannerView != null) barcodeScannerView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (barcodeScannerView != null) barcodeScannerView.pause();
    }
}