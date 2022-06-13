package ru.gui

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView
import ru.gui.services.integration.SendQRCodeToServer
import java.net.URL

class ScannerActivity : AppCompatActivity(), ZBarScannerView.ResultHandler {

    private val PERMISSION_CODE = 1000;
    private val IMAGE_CAPTURE_CODE = 1001
    var image_uri: Uri? = null
    private lateinit var zbView: ZBarScannerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED ||
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED){
                //permission was not enabled дать право на использование камеру
                val permission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                //show popup to request permission
                //пуш окно для выполнения
                requestPermissions(permission, PERMISSION_CODE)
            }
            else{
                //permission already granted
                //разрешение уже предоставлено
                openCamera()
            }
        }
        else{
            //system os is < marshmallow
            // система OC меньше marshmallow
            openCamera()
        }

    }

    private fun openCamera() {
        zbView= ZBarScannerView(this)
        setContentView(zbView)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        //called when user presses ALLOW or DENY from Permission Request Popup
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup was granted
                    openCamera()
                }
                else{
                    //permission from popup was denied
                    Toast.makeText(this, "У вас нет права запускать камеру", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        zbView.setResultHandler(this)
        zbView.startCamera()
    }


    override fun onPause() {
        super.onPause()
        zbView.stopCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun handleResult(result: Result?) {
        Log.d("Mylog", "result ${result?.contents}")
        //var sendQRCodeToServer = SendQRCodeToServer("als",result?.contents.toString(), URL("http://172.17.122.162:8080/api/qrcode/receive_qrcode/"))

        var sendQRCodeToServer = SendQRCodeToServer("als",result?.contents.toString(), URL("https://www.google.fr/"))
        //sendQRCodeToServer.createConnexion(this)
        sendQRCodeToServer.createGetRequest(this)


        println("Log "+sendQRCodeToServer.generateJsonTo())

        finish()
    }
}