package com.example.app_hub

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class DadoActivity : AppCompatActivity() {

    private var resultado = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dado)

        val imgDado: ImageView = findViewById(R.id.imgDado)
        val btnRolar: MaterialButton = findViewById(R.id.btnRolar)

        btnRolar.setOnClickListener {
            resultado = (1..6).random()
            val imageResource = when (resultado) {
                1 -> R.drawable.dice_1
                2 -> R.drawable.dice_2
                3 -> R.drawable.dice_3
                4 -> R.drawable.dice_4
                5 -> R.drawable.dice_5
                else -> R.drawable.dice_6
            }
            imgDado.setImageResource(imageResource)
        }
    }
}
