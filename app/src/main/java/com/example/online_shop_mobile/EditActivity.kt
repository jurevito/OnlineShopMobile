package com.example.online_shop_mobile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.online_shop_mobile.api.UserAPI
import com.example.online_shop_mobile.pojo.User
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_profile.cart
import kotlinx.android.synthetic.main.activity_profile.login
import kotlinx.android.synthetic.main.activity_profile.store
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class EditActivity : AppCompatActivity(), Callback<User> {

    private var user: User? = null
    private var idUser: Int = 0
    private var name: String = ""
    private var lastName: String = ""
    private var email: String = ""
    private var password: String = ""
    private var type: Int = 0
    private var address: String = ""
    private var zipcode_id: Int = 0
    private var phone: String = ""
    private var activated: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        // get user data
        val id = (application as MyApplication).id
        if(id != null) {
            UserAPI.instance.getUserData(id).enqueue(this)
        }

        // submit button pressed
        btnSubmit.setOnClickListener {
            val newName = editName.text.toString()
            val newLastname = editLastname.text.toString()
            val newEmail = editEmail.text.toString()
            val newAddress = editAddress.text.toString()
            val newPhone = editPhone.text.toString()

            if(newName.isNotBlank() && newLastname.isNotBlank()) {
                if(newEmail.isNotBlank() && newAddress.isNotBlank() && newPhone.isNotBlank()) {
                    UserAPI.instance.updateUserData(idUser, newName, newLastname, newEmail, password, type, newAddress, zipcode_id, newPhone, activated).enqueue(this)
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "No blank inputs allowed.", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this, "Name cannot be blank.", Toast.LENGTH_SHORT).show()
            }
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
            Log.i("edit","Cart tab")
        }

        login.setOnClickListener {
            // user is logged in
            if(id != null) {
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
            }

            // login
            else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        // <---END--->
    }

    override fun onResponse(call: Call<User>, response: Response<User>) {
        user = response.body()
        //Log.i("debugTag1", "Got result: " + user)

        if(response.code() == 201) {
            Toast.makeText(this, "Successful Submit", Toast.LENGTH_SHORT).show()
        } else {
            if (response.code() == 200) { // 200 .. 300
                Log.i("debugTagEDIT","Response successful " + response.code())
                Log.i("debugTag3","Name = " + user!!.name)

                idUser = user!!.id
                name = user!!.name
                lastName = user!!.lastName
                email = user!!.email
                password = user!!.password
                type = user!!.type
                address = user!!.address
                zipcode_id = user!!.zipcode_id
                phone = user!!.phone
                activated = user!!.activated

                editName.setText(name)
                editLastname.setText(lastName)
                editEmail.setText(email)
                editAddress.setText(address)
                editPhone.setText(phone)

            } else {
                val errorMessage = try {
                    "An error occurred: ${response.errorBody().toString()}"
                } catch (e: IOException) {
                    "An error occurred: error while decoding the error message."
                }

                Toast.makeText(this, "Oh shiet." + response.code(), Toast.LENGTH_SHORT).show()
                Log.e("debugTag4", errorMessage)
            }
        }
    }

    override fun onFailure(call: Call<User>, t: Throwable) {
        Log.w("debugTag5", "Error: ${t.message}", t)
        //Toast.makeText(this, "You are a failure2!", Toast.LENGTH_SHORT).show()
    }
}