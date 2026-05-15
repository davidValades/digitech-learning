package com.valades.smartorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Referencias
        LinearLayout navHome = findViewById(R.id.navHome);
        LinearLayout navScan = findViewById(R.id.navScan);
        Button btnLogout = findViewById(R.id.btnLogout);
        Switch switchDarkMode = findViewById(R.id.switchDarkMode);

        // Lógica del Footer (Navegación)
        navHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Viajamos a Home y limpiamos la pila para que no se apilen ventanas
                Intent intent = new Intent(ProfileActivity.this, HomeMenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        navScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ScanActivity.class);
                startActivity(intent);
            }
        });

        // Lógica del Modo Oscuro (Requisito Rúbrica)
        // (Esto es una implementación básica. Para que funcione en toda la app se requiere configurar themes.xml)
        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                Toast.makeText(ProfileActivity.this, "Modo Oscuro activado", Toast.LENGTH_SHORT).show();
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                Toast.makeText(ProfileActivity.this, "Modo Claro activado", Toast.LENGTH_SHORT).show();
            }
        });

        // Lógica de Cerrar Sesión
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Vaciamos el carrito por seguridad
                CartManager.vaciarCarrito();

                Toast.makeText(ProfileActivity.this, "Sesión cerrada correctamente", Toast.LENGTH_SHORT).show();

                // Aquí deberías redirigir a tu LoginActivity
                // Si la llamaste LoginActivity, usa esto:
                /*
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                */
            }
        });
    }
}