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
        val nomUtilisateur = findViewById<TextView>(R.id.nomutilisateur)
        val motdepasse = findViewById<TextView>(R.id.motdepasse)

        val annuler = findViewById<Button>(R.id.annuler)
        annuler.setBackgroundColor(Color.GRAY)
        annuler.setOnClickListener {
             println("annuler")
            annuler.setBackgroundColor(Color.GRAY)
            adresseIP.setBackgroundColor(Color.BLUE)


        }
      //  String ladresse
        val valider = findViewById<Button>(R.id.valider)
        valider.setBackgroundColor(Color.GRAY)
        valider.setOnClickListener {//on appuyé sur le bouton annulé
            adresseIP.setBackgroundColor(Color.GRAY)

            val ladresse =  adresseIP.text.toString()
            println("adresse = " + ladresse)

            val nomUti =  nomUtilisateur.text.toString()
            println("nom = " + nomUti)

            val MotPa =  motdepasse.text.toString()
            println("mot de passe = " + MotPa)

            val returnIntent = Intent()
            returnIntent.putExtra("adresseIP", ladresse)//on envoie ici la valeur à renvoyer à l 'acitvité principale
            returnIntent.putExtra("nomUti", nomUti)//on envoie ici la valeur à renvoyer à l 'acitvité principale
            returnIntent.putExtra("motDePasse", MotPa)//on envoie ici la valeur à renvoyer à l 'acitvité principale

            setResult(RESULT_OK, returnIntent)
            finish()


        }

    }
}