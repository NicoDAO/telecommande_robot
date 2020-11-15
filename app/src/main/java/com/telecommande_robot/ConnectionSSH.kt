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
    private var etat_connection = com.telecommande_robot.etatConnectionRobot.PasConnecte
    var jsch = JSch()
    lateinit var channel: Channel///= session.openChannel("exec")
    lateinit var session: Session
    var etatConnectionRobot = com.telecommande_robot.etatConnectionRobot.PasConnecte

     public var adresseIp = "10.0.0.10"
    public  var utilisateur = "choupinette"
    public var motdepasse = "miaou"
    fun initieConnection(): Result.Success<String> {
       if( etatConnectionRobot == com.telecommande_robot.etatConnectionRobot.Connecté){
           deconnecte()
           return  Result.Success("deconnecte")
       }

        var i = 0
        val config = Properties()
        config.put("StrictHostKeyChecking", "no")
        config.put("PreferredAuthentications", "publickey,keyboard-interactive,password")
        session = jsch.getSession(utilisateur, adresseIp, 22)
        session.setPassword(motdepasse)
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
            etatConnectionRobot = com.telecommande_robot.etatConnectionRobot.Connecté
            Thread.sleep(100)
        }
        var formatted = ""

        while (`in`.available() > 0) {

            val lit = `in`.read().toByte()
            formatted += String.format("%c", lit)
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

            if(session.isConnected)   return Result.Success("connecte")
        else return Result.Success("pas connecte")
    }

    fun envoie(commande: String): Result.Success<String> {

        var i = 0
        val config = Properties()
        config.put("StrictHostKeyChecking", "no")
        config.put("PreferredAuthentications", "publickey,keyboard-interactive,password")
        println("Connected")
        channel = session.openChannel("exec")
        var `in`: InputStream = channel.inputStream
        val out: OutputStream = channel.outputStream
        (channel as ChannelExec).setErrStream(System.err)
        (channel as ChannelExec).setCommand(commande)
        channel.connect()
        while (channel.isConnected) {
            println("connect")
            etatConnectionRobot == com.telecommande_robot.etatConnectionRobot.Connecté
            //  Thread.sleep(100)
        }
        var formatted = ""
        while (`in`.available() > 0) {

            val lit = `in`.read().toByte()
            formatted += (String.format("%c", lit))
            //Thread.sleep(10)
        }
        println("on recoit : " + formatted)
        return Result.Success("okok")

    }


    fun deconnecte(): Result.Success<String> {
        channel.disconnect()
        session.disconnect()

        return Result.Success("deconnecte")
    }


}
