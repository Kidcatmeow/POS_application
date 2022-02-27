//package com.example.pos2
//
//import android.annotation.SuppressLint
//import android.os.Bundle
//import android.provider.ContactsContract
//import android.text.Editable
//import android.text.TextWatcher
//import android.util.Log
//import android.widget.*
//import androidx.appcompat.app.AppCompatActivity
//
//class ContactActivity : AppCompatActivity() {
//
//    private var listView: ListView? = null
//    private var customAdapter: CustomAdapter? = null
//    private var contactModelArrayList: ArrayList<ContactModel>? = null
//    var list : ArrayList<ContactModel> = ArrayList()
//    lateinit var Search_btn : EditText
//
//    @SuppressLint("Range")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_contact)
//
//        listView = findViewById(R.id.contactlist) as ListView
//
//        Search_btn = findViewById<EditText>(R.id.SearchContactBtn)
//
//        contactModelArrayList = ArrayList()
//
//        val phones = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC")
//        while (phones!!.moveToNext()) {
//            val name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
//            val phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
//
//            val contactModel = ContactModel()
//            contactModel.setNames(name)
//            contactModel.setNumbers(phoneNumber)
//            contactModelArrayList!!.add(contactModel)
//            Log.d("name>>", name + "  " + phoneNumber)
//        }
//        phones.close()
//        var arrayAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,android.R.id.text1,list)
//        listView!!.adapter = arrayAdapter
//        Search_btn.addTextChangedListener(object: TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                arrayAdapter!!.filter.filter(s)
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//            }
//        })
//        customAdapter = CustomAdapter(this, contactModelArrayList!!)
//        listView!!.adapter = customAdapter
//
//
//
//
//    }
//
//
//    @SuppressLint("Range")
//    fun Createdata() {
////        contactModelArrayList = ArrayList()
////
////        val phones = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC")
////        while (phones!!.moveToNext()) {
////            val name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
////            val phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
////
////            val contactModel = ContactModel()
////            contactModel.setNames(name)
////            contactModel.setNumbers(phoneNumber)
////            contactModelArrayList!!.add(contactModel)
////            Log.d("name>>", name + "  " + phoneNumber)
////        }
////        phones.close()
////
////        customAdapter = CustomAdapter(this, contactModelArrayList!!)
////        listView!!.adapter = customAdapter
//
//    }
//
//
//}