package ru.gui.services

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.ImageView
import androidx.annotation.RequiresApi
import ru.gui.MainActivity
import ru.gui.services.integration.RequestTemplate
import ru.gui.services.integration.SendRequestToNotificationServer
import java.net.URL

open interface Logout {

    open fun logout(context: Context ,token: String?){
        val url = URL("http://172.17.158.45:8085/api/auth/logout/")
        val requestTemplate= RequestTemplate(url)
        val sendRequestToNotificationServer = SendRequestToNotificationServer(url)
        //sendRequestToNotificationServer.token=token3q
        sendRequestToNotificationServer.sendRequestToGetPushCode(context.applicationContext,sendRequestToNotificationServer.generateJsonTo(token))

    }
    open fun loadCredential(context: Context): String? {
        val sharedPreferences=  context.applicationContext.getSharedPreferences("Credential", Context.MODE_PRIVATE)
        val acccess_token= sharedPreferences.getString("access_token",null)
        return acccess_token

    }
    @RequiresApi(Build.VERSION_CODES.N)
    open fun logoutBtn(context: Context, imageView: ImageView){
        imageView.setOnClickListener {
            logout(context,loadCredential(context))
            context.applicationContext.deleteSharedPreferences("Credential")
            context.startActivity(Intent(context,MainActivity::class.java))
            Log.e("Mylog","logout ok")
        }
    }
}