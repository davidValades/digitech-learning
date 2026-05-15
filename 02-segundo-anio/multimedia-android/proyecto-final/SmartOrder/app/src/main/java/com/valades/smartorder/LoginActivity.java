package com.valades.smartorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private CheckBox cbTerms;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        cbTerms = findViewById(R.id.cbTerms);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbTerms.isChecked()) {
                    // Navegación con Intent
                    Intent intent = new Intent(LoginActivity.this, ScanActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // 1º Uso de la clase Toast
                    Toast.makeText(LoginActivity.this, "Debes aceptar los Términos y Condiciones", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}