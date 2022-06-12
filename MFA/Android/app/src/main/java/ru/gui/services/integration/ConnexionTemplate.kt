package ru.gui.services.integration

import android.content.Context
import java.net.URL

interface ConnexionTemplate {
    var url: URL
    fun createConnexion(context: Context, jsonData: String) : HashMap<String, String>
    fun createGetRequest(context: Context)
    fun getPushCode()
    fun sendRequestToGetPushCode(context: Context, jsonData: String)
    fun generateJsonTo() : String

}
