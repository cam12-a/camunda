package ru.gui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import ru.gui.services.Logout

class PushCode : AppCompatActivity(), Logout {

    private val chooseMFAType : ChooseMFAType= ChooseMFAType()
    private val TAG="Mylog"

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_push_code)
        var getIntentData = intent
        Log.d(TAG,"link to send "+getIntentData.getStringExtra("link").toString())

        val token: String?=loadCredential(this)
        findViewById<Button>(R.id.killSession).setOnClickListener {
            /*
            val url = URL("http://172.17.122.162:8085/api/auth/logout/")
            val requestTemplate= RequestTemplate(url)
            val sendRequestToNotificationServer = SendRequestToNotificationServer(url)
            //sendRequestToNotificationServer.token=token
            sendRequestToNotificationServer.sendRequestToGetPushCode(this@PushCode,sendRequestToNotificationServer.generateJsonTo(token))

             */
            logout(this@PushCode,token);
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






}