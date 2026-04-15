package com.example.app_hub

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton

/**
 * JogoDaVelhaActivity - Jogo da Velha para dois jogadores.
 *
 * Funcionalidades:
 * - Tabuleiro 3×3 interativo
 * - Alternância entre jogadores X e O
 * - Detecção de vitória (linhas, colunas, diagonais)
 * - Detecção de empate
 * - Destaque visual da combinação vencedora
 * - Placar acumulado entre partidas
 * - Animação ao marcar célula
 */
class JogoDaVelhaActivity : AppCompatActivity() {

    private val tabuleiro = Array(3) { arrayOfNulls<String>(3) }
    private var jogadorAtual = "X"
    private var jogoEncerrado = false
    private var vitorias_x = 0
    private var vitorias_o = 0

    private lateinit var txtStatus: TextView
    private lateinit var txtPlacar: TextView
    private lateinit var celulas: Array<Array<MaterialButton>>

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemePreference.applySavedTheme(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogo_da_velha)

        txtStatus = findViewById(R.id.txtStatus)
        txtPlacar = findViewById(R.id.txtPlacar)

        celulas = arrayOf(
            arrayOf(findViewById(R.id.cell00), findViewById(R.id.cell01), findViewById(R.id.cell02)),
            arrayOf(findViewById(R.id.cell10), findViewById(R.id.cell11), findViewById(R.id.cell12)),
            arrayOf(findViewById(R.id.cell20), findViewById(R.id.cell21), findViewById(R.id.cell22))
        )

        configurarTabuleiro()
        atualizarPlacar()
        atualizarStatus()

        val btnReiniciar: MaterialButton = findViewById(R.id.btnReiniciar)
        btnReiniciar.setOnClickListener { reiniciarPartida() }
    }

    /**
     * Configura os listeners de clique para cada célula do tabuleiro.
     */
    private fun configurarTabuleiro() {
        for (i in 0..2) {
            for (j in 0..2) {
                celulas[i][j].setOnClickListener { marcarCelula(i, j) }
            }
        }
    }

    /**
     * Processa a jogada na célula (linha, coluna).
     */
    private fun marcarCelula(linha: Int, coluna: Int) {
        if (jogoEncerrado || tabuleiro[linha][coluna] != null) return

        tabuleiro[linha][coluna] = jogadorAtual
        val botao = celulas[linha][coluna]
        botao.text = jogadorAtual

        // Cor diferente para X e O
        val cor = if (jogadorAtual == "X") {
            ContextCompat.getColor(this, R.color.velha_player_x)
        } else {
            ContextCompat.getColor(this, R.color.velha_player_o)
        }
        botao.setTextColor(cor)

        // Animação de escala
        botao.scaleX = 0f
        botao.scaleY = 0f
        botao.animate()
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(200)
            .start()

        val combinacaoVencedora = verificarVitoria()
        if (combinacaoVencedora != null) {
            jogoEncerrado = true
            txtStatus.text = getString(R.string.velha_vitoria, jogadorAtual)
            txtStatus.setTextColor(ContextCompat.getColor(this, R.color.velha_win_highlight))

            if (jogadorAtual == "X") vitorias_x++ else vitorias_o++
            atualizarPlacar()

            destacarVencedor(combinacaoVencedora)
        } else if (tabuleiroCheio()) {
            jogoEncerrado = true
            txtStatus.text = getString(R.string.velha_empate)
            txtStatus.setTextColor(ContextCompat.getColor(this, R.color.hub_text_secondary_light))
        } else {
            jogadorAtual = if (jogadorAtual == "X") "O" else "X"
            atualizarStatus()
        }
    }

    /**
     * Verifica se há uma combinação vencedora no tabuleiro.
     * Retorna lista de posições (Pair<linha, coluna>) da combinação vencedora ou null.
     */
    private fun verificarVitoria(): List<Pair<Int, Int>>? {
        // Linhas
        for (i in 0..2) {
            if (tabuleiro[i][0] != null &&
                tabuleiro[i][0] == tabuleiro[i][1] &&
                tabuleiro[i][1] == tabuleiro[i][2]
            ) {
                return listOf(Pair(i, 0), Pair(i, 1), Pair(i, 2))
            }
        }
        // Colunas
        for (j in 0..2) {
            if (tabuleiro[0][j] != null &&
                tabuleiro[0][j] == tabuleiro[1][j] &&
                tabuleiro[1][j] == tabuleiro[2][j]
            ) {
                return listOf(Pair(0, j), Pair(1, j), Pair(2, j))
            }
        }
        // Diagonal principal
        if (tabuleiro[0][0] != null &&
            tabuleiro[0][0] == tabuleiro[1][1] &&
            tabuleiro[1][1] == tabuleiro[2][2]
        ) {
            return listOf(Pair(0, 0), Pair(1, 1), Pair(2, 2))
        }
        // Diagonal secundária
        if (tabuleiro[0][2] != null &&
            tabuleiro[0][2] == tabuleiro[1][1] &&
            tabuleiro[1][1] == tabuleiro[2][0]
        ) {
            return listOf(Pair(0, 2), Pair(1, 1), Pair(2, 0))
        }
        return null
    }

    /**
     * Verifica se todas as células do tabuleiro estão preenchidas.
     */
    private fun tabuleiroCheio(): Boolean {
        return tabuleiro.all { linha -> linha.all { it != null } }
    }

    /**
     * Destaca visualmente as células da combinação vencedora.
     */
    private fun destacarVencedor(posicoes: List<Pair<Int, Int>>) {
        val corVitoria = ContextCompat.getColor(this, R.color.velha_win_highlight)
        for ((linha, coluna) in posicoes) {
            celulas[linha][coluna].setTextColor(corVitoria)
            celulas[linha][coluna].animate()
                .scaleX(1.2f)
                .scaleY(1.2f)
                .setDuration(300)
                .start()
        }
    }

    /**
     * Atualiza o texto de status com o jogador atual.
     */
    private fun atualizarStatus() {
        txtStatus.text = getString(R.string.velha_vez_jogador, jogadorAtual)
        val cor = if (jogadorAtual == "X") {
            ContextCompat.getColor(this, R.color.velha_player_x)
        } else {
            ContextCompat.getColor(this, R.color.velha_player_o)
        }
        txtStatus.setTextColor(cor)
    }

    /**
     * Atualiza o placar acumulado de vitórias.
     */
    private fun atualizarPlacar() {
        txtPlacar.text = getString(R.string.velha_placar, vitorias_x, vitorias_o)
    }

    /**
     * Reinicia o tabuleiro para uma nova partida sem zerar o placar.
     */
    private fun reiniciarPartida() {
        for (i in 0..2) {
            for (j in 0..2) {
                tabuleiro[i][j] = null
                celulas[i][j].text = ""
                celulas[i][j].scaleX = 1f
                celulas[i][j].scaleY = 1f
            }
        }
        jogadorAtual = "X"
        jogoEncerrado = false
        atualizarStatus()
    }
}
