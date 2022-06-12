package ru.gui.services.integration

import android.content.Context
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import ru.gui.services.QRcodeData
import java.net.URL

class SendQRCodeToServer(private val hostname: String, private val QRCode: String, override var url: URL):
    RequestTemplate(url) {

    lateinit var qRcodeData : QRcodeData
    //private val httpURLConnection: HttpURLConnection = url.openConnection() as HttpURLConnection

    override fun generateJsonTo() : String {

        qRcodeData = QRcodeData(hostname,QRCode)
        var objectMapper = ObjectMapper()
        var rootNode: ObjectNode = objectMapper.createObjectNode()

        rootNode.put("hostname",hostname)
        rootNode.put("qrCode",QRCode)

        return  objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode)

    }

/*
    override fun createConnexion(context: Context) {

        //url= URL(hostname)
        GlobalScope.launch(Dispatchers.IO) {

            try {
                httpURLConnection.doOutput = true
                httpURLConnection.requestMethod = "POST"
                httpURLConnection.setRequestProperty(
                    "Content-Type",
                    "application/json"
                ) // The format of the content we're sending to the server
                httpURLConnection.setRequestProperty("Accept", "application/json")

               // httpURLConnection.connect()

                // Send the JSON we created
               val outputStreamWriter = OutputStreamWriter(httpURLConnection.outputStream)
                outputStreamWriter.write(generateJsonTo())
                outputStreamWriter.flush()
                outputStreamWriter.close()
                val responseCode = httpURLConnection.responseCode
                println("responseCode $responseCode")
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val response = httpURLConnection.inputStream.bufferedReader()
                        .use { it.readText() }  // defaults to UTF-8
                    withContext(Dispatchers.Main) {
                        Log.d("MYlog","Pretty Printed JSON : $response")
                        println("response $response")

                    }
                }
                else {
                    Log.e("Mylog", "error of submitted")
                }


            } catch (e: Exception) {

               // Toast.makeText(context, "Отсуствует соединения к интернету", Toast.LENGTH_SHORT).show()
               // Toast.makeText(context,"asad",Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }


    override fun createGetRequest(context: Context) {
        GlobalScope.launch(Dispatchers.IO) {
            val httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.setRequestProperty("Accept", "application/json") // The format of response we want to get from the server
            httpURLConnection.requestMethod = "GET"
            httpURLConnection.doInput = true
            httpURLConnection.doOutput = false
            // Check if the connection is successful
            val responseCode = httpURLConnection.responseCode
            Log.d("MYlog", responseCode.toString())
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val response = httpURLConnection.inputStream.bufferedReader()
                    .use { it.readText() }  // defaults to UTF-8
                withContext(Dispatchers.Main) {

                    // Convert raw JSON to pretty JSON using GSON library

                    Log.d("MYlog", response)

                }
            } else {
                Log.e("MYlog", responseCode.toString())
            }
        }
    }


 */

    override fun getPushCode() {

    }

    override fun sendRequestToGetPushCode(context: Context, jsonData: String) {
        TODO("Not yet implemented")
    }

}