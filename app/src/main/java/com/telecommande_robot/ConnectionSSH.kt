package com.telecommande_robot

import com.jcraft.jsch.Channel
import com.jcraft.jsch.ChannelExec
import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session
import java.io.InputStream
import java.io.OutputStream
import java.util.*

enum class etatConnectionRobot {
    PasConnecte, ConnectionEnCours, Connecté, ConnectionMorte
}

class ConnectionSSH {
    private var etat_connection = etatConnectionRobot.PasConnecte
    var jsch = JSch()
    lateinit var channel: Channel///= session.openChannel("exec")
    lateinit var session: Session
    fun initieConnection(): Result.Success<String> {
        var i = 0
        val config = Properties()
        config.put("StrictHostKeyChecking", "no")
        config.put("PreferredAuthentications", "publickey,keyboard-interactive,password")
        session = jsch.getSession("android", "10.0.0.13", 22)
        session.setPassword("android")
        session.setConfig(config)
        session.connect(3000)//connection avec un timeout de 3 secondes
        println("Connected")
        channel = session.openChannel("exec")
        var `in`: InputStream = channel.inputStream
        val out: OutputStream = channel.outputStream
        (channel as ChannelExec).setErrStream(System.err)
        (channel as ChannelExec).setCommand("ls")
        channel.connect()
        while (channel.isConnected) {
            println("connecté")
            etat_connection = etatConnectionRobot.Connecté
            Thread.sleep(100)
        }
        while (`in`.available() > 0) {

            val lit = `in`.read().toByte()
            val formatted = String.format("%c", lit)
            println("on lit " + formatted)
            Thread.sleep(10)
        }
        if (channel.isClosed) {
            println("exit-status: " + channel.exitStatus)
        }
        try {
            Thread.sleep(1000)
        } catch (ee: Exception) {
        }
        println("has runttourne")
          channel.disconnect()
        //  session.disconnect()
        return Result.Success("okok")
    }

    fun envoie(commande : String): Result.Success<String> {
        println("on tente envoie")

       // if (etat_connection == etatConnectionRobot.Connecté) {
            var i = 0
            val config = Properties()
            config.put("StrictHostKeyChecking", "no")
            config.put("PreferredAuthentications", "publickey,keyboard-interactive,password")
            session = jsch.getSession("android", "10.0.0.13", 22)
            session.setPassword("android")
            session.setConfig(config)
            session.connect(3000)//connection avec un timeout de 3 secondes
            println("Connected")
            channel = session.openChannel("exec")
            var `in`: InputStream = channel.inputStream
            val out: OutputStream = channel.outputStream
            (channel as ChannelExec).setErrStream(System.err)
            (channel as ChannelExec).setCommand(commande)
            channel.connect()
            while (channel.isConnected) {
                println("connect")
                etat_connection = etatConnectionRobot.Connecté
              //  Thread.sleep(100)
            }
            while (`in`.available() > 0) {

                val lit = `in`.read().toByte()
                val formatted = String.format("%c", lit)
                println("on lit " + formatted)
              //  Thread.sleep(10)
            }
            channel.disconnect()
            // if (channel.isClosed) {
            println("exit-status: " + channel.exitStatus)
            // }
       // }
        // }
        return Result.Success("okok")

    }


    fun deconnecte(): Result.Success<String> {
        channel.disconnect()
        session.disconnect()

        return Result.Success("okok")
    }

}