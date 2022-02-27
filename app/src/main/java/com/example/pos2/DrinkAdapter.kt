package com.example.pos2

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DrinkAdapter(
    private val nDrinks: List<Drink>,
    private val onItemClicked: (Drink) -> Unit

) : RecyclerView.Adapter<DrinkAdapter.ViewHolder>() {
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
                Log.d(ContentValues.TAG,"adapterPosition = $adapterPosition")
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

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        //val macaronView = LayoutInflater.from(viewGroup.context).inflate(R.layout.layout_item_product, viewGroup, false)
        //return ViewHolder(view)

        val context = parent.context
        val inflater = LayoutInflater.from(context)
        //inflate from custom layout abd create views
        val drinkView = inflater.inflate(R.layout.layout_item_product, parent, false)
        //Return  a new holder instance
        //return ViewHolder(macaronView)


        return ViewHolder(drinkView) {
            onItemClicked(nDrinks[it])
        }


    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the contents of the view with that element

        // //get the data model based on position
        val drink = nDrinks[position]

        //set item views based on your views and data model
        val imageView = viewHolder.productImageView
        val nameTextView = viewHolder.productNameTextView
        val priceTextView = viewHolder.productPriceTextView
        val addButton = viewHolder.addProductButton
        val delButton = viewHolder.delProductButton

        when (position) {
            0 -> imageView.setImageResource(R.drawable.cocktail)
            1 -> imageView.setImageResource(R.drawable.cocktail)
            2 -> imageView.setImageResource(R.drawable.cocktail)
            3 -> imageView.setImageResource(R.drawable.cocktail)
            4 -> imageView.setImageResource(R.drawable.cocktail)
            5 -> imageView.setImageResource(R.drawable.cocktail)
            6 -> imageView.setImageResource(R.drawable.cocktail)
            else -> {
                imageView.setImageResource(R.drawable.cocktail)
            }
        }

        nameTextView.setText(drink.name)
        priceTextView.setText(drink.price.toString())
        addButton.setText("+")
        delButton.setText("-")
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return nDrinks.size
    }

    fun onScrolled(RecyclerView: RecyclerView, dx:Int, dy:Int){

    }

}