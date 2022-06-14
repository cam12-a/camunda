package ru.gui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import ru.gui.services.GoBack

class LoginRegistration : AppCompatActivity(), GoBack {

    private val  TAG="Mylog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_registration)
        goBack()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onRestart() {
        super.onRestart()
    }
    override fun goBack(){
        findViewById<ImageButton>(R.id.back_btn).setOnClickListener {
            startActivity(Intent(this@LoginRegistration,MainActivity::class.java))
            Log.i(TAG,"back btn")
        }
    }
}