package com.example.tictactoe

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var newGame = findViewById<Button>(R.id.button)
        newGame.setOnClickListener {
            val intent: Intent = Intent(this, NewGameActivity::class.java)
            startActivity(intent)
        }
    }
}

