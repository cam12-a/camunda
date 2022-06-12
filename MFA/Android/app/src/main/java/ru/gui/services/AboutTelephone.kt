package ru.gui.services

import android.app.Activity
import android.content.Context
import androidx.biometric.BiometricManager

interface AboutTelephone {
     fun checkDeviceSecurity(biometricManager: BiometricManager) : Boolean
     fun phoneData(context: Context, activity: Activity)
}