package ru.gui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import ru.gui.services.CompanyData
import ru.gui.services.GoBack
import ru.gui.services.SendEmail

class Feedback : AppCompatActivity(), GoBack, CompanyData, SendEmail {

    private val TAG="Mylog"
    override var companyLink=""
        get() {return field}
    override var companyEmail=""
        get()  {return field }

    override lateinit var context: Context


    override lateinit var  spinner: Spinner


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)
        findViewById<EditText>(R.id.feedback_info).setText("Если у вас возникла проблему, то напишите нам сообщение на почту " +
                "мы обязательно разберемся в вашей проблеме")
        context=this
        spinner=findViewById(R.id.company_list)
        getAllCompany().get().addOnSuccessListener {
            if(!it.isEmpty)
                fillDropdownCompanyLis(it)
            Log.e(TAG,"IN CREATE "+fillDropdownCompanyLis(it).toString())
        }

        selectedCompany(this)

        sendFeedback(findViewById(R.id.send_feedback_btn))
        Log.d(TAG,"companyLink $companyLink")
        goBack()

    }

    override fun goBack(){
        findViewById<ImageButton>(R.id.back_btn).setOnClickListener {
            startActivity(Intent(this@Feedback,MainActivity::class.java))
            Log.i(TAG,"back btn")
        }
    }

    private fun sendFeedback(button: Button){
        button.setOnClickListener {
        sendReport(
                it,
                 context,
                companyEmail,
                findViewById<EditText>(R.id.feedback_text).text.toString(),
                findViewById<EditText>(R.id.subjectIssue).text.toString()
            )
        }
    }




}