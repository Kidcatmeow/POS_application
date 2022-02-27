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
import kotlinx.coroutines.NonDisposableHandle.parent

class CartAdapter(
    private val nMacarons: List<Macaron>


) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    inner class ViewHolder(
        listItemView: View
    ) : RecyclerView.ViewHolder(listItemView) {
        //Bind data by defining each field:
        //Your holder should contain and initialize a member variable
        //for any view that will be set as your render a row



        val cart_product_name: TextView =listItemView.findViewById<TextView>(R.id.cart_productnameText)
        val cart_product_price: TextView =listItemView.findViewById<TextView>(R.id.cart_productpriceText)
        val cart_product_quantity: TextView=listItemView.findViewById(R.id.cart_productquantityText)



    }




    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        //inflate from custom layout abd create views
        val CartView = inflater.inflate(R.layout.fragment_show_cart, parent, false)
        //Return  a new holder instance
        // return ViewHolder(macaronView)
        return ViewHolder(CartView)

    }



    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return nMacarons.size
    }

    fun onScrolled(RecyclerView: RecyclerView, dx:Int, dy:Int){

    }

    override fun onBindViewHolder(viewHolder: CartAdapter.ViewHolder, position: Int) {
        val macaron : Macaron = nMacarons[position] // position is the index of an arraylist (we use index to access each item in an Arraylist)
        val productNameTextView = viewHolder.cart_product_name
        val productPriceTextView = viewHolder.cart_product_price
        val productQuantityTextView = viewHolder.cart_product_quantity

        productNameTextView.setText(macaron.name)
        productPriceTextView.setText(macaron.price.toString())
        productQuantityTextView.setText(macaron.quantity.toString())


    }


}