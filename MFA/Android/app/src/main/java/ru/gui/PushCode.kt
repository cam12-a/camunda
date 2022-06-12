package ru.gui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import ru.gui.services.integration.SendRequestToNotificationServer
import ru.gui.services.integration.RequestTemplate
import java.net.URL

class PushCode : AppCompatActivity() {

    private val chooseMFAType : ChooseMFAType= ChooseMFAType()
    private val TAG="Mylog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_push_code)
        var getIntentData = intent
        Log.d(TAG,"link to send "+getIntentData.getStringExtra("link").toString())
        //Toast.makeText(this,getIntentData.getStringExtra("username").toString(),Toast.LENGTH_SHORT).show()
        val token: String?=loadCredential()
        findViewById<Button>(R.id.killSession).setOnClickListener {
            val url = URL("http://172.17.122.162:8085/api/auth/logout/")
            val requestTemplate= RequestTemplate(url)
            val sendRequestToNotificationServer = SendRequestToNotificationServer(url)
            //sendRequestToNotificationServer.token=token
            sendRequestToNotificationServer.sendRequestToGetPushCode(this@PushCode,sendRequestToNotificationServer.generateJsonTo(token))

        }
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun loadCredential(): String? {
        val sharedPreferences= getSharedPreferences("Credential", Context.MODE_PRIVATE)
        val acccess_token= sharedPreferences.getString("access_token",null)

        Toast.makeText(this,acccess_token,Toast.LENGTH_SHORT).show()
        return acccess_token

    }



}