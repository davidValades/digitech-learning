package com.valades.smartorder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.BarcodeView;

import java.util.List;

public class ScanActivity extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 200;
    private BarcodeView barcodeScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        ImageView btnBack = findViewById(R.id.btnBack);
        barcodeScannerView = findViewById(R.id.barcodeScannerView);

        // --- 0. SOLICITUD DE PERMISOS ---
        solicitarPermisoCamara();

        // --- 1. QR: EL ESCÁNER ÓPTICO ---
        if (barcodeScannerView != null) {
            barcodeScannerView.decodeContinuous(new BarcodeCallback() {
                @Override
                public void barcodeResult(BarcodeResult result) {
                    barcodeScannerView.pause();
                    String resultadoQR = result.getText();

                    try {
                        Uri uri = Uri.parse(resultadoQR);
                        List<String> pathSegments = uri.getPathSegments();

                        // Formato esperado de QR: smartorder://m/{RestaurantID}/{TableID}
                        if (pathSegments.size() >= 3 && pathSegments.get(0).equals("m")) {
                            String restaurantId = pathSegments.get(1);
                            String tableId = pathSegments.get(2);
                            viajarAlMenu(restaurantId, tableId);
                        } else {
                            Toast.makeText(ScanActivity.this, "Código QR no válido", Toast.LENGTH_SHORT).show();
                            barcodeScannerView.resume();
                        }
                    } catch (Exception e) {
                        Toast.makeText(ScanActivity.this, "Error leyendo QR", Toast.LENGTH_SHORT).show();
                        barcodeScannerView.resume();
                    }
                }
            });
        }

        // Botón físico/visual de retroceso
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }

    private void viajarAlMenu(String uuidReal, String mesaId) {
        Intent intent = new Intent(ScanActivity.this, HomeMenuActivity.class);
        intent.putExtra("RESTAURANT_ID", uuidReal);
        intent.putExtra("TABLE_ID", mesaId);
        // Reseteamos la pila para que al darle atrás en el menú no vuelva a la cámara
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            if (barcodeScannerView != null) barcodeScannerView.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (barcodeScannerView != null) barcodeScannerView.pause();
    }

    private void solicitarPermisoCamara() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (barcodeScannerView != null) barcodeScannerView.resume();
            } else {
                Toast.makeText(this, "Se requiere permiso de cámara para escanear QR", Toast.LENGTH_LONG).show();
            }
        }
    }
}