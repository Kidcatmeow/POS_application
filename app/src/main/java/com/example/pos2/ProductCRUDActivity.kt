package com.example.pos2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.*
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.example.pos2.databinding.ActivityProductcrudBinding
import kotlinx.android.synthetic.main.activity_productcrud.*


class ProductCRUDActivity : AppCompatActivity() {

    private val TAG ="ProductCRUDActivity"
    private lateinit var appBarConfiguration : AppBarConfiguration
//    private lateinit var binding: ActivityProductcrudBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

//        binding = ActivityProductcrudBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_productcrud)


//        replaceFragment1(Fragment(R.layout.fragment_product))


//        //Add the first fragment
//        supportFragmentManager.commit {
//            setReorderingAllowed(true)
//            add<ProductFragment>(R.id.fragA) as FragmentContainerView
//        }


//
//        //Add the second fragment only if device is xlarge
//        val view: FragmentContainerView? = binding.root.findViewById<FragmentContainerView>(R.id.fragB)
//
//        if (view != null)
////            supportFragmentManager.commit {
////                setReorderingAllowed(true)
////                add<ShowProductFragment>(R.id.show_product_fragmentContainerView2)
////
////            }
//            replaceFragment2(Fragment(R.layout.fragment_show_product))


    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController =
//            findNavController(R.id.nav_host_fragment_container)
//
//        return navController.navigateUp(appBarConfiguration)
//                || super.onSupportNavigateUp()
//    }

    private fun replaceFragment1 (fragment: Fragment){
        val fragmentManger = supportFragmentManager
        val fragmentTransaction = fragmentManger.beginTransaction()
        fragmentTransaction.replace(R.id.fragA,fragment)
        fragmentTransaction.commit()
    }
    private fun replaceFragment2 (fragment: Fragment){
        val fragmentManger = supportFragmentManager
        val fragmentTransaction = fragmentManger.beginTransaction()
        fragmentTransaction.replace(R.id.fragB,fragment)
        fragmentTransaction.commit()

    }
}

