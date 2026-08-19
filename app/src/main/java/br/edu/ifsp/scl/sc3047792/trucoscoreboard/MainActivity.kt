package br.edu.ifsp.scl.sc3047792.trucoscoreboard

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

private const val PONTUACAO_MAXIMA = 12

class MainActivity : AppCompatActivity() {

    private var pontosTimeA = 0
    private var pontosTimeB = 0

    private lateinit var tvPontosA: TextView
    private lateinit var tvPontosB: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvPontosA = findViewById(R.id.tvPontosA)
        tvPontosB = findViewById(R.id.tvPontosB)

        val btnPontoA: Button = findViewById(R.id.btnPontoA)
        val btnTrucoA: Button = findViewById(R.id.btnTrucoA)
        val btnPontoB: Button = findViewById(R.id.btnPontoB)
        val btnTrucoB: Button = findViewById(R.id.btnTrucoB)
        val btnReiniciar: Button = findViewById(R.id.btnReiniciar)

        btnPontoA.setOnClickListener {
            pontosTimeA = adicionarPontos(pontosTimeA, 1)
            atualizarPlacar()
        }

        btnTrucoA.setOnClickListener {
            pontosTimeA = adicionarPontos(pontosTimeA, 3)
            atualizarPlacar()
        }

        btnPontoB.setOnClickListener {
            pontosTimeB = adicionarPontos(pontosTimeB, 1)
            atualizarPlacar()
        }

        btnTrucoB.setOnClickListener {
            pontosTimeB = adicionarPontos(pontosTimeB, 3)
            atualizarPlacar()
        }

        btnReiniciar.setOnClickListener {
            pontosTimeA = 0
            pontosTimeB = 0
            atualizarPlacar()
        }
    }

    private fun atualizarPlacar() {
        tvPontosA.text = pontosTimeA.toString()
        tvPontosB.text = pontosTimeB.toString()
    }

    private fun adicionarPontos(pontosAtuais: Int, incremento: Int): Int {
        val novoTotal = pontosAtuais + incremento
        return if (novoTotal > PONTUACAO_MAXIMA) PONTUACAO_MAXIMA else novoTotal
    }
}