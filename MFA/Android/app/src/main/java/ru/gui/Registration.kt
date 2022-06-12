package ru.gui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Registration : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
    }

    override fun onResume() {
        super.onResume()
        setContentView(R.layout.activity_registration)
    }

    override fun onPause() {
        super.onPause()
       setContentView(R.layout.activity_registration)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}