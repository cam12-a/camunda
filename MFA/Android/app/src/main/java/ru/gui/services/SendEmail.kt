package ru.gui.services

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View

interface SendEmail {

    open fun sendReport(v: View, context: Context, receiver: String, issue: String, subjectIssue: String) {
        val intent =Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$receiver"));
        intent.putExtra(Intent.EXTRA_SUBJECT,subjectIssue)
        intent.putExtra(Intent.EXTRA_TEXT,issue)
        context.startActivity(Intent.createChooser(intent,"Выберите почту"))
    }
}