package com.telecommande_robot


import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.telecommande_robot.R

class Boite_configuration : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.boite_configuration)

        val adresseIP = findViewById<TextView>(R.id.adresseIP)

        val annuler = findViewById<Button>(R.id.annuler)
        annuler.setBackgroundColor(Color.GRAY)
        annuler.setOnClickListener {
             println("annuler")
            annuler.setBackgroundColor(Color.GRAY)
            adresseIP.setBackgroundColor(Color.BLUE)


        }
        val valider = findViewById<Button>(R.id.valider)
        valider.setBackgroundColor(Color.GRAY)
        valider.setOnClickListener {
            val ladresse =  adresseIP.text
            println("valider " + ladresse)

            adresseIP.setBackgroundColor(Color.GRAY)
            finish()


        }

    }
}