package com.Valades.practicamemory; // ¡OJO! Asegúrate de que esto coincide con tu paquete real

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // --- 1. Variables de la Interfaz (UI) ---
    private TextView tvPuntuacion;
    private Button btnComprobar, btnReset;
    private ImageButton[] botonesCartas = new ImageButton[12];

    private int puntuacion = 0;
    private int fallos = 0;
    private int cartaSeleccionada1 = -1;
    private int cartaSeleccionada2 = -1;
    private List<Integer> imagenesMezcladas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvPuntuacion = findViewById(R.id.tvPuntuacion);
        btnComprobar = findViewById(R.id.btnComprobar);
        btnReset = findViewById(R.id.btnReset);

        for (int i = 0; i < 12; i++) {
            String buttonID = "btnCarta" + i;
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            botonesCartas[i] = findViewById(resID);

            final int indiceCarta = i;
            botonesCartas[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cartaPulsada(indiceCarta);
                }
            });
        }

        btnComprobar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprobarPareja();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarJuego();
            }
        });

        iniciarJuego();
    }

    private void iniciarJuego() {
        puntuacion = 0;
        fallos = 0;
        cartaSeleccionada1 = -1;
        cartaSeleccionada2 = -1;
        actualizarPuntuacion();

        imagenesMezcladas = new ArrayList<>();
        int[] figuras = {
                R.drawable.circulo_amarillo,
                R.drawable.corazon_rosa,
                R.drawable.cuadrado_rojo,
                R.drawable.luna_amarilla,
                R.drawable.triangulo_verde,
                R.drawable.estrella_azul
                };
        for (int figura : figuras) {
            imagenesMezcladas.add(figura);
            imagenesMezcladas.add(figura);
        }
        Collections.shuffle(imagenesMezcladas);

        for (int i = 0; i < 12; i++) {
            botonesCartas[i].setImageResource(R.drawable.carta);

            botonesCartas[i].setEnabled(true);
        }
    }


    private void cartaPulsada(int indiceCarta) {
        if (cartaSeleccionada1 != -1 && cartaSeleccionada2 != -1) {
            Toast.makeText(this, "Pulsa comprobar primero", Toast.LENGTH_SHORT).show();
            return;
        }

        if (indiceCarta == cartaSeleccionada1) {
            return;
        }

        int idImagenDescubierta = imagenesMezcladas.get(indiceCarta);
        botonesCartas[indiceCarta].setImageResource(idImagenDescubierta);


        if (cartaSeleccionada1 == -1) {
            cartaSeleccionada1 = indiceCarta;
        } else {
            cartaSeleccionada2 = indiceCarta;
        }
    }

    private void comprobarPareja() {
        // Prevenir error
        if (cartaSeleccionada1 == -1 || cartaSeleccionada2 == -1) {
            Toast.makeText(this, "Selecciona dos cartas primero", Toast.LENGTH_SHORT).show();
            return;
        }

        // Extraer figuras y voltear
        int idFigura1 = imagenesMezcladas.get(cartaSeleccionada1);
        int idFigura2 = imagenesMezcladas.get(cartaSeleccionada2);

        //  Lógica de comparación
        if (idFigura1 == idFigura2) {
            puntuacion += 20;

            botonesCartas[cartaSeleccionada1].setEnabled(false);
            botonesCartas[cartaSeleccionada2].setEnabled(false);
            fallos = 0;

            Toast.makeText(this, "¡Pareja encontrada!", Toast.LENGTH_SHORT).show();

        } else {
            fallos++;

            botonesCartas[cartaSeleccionada1].setImageResource(R.drawable.carta);
            botonesCartas[cartaSeleccionada2].setImageResource(R.drawable.carta);

            if (fallos >= 3) {
                puntuacion -= 5;
                fallos = 0; // Reiniciamos el contador para el próximo bloque de 3 fallos
                Toast.makeText(this, "¡3 fallos seguidos! -5 puntos", Toast.LENGTH_SHORT).show();
            }
        }

        actualizarPuntuacion();

        cartaSeleccionada1 = -1;
        cartaSeleccionada2 = -1;
    }

    private void actualizarPuntuacion() {
        // Regla estricta: La puntuación no puede ser negativa
        if (puntuacion < 0) {
            puntuacion = 0;
        }

        // Actualizamos el texto en la interfaz.
        // Importante: No borres el ID que le pusiste en el XML a tu TextView (tvPuntuacion)
        tvPuntuacion.setText("Puntuación: " + puntuacion);
    }
}