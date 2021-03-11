package com.telecommande_robot

import Client
import com.jcraft.jsch.Channel
import com.jcraft.jsch.ChannelExec
import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session
import java.io.OutputStream
import java.util.*
import java.io.InputStream as InputStream1

enum class etatConnectionRobot {
    pasdemarré, demarré, PasConnecte, ConnectionEnCours, Connecté, ConnectionMorte
}

class ConnectionSSH {
    private var etat_connection = com.telecommande_robot.etatConnectionRobot.PasConnecte
    var jsch = JSch()
    lateinit var channel: Channel///= session.openChannel("exec")
    lateinit var session: Session
    var etatConnectionRobot = com.telecommande_robot.etatConnectionRobot.PasConnecte

    var adresseIp = "10.0.0.10"
    var utilisateur = "choupinette"
    var motdepasse = "miaou"
    lateinit var client : Client

    fun initieConnection(): Result.Success<String> {
        if (etatConnectionRobot == com.telecommande_robot.etatConnectionRobot.Connecté) {
            deconnecte()
            return Result.Success("deconnecte")
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
        var `in`: InputStream1 = channel.inputStream
        val out: OutputStream = channel.outputStream
        (channel as ChannelExec).setErrStream(System.err)
        (channel as ChannelExec).setCommand("ls demarre*")
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
            println("on lit : " + formatted)
            Thread.sleep(100)
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

        if (session.isConnected) return Result.Success("connecte")
        else return Result.Success("pas connecte")
    }

    fun envoie(commande: String): Result.Success<String> {

        client.write(commande)
        Result.Success("formatted")
        return  Result.Success("formatted")
/*
        var i = 0
        val config = Properties()
        config.put("StrictHostKeyChecking", "no")
        config.put("PreferredAuthentications", "publickey,keyboard-interactive,password")
        println("on envoie la commande : " + commande)
        channel = session.openChannel("exec")
        var `in`: InputStream1 = channel.inputStream
        val out: OutputStream = channel.outputStream
        var formatted = ""
        (channel as ChannelExec).setErrStream(System.err)
        (channel as ChannelExec).setCommand(commande)
        channel.connect()
        while (channel.isConnected) {
            println("connecté,")
            etatConnectionRobot == com.telecommande_robot.etatConnectionRobot.Connecté
            //  Thread.sleep(100)


            //on laisse la session ouverte

            while (`in`.available() > 0) {

                val lit = `in`.read().toByte()
                formatted += (String.format("%c", lit))
                //Thread.sleep(10)
            }
            println("!on recoit : " + formatted)

        }


        return Result.Success(formatted)
*/
    }

    /*
    litTelemetrie, interroge le robot sur ses différentes télémetries issues des capteurs distances et gyroscope
     */
    fun litTelemetrie(commande: String): Result.Success<String> {
        //JschExecutor =
        /*var tunnel = JschExecutor()
        tunnel.go()
        return Result.Success("super")*/
        var i = 0
        val config = Properties()
        config.put("StrictHostKeyChecking", "no")
        config.put("PreferredAuthentications", "publickey,keyboard-interactive,password")
        println("Connected")
        channel = session.openChannel("exec")
        var `in`: InputStream1 = channel.inputStream
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
            Thread.sleep(10)
        }
        println("on recoit : " + formatted)
        return Result.Success(formatted)

    }

    fun demarreRobotSSH(): Result.Success<String> {

        var i = 0
        val config = Properties()
        config.put("StrictHostKeyChecking", "no")
        config.put("PreferredAuthentications", "publickey,keyboard-interactive,password")
        session = jsch.getSession(utilisateur, adresseIp, 22)
        session.setPassword(motdepasse)
        session.setConfig(config)
        session.connect(3000)//connection avec un timeout de 3 secondes

        //test


//on créé un client tcp et on redirige vers un tunnel ssh
        val address = adresseIp
        val port = 7777

         client = Client(address, port)

        //test, on redirige les requetes venant sur le port 8181 vers  le port 80
        //   session.setPortForwardingL(8080, "http://10.0.0.14", 8080)
        client.run()

        // fin test
        if (false) {

            println("ah Connected")
            val channel = session.openChannel("exec")

            val out: OutputStream = channel.outputStream
            (channel as ChannelExec).setErrStream(System.err)
            channel.setCommand("sudo -S -p  sh demarre_robot.sh")//on demarre le robot, et on rentre le mot de passe
            val value = channel.getOutputStream()
            channel.connect()

            out.write((motdepasse + "\n").toByteArray())
            out.flush()
            if (channel.isConnected) {
                println("connecté")
                etatConnectionRobot = com.telecommande_robot.etatConnectionRobot.demarré
                Thread.sleep(100)
            }

            while (false) {
                var formatted = ""
                var `in`: InputStream1 = channel.inputStream

                while (`in`.available() != -1) {

                    val lit = `in`.read().toByte()
                    formatted += String.format("%c", lit)
                    println("on lit : " + formatted)
                    // Thread.sleep(10)
                }
            }

            Thread.sleep(10)


        }


        channel.disconnect()
        session.disconnect()
        if (session.isConnected) return Result.Success("connecte")
        else return Result.Success("pas connecte")

    }


    fun deconnecte(): Result.Success<String> {
        channel.disconnect()
        session.disconnect()

        return Result.Success("deconnecte")
    }


}
