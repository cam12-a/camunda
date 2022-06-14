package ru.gui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import ru.gui.services.GoBack
import kotlin.math.log

class Registration : AppCompatActivity(), GoBack {

    private val  TAG="Mylog"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        Log.e(TAG,"Registration")
        goBack()
    }

    override fun onResume() {
        super.onResume()
        //setContentView(R.layout.activity_registration)
    }

    override fun onPause() {
        super.onPause()
      //setContentView(R.layout.activity_registration)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun goBack(){
        findViewById<ImageButton>(R.id.back_btn).setOnClickListener {
            startActivity(Intent(this@Registration,MainActivity::class.java))
            Log.i(TAG,"back btn")
        }
    }
}