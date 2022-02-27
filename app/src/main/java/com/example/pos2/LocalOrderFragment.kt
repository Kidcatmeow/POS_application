package com.example.pos2

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

//import kotlinx.android.synthetic.main.fragment_order.view.*
//import kotlinx.android.synthetic.main.activity_order_manager.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OrderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrderFragment : Fragment() {
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
        val layout = inflater.inflate(R.layout.fragment_order, container, false)
        val localorderRecyclerView = layout.findViewById<RecyclerView>(R.id.rvOrder)
        val db = POSAppDatabase.getInstance(this.requireContext())
        var localorderlist = arrayListOf<Order>()
        var listoforderline = arrayListOf<OrderLine>()

        //Retrieve orderline data from our local database and put it in 'localorderlist'
        GlobalScope.launch {
            val orders = db.orderDao().getAll()
            localorderlist.addAll(orders)

        }

        var adapter:LocalOrderAdapter? =null
        adapter = LocalOrderAdapter(localorderlist){
                order ->
            if (RemoveOrNot.equals("")) {
                val arg = Bundle()
                arg.putInt(
                    "order_local_id",
                    order.uid!!.toInt()
                ) //pass information from this fragment to OrderlinesFragment for further use
                fragmentManager?.commit {
                    setReorderingAllowed(true)
                    //replace whatever is in the orderlinesFragmentContainer with OrderlinesFragment
                    replace<OrderlinesFragment>(
                        R.id.orderlinesFragmentContainer,
                        "order_local_id",
                        arg
                    )
                    setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                }
            }

            else if (RemoveOrNot.equals("Delete")) {

                val arg = Bundle()
                arg.putInt(
                    "order_local_id_to_delete",
                    order.uid!!.toInt()
                ) //pass information from this fragment to OrderlinesFragment for further use


                val alertDialog = AlertDialog.Builder(this.requireContext())
                alertDialog.setTitle("Order Removal")
                alertDialog.setMessage("Do you want to remove this order?")

                alertDialog.setPositiveButton("Yes") { dialog, which ->
                    //creates a button that when clicked,it deletes tje clicked orderline from db
                    val orderindex = localorderlist.indexOf(order)//Get index of that order from listoforder
                    GlobalScope.launch {
                        val db = POSAppDatabase.getInstance(requireContext())
                        db.orderDao().delete(order) //delete that from the dabase
                        localorderlist.removeAt(orderindex) //remove that orderline from the listoforderline

                    }
                    adapter?.notifyItemRemoved(orderindex) //update the recyclerview
                    Toast.makeText(requireContext(), "Selected order removed", Toast.LENGTH_SHORT).show()
                    fragmentManager?.commit {
                        setReorderingAllowed(true)
                        //replace whatever is in the orderlinesFragmentContainer with OrderlinesFragment
                        replace<OrderlinesFragment>(
                            R.id.orderlinesFragmentContainer,
                            "order_local_id_to_delete",
                            arg
                        )
                        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    }
                }


                //create a button that when clicked,will cancel the operation if the user misclicks
                alertDialog.setNegativeButton("No") {
                        dialog,which ->
                    Toast.makeText(this.requireContext(), "Canceled", Toast.LENGTH_SHORT).show()
                }
                alertDialog.show()



            }

            val alertDialog = AlertDialog.Builder(this.requireContext())
            alertDialog.setTitle("Update Operation")
            alertDialog.setMessage("Update succeed")
            alertDialog.setNeutralButton("Return to Order Manager"){
                    dialog, which ->
                val intent = Intent(this.requireContext(),OrderManagerActivity::class.java)
                startActivity(intent)
            }
        }


        localorderRecyclerView.adapter = adapter

        adapter.notifyDataSetChanged()
        localorderRecyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        return layout
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OrderFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OrderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

        var RemoveOrNot: String= ""
    }
}