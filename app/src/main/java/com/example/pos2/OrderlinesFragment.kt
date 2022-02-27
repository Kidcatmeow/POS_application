package com.example.pos2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OrderlinesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrderlinesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var listoforderline = arrayListOf<OrderLine>()
        val layout = inflater.inflate(R.layout.fragment_orderline, container, false)
        val localorderlineRecyclerView = layout.findViewById<RecyclerView>(R.id.rvOrderlines)
        val ClickedorderID = arguments?.getInt("order_local_id") //get the information passed from LocalOrderFragment
        val orderIDtoDelete = arguments?.getInt("order_local_id_to_delete")
        var adapter:LocalOrderlineAdapter? =null //we did this so that we can refer itself inside the lambda below
       adapter = LocalOrderlineAdapter(listoforderline){
            orderLine->
            if (EditOrRemove.equals("Edit")){
                //if the user clicks edit,it leads to another activity,where they can fill information to change order details
                val intent = Intent(this.requireContext(),EditOrderLineActivity::class.java)
                intent.putExtra("order_id",orderLine.orderID)
                intent.putExtra("product_id",orderLine.productID)
                Log.i("orderlinefragment","product id: ${orderLine.productID}")
                intent.putExtra("product_price",orderLine.price)
                intent.putExtra("product_quantity",orderLine.quantity)
//                val bundle = Bundle()
//                bundle.putInt("order_local_id",order.uid!!.toInt())
                startActivity(intent)
            }
            else if (EditOrRemove.equals("Remove")){
                val alertDialog = AlertDialog.Builder(this.requireContext())
                alertDialog.setTitle("OrderLine Removal")
                alertDialog.setMessage("Do you want to remove this orderline?")

                alertDialog.setPositiveButton("Yes") { dialog, which ->
                    //creates a button that when clicked,it deletes tje clicked orderline from db
                    val index = listoforderline.indexOf(orderLine)//Get index of that orderline from listoforderline
                    GlobalScope.launch {
                        val db = POSAppDatabase.getInstance(requireContext())
                        db.orderLineDao().delete(orderLine) //delete that from the database
                        listoforderline.removeAt(index) //remove that orderline from the listoforderline
                    }
                    adapter?.notifyItemRemoved(index) //update the recyclerview
                    Toast.makeText(requireContext(), "Selected orderline removed", Toast.LENGTH_SHORT).show()

                }
                //create a button that when clicked,will cancel the operation if the user misclicks
                alertDialog.setNegativeButton("No") {
                        dialog,which ->
                    Toast.makeText(this.requireContext(), "Canceled", Toast.LENGTH_SHORT).show()
                }
                alertDialog.show()
            }



        }

        localorderlineRecyclerView.adapter = adapter
        val db = POSAppDatabase.getInstance(this.requireContext())

        GlobalScope.launch {
            val orderlineinDB = db.orderLineDao().getAll()
            for (orderline in orderlineinDB) {
                if (orderline.orderID.toInt()==(ClickedorderID)) {
                    listoforderline.add(orderline)
                }
                else if (orderline.orderID.toInt()==(orderIDtoDelete)) {
                    listoforderline.remove(orderline)
                    db.orderLineDao().delete(orderline)
                }

            }
        }


        adapter.notifyDataSetChanged()
        localorderlineRecyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        return layout
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OrderlinesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OrderlinesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

        var EditOrRemove: String= ""
        var DeleteDataFromOrderlines: String= ""
    }
}