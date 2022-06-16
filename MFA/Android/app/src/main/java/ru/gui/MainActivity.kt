package ru.gui

import android.content.Context
import android.content.Intent

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import ru.gui.dataBaseService.Database
import ru.gui.services.CheckDevice
import ru.gui.services.Logout


class MainActivity : AppCompatActivity() {

    private lateinit var checkSupportedDeviceSecurity : CheckDevice


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))



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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //super.onCreateOptionsMenu(menu)
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.app_menu , menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
                R.id.feedback -> {
                    startActivity(Intent(this@MainActivity,Feedback::class.java))
                    true
                }
                R.id.help -> {
                    startActivity(Intent(this@MainActivity,AppHelp::class.java))
                    true
                }
                R.id.agreement -> {
                    true
                }
                R.id.setting -> {
                    true
                }
            else -> super.onOptionsItemSelected(item)
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