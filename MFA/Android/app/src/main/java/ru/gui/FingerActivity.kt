package ru.gui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor

class FingerActivity : AppCompatActivity() {

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private val TAG="Mylog"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finger)
        executor = ContextCompat.getMainExecutor(this)

        //sendQRCodeToServer.createConnexion(this)

        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                /*override fun onAuthenticationError(errorCode: Int,
                                                   errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(applicationContext,
                        "Обишка при аутентификации : $errString", Toast.LENGTH_SHORT)
                        .show()
                }*/

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(applicationContext,
                        "Отпечатка успешна верна", Toast.LENGTH_SHORT)
                        .show()

                    var getIntentData = intent
                    Log.d(TAG,"link to send "+getIntentData.getStringExtra("link").toString())

                    var intent: Intent = Intent(this@FingerActivity,ScannerActivity::class.java)
                    startActivity(intent)

                   // fingerPrintValidation.analyze()

                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()

                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Сканируйте отпечатку")
            .setNegativeButtonText("ОТМЕНА")
            .build()

        val biometricLoginButton =
            findViewById<Button>(R.id.showFingerScreen)
        biometricLoginButton.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()
    }

}