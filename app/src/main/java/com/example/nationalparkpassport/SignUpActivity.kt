package com.example.nationalparkpassport
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity()
{
    private lateinit var username :EditText
    private lateinit var passwordOne:EditText
    private lateinit var passwordTwo :EditText
    private lateinit var signUpButton :Button

    private lateinit var firebaseAuth: FirebaseAuth


        private val textWatcher: TextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val inputtedUsername: String = username.text.toString().trim()
                val inputtedPassword: String = passwordOne.text.toString().trim()
                val inputtedPasswordTwo :String=passwordTwo.text.toString().trim()


                val enableButton: Boolean = inputtedUsername.isNotEmpty() && inputtedPassword.isNotEmpty() && inputtedPasswordTwo.isNotEmpty()
                signUpButton.isEnabled=enableButton


            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_activity)

        firebaseAuth = FirebaseAuth.getInstance()

        username = findViewById(R.id.signUpUsername)
        passwordOne = findViewById(R.id.passwordOne)
        passwordTwo = findViewById(R.id.passwordTwo)
        signUpButton = findViewById(R.id.signUpButton)



        //compare passwords and disable buttons until the fields are filled
        signUpButton.setOnClickListener{
            val inputtedPassword: String = passwordOne.text.toString().trim()
            val inputtedPasswordTwo :String=passwordTwo.text.toString().trim()
            val inputtedUsername = username.text.toString().trim()
            if(!(inputtedPassword).equals(inputtedPasswordTwo))
            {

                val toast = Toast.makeText(this@SignUpActivity,"Passwords do not match. Try again",Toast.LENGTH_SHORT)
                toast.show()

            }
            else
            {
                firebaseAuth.createUserWithEmailAndPassword(
                    inputtedUsername, inputtedPassword
                ).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = firebaseAuth.currentUser
                        Toast.makeText(this@SignUpActivity, "Created user: $user", Toast.LENGTH_SHORT).show()
                        Log.d("SignUpActivity", "Sign Up Clicked")
                        val intent: Intent = Intent(this, LandingActivity::class.java)
                        startActivity(intent)
                    } else {
                        val exception = task.exception
                        Toast.makeText(this@SignUpActivity, "Failed: $exception", Toast.LENGTH_SHORT).show()
                    }
                }

            }


        }





    }
}

