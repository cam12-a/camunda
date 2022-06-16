package ru.gui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.ms.square.android.expandabletextview.ExpandableTextView

class AppHelp : AppCompatActivity() {

    private lateinit var installation: ExpandableTextView
    private lateinit var feedbackInfo: ExpandableTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_help)

        installation = findViewById<ExpandableTextView?>(R.id.expand_text_view).findViewById(R.id.expand_text_view)

        installation.text = getString(R.string.expandable_text)
    }


}