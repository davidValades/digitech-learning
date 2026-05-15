package com.valades.smartorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class HomeMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_menu);

        Button btnStarters = findViewById(R.id.btnStarters);
        Button btnMains = findViewById(R.id.btnMains);

        View.OnClickListener navigateToList = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeMenuActivity.this, ProductListActivity.class);
                startActivity(intent);
            }
        };

        btnStarters.setOnClickListener(navigateToList);
        btnMains.setOnClickListener(navigateToList);
        // Puedes replicar esto para el resto de botones
    }
}