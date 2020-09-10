package com.example.tictactoe

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_new_game.*

class NewGameActivity : AppCompatActivity() {
    private var playerOneTurn = true
    private var playerOneMoves = mutableListOf<Int>()
    private var playerTwoMoves = mutableListOf<Int>()

    private val possibleWins: Array<List<Int>> = arrayOf(
        listOf(1, 2, 3),
        listOf(4, 5, 6),
        listOf(7, 8, 9),
        listOf(1, 4, 7),
        listOf(2, 5, 8),
        listOf(3, 6, 9),
        listOf(1, 5, 9),
        listOf(3, 5, 7)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_game)
        setupBoard()
    }

    private fun setupBoard() {
        var counter = 1
        val params1 = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0)
        val params2 = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT)
        for (i in 1..3) {
            val linearLayout = LinearLayout(this)
            linearLayout.orientation = LinearLayout.HORIZONTAL
            linearLayout.layoutParams = params1
            params1.weight = 1.0F
            for (j in 1..3) {
                val button = Button(this)
                button.id = counter
                button.textSize = 42.0F
                button.layoutParams = params2
                params2.weight = 1.0F
                button.setOnClickListener {
                    recordMove(it)
                }
                linearLayout.addView(button)
                counter++
            }
            board.addView(linearLayout)
        }
    }

    private fun recordMove(view: View) {
        val button = view as Button
        val id = button.id

        if (playerOneTurn) {
            playerOneMoves.add(id)
            button.text = "O"
            button.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
            button.isEnabled = false
            if (checkWin(playerOneMoves)) {
                showWinMessage(player_one)
                Handler().postDelayed({
                    val i = Intent(this, MainActivity::class.java)
                    startActivity(i)
                }, 3000)
            } else {
                playerOneTurn = false
                togglePlayerTurn(player_two_label, player_one_label)
            }
        } else if (!playerOneTurn) {
            playerTwoMoves.add(id)
            button.text = "X"
            button.setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
            button.isEnabled = false
            if (checkWin(playerTwoMoves)) {
                showWinMessage(player_two)
                Handler().postDelayed({
                    val i = Intent(this, MainActivity::class.java)
                    startActivity(i)
                }, 3000)
            } else {
                playerOneTurn = true
                togglePlayerTurn(player_one_label, player_two_label)
            }

        }
        if ((!checkWin(playerOneMoves)) && (!checkWin(playerTwoMoves)) && (playerOneMoves.size + playerTwoMoves.size == 9)) {
            Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show()
            Handler().postDelayed({
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
            }, 3000)
        }

    }

     private fun checkWin(moves: List<Int>): Boolean {
        var won = false
        if (moves.size >= 3) {
            run loop@{
                possibleWins.forEach {
                    if (moves.containsAll(it)) {
                        won = true
                        return@loop
                    }
                }
            }

        }
        return won
    }

    private fun showWinMessage(player: EditText) {
        var playerName = player.text.toString()
        if (playerName.isBlank()) {
            playerName = player.hint.toString()
        }
        Toast.makeText(
            this, "Congratulations! $playerName You Won",
            Toast.LENGTH_SHORT
        ).show()

    }


    private fun togglePlayerTurn(playerOn: TextView, playerOff: TextView) {
        playerOff.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
        playerOff.setTypeface(null, Typeface.NORMAL)
        if (playerOn == player_one_label) {
            playerOn.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
            playerOn.setTypeface(null, Typeface.BOLD)
        }
        else {
            playerOn.setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
            playerOn.setTypeface(null, Typeface.BOLD)
        }
    }

}


