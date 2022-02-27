package com.example.pos2


import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.fragment_show_cart.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class activity_order : AppCompatActivity() {

    private lateinit var list:ArrayList<Macaron>
    private val model : OrderViewModel by viewModels()
    private var totalprice : Int = 0

    companion object{
        var operation: Int? =null
    }

    @SuppressLint("StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

//        val start = System.nanoTime()

        val intent = intent
        val catID = intent.getIntExtra ("CatID",0)

//        val loaded = System.nanoTime()
//
//        println("Load time for macaron is : ${loaded - start}")

        list = arrayListOf()
        val rvMacarons = findViewById<View>(R.id.rvProductList) as RecyclerView
        val lvCart = findViewById<View>(R.id.lvshoworder) as RecyclerView
        val orders = list //Data creation (There are 50 items)
        val cartadapter = CartAdapter(orders)
        lvCart.adapter = cartadapter
        lvCart.layoutManager = LinearLayoutManager(this)
        rvMacarons.layoutManager = LinearLayoutManager(this)

        // Lookup the recyclerview in activity layout
        when (catID) {
            MACARON_CAT_ID -> {

                // Initialize macarons
                val macaron = Macaron.createMacaronList()
                // Create adapter passing in the sample user data
                val adapter = MacaronAdapter(macaron){macaron ->
                    Log.d("ECKSDEE",macaron.name)
                    Log.d("operation value",operation.toString())
                    if(operation ==1){
                        if(orders.contains(macaron)){
                            val index = orders.indexOf(macaron)
                            macaron.quantity+=1
                            orders.set(index,macaron)
                            cartadapter.notifyItemChanged(index)
                        }else{
                            orders.add(macaron)
                            cartadapter.notifyItemInserted(orders.size)
                            Log.d("array size",orders.size.toString())
                        }



                    }
                    else if(operation==2){
                        if(orders.contains(macaron)) {
                            val index = orders.indexOf(macaron)
                            if (macaron.quantity > 1) {
                                macaron.quantity-=1
                                orders.set(index, macaron)
                                cartadapter.notifyItemChanged(index)
                            } else {
                                orders.removeAt(index)
                                cartadapter.notifyItemRemoved(index)
                            }
                        }
                    }
                    var totalprice = 0
                    /*
                    for (item : List) {}
                    for (item in List) {}

                    list = {xd, lmao ,hehe,ayelmao,lol}
                    for( int i =0;i<list.lenght();i++){
                    print i.get
                     */

                    for (item in list){

                        totalprice += item.price * item.quantity
                    }
                    var priceText = findViewById<TextView>(R.id.textView_totalprice)
                    priceText.setText(totalprice.toString())
                }
                // Attach the adapter to the recyclerview to populate items
                rvMacarons.adapter = adapter
                // Set layout manager to position the items



            }

            DRINK_CAT_ID -> {
                val rvDrink = findViewById<View>(R.id.rvProductList) as RecyclerView
                // Initialize drinks
                val drinks = Drink.createDrinkList()
                // Create adapter passing in the sample user data
                val adapter = DrinkAdapter(drinks){
                }
                //attach the adapter to the recyclerview to populate items
                rvDrink.adapter = adapter
                //set layout manager to position the items
                rvDrink.layoutManager = LinearLayoutManager(this)
                Toast.makeText(this, "Drink Category!", Toast.LENGTH_SHORT).show()
            }

            else -> {
                val rvMacarons = findViewById<View>(R.id.rvProductList) as RecyclerView
                // Initialize macarons
                val macarons = Macaron.createMacaronList()
                // Create adapter passing in the sample user data
                val adapter = MacaronAdapter(macarons) {
                }
                // Attach the adapter to the recyclerview to populate items
                rvMacarons.adapter = adapter
                // Set layout manager to position the items
                rvMacarons.layoutManager = LinearLayoutManager(this)
                Toast.makeText(this, "Unknown Category!", Toast.LENGTH_SHORT).show()
            }

        }

    }

//
//    fun onclick_increment_btn (view:View){
//
//        val lvCart = findViewById<View>(R.id.lvshoworder) as ListView
//        val orders = Macaron.createMacaronList() //Data creation (There are 50 items)
//        val cartadapter = CartAdapter(this, orders)
//        lvCart.adapter = cartadapter
//
//    }


    fun onclick_submit_order_btn (view:View){
        Log.i(TAG,"Submit order button clicked.Order is going to be stored")

        //Create a launch coroutine
        GlobalScope.launch{
            val order1 = Order (null,5001,2001)
            //Get a reference tp database
            val db = POSAppDatabase.getInstance(applicationContext)
//            db.orderDao().deleteAll()
//            db.orderLineDao().deleteAll()
            val orderID : Long = db.orderDao().insert(order1)

            //Create two dummy order lines with productID = 1001 and 1005
            for (item in list){
                val order = OrderLine(null,orderID,item.id,item.price,item.quantity)
                db.orderLineDao().insertAll(order)
            }
        }
    }

    fun onclick_retrieve_orders_btn (view:View){
        Log.i (TAG,"Retrieve Orders button clicked.")

        //create a launch coroutine
        GlobalScope.launch {
            //Get a reference to database
            val db = POSAppDatabase.getInstance(applicationContext)
            val orders = db.orderDao().getAll()
//            db.orderDao().deleteAll()
//            db.orderLineDao().deleteAll()
            Log.i(TAG, "Orders:")
            for(order in orders) {
                Log.i(TAG, "\n Order ID = ${order.uid}, " + "Branch ID = ${order.branchID} " + "Staff ID = ${order.staffID}")
            }


            val orderLines = db.orderLineDao().getAll()
            Log.i(TAG, "orderLines:")
            for (orderLine in orderLines) {
                Log.i(TAG,  "\n Order ID = ${orderLine.orderID} " + "OrderLine ID = ${orderLine.uid}, " + "Product ID = ${orderLine.productID} Product Quantity = ${orderLine.quantity}")
            }
        }
    }

}

