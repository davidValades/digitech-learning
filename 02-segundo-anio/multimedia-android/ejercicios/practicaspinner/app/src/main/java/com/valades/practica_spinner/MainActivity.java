package com.valades.practica_spinner;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText etNumero1;
    private EditText etNumero2;
    private Spinner sp;
    private Button btnCalcular;
    private TextView tvResultado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etNumero1 = findViewById(R.id.numero1);
        etNumero2 = findViewById(R.id.numero2);
        sp = findViewById(R.id.spinner);
        btnCalcular = findViewById(R.id.button);
        tvResultado = findViewById(R.id.resultado);

        String[] opciones = {"sumar", "restar", "multiplicar", "dividir"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones);
        sp.setAdapter(adapter);


        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                String texto1 = etNumero1.getText().toString();
                String texto2 = etNumero2.getText().toString();


                if (texto1.isEmpty() || texto2.isEmpty()) {
                    tvResultado.setText("Por favor, introduce ambos números");
                    return; // Cortamos la ejecución aquí para que la app no explote
                }

                // 5B. Convertir texto a número (Double para aceptar decimales)
                double num1 = Double.parseDouble(texto1);
                double num2 = Double.parseDouble(texto2);

                        String seleccion = sp.getSelectedItem().toString();

                double resultadoFinal = 0;


                if (seleccion.equals("sumar")) {
                    resultadoFinal = num1 + num2;
                } else if (seleccion.equals("restar")) {
                    resultadoFinal = num1 - num2;
                } else if (seleccion.equals("multiplicar")) {
                    resultadoFinal = num1 * num2;
                } else if (seleccion.equals("dividir")) {
                    if (num2 != 0) {
                        resultadoFinal = num1 / num2;
                    }else
                        tvResultado.setText("Error: No se puede dividir entre 0"); // para que salga en la caja ponemos el tvResultado, ya no utilizamos "sout"
                    return; // cortamos ejecucion
                }

                tvResultado.setText(String.valueOf(resultadoFinal));
            }
        });
    }
}