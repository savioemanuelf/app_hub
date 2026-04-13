package com.example.app_hub

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView

/**
 * MainActivity - Tela principal do HUB.
 *
 * Funciona como menu principal do aplicativo, exibindo cards
 * para navegação às funcionalidades disponíveis:
 * - Placar de Basquete
 * - Calculadora Científica
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupNavigation()
    }

    /**
     * Configura a navegação dos cards para as Activities correspondentes.
     * Utiliza Intent explícito para abrir cada funcionalidade.
     */
    private fun setupNavigation() {
        // Card do Placar de Basquete
        val cardPlacar: MaterialCardView = findViewById(R.id.cardPlacar)
        cardPlacar.setOnClickListener {
            val intent = Intent(this, PlacarActivity::class.java)
            startActivity(intent)
        }

        // Card da Calculadora Científica
        val cardCalculadora: MaterialCardView = findViewById(R.id.cardCalculadora)
        cardCalculadora.setOnClickListener {
            val intent = Intent(this, CalculadoraActivity::class.java)
            startActivity(intent)
        }
    }
}