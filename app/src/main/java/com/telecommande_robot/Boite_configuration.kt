package com.telecommande_robot


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class Boite_configuration : AppCompatActivity() {


    lateinit var  ladresse: String
    lateinit var  nomUti: String
    lateinit var  MotPa: String

    var adresseIpPreferre: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.boite_configuration)

        //on récupère les valeurs préférées pour le préremplissage
         var adresseIpPreferre: SharedPreferences? = null

        adresseIpPreferre = getSharedPreferences("adresseIP", Context.MODE_PRIVATE)


        val adresseIP = findViewById<TextView>(R.id.adresseIP)
        var ancienneAdresse = adresseIpPreferre.getString("adresseIP", "10.0.0.1")
        adresseIP.setText(ancienneAdresse)
        val nomUtilisateur = findViewById<TextView>(R.id.nomutilisateur)
        val motdepasse = findViewById<TextView>(R.id.motdepasse)

        val annuler = findViewById<Button>(R.id.annuler)
        annuler.setBackgroundColor(Color.GRAY)
        annuler.setOnClickListener {
             println("annuler")
            annuler.setBackgroundColor(Color.GRAY)
            adresseIP.setBackgroundColor(Color.BLUE)


        }
        val valider = findViewById<Button>(R.id.valider)
        valider.setBackgroundColor(Color.GRAY)
        valider.setOnClickListener {//on appuyé sur le bouton annulé
            adresseIP.setBackgroundColor(Color.GRAY)

            ladresse =  adresseIP.text.toString()
            println("adresse = " + ladresse)

             nomUti =  nomUtilisateur.text.toString()
            println("nom = " + nomUti)

            MotPa =  motdepasse.text.toString()
            println("mot de passe = " + MotPa)

            val returnIntent = Intent()
            returnIntent.putExtra("adresseIP", ladresse)//on envoie ici la valeur à renvoyer à l 'acitvité principale
            returnIntent.putExtra("nomUti", nomUti)//on envoie ici la valeur à renvoyer à l 'acitvité principale
            returnIntent.putExtra("motDePasse", MotPa)//on envoie ici la valeur à renvoyer à l 'acitvité principale


            //met les valeurs en persistant
            val editor: SharedPreferences.Editor = adresseIpPreferre.edit()
            editor.putString("adresseIP", ladresse);
            editor.apply();



            setResult(RESULT_OK, returnIntent)
            finish()


        }

    }


}