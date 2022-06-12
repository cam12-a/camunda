package ru.gui.services.integration

import android.content.Context
import android.util.Log
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import kotlinx.coroutines.*
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

open class SendRequestToNotificationServer(override var url: URL) : ConnexionTemplate {

    private val httpURLConnection: HttpURLConnection = url.openConnection() as HttpURLConnection

    open  var token: String = ""
        get() {return field}
        set(value) {field=token}
    private var title="Внимание вашу учетку хотят взломать"
    private var message="К вашей учетной записи хотят получить доступ"
    private var topic="credential"


    private val TAG="Mylog"
    override fun createConnexion(context: Context, jsonData: String): HashMap<String, String> {
        TODO("Not yet implemented")
    }

    override fun createGetRequest(context: Context) {
        TODO("Not yet implemented")
    }

    override fun getPushCode() {
        TODO("Not yet implemented")
    }

    override fun sendRequestToGetPushCode(context: Context, jsonData: String) {

        runBlocking {
            val job= GlobalScope.async(Dispatchers.Default) {

                try {
                    //httpURLConnection.doInput = true
                    httpURLConnection.doOutput = true
                    httpURLConnection.requestMethod = "POST"
                    httpURLConnection.setRequestProperty(
                        "Content-Type",
                        "application/json"
                    ) // The format of the content we're sending to the server
                    httpURLConnection.setRequestProperty("Accept", "application/json")

                    //httpURLConnection.connect()
                    Log.d(TAG,"json $jsonData")
                    Log.d(TAG,"here")
                    // Send the JSON we created
                    val outputStreamWriter = OutputStreamWriter(httpURLConnection.outputStream)
                    outputStreamWriter.write(jsonData)
                    outputStreamWriter.flush()
                    outputStreamWriter.close()

                    val responseCode = httpURLConnection.responseCode
                    Log.d(TAG,"responseCode $responseCode")
                    Log.d(TAG,"thread name after response code ${Thread.currentThread().name}")
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        val response = httpURLConnection.inputStream.bufferedReader()
                            .use { it.readText() }  // defaults to UTF-8
                        val job2= async(Dispatchers.Default){
                            Log.d(TAG,"Pretty Printed JSON : $response")
                            val jObject =  JSONObject(response)

                        }
                      job2.await()


                    }
                    else {
                        Log.e(TAG, "error of submitted")
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            job.await()

        }


    }

    override fun generateJsonTo(): String {

        var objectMapper = ObjectMapper()
        var rootNode: ObjectNode = objectMapper.createObjectNode()

        rootNode.put("token",token)
        rootNode.put("title",title)
        rootNode.put("message", message)
        rootNode.put("topic",topic)

        return  objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode)
    }
    fun generateJsonTo(tokens: String, username:String, password: String): String {

        var objectMapper = ObjectMapper()
        var rootNode: ObjectNode = objectMapper.createObjectNode()

        rootNode.put("username",username)
        rootNode.put("password",password)
        rootNode.put("idFirebaseMessagingCloud", tokens)


        return  objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode)
    }

    fun generateJsonTo(tokens: String?): String {

        var objectMapper = ObjectMapper()
        var rootNode: ObjectNode = objectMapper.createObjectNode()

        rootNode.put("token",tokens)



        return  objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode)
    }



}