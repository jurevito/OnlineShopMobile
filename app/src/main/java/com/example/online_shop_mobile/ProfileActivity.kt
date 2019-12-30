package com.example.online_shop_mobile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_profile.*


class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val name = intent.getStringExtra("name")
        val lastname = intent.getStringExtra("lastname")
        val email = intent.getStringExtra("email")
        val address = intent.getStringExtra("address")
        val zipcode = intent.getIntExtra("zipcode", -1)
        val phone = intent.getStringExtra("phone")

        Log.i("debugProfile","$name, $lastname, $email, $address, $zipcode, $phone")

        nameView.text = "$name $lastname"
        emailView.text = email
        addressView.text = "$address ($zipcode)"
        phoneView.text = phone


        // when edit button clicked
        btnEdit.setOnClickListener {
            Log.i("profile","Edit Profile")
        }

        // when logout button pressed
        btnLogout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }



        // <---NAVIGATION--->
        if(name != null) {
            login.text = "Profile"
        } else {
            login.text = "Login"
        }

        store.setOnClickListener {
            // user is logged in
            if(name != null) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("name", name)
                intent.putExtra("lastname", lastname)
                intent.putExtra("email", email)
                intent.putExtra("address", address)
                intent.putExtra("zipcode", zipcode)
                intent.putExtra("phone", phone)
                startActivity(intent)
            }

            // go to store without info
            else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

        cart.setOnClickListener {
            Log.i("profile","Cart tab")
        }
        // <---END--->
    }
}