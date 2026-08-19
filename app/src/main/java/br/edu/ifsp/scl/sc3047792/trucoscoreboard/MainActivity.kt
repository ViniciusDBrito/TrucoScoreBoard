package br.edu.ifsp.scl.sc3047792.trucoscoreboard

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

private const val PONTUACAO_MAXIMA = 12

class MainActivity : AppCompatActivity() {

    private var pontosTimeA = 0
    private var pontosTimeB = 0
    private var incrementoAtual = 1

    private lateinit var tvPontosA: TextView
    private lateinit var tvPontosB: TextView
    private lateinit var tvMaoDeOnze: TextView
    private lateinit var btnPontoA: Button
    private lateinit var btnPontoB: Button
    private lateinit var btnTruco: Button
    private lateinit var btnReiniciar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvPontosA = findViewById(R.id.tvPontosA)
        tvPontosB = findViewById(R.id.tvPontosB)
        tvMaoDeOnze = findViewById(R.id.tvMaoDeOnze)
        btnPontoA = findViewById(R.id.btnPontoA)
        btnPontoB = findViewById(R.id.btnPontoB)
        btnTruco = findViewById(R.id.btnTruco)
        btnReiniciar = findViewById(R.id.btnReiniciar)

        btnPontoA.setOnClickListener { somarPontos(isTimeA = true) }
        btnPontoB.setOnClickListener { somarPontos(isTimeA = false) }
        btnTruco.setOnClickListener { pedirTruco() }
        btnReiniciar.setOnClickListener { reiniciar() }

        atualizarInterface()
    }

    private fun maoDeOnze(): Boolean = pontosTimeA == 11 || pontosTimeB == 11

    private fun somarPontos(isTimeA: Boolean) {
        val pontosASomar = if (maoDeOnze()) 1 else incrementoAtual
        if (isTimeA) {
            pontosTimeA = (pontosTimeA + pontosASomar).coerceAtMost(PONTUACAO_MAXIMA)
        } else {
            pontosTimeB = (pontosTimeB + pontosASomar).coerceAtMost(PONTUACAO_MAXIMA)
        }
        incrementoAtual = 1
        atualizarInterface()
        verificarVencedor()
    }

    private fun pedirTruco() {
        if (!maoDeOnze()) {
            incrementoAtual = if (incrementoAtual < 3) 3 else (incrementoAtual + 3).coerceAtMost(12)
            atualizarInterface()
        }
    }

    private fun reiniciar() {
        pontosTimeA = 0
        pontosTimeB = 0
        incrementoAtual = 1
        atualizarInterface()
    }

    private fun atualizarInterface() {
        tvPontosA.text = pontosTimeA.toString()
        tvPontosB.text = pontosTimeB.toString()

        btnPontoA.text = "+$incrementoAtual"
        btnPontoB.text = "+$incrementoAtual"

        val isMaoDeOnze = maoDeOnze()
        tvMaoDeOnze.visibility = if (isMaoDeOnze) View.VISIBLE else View.GONE

        btnTruco.text = when (incrementoAtual) {
            1 -> "TRUCO! (+3)"
            3 -> "SEIS! (+6)"
            6 -> "NOVE! (+9)"
            9 -> "DOZE! (+12)"
            else -> "VALE 12!"
        }

        btnTruco.isEnabled = !isMaoDeOnze && incrementoAtual < 12
    }

    private fun verificarVencedor() {
        val vencedor = when {
            pontosTimeA >= PONTUACAO_MAXIMA -> "Time A"
            pontosTimeB >= PONTUACAO_MAXIMA -> "Time B"
            else -> null
        }

        vencedor?.let {
            AlertDialog.Builder(this)
                .setTitle("Fim de Jogo!")
                .setMessage("$it venceu a partida! 🦆")
                .setPositiveButton("OK") { _, _ -> reiniciar() }
                .setOnDismissListener { reiniciar() }
                .show()
        }
    }
}