package com.example.app_hub

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView

/**
 * MainActivity - Tela principal do HUB.
 *
 * Funciona como menu principal do aplicativo, exibindo cards
 * para navegação às funcionalidades disponíveis:
 * - Placar de Basquete
 * - Calculadora Científica
 * - Dado
 * - Jogo da Velha
 */
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemePreference.applySavedTheme(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        applyWindowInsets(findViewById(R.id.mainScrollView))
        setupThemeToggleButton()
        setupNavigation()
    }

    private fun setupThemeToggleButton() {
        val btnThemeToggle: ImageButton = findViewById(R.id.btnThemeToggle)
        updateThemeToggleIcon(btnThemeToggle, ThemePreference.isDarkModeEnabled(this))

        btnThemeToggle.setOnClickListener {
            val isDarkModeEnabled = ThemePreference.isDarkModeEnabled(this)
            btnThemeToggle.isEnabled = false

            btnThemeToggle.animate()
                .rotationBy(180f)
                .scaleX(0.8f)
                .scaleY(0.8f)
                .setDuration(180)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .withEndAction {
                    ThemePreference.setDarkModeEnabled(this, !isDarkModeEnabled)
                    btnThemeToggle.isEnabled = true
                }
                .start()
        }
    }

    private fun updateThemeToggleIcon(button: ImageButton, isDarkModeEnabled: Boolean) {
        if (isDarkModeEnabled) {
            button.setImageResource(R.drawable.ic_light_mode)
            button.contentDescription = getString(R.string.dark_mode_switch_to_light)
        } else {
            button.setImageResource(R.drawable.ic_dark_mode)
            button.contentDescription = getString(R.string.dark_mode_switch_to_dark)
        }
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

        // Card do Dado
        val cardDado: MaterialCardView = findViewById(R.id.cardDado)
        cardDado.setOnClickListener {
            val intent = Intent(this, DadoActivity::class.java)
            startActivity(intent)
        }

        // Card do Jogo da Velha
        val cardJogoDaVelha: MaterialCardView = findViewById(R.id.cardJogoDaVelha)
        cardJogoDaVelha.setOnClickListener {
            val intent = Intent(this, JogoDaVelhaActivity::class.java)
            startActivity(intent)
        }
    }
}