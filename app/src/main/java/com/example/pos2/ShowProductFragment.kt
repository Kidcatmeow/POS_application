package com.example.pos2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ShowProductFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShowProductFragment : Fragment(R.layout.fragment_show_product) {
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

        val rootView = inflater.inflate(R.layout.fragment_show_product, container, false)
        //This is how we get the information from Bundle
        //Using Elvis operator?
        //val macaron_id = arguments?.getInt("macaron_id_int)?:1000
        //or you can use this:
//
//        val macarons = Macaron.createMacaronList()
//        val adapter = MacaronAdapter(macarons) { macaron ->
////            arguments?.getInt("macaron_id_int")?.let { macaron.id }
//
//
//            val macaron_tv = rootView.findViewById<TextView>(R.id.productName)
//            //tricl : subtract vy 1000 so we can get index of macarons
        val macaron_img = rootView.findViewById<ImageView>(R.id.imageFrag)
        val macaron_id_from_another_fragment = arguments?.getInt("macaron_id")


        when (macaron_id_from_another_fragment) {
            1001 -> macaron_img.setImageResource(R.drawable.blueberry_lemon)
            1002 -> macaron_img.setImageResource(R.drawable.red_velvet)
            1003 -> macaron_img.setImageResource(R.drawable.earl_grey)
            1004 -> macaron_img.setImageResource(R.drawable.rose)
            1005 -> macaron_img.setImageResource(R.drawable.mint_chocolate)
            1006 -> macaron_img.setImageResource(R.drawable.coffee)
            1007 -> macaron_img.setImageResource(R.drawable.matcha)
            else -> {
                macaron_img.setImageResource(R.drawable.blueberry_lemon)
            }
        }


        return rootView
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ShowProductFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ShowProductFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}