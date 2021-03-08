import java.io.OutputStream
import java.net.Socket
import java.nio.charset.Charset
import java.util.*
import kotlin.concurrent.thread
//voir
//https://gist.github.com/Silverbaq/1fdaf8aee72b86b8c9e2bd47fd1976f4
class Client(address: String, port: Int) {
    //private val connection : Socket = Socket(address, port)
    private var adresse =address
    private var port =port

    init {
        println("Connected to server at $address on port $port")
    }
    private val connection: Socket = Socket(address, port)
    private var connected: Boolean = true
    private val reader: Scanner = Scanner(connection.getInputStream())
    private val writer: OutputStream = connection.getOutputStream()

    fun run() {
         var connection : Socket = Socket(adresse, port)
         var reader: Scanner = Scanner(connection.getInputStream())
         var writer: OutputStream = connection.getOutputStream()
       // Socket = Socket(address, port)
        thread { read() }
        while (connected) {

            val input = readLine() ?: "ahah"
            println("input =  ${connection.getInputStream().readBytes()} ")

            if ("exit" in input) {
                connected = false
                reader.close()
                connection.close()
            } else {
                val envoie = "salut"
                write(envoie)
            }
            Thread.sleep(1000)

        }

    }

    public fun write(message: String) {
        writer.write((message + '\n').toByteArray(Charset.defaultCharset()))
    }

    public fun read() {
        while (connected)
            println(reader.nextLine())
    }
}