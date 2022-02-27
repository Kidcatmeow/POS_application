package com.example.pos2

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_contact.*
import java.util.jar.Manifest
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import android.widget.TextView
import android.widget.Toast

import android.app.Activity
import android.database.Cursor
import kotlinx.android.synthetic.main.lv_item.*


class ContactActivity : AppCompatActivity() {

    var contact = listOf<String>( //provide list of elements you want from the user
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER,
        ContactsContract.CommonDataKinds.Phone._ID //ID needed to bind the data of an array to the cursor adapter

    ).toTypedArray()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)


        //Asking user to grant permission if it havent been granted , or readContact() if it has already been granted
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_CONTACTS)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, Array(1){android.Manifest.permission.READ_CONTACTS},111) //want only 1 permission, so we set array as only 1
        }
        else readContact()

    }

    //Check if user has already granted permission and if the request code is the one that we had passed (111)
    //if the code is the same then we will call the readContact()
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==111 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            readContact()
    }

    private fun readContact() {

        var from = listOf<String>( ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER).toTypedArray() //columns you want to display then convert it to array
        var to = intArrayOf(R.id.name,R.id.number)
        var resultset = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, contact,null,null,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
        //contentResolver is a class that provide access to the content model,its arguments are(URI you want to access data,columns/elements you want to select,conditions when you want to filter the context,value related to condition,sorting:in this case,we sort the data by name)
        var adapter = SimpleCursorAdapter(this,R.layout.lv_item,resultset,from,to,0) //bind with cursor adapter class,its arguments are (context,the id of layout you want,array of string correspond to names of columns in cursor,int array of layouts IDs,flag)

        contactlist.adapter = adapter


        SearchContactBtn.setOnQueryTextListener(object: SearchView.OnQueryTextListener{//Whenever the user type anything in the search view,that value will be available in p0

            override fun onQueryTextSubmit(p0: String?): Boolean { //p0 is the value that the user put in search view
                return false
            }


            override fun onQueryTextChange(p0: String?): Boolean {
                //Change the list
                var resultset = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    contact,"${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} LIKE?",//we filter the data by giving the condition .in this case want to search the contact by name
                    Array(1){"%$p0%"} /* match the context with p0 (the value that user enter) */,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)

                adapter.changeCursor(resultset) //set this array to your adapter to change the value of contact list view
                return false
            }


        })
    }


    private fun retrieveContactEmail() {

    }
}

