package com.telecommande_robot

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ref.WeakReference
import java.util.Arrays.toString


sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}


class MainActivity : AppCompatActivity() {

    external fun stringFromJNI(): String

    val connectSSH = ConnectionSSH()

    val log_vac_coroutine = LoginViewModel(connectSSH, this)
    var etat_connection = etatConnectionRobot.PasConnecte
    fun afficheConnectionRéussie() {
        val texte_cc = findViewById<TextView>(R.id.connectSSH)

        println("majdeIhm")
        if (texte_cc != null) {

            texte_cc.text = ""
            val gauche = findViewById<Button>(R.id.gauche)
            gauche.setBackgroundColor(Color.MAGENTA)
            val droite = findViewById<Button>(R.id.droite)
            droite.setBackgroundColor(Color.MAGENTA)

            val avance = findViewById<Button>(R.id.avance)
            avance.setBackgroundColor(Color.MAGENTA)

            val recule = findViewById<Button>(R.id.recule)
            recule.setBackgroundColor(Color.MAGENTA)

            val connection = findViewById<Button>(R.id.connection)
            connection.setBackgroundColor(Color.MAGENTA)
            etat_connection = etatConnectionRobot.Connecté

            val decconnection = findViewById<Button>(R.id.deconnection)


        }
    }

    fun afficheEnvoieRéussie(result: Result<String>) {
        val texte_cc = findViewById<TextView>(R.id.editTextTextMultiLine)

        // println("envoieréusiie")
        if (texte_cc != null) {
            var reception= result.toString()
            texte_cc.text = reception
            val gauche = findViewById<Button>(R.id.gauche)
            gauche.setBackgroundColor(Color.BLUE)
            val droite = findViewById<Button>(R.id.droite)
            droite.setBackgroundColor(Color.BLUE)

            val avance = findViewById<Button>(R.id.avance)
            avance.setBackgroundColor(Color.BLUE)

            val recule = findViewById<Button>(R.id.recule)
            recule.setBackgroundColor(Color.BLUE)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // get reference to button
        val activation = findViewById<Button>(R.id.configure)
        activation.setOnClickListener {
            Toast.makeText(this@MainActivity, "configure.", Toast.LENGTH_SHORT).show()
        }

        val gauche = findViewById<Button>(R.id.gauche)
        gauche.setBackgroundColor(Color.GRAY)
        gauche.setOnClickListener {//quand on appuie sur le bouton gauche, on arrive ici
            Toast.makeText(this@MainActivity, "gauche.", Toast.LENGTH_SHORT).show()
            println("gauche.setOnClickListener")
            log_vac_coroutine.envoie(
                "sh avancegauche.sh",
                "moi"
            )//on est sur la session ssh robot, on lance le script


        }

        val droite = findViewById<Button>(R.id.droite)
        droite.setBackgroundColor(Color.GRAY)
        droite.visibility = View.INVISIBLE
        droite.setOnClickListener {//quand on appuie sur le bouton droite, on arrive ici
            Toast.makeText(this@MainActivity, "droite.", Toast.LENGTH_SHORT).show()
            println("droite.setOnClickListener")
            log_vac_coroutine.envoie(
                "sh avancedroite.sh",
                "moi"
            )//on est sur la session ssh robot, on lance le script
            droite.setBackgroundColor(Color.GRAY)

        }
        val avance = findViewById<Button>(R.id.avance)
        avance.setBackgroundColor(Color.GRAY)
        avance.setOnClickListener {//quand on appuie sur le bouton avance, on arrive ici
            Toast.makeText(this@MainActivity, "avance.", Toast.LENGTH_SHORT).show()
            println("avance.setOnClickListener")
            log_vac_coroutine.envoie(
                "sh avance.sh",
                "moi"
            )//on est sur la session ssh robot, on lance le script
            avance.setBackgroundColor(Color.GRAY)

        }
        val recule = findViewById<Button>(R.id.recule)
        recule.setBackgroundColor(Color.GRAY)
        recule.setOnClickListener {//quand on appuie sur le bouton recule, on arrive ici
            Toast.makeText(this@MainActivity, "recule.", Toast.LENGTH_SHORT).show()
            println("recule.setOnClickListener")
            log_vac_coroutine.envoie(
                "sh recule.sh ",
                "moi"
            )//on est sur la session ssh robot, on lance le script
            recule.setBackgroundColor(Color.GRAY)

        }
        val arrete = findViewById<Button>(R.id.arrete)
        recule.setBackgroundColor(Color.RED)
        recule.setOnClickListener {//quand on appuie sur le bouton recule, on arrive ici
            Toast.makeText(this@MainActivity, "recule.", Toast.LENGTH_SHORT).show()
            println("arret.setOnClickListener")
            log_vac_coroutine.envoie(
                "sh arret.sh ",
                "moi"
            )//on est sur la session ssh robot, on lance le script
            recule.setBackgroundColor(Color.GRAY)

        }
        val connection = findViewById<Button>(R.id.connection)
        connection.setOnClickListener {
            Toast.makeText(this@MainActivity, "connection.", Toast.LENGTH_SHORT).show()
            println("connection.setOnClickListener")
            log_vac_coroutine.login("coucou", "moi")
            connection.setBackgroundColor(Color.GRAY)

        }

        val deconnection = findViewById<Button>(R.id.deconnection)
        deconnection.setOnClickListener {
            Toast.makeText(this@MainActivity, "deconnection.", Toast.LENGTH_SHORT).show()
            println("deconnection.setOnClickListener")
            log_vac_coroutine.deconnecte()
            deconnection.setBackgroundColor(Color.GRAY)

        }
        val demarre = findViewById<Button>(R.id.demarreRobot)
        demarre.setOnClickListener {
            Toast.makeText(this@MainActivity, "demarre.", Toast.LENGTH_SHORT).show()
            println("demarre.setOnClickListener")
            log_vac_coroutine.envoie(
                "sh demarre_robot.sh ",
                "moi"
            )//on est sur la session ssh robot, on lance le script
            demarre.setBackgroundColor(Color.GRAY)

        }

        val PICK_CONTACT_REQUEST = 0
        val config = ConfigurationRobot()

        val configure = findViewById<Button>(R.id.configure)
        configure.setOnClickListener {
            Toast.makeText(this@MainActivity, "configure.", Toast.LENGTH_SHORT).show()
            println("configure.setOnClickListener")
            log_vac_coroutine.login("configuration", "moi")
            connection.setBackgroundColor(Color.GRAY)
            val intent = Intent(this, Boite_configuration::class.java)

            startActivityForResult(
                intent,
                2
            )//on lance la page de configuration de connection avec le robot.

        }

        // Example of a call to a native method
        findViewById<TextView>(R.id.puissancemoteur).text = stringFromJNI()
        val texte_cc = findViewById<TextView>(R.id.connectSSH)
        cacheLesBoutons()//on cache les boutons du robot tant qu'on a pas établit la connection
    }

    fun afficheTousLesBoutons() {
        val droite = findViewById<Button>(R.id.droite)
        droite.visibility = View.VISIBLE
        val gauche = findViewById<Button>(R.id.gauche)
        gauche.visibility = View.VISIBLE
        val recu = findViewById<Button>(R.id.recule)
        recu.visibility = View.VISIBLE
        val avanc = findViewById<Button>(R.id.avance)
        avanc.visibility = View.VISIBLE
        val stop = findViewById<Button>(R.id.arrete)
        stop.visibility = View.VISIBLE
        val connecte = findViewById<Button>(R.id.connection)
        connecte.visibility = View.VISIBLE
        val deconnect = findViewById<Button>(R.id.deconnection)
        deconnect.visibility = View.VISIBLE
        val demarre = findViewById<Button>(R.id.demarreRobot)
        demarre.visibility = View.VISIBLE

    }

    fun afficheLesBoutonsConnection() {
        val connecte = findViewById<Button>(R.id.connection)
        connecte.visibility = View.VISIBLE
        val deconnect = findViewById<Button>(R.id.deconnection)
        deconnect.visibility = View.VISIBLE

    }

    fun cacheLesBoutons() {
        val droite = findViewById<Button>(R.id.droite)
        droite.visibility = View.INVISIBLE
        val gauche = findViewById<Button>(R.id.gauche)
        gauche.visibility = View.INVISIBLE
        val recu = findViewById<Button>(R.id.recule)
        recu.visibility = View.INVISIBLE
        val avanc = findViewById<Button>(R.id.avance)
        avanc.visibility = View.INVISIBLE
        val stop = findViewById<Button>(R.id.arrete)
        stop.visibility = View.INVISIBLE
        val connecte = findViewById<Button>(R.id.connection)
        connecte.visibility = View.INVISIBLE
        val deconnect = findViewById<Button>(R.id.deconnection)
        deconnect.visibility = View.INVISIBLE
        val demarre = findViewById<Button>(R.id.demarreRobot)
        demarre.visibility = View.INVISIBLE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) return

        var returnString =
            data?.getStringExtra("adresseIP")//on r&cupère ici la valeur de l'acivité fille
        println("on récupère l'adresse IP = " + returnString)
        connectSSH.adresseIp = returnString.toString()

        returnString = data?.getStringExtra("nomUti")//on r&cupère ici la valeur de l'acivité fille
        println("on récupère le nom utilisateur = " + returnString)
        connectSSH.utilisateur = returnString.toString()

        returnString =
            data?.getStringExtra("motDePasse")//on r&cupère ici la valeur de l'acivité fille
        println("on récupère motDePasse = " + returnString)
        connectSSH.motdepasse = returnString.toString()
        val droite = findViewById<Button>(R.id.droite)

        afficheLesBoutonsConnection()//on affiche les boutons de connection une fois qu'on a configuré la connection

    }

    companion object {
        fun majIhm() {

        }

        fun getLaunchIntent(context: Context): Intent {
            val intent = Intent(context, MainActivity::class.java)
            return intent
        }

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }

        val resultatConnection: String = String()

        /**
         * Setup WorkManager background job to 'fetch' new network data daily.
         */
        class LoginViewModel(
            private val loginRepository: ConnectionSSH, context: MainActivity
        ) : ViewModel() {
            private val activityReference: WeakReference<MainActivity> = WeakReference(context)
            fun deconnecte() {
                loginRepository.deconnecte()
            }

            fun login(username: String, token: String) {
                val activity = activityReference.get()

                // Create a new coroutine to move the execution off the UI thread
                viewModelScope.launch(Dispatchers.IO) {
                    val result = try {
                        println("result.toString()")

                        loginRepository.initieConnection()


                    } catch (e: Exception) {
                        println("Network request failed")
                        Result.Error(Exception("Network request failed"))
                        return@launch
                    }
                     if (activity != null) {
                         withContext(Dispatchers.Main) {
                            // if(result.)
                            println("resulat= " + result.toString())
                            activity.afficheTousLesBoutons()//on affiche tous les boutons une fois qu'on a établit la connection avec le robot

                            //activity.afficheConnectionRéussie()
                         }
                    }
                }
            }

            fun envoie(commande: String, token: String) {
                // Create a new coroutine to move the execution off the UI thread
                viewModelScope.launch(Dispatchers.IO) {
                    val result = try {
                        loginRepository.envoie(commande)
                    } catch (e: Exception) {
                        Result.Error(Exception("Network request failed"))
                    }
                    val activity = activityReference.get()
                    if (activity != null) {
                        withContext(Dispatchers.Main) {
                            println( "on recoit " + result)
                            activity.afficheEnvoieRéussie(result)
                        }

                    }
                }
            }

        }


    }
}