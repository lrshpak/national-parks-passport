package com.example.nationalparkpassport

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var username: EditText

    private lateinit var password: EditText

    private lateinit var login: Button

    private lateinit var signup: Button

    private lateinit var rememberUsername: CheckBox

    private lateinit var rememberPassword: CheckBox

    private lateinit var  firebaseAuth: FirebaseAuth

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val inputtedUsername: String = username.text.toString().trim()
            val inputtedPassword: String = password.text.toString().trim()
            val enableButton: Boolean = inputtedUsername.isNotEmpty() && inputtedPassword.isNotEmpty()

            login.isEnabled = enableButton
            signup.isEnabled = true
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseAuth = FirebaseAuth.getInstance()

        Log.d("MainActivity", "onCreate called")

        val sharedPrefs = getSharedPreferences("user_settings", Context.MODE_PRIVATE)

        username = findViewById(R.id.parkName)
        password = findViewById(R.id.password)
        login = findViewById(R.id.login)
        signup = findViewById(R.id.signup)
        rememberUsername = findViewById(R.id.rememberUsername)
        rememberPassword = findViewById(R.id.rememberPassword)

        rememberUsername.isChecked = true
        rememberPassword.isChecked = true

        val savedUsername = sharedPrefs.getString("SAVED_USERNAME", "")
        val savedPassword = sharedPrefs.getString("SAVED_PASSWORD", "")

        username.setText(savedUsername)
        password.setText(savedPassword)

        username.addTextChangedListener(textWatcher)
        password.addTextChangedListener(textWatcher)

        var boolUser = false
        var boolPass = false


        rememberUsername.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
                boolUser = true
        }

        rememberPassword.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
                boolPass = true
        }

        login.setOnClickListener {
            val inputtedUsername = username.text.toString().trim()
            val inputtedPassword = password.text.toString().trim()
            Log.d("MainActivity", "Login Clicked")
            if (boolUser) {
                sharedPrefs.edit().putString("SAVED_USERNAME", inputtedUsername).apply()
            }
            if (boolPass) {
                sharedPrefs.edit().putString("SAVED_PASSWORD", inputtedPassword).apply()
            }

            firebaseAuth.signInWithEmailAndPassword(
                inputtedUsername, inputtedPassword
            ).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    Toast.makeText(this@MainActivity, "Logged in as user: $user", Toast.LENGTH_SHORT).show()
                    val intent : Intent = Intent(this, LandingActivity::class.java)
                    startActivity(intent)
                } else {
                    val exception = task.exception
                    Toast.makeText(this@MainActivity, "Failed: $exception", Toast.LENGTH_SHORT).show()
                }
            }

        }

        signup.setOnClickListener {
            Log.d("MaingActivity", "Sign Up Clicked")
            val intent: Intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)


        }

    }
}
