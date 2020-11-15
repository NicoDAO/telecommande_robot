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


    private lateinit var ladresse: String
    private lateinit var nomUti: String
    private lateinit var MotPa: String

    override fun onCreate(savedInstanceState: Bundle?) {//quand on démarre l'activité on arrive ici
        super.onCreate(savedInstanceState)
        setContentView(R.layout.boite_configuration)

        //on récupère les valeurs préférées pour le préremplissage
        var enregistremnts: SharedPreferences? = null

        enregistremnts = getSharedPreferences("adresseIP", Context.MODE_PRIVATE)


        val adresseIP = findViewById<TextView>(R.id.adresseIP)
        val ancienneAdresse =
            enregistremnts.getString("adresseIP", "10.0.0.10")//on recupère la variable persistante
        adresseIP.text = ancienneAdresse// on l'affiche à l'écran

        val nomUtilisateur = findViewById<TextView>(R.id.nomutilisateur)
        val anciennenomUtilisateur =
            enregistremnts.getString("nomUti", "Choupinette")//on recupère la variable persistante
        nomUtilisateur.text = anciennenomUtilisateur// on l'affiche à l'écran

        val motdepasse = findViewById<TextView>(R.id.motdepasse)
        val ancienMdp =
            enregistremnts.getString("motDePasse", "miaou")//on recupère la variable persistante
        motdepasse.text = ancienMdp// on l'affiche à l'écran


        val annuler = findViewById<Button>(R.id.annuler)
        annuler.setBackgroundColor(Color.GRAY)
        annuler.setOnClickListener {//si on clic sur annuler on
            println("annuler")    //arrive là
            annuler.setBackgroundColor(Color.GRAY)
            adresseIP.setBackgroundColor(Color.BLUE)
            finish()//on sort de l'acitivité sans enregistrer les valeurs saisies comme valeur par défaut

        }
        val valider = findViewById<Button>(R.id.valider)
        valider.setBackgroundColor(Color.GRAY)
        valider.setOnClickListener {//on appuyé sur le bouton annuler
            adresseIP.setBackgroundColor(Color.GRAY)

            ladresse = adresseIP.text.toString()
            println("adresse = " + ladresse)

            nomUti = nomUtilisateur.text.toString()
            println("nom = " + nomUti)

            MotPa = motdepasse.text.toString()
            println("mot de passe = " + MotPa)

            val returnIntent = Intent()
            returnIntent.putExtra(
                "adresseIP",
                ladresse
            )//on envoie ici la valeur à renvoyer à l 'acitvité principale
            returnIntent.putExtra(
                "nomUti",
                nomUti
            )//on envoie ici la valeur à renvoyer à l 'acitvité principale
            returnIntent.putExtra(
                "motDePasse",
                MotPa
            )//on envoie ici la valeur à renvoyer à l 'acitvité principale


            //enregistre les valeurs en persistant pour ne pas à avoir à les retaper à chaque fois
            val editor: SharedPreferences.Editor = enregistremnts.edit()
            editor.putString("adresseIP", ladresse)
            editor.putString("nomUti", nomUti)
            editor.putString("motDePasse", MotPa)
            editor.apply()

            setResult(RESULT_OK, returnIntent)
            finish()//on ferme l'activité et on retourne dans le programme principal
        }

    }


}