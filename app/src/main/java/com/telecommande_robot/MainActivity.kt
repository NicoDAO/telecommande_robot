package com.telecommande_robot

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jcraft.jsch.Channel
import com.jcraft.jsch.ChannelExec
import com.jcraft.jsch.JSch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.OutputStream
import java.lang.Thread.sleep
import java.lang.ref.WeakReference
import java.util.*



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
    val connectSSH = LoginRepository()
    val log_vac_coroutine = LoginViewModel(connectSSH,this)

    fun majdeIhm(){
        val texte_cc = findViewById(R.id.connectSSH) as TextView

        println("majdeIhm")
        if (texte_cc != null){

           texte_cc.setText("Connection OK");
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // get reference to button
        val activation = findViewById(R.id.active) as Button
        activation.setOnClickListener {
            Toast.makeText(this@MainActivity, "activation.", Toast.LENGTH_SHORT).show()
        }
        var lance = 0
        val gauche = findViewById(R.id.gauche) as Button

        gauche.setOnClickListener {
            Toast.makeText(this@MainActivity, "gauche.", Toast.LENGTH_SHORT).show()
            println("gauche.setOnClickListener")
            // connectSSH.makeLoginRequest()
            //lance = 1;// connectSSH.makeLoginRequest()
            log_vac_coroutine.login("coucou","moi")

        }

        val droite = findViewById(R.id.droite) as Button
        droite.setOnClickListener {
            Toast.makeText(this@MainActivity, "droite.", Toast.LENGTH_SHORT).show()
            println("droite.setOnClickListener")
           // connectSSH.makeLoginRequest()
            //lance =1;// connectSSH.makeLoginRequest()
            log_vac_coroutine.login("coucou","moi")

        }
        val avance = findViewById(R.id.avance) as Button
        avance.setOnClickListener {
            Toast.makeText(this@MainActivity, "avance.", Toast.LENGTH_SHORT).show()
            println("avance.setOnClickListener")
            log_vac_coroutine.login("coucou","moi")
           // connectSSH.makeLoginRequest()
            //lance =1;// connectSSH.makeLoginRequest()

        }
        val recule = findViewById(R.id.recule) as Button
        recule.setOnClickListener {
            Toast.makeText(this@MainActivity, "recule.", Toast.LENGTH_SHORT).show()
            println("recule.setOnClickListener")
            log_vac_coroutine.login("coucou","moi")
            // connectSSH.makeLoginRequest()
            //lance =1;// connectSSH.makeLoginRequest()

        }

        // Example of a call to a native method
        findViewById<TextView>(R.id.puissancemoteur).text = stringFromJNI()
        val texte_cc = findViewById(R.id.connectSSH) as TextView

    }

    override fun onActivityReenter(resultCode: Int, data: Intent?) {
        super.onActivityReenter(resultCode, data)
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
                private val loginRepository: LoginRepository,context: MainActivity
        ) : ViewModel() {
            private val activityReference: WeakReference<MainActivity> = WeakReference(context)
            fun login(username: String, token: String) {
                // Create a new coroutine to move the execution off the UI thread
                viewModelScope.launch(Dispatchers.IO) {
                    val jsonBody = "{ username: \"$username\", token: \"$token\"}"
                    val result = try {

                        loginRepository.makeLoginRequest()
                    }catch(e: Exception) {
                        Result.Error(Exception("Network request failed"))
                    }

                    val activity = activityReference.get()
                    if (activity != null) {

                      //  activity.texte_cc.text = "coucou"
                        withContext(Dispatchers.Main) {

                            activity.majdeIhm()
                        }
                    };


                }

            }

            }
    }


    class LoginRepository() {

        fun makeLoginRequest(): Result.Success<String> {
            var i = 0;

            val config = Properties()
            config.put("StrictHostKeyChecking", "no")
            config.put("PreferredAuthentications", "publickey,keyboard-interactive,password");

            val jsch = JSch()
            val session = jsch.getSession("android", "10.0.0.13", 22)
            session.setPassword("android")
            session.setConfig(config)
            session.connect(3000)//connection avec un timeout de 3 secondes
            println("Connected")
            val channel: Channel = session.openChannel("exec")
            var `in`: InputStream = channel.getInputStream()
            val out: OutputStream = channel.getOutputStream()
            (channel as ChannelExec).setErrStream(System.err)
            (channel as ChannelExec).setCommand("ls")
            channel.connect()
            while (channel.isConnected()) {
                println("connect")
                // val lit = `in`.read()
                //println("   :" + lit)
                Thread.sleep(100);
            }
            while (`in`.available() > 0) {

                val lit = `in`.read().toByte()
                val formatted = String.format("%c", lit);
                println("on lit " + formatted)
                sleep(10)
            }
            if (channel.isClosed()) {
                println("exit-status: " + channel.getExitStatus())
            }
            try {
                sleep(1000)
            } catch (ee: Exception) {
            }
            println("has runttourne")
            channel.disconnect();
            session.disconnect();
            return Result.Success("okok")
        }
    }




}