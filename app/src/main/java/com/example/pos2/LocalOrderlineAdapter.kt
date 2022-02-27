package com.example.pos2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LocalOrderlineAdapter (
    private val localorderlinelist:List<OrderLine>,
    private val onOrderLineClicked: (OrderLine) -> Unit

): RecyclerView.Adapter<LocalOrderlineAdapter.ViewHolder>(){

    class ViewHolder(ListitemView : View, onOrderLineClicked_fun:(Int)->Unit): RecyclerView.ViewHolder(ListitemView){
        val localorderlineID = ListitemView.findViewById<TextView>(R.id.localorderline_orderlineid_text)
        val localorderlineproductid = ListitemView.findViewById<TextView>(R.id.localorderline_productid_text)
        val localorderlineproductprice = ListitemView.findViewById<TextView>(R.id.localorderline_prodctprice_text)
        val localorderlineproductquantity = ListitemView.findViewById<TextView>(R.id.localorderline_productquantity_text)
        val localorderlineEditbtn = ListitemView.findViewById<Button>(R.id.orderline_editbtn)
        val localorderlineDeletebtn = ListitemView.findViewById<Button>(R.id.orderline_deletebtn)

        init {

            localorderlineEditbtn.setOnClickListener {
                OrderlinesFragment.EditOrRemove="Edit"
                onOrderLineClicked_fun(adapterPosition)
            }

            localorderlineDeletebtn.setOnClickListener {
                OrderlinesFragment.EditOrRemove="Remove"
                onOrderLineClicked_fun(adapterPosition)
            }

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val context = parent.context
        val localOrderlineView = LayoutInflater.from(context).inflate(R.layout.viewholder_localorderline,parent,false)
        return ViewHolder(localOrderlineView){
            onOrderLineClicked(localorderlinelist[it])
        }

    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        val item = localorderlinelist[position]
        holder.localorderlineID.text = item.uid.toString()
        holder.localorderlineproductid.text = item.productID.toString()
        holder.localorderlineproductprice.text = item.price.toString()
        holder.localorderlineproductquantity.text = item.quantity.toString()

    }

    override fun getItemCount(): Int {
        return localorderlinelist.size
    }

}