package ru.gui.services.integration

import android.content.Context
import android.util.Log
import kotlinx.coroutines.*
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

open class  RequestTemplate(override var url: URL) : ConnexionTemplate {

    private val httpURLConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
    private val TAG="Mylog"


    override fun createGetRequest(context: Context) {

        GlobalScope.launch(Dispatchers.IO) {
            val httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.setRequestProperty("Accept", "application/json") // The format of response we want to get from the server
            httpURLConnection.requestMethod = "GET"
            httpURLConnection.doInput = true
            httpURLConnection.doOutput = false
            // Check if the connection is successful
            val responseCode = httpURLConnection.responseCode
            Log.d(TAG, responseCode.toString())
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val response = httpURLConnection.inputStream.bufferedReader()
                    .use { it.readText() }  // defaults to UTF-8
                withContext(Dispatchers.Main) {

                    // Convert raw JSON to pretty JSON using GSON library

                    Log.d(TAG, response)

                }
            } else {
                Log.e(TAG, responseCode.toString())
            }
        }

    }

    override fun getPushCode() {

    }

    override fun sendRequestToGetPushCode(context: Context, jsonData: String) {

    }

    override fun generateJsonTo(): String {
        return ""
    }

    override fun createConnexion(context: Context, jsonData: String): HashMap<String, String> {
       var  isCredentialFound = HashMap<String, String>()



        runBlocking {
            val job=GlobalScope.async(Dispatchers.Default) {

                try {
                    //httpURLConnection.doInput = true
                    httpURLConnection.doOutput = true
                    httpURLConnection.requestMethod = "POST"
                    httpURLConnection.setRequestProperty(
                        "Content-Type",
                        "application/json"
                    ) // The format of the content we're sending to the server
                    httpURLConnection.setRequestProperty("Accept", "application/json")

                    httpURLConnection.connect()
                    Log.d(TAG,"json $jsonData")
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

                            val message=jObject.getJSONObject("responseModel").getString("message")
                            val access_token=jObject.getString("access_token")
                            Log.i(TAG,"access_token ${loadRequestAnswer(access_token)}")
                           // isCredentialFound=loadCategories(message.equals("login success"))
                            isCredentialFound.put("message",loadRequestAnswer(message.toString()))
                            isCredentialFound.put("access_token",access_token)

                            Log.d(TAG,"isCredentialFound in response ok ${loadCategories(message.equals("login success"))} message $message")
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
        return isCredentialFound

    }

    suspend fun loadCategories(flag: Boolean): Boolean {
        return suspendCoroutine { cont ->
           cont.resume(flag)
        }
    }
    suspend fun loadRequestAnswer(answer: String ): String{
        return suspendCoroutine {
            cont->
            cont.resume(answer)
        }
    }



/*
    open fun createPostRequest(context: Context, paramsURL: String): Boolean {
        var isCredentialFound=false

        runBlocking {
            val job=GlobalScope.async(Dispatchers.Default){
                try {
                    httpURLConnection.doOutput=true
                    httpURLConnection.requestMethod="POST"
                    httpURLConnection.setRequestProperty(
                        "Content-Type", "application/x-www-form-urlencoded"
                    )
                    httpURLConnection.setRequestProperty(
                        "charset", "utf-8"
                    )
                    httpURLConnection.setRequestProperty(
                        "Accept", "application/json;application/x-www-form-urlencoded"
                    )

                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
    }

 */






}