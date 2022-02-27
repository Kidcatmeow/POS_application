package com.example.pos2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class FruitsAdapter(private val context: Context,
                    private val dataSource: ArrayList<Drink>) : BaseAdapter() {


    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater



    //1
    override fun getCount(): Int {
        return dataSource.size
    }

    //2
    override fun getItem(position: Int): Any {
        return dataSource[position]

    }

    //3
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }



    //4
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Get view for row item
        val rowView = inflater.inflate(R.layout.layout_item_product, parent, false)
        val Fruits = dataSource[position]


        val productNameTextView = rowView.findViewById(R.id.productName) as TextView
        val productPriceTextView = rowView.findViewById(R.id.textView_productPrice) as TextView
        val productImageView = rowView.findViewById(R.id.imageView_product) as ImageView
        val addProductButton =  rowView.findViewById(R.id.button_product_add) as Button
        val delProductButton = rowView.findViewById(R.id.button_product_del) as Button



        when (position) {
            0 -> productImageView.setImageResource(R.drawable.blueberry_lemon)
            1 -> productImageView.setImageResource(R.drawable.red_velvet)
            2 -> productImageView.setImageResource(R.drawable.earl_grey)
            3 -> productImageView.setImageResource(R.drawable.rose)
            4 -> productImageView.setImageResource(R.drawable.mint_chocolate)
            5 -> productImageView.setImageResource(R.drawable.coffee)
            6 -> productImageView.setImageResource(R.drawable.matcha)
            else -> {
                productImageView.setImageResource(R.drawable.blueberry_lemon)
            }
        }


        productNameTextView.text = Fruits.name
        productNameTextView.setText(Fruits.name)
        productPriceTextView.setText(Fruits.price.toString())
        addProductButton.setText("+")
        delProductButton.setText("-")


        return rowView
    }

}
