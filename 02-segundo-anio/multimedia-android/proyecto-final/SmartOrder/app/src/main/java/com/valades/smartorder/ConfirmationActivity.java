package com.valades.smartorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class ConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        Button btnLeaveReview = findViewById(R.id.btnLeaveReview);

        btnLeaveReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmationActivity.this, ReviewAndInfoActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}