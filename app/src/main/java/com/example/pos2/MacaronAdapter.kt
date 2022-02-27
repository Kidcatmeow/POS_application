package com.example.pos2

import android.content.ContentValues.TAG
import android.database.DataSetObserver
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MacaronAdapter(
    private val nMacarons: List<Macaron>,
    private val onItemClicked: (Macaron) -> Unit


) : RecyclerView.Adapter<MacaronAdapter.ViewHolder>() {

    inner class ViewHolder(
        listItemView: View,
        onItemClicked_fun: (Int) -> Unit
    ) : RecyclerView.ViewHolder(listItemView) {
        //Bind data by defining each field:
        //Your holder should contain and initialize a member variable
        //for any view that will be set as your render a row

        val productImageView: ImageView =
            listItemView.findViewById<ImageView>(R.id.imageView_product)
        val productNameTextView: TextView =
            listItemView.findViewById<TextView>(R.id.productName)
        val productPriceTextView: TextView =
            listItemView.findViewById<TextView>(R.id.textView_productPrice)
        val addProductButton: Button = listItemView.findViewById<Button>(R.id.button_product_add)
        val delProductButton: Button = listItemView.findViewById<Button>(R.id.button_product_del)
        init {
            // Define click listener for the ViewHolder's View.
            listItemView.setOnClickListener {
                Log.d(TAG,"adapterPosition = $adapterPosition")
                onItemClicked_fun(adapterPosition)

            }
            addProductButton.setOnClickListener {
                activity_order.operation=1
                onItemClicked_fun(adapterPosition)

            }
            delProductButton.setOnClickListener {
                activity_order.operation=2
                onItemClicked_fun(adapterPosition)
            }

        }





    }




    // Create new views (invoked by the layout manager) will inflate the empty frame of layout that we created (in this case layout_item_product)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MacaronAdapter.ViewHolder {
        // Create a new view, which defines the UI of the list item
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        //inflate from custom layout abd create views
        val macaronView = inflater.inflate(R.layout.layout_item_product, parent, false)
        //Return  a new holder instance
        // return ViewHolder(macaronView)
        return ViewHolder(macaronView) {
            Log.d(TAG,"ViewHolder with item no. $it")
            onItemClicked(nMacarons[it])
        }


    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: MacaronAdapter.ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the contents of the view with that element


        // //get the data model based on position
        val macaron : Macaron = nMacarons[position]

        //set item views based on your views and data model
        val imageView = viewHolder.productImageView
        val nameTextView = viewHolder.productNameTextView
        val priceTextView = viewHolder.productPriceTextView
        val addButton = viewHolder.addProductButton
        val delButton = viewHolder.delProductButton

        when (position) {
            0 -> imageView.setImageResource(R.drawable.blueberry_lemon)
            1 -> imageView.setImageResource(R.drawable.red_velvet)
            2 -> imageView.setImageResource(R.drawable.earl_grey)
            3 -> imageView.setImageResource(R.drawable.rose)
            4 -> imageView.setImageResource(R.drawable.mint_chocolate)
            5 -> imageView.setImageResource(R.drawable.coffee)
            6 -> imageView.setImageResource(R.drawable.matcha)
            else -> {
                imageView.setImageResource(R.drawable.blueberry_lemon)
            }
        }

        nameTextView.setText(macaron.name)
        priceTextView.setText(macaron.price.toString())
        addButton.setText("+")
        delButton.setText("-")


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return nMacarons.size
    }



}