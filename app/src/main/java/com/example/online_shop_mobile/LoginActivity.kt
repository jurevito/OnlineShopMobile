package com.example.online_shop_mobile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class LoginActivity : AppCompatActivity(), Callback<Login> {

    private var login: Login? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // when login button clicked get session
        btnLogin.setOnClickListener {

            val email = emailEdit.text.toString()
            val password = passwordEdit.text.toString()
            if(email.isNotBlank() && password.isNotBlank()) {
                LoginAPI.instance.login(email, password).enqueue(this)
            } else {
                Toast.makeText(this, "Do not leave blanks.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResponse(call: Call<Login>, response: Response<Login>) {
        login = response.body()
        //Log.i("debugTag1", "Got result: " + login)

        if (response.isSuccessful) { // 200 .. 300
            Log.i("debugTag2","Response successful")
            //Log.i("debugTag3","Name = " + login!!.name)

            // go to user profile with info
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("name", login?.name)
            intent.putExtra("lastname", login?.lastName)
            intent.putExtra("email", login?.email)
            intent.putExtra("address", login?.address)
            intent.putExtra("zipcode", login?.zipcode)
            intent.putExtra("phone", login?.phone)
            startActivity(intent)

        } else {
            val errorMessage = try {
                "An error occurred: ${response.errorBody().toString()}"
            } catch (e: IOException) {
                "An error occurred: error while decoding the error message."
            }

            Toast.makeText(this, "Invalid login.", Toast.LENGTH_SHORT).show()
            Log.e("debugTag4", errorMessage)
        }
    }

    override fun onFailure(call: Call<Login>, t: Throwable) {
        Log.w("debugTag5", "Error: ${t.message}", t)
        Toast.makeText(this, "You are a failure!", Toast.LENGTH_SHORT).show()
    }
}