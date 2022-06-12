package ru.gui

import android.content.Context
import android.content.Intent

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import ru.gui.dataBaseService.Database
import ru.gui.services.CheckDevice


class MainActivity : AppCompatActivity() {

    private lateinit var checkSupportedDeviceSecurity : CheckDevice


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContentView(R.layout.activity_main)
        var database = Database()
        //database.getExtraConnection()
        val biometricManager = BiometricManager.from(this)
        findViewById<Button>(R.id.sing_in).setOnClickListener {
            checkSupportedDeviceSecurity = CheckDevice()
            checkSupportedDeviceSecurity.checkDeviceSecurity(biometricManager)




            var intent = Intent(this,Login::class.java)
            startActivity(intent)

        }
        findViewById<Button>(R.id.sing_out).setOnClickListener {

            var intent= Intent(this, Registration::class.java)
            startActivity(intent)
        }
    }



    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onDestroy() {
        super.onDestroy()
    }


}