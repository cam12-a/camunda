package ru.gui.services

import android.R
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import ru.gui.dataBaseService.Database
import ru.gui.entity.Company

interface CompanyData {

    open var companyLink: String
    open var context: Context
    open var spinner: Spinner
    open var companyEmail: String


    open fun getAllCompany() : Query {
        return FirebaseFirestore.getInstance().collection("company")
    }


    open fun companyExist(name: String, query: Query): Boolean{
        //return name in fillDropdownCompanyLis(query)
        return true
    }

    open fun getCompanyInfo(name: String): Query {

        var db = FirebaseFirestore.getInstance()
        return  db.collection("company").whereEqualTo("companyName",name)
    }


    open fun fillDropdownCompanyLis(querySnapshot: QuerySnapshot): ArrayList<String> {

        //val spinner: Spinner = findViewById(R.id.company_list)

        var items = ArrayList<String>()

        querySnapshot.forEach {
            if(it.exists()){
                items.add(it.data["companyName"].toString())
            }
        }
        var arrayAdapter = ArrayAdapter<String>(
            context,
            R.layout.simple_spinner_dropdown_item,
            items)
        spinner.adapter=arrayAdapter

        Log.i("Mylog","selected item id  ${spinner.selectedItemId} selected item ${spinner.selectedItem}")
        return items
    }


    open fun createCompany() {
      //  mDataBase = Database()
        var mcompany: Company = Company("Alpha Bank", "http://172.17.122.162:8080/api/user/fibank/")
        var company: HashMap<String, String> = HashMap()
        company["companyName"] = mcompany.companyName
        company["companyLinkToAuthenticatorAPI"] = mcompany.companyLinkToAuthenticatorAPI

        val query=getAllCompany()
        //Log.e(TAG,"COMPANY LIST"+ fillDropdownCompanyLis(getAllCompany()).toString())
        Log.e("Mylog","Exits "+companyExist(mcompany.companyName,query).toString())
        query.get().addOnSuccessListener {
            if(companyExist(mcompany.companyName,query)) {

            }

        }

        Log.d("Mylog", "saving data ${mcompany.toString()}")
        //companyExist()
    }

    open fun selectedCompany(context: Context){

      //  val spinner: Spinner = findViewById(R.id.company_list)
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.e("Mylog","nothing")
                Toast.makeText(context,spinner.selectedItem.toString()+" "+spinner.selectedItemId.toString(),Toast.LENGTH_SHORT)
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.e("Mylog",spinner.selectedItem.toString()+" "+spinner.selectedItemId.toString())

                (view as TextView).setTextColor(Color.WHITE)
                getCompanyInfo(spinner.selectedItem.toString()).get().addOnSuccessListener {
                    it.forEach {  r->
                        Log.d("Mylog","Link "+r.data["companyLinkToAuthenticatorAPI"].toString())
                        companyLink=r.data["companyLinkToAuthenticatorAPI"].toString()
                        companyEmail=r.data["companyEmail"].toString()
                    }
                }

            }

        }

    }

}