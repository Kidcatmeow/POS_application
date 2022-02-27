package com.example.pos2

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Base64
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.nio.charset.Charset
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec
import com.example.pos2.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {

    lateinit var sharedPreferences : SharedPreferences
    var isRemembered = false
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.i(TAG, "OnCreate() is called")


        loadData()


//        sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE)
//        isRemembered = sharedPreferences.getBoolean("CHECKBOX",false)
//        if (isRemembered){
//
//            val name = sharedPreferences.getString((getString(R.string.username_key)),"")
//            val password = sharedPreferences.getString((getString(R.string.password_key)),"")
//            binding.usernameFill?.setText(name)
//            binding.passwordFill?.setText(password)
//            binding.rememberMeCheckBox?.isChecked = true
//        }


        // get reference to button
        val btnClickMe = findViewById<Button>(R.id.loginBtn)
        // set on-click listener
        btnClickMe.setOnClickListener {
//            onclick_login_btn(btnClickMe)
//            Log.i(TAG,"Login button click. Command center UI started")


//            val name : String = usernameFill.text.toString()
//            val password : String = passwordFill.text.toString()
//            val checked : Boolean = rememberMeCheckBox.isChecked
//            val editor : SharedPreferences.Editor = sharedPreferences.edit()
//            editor.putString((getString(R.string.username_key)),name)
//            editor.putString((getString(R.string.password_key)),password)
//            editor.putBoolean("CHECKBOX",checked)
//            editor.apply()
//
//            val intent = Intent(this,CommandCenterActivity::class.java)
//        Log.d(TAG,"Login process successful,going to start the AddProductActivity")
//        startActivity(intent)
            saveData()

        val intent = Intent(this,CommandCenterActivity::class.java)
        Log.d(TAG,"Login process successful,going to start the AddProductActivity")
        startActivity(intent)


        }

//        btnClickMe.setOnClickListener() {
//            Toast.makeText(this@MainActivity, "Welcome,manager!", Toast.LENGTH_SHORT).show()
//
//            val intent = Intent(this,CommandCenterActivity::class.java)
//            startActivity(intent)
//
//        }


//-------------------------------Ajarn Ehsan Version------------------
//        //Load the saved credentials in shared preferences whenever the app start
//        val sharedPref = getSharedPreferences(
//            getString(R.string.preference_file_key),Context.MODE_PRIVATE)
//
//        //read credentials_stored_key from shared preferences which is a boolean value
//        //if it is true then it means a username and password has already been saved
//        val isAnyCredentialsSaved = sharedPref.getBoolean(getString(R.string.credential_stored_key),false)
//
//        if (isAnyCredentialsSaved){
//            //if credentials exists
//                // We need only keyBtyes and iv to decrypted the password
//                    //user name is plain text so it needs no decryption
//            val username = sharedPref.getString(getString(R.string.username_key),"")
//            val passwordBase64 = sharedPref.getString(getString(R.string.password_key),"")
//            val keyBytesBase64 = sharedPref.getString(getString(R.string.keyBytes_key),"")
//            val ivBase64 = sharedPref.getString(getString(R.string.iv_key),"")
//
//            val password = Base64.decode(passwordBase64,Base64.NO_WRAP)
//            val keyBytes = Base64.decode(keyBytesBase64,Base64.NO_WRAP)
//            val iv = Base64.decode(ivBase64,Base64.NO_WRAP)
//
//            val ivSpec = IvParameterSpec(iv)
//
//            val keySpec = SecretKeySpec (keyBytes,"AES")
//            val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
//            cipher.init(Cipher.DECRYPT_MODE,keySpec,ivSpec)
//            val decryptedPassword : ByteArray = cipher.doFinal(password)
//            val passwordStr = String(decryptedPassword, Charset.forName("UTF-8"))
//            Log.i(TAG,"Decrypted password is = $passwordStr")
//
//            binding.usernameFill?.setText(username)
//            binding.passwordFill.setText(passwordStr)
//            binding.rememberMeCheckBox?.isChecked = true
//
//        }



        // get reference textview
        val textTitle = findViewById<TextView>(R.id.CommandCenter)
        // set long-click listener
        textTitle.setOnVeryLongClickListener {
            Toast.makeText(this@MainActivity, "Proceed to Settings.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this,SettingActivity::class.java)
            startActivity(intent)
        }

    }

    fun saveData(){

        //Set up variable for the credentials
        val name : String = usernameFill.text.toString()
        val password : String = passwordFill.text.toString()
        val checked : Boolean = rememberMeCheckBox.isChecked

        //set up shared preferences
        sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE)
        //set up editor
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.apply{//save data into the editor
            putString((getString(R.string.username_key)),name)
            putString((getString(R.string.password_key)),password)
            putBoolean("CHECKBOX",checked)

        }.apply() //apply is a must in order to save the data into shared preferences
    }


    fun loadData(){
        //set up shared preferences
        sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE)
        isRemembered = sharedPreferences.getBoolean("CHECKBOX",false)

        if(isRemembered) {
            val name = sharedPreferences.getString((getString(R.string.username_key)), "")
            val password = sharedPreferences.getString((getString(R.string.password_key)), "")
            binding.usernameFill?.setText(name)
            binding.passwordFill?.setText(password)
            binding.rememberMeCheckBox?.isChecked = true
        }
    }

    fun onclick_login_btn(view : View){

        //Check if username and password is empty or not
        if(binding.rememberMeCheckBox?.isChecked == true){
            val username = binding.usernameFill?.text.toString()
            val password = binding.passwordFill?.text.toString()

            if (username.isNotEmpty()){ //if username is not empty then we get access to shared reference
                val sharedPref = getSharedPreferences(
                    getString(R.string.preference_file_key), Context.MODE_PRIVATE)

                //Due to security reasons,we have to use encryption mechanism to hide the password,using android cryptography
                //In this example,we use Advanced Encryption Standard(AES) encryption algorithms which is a symmetric block cipher
                //Password Based Encryption(PBE) where human memorizable password is used , we use salt in combination to generate AES key
                //Salt is used so the same password won't always generate the same key
                val random = SecureRandom()
                val salt = ByteArray(256)
                val passkey = ByteArray(8)
                random.nextBytes(salt)
                random.nextBytes(passkey)

                val pbKeySpace = PBEKeySpec(passkey.toString().toCharArray(),salt,1324,256)
                val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
                val keyBytes = secretKeyFactory.generateSecret(pbKeySpace).encoded


                val keySpec = SecretKeySpec (keyBytes,"AES")
                //this encryption algorithm need IV which is a random number that guarantees the encryped text is unique
                val ivRandom = SecureRandom()
                val iv = ByteArray(16)
                ivRandom.nextBytes(iv)
                val ivSpec = IvParameterSpec(iv)
                val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
                cipher.init(Cipher.ENCRYPT_MODE,keySpec,ivSpec)
                val encrypted = cipher.doFinal(password.toByteArray(Charset.forName("UTF-8"))) //2

                //Save username and password into shared preferences
                Log.i(TAG,"Remember me is checked.Username is = $username , password = $password")
                Log.i(TAG,"Enconded ciphered password is = $encrypted")

                //Encrypted password is a ByteArray and cannot be save directly to shared preferences
                //We have to convert it to String and then save it using Base64
                val passwordBase64 = Base64.encodeToString(encrypted,Base64.NO_WRAP)
                val keyBytesBase64 = Base64.encodeToString(keyBytes,Base64.NO_WRAP)
                val saltBase64 = Base64.encodeToString(salt,Base64.NO_WRAP)
                val ivBase64 = Base64.encodeToString(iv,Base64.NO_WRAP)


                // save the credentials into shared preferences XML file specified by R.string.preference_file_key
                with(sharedPref.edit()){
                    putString(getString(R.string.username_key),username)
                    putString(getString(R.string.password_key),passwordBase64)
                    putString(getString(R.string.iv_key),ivBase64)
                    putBoolean(getString(R.string.credential_stored_key),true)
                    apply()
                }
            }
        }
        else {
            val sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key),Context.MODE_PRIVATE)
            with(sharedPref.edit()){
                putBoolean(getString(R.string.credential_stored_key),false)
                apply()
            }
        }
        val intent = Intent(this,CommandCenterActivity::class.java)
        Log.d(TAG,"Login process successful,going to start the AddProductActivity")
        startActivity(intent)
    }

    fun View.setOnVeryLongClickListener(listener: () -> Unit) {
        setOnTouchListener(object : View.OnTouchListener {

            private val longClickDuration = 1000L
            private val handler = Handler()

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                if (event?.action == MotionEvent.ACTION_DOWN) {
                    handler.postDelayed({ listener.invoke() }, longClickDuration)
                } else if (event?.action == MotionEvent.ACTION_UP) {
                    handler.removeCallbacksAndMessages(null)
                }
                return true
            }
        })
    }

    override fun onStart(){
        super.onStart()
        Log.i(ContentValues.TAG, "OnStart() is called")

    }

    override fun onRestart(){
        super.onRestart()
        Log.i(ContentValues.TAG, "OnRestart() is called")

    }


    override fun onResume(){
        super.onResume()
        Log.i(ContentValues.TAG, "OnResume() is called")

    }

    override fun onPause(){
        super.onPause()
        Log.i(ContentValues.TAG, "OnPause() is called")
    }


    override fun onSaveInstanceState(SavedInstanceState: Bundle) {
        super.onSaveInstanceState(SavedInstanceState)
        Log.i(ContentValues.TAG, "onSaveInstanceState() is called")
    }


    override fun onStop(){
        super.onStop()
        Log.i(ContentValues.TAG, "OnStop() is called")
    }

    override fun onDestroy(){
        super.onDestroy()
        Log.i(ContentValues.TAG, "OnDestroy() is called")
    }


}