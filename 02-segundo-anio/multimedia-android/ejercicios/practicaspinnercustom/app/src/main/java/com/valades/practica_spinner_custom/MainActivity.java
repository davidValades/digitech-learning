package com.valades.practica_spinner_custom;

import android.os.Bundle;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Spinner spBanderas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // (Código de los insets que ya tienes...)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        spBanderas = findViewById(R.id.spinner);

        String[] nombresPaises = {"España", "Francia", "Italia", "EEUU"};

        int[] imagenesBanderas = {
                R.drawable.espana,
                R.drawable.francia,
                R.drawable.italia,
                R.drawable.eeuu
        };


        BanderaAdapter miAdaptador = new BanderaAdapter(this, nombresPaises, imagenesBanderas);

        spBanderas.setAdapter(miAdaptador);
    }
}