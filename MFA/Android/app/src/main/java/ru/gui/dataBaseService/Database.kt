package ru.gui.dataBaseService

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.sql.Connection
import java.sql.DriverManager

class Database  {

    private val COMPANY_KEY="companyInfo"
    open var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference(COMPANY_KEY)
    get()  {return field}

    constructor() {
        databaseReference
    }

}