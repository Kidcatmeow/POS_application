package com.example.pos2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        Toast.makeText(this,"Pick a category",Toast.LENGTH_SHORT).show()


        // get reference to button
        val btnMacaron = findViewById<ImageButton>(R.id.imageButtonMacaron)
        // set on-click listener
        btnMacaron.setOnClickListener {
            Toast.makeText(this@CategoryActivity, "Proceed to Macaron product list", Toast.LENGTH_SHORT).show()
            val intent = Intent(this,activity_order::class.java)
            intent.putExtra("CatID", MACARON_CAT_ID)
            startActivity(intent)

        }

        // get reference to button
        val btnDrink = findViewById<ImageButton>(R.id.imageButtonDrink)
        // set on-click listener
        btnDrink.setOnClickListener {
            Toast.makeText(this@CategoryActivity, "Proceed to drink product list", Toast.LENGTH_SHORT).show()
            val intent = Intent(this,activity_order::class.java)
            intent.putExtra("CatID", DRINK_CAT_ID)
            startActivity(intent)

        }


    }

    //Functions for button to use alternative to onclicklistener
    fun onclickMacaronsBtn(view: View) {
        val intent = Intent(this, activity_order::class.java)
        intent.putExtra("CatID", MACARON_CAT_ID)
        startActivity(intent)
    }

    fun onclickDrinkBtn(view: View) {
        val intent = Intent(this, activity_order::class.java)
        intent.putExtra("CatID", DRINK_CAT_ID)
        startActivity(intent)
    }


}