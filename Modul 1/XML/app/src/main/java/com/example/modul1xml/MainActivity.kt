package com.example.modul1xml

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class XmlActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dice1 = findViewById<ImageView>(R.id.imageViewDice1)
        val dice2 = findViewById<ImageView>(R.id.imageViewDice2)
        val button = findViewById<Button>(R.id.button)
        val message = findViewById<TextView>(R.id.textViewMessage)

        button.setOnClickListener {
            val result1 = (1..6).random()
            val result2 = (1..6).random()

            dice1.setImageResource(when (result1) {
                1 -> R.drawable.dice_1
                2 -> R.drawable.dice_2
                3 -> R.drawable.dice_3
                4 -> R.drawable.dice_4
                5 -> R.drawable.dice_5
                else -> R.drawable.dice_6
            })

            dice2.setImageResource(when (result2) {
                1 -> R.drawable.dice_1
                2 -> R.drawable.dice_2
                3 -> R.drawable.dice_3
                4 -> R.drawable.dice_4
                5 -> R.drawable.dice_5
                else -> R.drawable.dice_6
            })

            message.text = if (result1 == result2) "Selamat, anda dapat dadu double!"
            else "Anda belum beruntung!"

            message.visibility = TextView.VISIBLE
        }
    }
}