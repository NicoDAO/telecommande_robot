package com.telecommande_robot

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jcraft.jsch.Channel
import com.jcraft.jsch.ChannelExec
import com.jcraft.jsch.JSch
import java.io.InputStream
import java.io.OutputStream
import java.lang.Thread.sleep
import java.util.*


class MainActivity : AppCompatActivity() {
    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // get reference to button
        val activation = findViewById(R.id.active) as Button
        activation.setOnClickListener {
            Toast.makeText(this@MainActivity, "activation.", Toast.LENGTH_SHORT).show()
        }
/*
        val accelere = findViewById(R.id.accelerate) as Button
        accelere.setOnClickListener {
            Toast.makeText(this@MainActivity, "accelere.", Toast.LENGTH_SHORT).show()
        }
*/
        val gauche = findViewById(R.id.gauche) as Button
        gauche.setOnClickListener {
            Toast.makeText(this@MainActivity, "gauche.", Toast.LENGTH_SHORT).show()
            println("gauche.setOnClickListener")

        }

        // Example of a call to a native method
        findViewById<TextView>(R.id.puissancemoteur).text = stringFromJNI()
        val texte = findViewById(R.id.connectSSH) as TextView

        Thread(Runnable {
            // performing some dummy time taking operation
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
                println("connect" )
               // val lit = `in`.read()
                //println("   :" + lit)
                Thread.sleep(100);
            }
             //out.write("ls "+"\n").;
           // val charset = Charsets.UTF_8
           // val byteArray = "ls\n".toByteArray(charset)
           // out.write(byteArray)
           // out.flush();
            while (`in`.available() > 0) {

                val lit = `in`.read().toByte()
                val formatted = String.format("%c", lit) ;
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

            // gauche.setText("gg " + i)
            println("has runttourne")
            //  texte.setText("ee");
            //  sleep(1000);

            channel.disconnect();
            session.disconnect();
            // try to touch View of UI thread
            this@MainActivity.runOnUiThread(java.lang.Runnable {
                // texte.setText("cocc")
            })
        }).start()
        val thread = SimpleThread()
        //  thread.start()
    }

    override fun onActivityReenter(resultCode: Int, data: Intent?) {
        super.onActivityReenter(resultCode, data)
    }

    companion object {
        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }

    class SimpleThread : Thread() {

        public override fun run() {
            while (true) {
                println("${Thread.currentThread()} has run.")

                sleep(1000);
            }
        }
    }

}



