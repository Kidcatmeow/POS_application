package com.example.pos2

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider.getUriForFile
import java.io.File
import android.widget.LinearLayout
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException


class SettingActivity : AppCompatActivity(){



    private val TAG = "settingActivity"
    private var imageUri: Uri? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        // get reference to button
        val contact_btn = findViewById<Button>(R.id.SearchContactBtn)
        // set on-click listener
        contact_btn.setOnClickListener {

            val intent = Intent(this, ContactActivity::class.java)
            startActivity(intent)

        }


        val uploadimage_btn = findViewById<Button>(R.id.UploadImageBtn)
        uploadimage_btn.setOnClickListener {
            GlobalScope.launch {
                val url = "http://10.0.2.2/ITE343/pos_api/public/XD"
                val jsonObj = JSONObject()
                jsonObj.put("image_path",imageUri)
                jsonObj.put("description","Prayuth The Best")

                val jsonObjRequest = JsonObjectRequest(Request.Method.POST,url,jsonObj,{ response ->
                    // Display the response string
                    Log.i(TAG, "Response is: $response")
                }, { Log.i(TAG, "That didn't work! Error is : $it")})
                jsonObjRequest.retryPolicy=DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,0,1f)
                VolleySingleton.getInstance(applicationContext).addToRequestQueue(jsonObjRequest)

            }
        }


        //VOLLEY GET REQUEST
        val retrieveOrdersFromRemoteServerBtn = findViewById<Button>(R.id.RetrieveLocalOrdersBtn)
        retrieveOrdersFromRemoteServerBtn.setOnClickListener {
            Log.i(TAG, "Retrieve orders from remote server")
            //We should create a coroutine to run our network operation in another thread and not on UI thread

            GlobalScope.launch {
                //This is the URL of our RESTful Web API - GET orders
                val url = "http://10.0.2.2/ITE343/pos_api/public/orders"


                //Request that we will pass to volley
                val jsonRequest = JsonArrayRequest(
                    Request.Method.GET,//Type of request
                    url,//where to send the request
                    null, //The result of request, We dont need to pass values in a GET method
                    { response -> //What we will do with response,this is a callback function and what
                        // Display the first 500 characters of the response string
                        Log.i(TAG, "Response: %s".format(response.toString()))
                        for (i in 0 until response.length()) {
                            val order = response.getJSONObject(i)

                            //Your code to perform operations on retrieved data should be here
                            Log.i(
                                TAG,
                                "Order ID = ${order.get("id")}, branch ID is = ${order.get("branch_id")}"
                            )
                        }
                    },
                    {
                        Log.i(TAG, "That didn't work! Error is : $it")
                    }
                )

                //Volley request policy , only one time request to avoid duplicate transaction
                //what to do if the request fails
                jsonRequest.retryPolicy = DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    //0 means no retry
                    0, //DefaultRetryPolicy.DEFAULT_MAX_RETRIES = 2
                    1f//DefaultRetryPolicy.DEFAULT+BACKOFF_MULT
                )
                //Add the volley post request to the request queue
                VolleySingleton.getInstance(applicationContext).addToRequestQueue(jsonRequest)
            }
        }


        //VOLLEY POST REQUEST
        val SubmitOrderToRemoteServerBtn = findViewById<Button>(R.id.SubmitLocalOrdersBtn)
        //set on click listener
        SubmitOrderToRemoteServerBtn.setOnClickListener {
            Log.i(TAG, "Submit orders to remote server...")
            //Get orders from local database
            GlobalScope.launch {
                //the return value is Deferred<List<Order>>
                //Deferred value is a non-blocking cancellable future
                //it is a job with result
                val localOrders = getOrdersFromLocalDBAsync()

                Log.i(TAG, "Orders:")
                for (order in localOrders.await()) {
                    Log.i(
                        TAG,
                        "Order ID = ${order.uid}, " + "Branch ID = ${order.branchID} " + "Staff ID = ${order.staffID}"
                    )

                    //Instantiate the RequestQueue.
                    val url = "http://10.0.2.2/ITE343/pos_api/public/order"
                    val params = JSONObject()
                    params.put("order_local_id", "${order.uid}")
                    params.put("branch_id", "${order.branchID}")
                    params.put("staff_id", "${order.staffID}")

                    //Request a string response from the provided URL.
                    val jsonRequest = JsonObjectRequest(
                        Request.Method.POST,
                        url,
                        params,
                        { response ->
                            // Display the response string
                            Log.i(TAG, "Response is: $response")
                        },
                        {
                            Log.i(TAG, "That didn't work! Error is : $it")
                        }
                    )

                    //Volley request policy , only one time request to avoid duplicate transaction

                    jsonRequest.retryPolicy = DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                        //0 means no retry
                        0, //DefaultRetryPolicy.DEFAULT_MAX_RETRIES = 2
                        1f//DefaultRetryPolicy.DEFAULT+BACKOFF_MULT

                    )
                    //Add the volley post request to the request queue
                    VolleySingleton.getInstance(applicationContext).addToRequestQueue(jsonRequest)

                }
            }
        }



//// PKhing version!
//        SubmitOrderToRemoteServerBtn.setOnClickListener {
//            GlobalScope.launch {
//                val datafromlocalDB = getOrdersFromLocalDBAsync()
//                for(order in datafromlocalDB.await()){
//                    val post_request_body = JSONObject()
//                    post_request_body.put("branch_id",order.branchID)
//                    post_request_body.put("staff_id",order.staffID)
//                    post_request_body.put("order_id",order.uid)
//
//                    val url = "http://10.0.2.2/ITE343/pos_api/public/order"
//                    val jsonObjReq = JsonObjectRequest(Request.Method.POST,url,post_request_body,{
//                        response -> Log.i ("VolleyPostRequest","Successful: $response")},
//                        { error -> Log.i ("VolleyPostError", error.toString()) })
//
//                    jsonObjReq.retryPolicy= DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0, 1f)
//                    VolleySingleton.getInstance(applicationContext).addToRequestQueue(jsonObjReq))
//                }
//            }
//
//        }


        val OrdermanagerButton = findViewById<Button>(R.id.OrderManagerBtn)

        OrdermanagerButton.setOnClickListener {
            val intent = Intent(this, OrderManagerActivity::class.java)
            startActivity(intent)
        }











        //Function called after taking the photo ,use alertdialog after it receive the image from camera to inform if the action of taking photo is successful or not
        val takePic = registerForActivityResult(
            ActivityResultContracts.TakePicture()
        ) { isSuccess -> //callback
            if (isSuccess) { // if the image is successfully taken
                val builder = AlertDialog.Builder(this)
                builder.setTitle(R.string.phototaken)
                builder.setMessage(R.string.uploadornot)

                builder.setPositiveButton(R.string.yes) { dialog, which ->
                    Toast.makeText(this, R.string.yes, Toast.LENGTH_SHORT).show()


                    //add an ImageView  programmatically to the linear layout
                    val layout = findViewById<LinearLayout>(R.id.imagelayout)
                    val factor: Float =
                        layout.context.resources.displayMetrics.density //get the dimension of device
                    val width = (layout.width * factor * 0.5)
                    val height = (layout.height * factor * 0.3)
                    //use the view to design the width and height of the picture you want to show
                    val imageView = ImageView(this)
                    imageView.layoutParams = LinearLayout.LayoutParams(
                        width.toInt(),
                        height.toInt()
                    ) // value is in pixels
                    imageView.setImageURI(imageUri)
                    //Add ImageView that you receive from the camera to the layout
                    layout?.addView(imageView)
                }
                builder.setNegativeButton(R.string.no) { dialog, which ->
                    Toast.makeText(this, R.string.no, Toast.LENGTH_SHORT).show()
                }

//                builder.setNeutralButton("Maybe") { dialog, which ->
//                    Toast.makeText(this, R.string.maybe, Toast.LENGTH_SHORT).show()
//                }
                builder.show()

            } else {
                val builder = AlertDialog.Builder(this)
                builder.setTitle(R.string.phototaken)
                builder.setMessage(R.string.failed)

                builder.setPositiveButton("Ok") { dialog, which ->
                    Toast.makeText(this, "Ok", Toast.LENGTH_SHORT).show()
                }


//                builder.setNegativeButton("No") { dialog, which ->
//                    Toast.makeText(this, "No", Toast.LENGTH_SHORT).show()
//                }
//
//                builder.setNeutralButton("Maybe") { dialog, which ->
//                    Toast.makeText(this, "Maybe", Toast.LENGTH_SHORT).show()
//                }
                builder.show()

            }
        }

//The following code creates folder ”my images” external storage area and creates a new file with a random
        //name, it then creates a new URI and launches takePic ActivityResultLauncher and passes this URI to it.
        val uploadDailyReportBtn = findViewById<Button>(R.id.DailyReportBtn)
        uploadDailyReportBtn.setOnClickListener {
            val imagePath: File =
                File(getExternalFilesDir(null), "my_images") //create a folder named "my_images"
            imagePath.mkdirs()
            val newFile = File(imagePath, "img_" + System.currentTimeMillis() + ".jpg")
            //generate the file name for images taken System.currentTimeMillis() = random number generator

            val imgUri: Uri = getUriForFile(//build object for Uri class
                this@SettingActivity,
                "com.example.pos2.fileprovider",
                newFile
            ) //must be exactly the same as the name you given to the authorities path in the provider tag of your manifest
            this.imageUri = imgUri //Save it so we can use it in the callback
            takePic.launch(imgUri)//Takes photo

        }




    }
    private fun getOrdersFromLocalDBAsync() = GlobalScope.async {
        val db = POSAppDatabase.getInstance(applicationContext)
        db.orderDao().getAll()}



}
