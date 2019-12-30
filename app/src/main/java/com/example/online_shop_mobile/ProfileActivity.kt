package com.example.online_shop_mobile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class ProfileActivity : AppCompatActivity(), Callback<User> {

    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // when edit button clicked
        btnEdit.setOnClickListener {
            Log.i("profile","Edit Profile")
        }

        // when logout button pressed
        btnLogout.setOnClickListener {
            (application as MyApplication).id = null
            (application as MyApplication).sessionID = null
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // get user data
        val id = (application as MyApplication).id
        if(id != null) {
            UserAPI.instance.getUserData(id).enqueue(this)
        }


        // <---NAVIGATION--->
        if(id != null) {
            login.text = "Profile"
        } else {
            login.text = "Login"
        }

        store.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        cart.setOnClickListener {
            Log.i("profile","Cart tab")
        }
        // <---END--->
    }

    override fun onResponse(call: Call<User>, response: Response<User>) {
        user = response.body()
        Log.i("debugTag1", "Got result: " + user)

        if (response.isSuccessful) { // 200 .. 300
            Log.i("debugTag2","Response successful")
            Log.i("debugTag3","Name = " + user!!.name)

            val name = user?.name
            val lastname = user?.lastName
            val email = user?.email
            val address = user?.address
            val phone = user?.phone

            Log.i("debugProfile","$name, $lastname, $email, $address, $phone")

            nameView.text = "$name $lastname"
            emailView.text = email
            addressView.text = address
            phoneView.text = phone

        } else {
            val errorMessage = try {
                "An error occurred: ${response.errorBody().toString()}"
            } catch (e: IOException) {
                "An error occurred: error while decoding the error message."
            }

            Toast.makeText(this, "Oh shiet.", Toast.LENGTH_SHORT).show()
            Log.e("debugTag4", errorMessage)
        }
    }

    override fun onFailure(call: Call<User>, t: Throwable) {
        Log.w("debugTag5", "Error: ${t.message}", t)
        Toast.makeText(this, "You are a failure2!", Toast.LENGTH_SHORT).show()
    }
}