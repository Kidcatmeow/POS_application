package com.example.pos2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LocalOrderAdapter (private val localorderlist:List<Order>, private val onOrderClicked: (Order) -> Unit):RecyclerView.Adapter<LocalOrderAdapter.ViewHolder>(){
    class ViewHolder(ListitemView : View, onOrderClicked_fun:(Int)->Unit): RecyclerView.ViewHolder(ListitemView){
        val localorderid = ListitemView.findViewById<TextView>(R.id.localorder_orderid_text)
        val localstaffid = ListitemView.findViewById<TextView>(R.id.localorder_staffid_text)
        val localbranchid = ListitemView.findViewById<TextView>(R.id.localorder_branchid_text)
        val localorderDeletebtn = ListitemView.findViewById<TextView>(R.id.localorder_delete_btn)

        init {
            ListitemView.setOnClickListener{
                OrderFragment.RemoveOrNot = ""
                onOrderClicked_fun(adapterPosition)
            }
            localorderDeletebtn.setOnClickListener {
                OrderFragment.RemoveOrNot = "Delete"
                OrderlinesFragment.DeleteDataFromOrderlines = "Delete"
                onOrderClicked_fun(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val context = parent.context
        val localOrderView = LayoutInflater.from(context).inflate(R.layout.viewholder_localorder,parent,false)
        return ViewHolder(localOrderView){
            onOrderClicked(localorderlist[it])
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = localorderlist[position]
        holder.localorderid.text = item.uid.toString()
        holder.localstaffid.text = item.staffID.toString()
        holder.localbranchid.text = item.branchID.toString()
    }

    override fun getItemCount(): Int {
        return localorderlist.size
    }

}