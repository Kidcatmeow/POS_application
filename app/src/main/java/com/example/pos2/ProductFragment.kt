package com.example.pos2

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import kotlinx.android.synthetic.main.activity_productcrud.*
import kotlinx.android.synthetic.main.lv_item.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProductFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductFragment : Fragment(R.layout.fragment_product) {

    private val TAG ="ProductFragment"

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1= it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{



        // Inflate the layout for this fragment
        val layout = inflater.inflate(R.layout.fragment_product,//fragment layout to be inflated
            container,//the parent ViewGroup (from activity's layout which our Fragment layout will be inserted
            false) //Bundle: If non-null,this fragment is being reconstructed from a previous save state as given here

        val rvMacarons = layout.findViewById<View>(R.id.recyclerFrag) as? RecyclerView
        //Initialize macarons
        val macarons = Macaron.createMacaronList()
        //create adapter passing in the sample user data
        //We are passing the onClickListener callback as the 2nd parameter (as lambda)
        //Macaron is the argument and it is an Int
        val adapter = MacaronAdapter(macarons) { macaron ->
            Log.d(TAG, "onClick Listener: ${macaron.name}")
            val arg = Bundle()
            arg.putInt("macaron_id", macaron.id.toInt())
            //Use bundles if you need to pass some information to fragmentF)
            //val bundle = bundleof("macaron_id_int" to macaron.id
//            val showProductFrag = ShowProductFragment()
            //showProductFrag.arguments = bundle
//            showProductFrag.arguments = Bundle(1).apply {
//                putInt("macaron_id_int", macaron.id.toInt())
//            }

            val view: FragmentContainerView? = inflater.inflate(R.layout.activity_productcrud,container,false).findViewById(R.id.fragB)
            if (view != null) {
                //device is xlarge and FragmentContainerView exists
//                transaction.replace(R.id.fragB, showProductFrag,arg,"macaron_id")

                fragmentManager?.commit {
                    setReorderingAllowed(true)
                    //replace whatever is in the fragB with the one in ShowProductFragment
                    replace<ShowProductFragment>(R.id.fragB,"macaron_id",arg)
                    setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                }
            } else { //device is small and FragmentContainerView does not exists
//                transaction.replace(R.id.fragA, showProductFrag)
                fragmentManager?.commit {
                    setReorderingAllowed(true)
                    //replace whatever is in the fragB with the one in ShowProductFragment
                    replace<ShowProductFragment>(R.id.fragA,"macaron_id",arg)
                    setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                }
            }


        }

        rvMacarons?.adapter = adapter

        //set layout manager to position the items
        rvMacarons?.layoutManager = LinearLayoutManager(layout.context)
        return layout
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProductFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProductFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}