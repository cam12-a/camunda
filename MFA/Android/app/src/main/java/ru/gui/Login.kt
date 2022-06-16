package ru.gui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.messaging.FirebaseMessaging
import ru.gui.dataBaseService.Database
import ru.gui.entity.Company
import ru.gui.services.CheckDevice
import ru.gui.services.CompanyData
import ru.gui.services.GoBack
import ru.gui.services.Logout
import ru.gui.services.integration.RequestTemplate
import ru.gui.services.integration.SendCredentialToServer
import ru.gui.services.integration.SendRequestToNotificationServer
import java.net.URL


class Login : AppCompatActivity(), GoBack, Logout, CompanyData {

    private lateinit var checkSupportedDeviceSecurity: CheckDevice
    private lateinit var  mDataBase: Database
    private val TAG="Mylog"
    private
    var firebaseMC=""
    var dataFireBase = FirebaseDatabase.getInstance().getReference("companyInfo")
    override var companyLink=""
        get() {return field}
    override lateinit var context: Context
    override lateinit var  spinner: Spinner
    override var companyEmail=""
        get() {return field}


    private lateinit var sp: SharedPreferences
    private lateinit var biometricManager :BiometricManager


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        checkSupportedDeviceSecurity = CheckDevice()
        biometricManager = BiometricManager.from(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        context=this
        spinner=findViewById(R.id.company_list)

        if(loadCredential(this@Login)!=null){
            startActivity(Intent(this@Login,FingerActivity::class.java))
        }

        getAllCompany().get().addOnSuccessListener {
            if(!it.isEmpty)
                fillDropdownCompanyLis(it)
            Log.e(TAG,"IN CREATE "+fillDropdownCompanyLis(it).toString())
        }

        selectedCompany(this)
        Log.d(TAG,"companyLink $companyLink")

        login()
        goBack();


    }

    override fun onResume() {
        super.onResume()
        //setContentView(R.layout.activity_finger)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()
       // setContentView(R.layout.activity_finger)
    }



    /*

    private fun getAllCompany() : Query {
        return FirebaseFirestore.getInstance().collection("company")
    }


    private fun companyExist(name: String, query: Query): Boolean{
        //return name in fillDropdownCompanyLis(query)
        return true
    }

    private fun getCompanyInfo(name: String):   Query {

        var db = FirebaseFirestore.getInstance()
        return  db.collection("company").whereEqualTo("companyName",name)
    }


    private fun fillDropdownCompanyLis(querySnapshot: QuerySnapshot): ArrayList<String> {

        val spinner: Spinner = findViewById(R.id.company_list)

        var items = ArrayList<String>()

        querySnapshot.forEach {
            if(it.exists()){
                items.add(it.data["companyName"].toString())
            }
        }
        var arrayAdapter = ArrayAdapter<String>(
           this,
            android.R.layout.simple_spinner_dropdown_item,
            items)
        spinner.adapter=arrayAdapter

        Log.i(TAG,"selected item id  ${spinner.selectedItemId} selected item ${spinner.selectedItem}")
        return items
    }

    private fun selectedCompany(){
        val spinner: Spinner = findViewById(R.id.company_list)
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.e("Mylog","nothing")
                Toast.makeText(this@Login,spinner.selectedItem.toString()+" "+spinner.selectedItemId.toString(),Toast.LENGTH_SHORT)
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.e("Mylog",spinner.selectedItem.toString()+" "+spinner.selectedItemId.toString())

                (view as TextView).setTextColor(Color.WHITE)
                getCompanyInfo(spinner.selectedItem.toString()).get().addOnSuccessListener {
                   it.forEach {  r->
                       Log.d(TAG,"Link "+r.data["companyLinkToAuthenticatorAPI"].toString())
                       companyLink=r.data["companyLinkToAuthenticatorAPI"].toString()
                   }
                }

            }

        }

    }


     */
    fun saveCredential(token: String){
        val sharedPreferences= getSharedPreferences("Credential", Context.MODE_PRIVATE)
        val editor =sharedPreferences.edit()
        editor.apply {
            putString("access_token",token)
        }.apply()
    }

    override fun goBack(){
        findViewById<ImageButton>(R.id.back_btn).setOnClickListener {
            startActivity(Intent(this@Login,MainActivity::class.java))
            Log.i(TAG,"back btn")
        }
    }

    private fun login(){
        findViewById<Button>(R.id.btn_sign_in).setOnClickListener {
            lateinit var intent: Intent
            println(checkSupportedDeviceSecurity.checkDeviceSecurity(biometricManager))
            when(checkSupportedDeviceSecurity.checkDeviceSecurity(biometricManager)){
                true ->{
                    intent = Intent(this@Login, FingerActivity::class.java)

                }
                else ->{
                    intent =Intent(this@Login, ChooseMFAType::class.java)
                }
            }
            val spinner = findViewById<Spinner>(R.id.company_list)

            getCompanyInfo(spinner.selectedItem.toString()).get().addOnSuccessListener {
                it.documents.forEach {
                        r->
                    companyLink= r.data?.get("companyLinkToAuthenticatorAPI").toString()
                    Company(r.data?.get("companyName").toString(), r.data?.get("companyLinkToAuthenticatorAPI").toString())
                }
            }.addOnFailureListener {
                Log.e(TAG,"Error to get company data ")
            }
            Log.d(TAG,"companyLinkeded $companyLink")
            intent.putExtra("link",companyLink)
            intent.putExtra("username",findViewById<TextView>(R.id.username).text)
            intent.putExtra("password",findViewById<TextView>(R.id.password).text)


            val url =URL(companyLink)
            val sendCredentialToServer = SendCredentialToServer(findViewById<TextView>(R.id.username).text.toString(),findViewById<TextView>(R.id.password).text.toString(),url)
            val requestTemplate= RequestTemplate(url)


            FirebaseMessaging.getInstance().token.addOnCompleteListener{
                    task->
                if(!task.isComplete){
                    Log.e(TAG,"token is not generated")
                    return@addOnCompleteListener
                }

                val token =task.result
                Log.i(TAG," Token $token")
                val sendRequest= requestTemplate.createConnexion(this@Login,sendCredentialToServer.generateJsonTo(token))
                Log.i(TAG,"my access_token ${sendRequest["access_token"]}")
                sendRequest["access_token"]?.let { it1 -> saveCredential(it1) }
                if(sendRequest["message"].equals("login success")) {
                    Log.d(TAG,"IN LOGIN IF $sendRequest")
                    startActivity(intent)
                }
                else{
                    Log.d(TAG,"IN LOGIN ELSE $sendRequest")
                    Toast.makeText(this@Login,"Логин или пароль не верны либо у вас открыто уже приложение", Toast.LENGTH_SHORT).show()

                    val url = URL("http://172.31.208.1:8085/api/auth/login/")
                    val requestTemplate= RequestTemplate(url)
                    val sendRequestToNotificationServer = SendRequestToNotificationServer(url)
                    //sendRequestToNotificationServer.token=token
                    sendRequestToNotificationServer.sendRequestToGetPushCode(this@Login,sendRequestToNotificationServer.generateJsonTo(token,findViewById<TextView>(R.id.username).text.toString(),findViewById<TextView>(R.id.password).text.toString()))

                    intent=Intent(this@Login, ChooseMFAType::class.java)
                    startActivity(intent)
                }

            }

            createCompany()

        }
    }









}