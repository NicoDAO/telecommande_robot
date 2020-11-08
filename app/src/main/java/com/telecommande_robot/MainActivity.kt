package com.telecommande_robot

import android.os.Bundle
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


sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

class MainActivity : AppCompatActivity() {
    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */

    external fun stringFromJNI(): String

    // val connectSSH = LoginRepository()
    val connectSSH = ConnectionSSH()
    val log_vac_coroutine = LoginViewModel(connectSSH, this)

    fun afficheConnectionRéussie() {
        val texte_cc = findViewById<TextView>(R.id.connectSSH)

        println("majdeIhm")
        if (texte_cc != null) {

            texte_cc.text = "Connection réussie"
        }
    }

    fun afficheEnvoieRéussie() {
        val texte_cc = findViewById<TextView>(R.id.connectSSH)

        println("envoieréusiie")
        if (texte_cc != null) {

            texte_cc.text = "Envoie réussie"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // get reference to button
        val activation = findViewById<Button>(R.id.active)
        activation.setOnClickListener {
            Toast.makeText(this@MainActivity, "activation.", Toast.LENGTH_SHORT).show()
        }

        val gauche = findViewById<Button>(R.id.gauche)
        gauche.setOnClickListener {
            Toast.makeText(this@MainActivity, "gauche.", Toast.LENGTH_SHORT).show()
            println("gauche.setOnClickListener")
            log_vac_coroutine.login("sh gauche.sh 10", "moi")

        }

        val droite = findViewById<Button>(R.id.droite)
        droite.setOnClickListener {
            Toast.makeText(this@MainActivity, "droite.", Toast.LENGTH_SHORT).show()
            println("droite.setOnClickListener")
            log_vac_coroutine.login("sh droite.sh 10", "moi")

        }
        val avance = findViewById<Button>(R.id.avance)
        avance.setOnClickListener {
            Toast.makeText(this@MainActivity, "avance.", Toast.LENGTH_SHORT).show()
            println("avance.setOnClickListener")
            log_vac_coroutine.envoie("sh avance.sh 10", "moi")
        }
        val recule = findViewById<Button>(R.id.recule)
        recule.setOnClickListener {
            Toast.makeText(this@MainActivity, "recule.", Toast.LENGTH_SHORT).show()
            println("recule.setOnClickListener")
            log_vac_coroutine.envoie("sh recule.sh 10", "moi")
         }
        val connection = findViewById<Button>(R.id.connection)
        connection.setOnClickListener {
            Toast.makeText(this@MainActivity, "connection.", Toast.LENGTH_SHORT).show()
            println("connection.setOnClickListener")
            log_vac_coroutine.login("coucou", "moi")
        }

        // Example of a call to a native method
        findViewById<TextView>(R.id.puissancemoteur).text = stringFromJNI()
        val texte_cc = findViewById<TextView>(R.id.connectSSH)
    }

    companion object {
        fun majIhm() {

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
            fun login(username: String, token: String) {
                // Create a new coroutine to move the execution off the UI thread
                viewModelScope.launch(Dispatchers.IO) {
                    val jsonBody = "{ username: \"$username\", token: \"$token\"}"
                    val result = try {
                        loginRepository.initieConnection()
                    } catch (e: Exception) {
                        Result.Error(Exception("Network request failed"))
                    }
                    val activity = activityReference.get()
                    if (activity != null) {
                        //  activity.texte_cc.text = "coucou"
                        withContext(Dispatchers.Main) {
                            activity.afficheConnectionRéussie()
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
                        //  activity.texte_cc.text = "coucou"
                        withContext(Dispatchers.Main) {
                            activity.afficheEnvoieRéussie()
                        }
                    }
                }
            }

        }
    }
}