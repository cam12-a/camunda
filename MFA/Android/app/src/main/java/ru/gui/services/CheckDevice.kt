package ru.gui.services

import android.Manifest
import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricManager.Authenticators
import android.os.Build
import android.telephony.TelephonyManager
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.core.app.ActivityCompat
import ru.gui.components.fragments.Content


class CheckDevice: AppCompatActivity(), AboutTelephone {
    val TAG : String = "Mylog"
    val REQUEST_CODE= 102
    lateinit var telephonyManager : TelephonyManager

    override fun checkDeviceSecurity(biometricManager: BiometricManager): Boolean {

        Log.d(TAG, "checkForBiometrics started")
        var canAuthenticate = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Build.VERSION.SDK_INT < 29) {
                val keyguardManager : KeyguardManager = applicationContext.getSystemService(KEYGUARD_SERVICE) as KeyguardManager
                val packageManager : PackageManager   = applicationContext.packageManager
                if(!packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
                    Log.w(TAG, "checkForBiometrics, Fingerprint Sensor not supported")
                    canAuthenticate = false
                }
                if (!keyguardManager.isKeyguardSecure) {
                    Log.w(TAG, "checkForBiometrics, Lock screen security not enabled in Settings")
                    canAuthenticate = false
                }
            } else {
                if(Authenticators.BIOMETRIC_STRONG==15 || Authenticators.BIOMETRIC_WEAK==255){
                    Log.w(TAG, "checkForBiometrics, biometrics  supported")
                    canAuthenticate = true
                }
            }
        }else{
            canAuthenticate = false
        }
        Log.d(TAG, "checkForBiometrics ended, canAuthenticate= $canAuthenticate ")
        return canAuthenticate

    }


    override fun phoneData(context: Context, activity: Activity) {
        println("als")
         telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as
                TelephonyManager
        if (ActivityCompat.checkSelfPermission( context,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                arrayOf(Manifest.permission.READ_PHONE_STATE), REQUEST_CODE)
            println("error imei")
        }
            //println(telephonyManager.imei)

    }

}

