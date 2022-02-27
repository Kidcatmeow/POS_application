package com.example.pos2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EditOrderLineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_order_line)

        val confirmchangebtn = findViewById<Button>(R.id.editorderline_confirmbtn)
        val productprice_userinput = findViewById<TextView>(R.id.editorderline_price_input)
        val productquantity_userinput = findViewById<TextView>(R.id.editorderline_quantity_input)
        val id = intent.getLongExtra("order_id",0)
        val productid = intent.getLongExtra("product_id",0)
        val price = intent.getIntExtra("product_price",0)
        val quantity = intent.getIntExtra("product_quantity",0)
        productprice_userinput.text = price.toString()
        productquantity_userinput.text = quantity.toString()

        confirmchangebtn.setOnClickListener {

//            Toast.makeText(this,productprice_userinput.text.toString(),Toast.LENGTH_SHORT).show()

            GlobalScope.launch {
                val db = POSAppDatabase.getInstance(this@EditOrderLineActivity)
                //NOTE: .toString().toInt() is just for conversion purpose to put into the update function
                db.orderLineDao().update(productid,productprice_userinput.text.toString().toInt(),productquantity_userinput.text.toString().toInt())

//                Log.i("EditOrderActivity",db.orderLineDao().getAll().toString())
//                Log.i("EditOrderActivity",id.toString())
//                Log.i("EditOrderActivity",productid.toString())
//                Log.i("EditOrderActivity",productprice_userinput.toString())
//                Log.i("EditOrderActivity",productquantity_userinput.toString())
            }
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Update Operation")
            alertDialog.setMessage("Update succeed")

            alertDialog.setNeutralButton("Updated"){
                    dialog, which ->
//                val intent = Intent(this,OrderManagerActivity::class.java)
//                startActivity(intent)
                finishActivity(0)
            }
//            Toast.makeText(this,"Updated",Toast.LENGTH_SHORT).show()
            alertDialog.show()
        }

    }
}