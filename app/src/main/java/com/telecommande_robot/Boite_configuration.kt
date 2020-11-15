package com.telecommande_robot


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


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


            val returnIntent = Intent()
            returnIntent.putExtra("result", "bonjour")//on envoie ici la valeur à renvoyer à l 'acitvité principale
            setResult(RESULT_OK, returnIntent)
            finish()


        }

    }
}