package com.isaiah.rattlerbees

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    companion object {
        const val TAG = "LoginActivity"
    }

    // Initialize Firebase Auth
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        val etEmail = findViewById<EditText>(R.id.login_etEmail)
        val etPassword = findViewById<EditText>(R.id.login_etPassword)
        val btnLogin = findViewById<Button>(R.id.login_btnLogin)
        val btnRegister = findViewById<Button>(R.id.login_btnRegister)

        btnLogin.setOnClickListener{
            // Perform this code when button is pressed
            if(etEmail.text.trim().toString().isNotEmpty() || etPassword.text.trim().toString().isNotEmpty()){

                val email = etEmail.text.trim().toString()
                val password = etPassword.text.trim().toString()

                signOn(email, password)

            } else {

                Toast.makeText(this, "Input required", Toast.LENGTH_LONG).show()

            }
        }

        btnRegister.setOnClickListener {
            // Perform this code when button is pressed

            // create intent to navigate to registration activity
            val intent = Intent(this, RegisterActivity::class.java)
            // start next activity
            startActivity(intent)
        }

    }


    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            goToMainActivity();
        }
    }

    private fun goToMainActivity() {
        // create intent to navigate to main activity
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
//        finish()
    }

    private fun goToLoginActivity() {
        // create intent to navigate to main activity
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
//        finish()
    }

    private fun signOn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful){
                        // Sign in success, update UI with current user's information
                        Log.d(RegisterActivity.TAG, "signInWithEmail: Success")
                        val user = auth.currentUser
                        goToMainActivity()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(RegisterActivity.TAG, "signInWithEmail: Failure", task.exception)
                        Toast.makeText(baseContext, "Incorrect username or password",
                                Toast.LENGTH_SHORT).show()
                    }
                }
    }



}
