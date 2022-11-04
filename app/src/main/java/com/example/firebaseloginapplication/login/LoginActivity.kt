package com.example.firebaseloginapplication.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaseloginapplication.MainActivity
import com.example.firebaseloginapplication.databinding.ActivityLoginBinding
import com.example.firebaseloginapplication.signup.SignUpActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = Firebase.auth
        binding.btSubmit.setOnClickListener { login() }
        binding.tvSignUp.setOnClickListener {
            startActivity(
                Intent(
                    this@LoginActivity,
                    SignUpActivity::class.java
                )
            )
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        currentUser?.reload()
    }

    private fun login() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                startActivity(
                    Intent(
                        this@LoginActivity,
                        MainActivity::class.java
                    )
                )
            }
            .addOnFailureListener { Log.w(TAG, "signInWithEmail:failure", it.cause) }
    }

//    private fun logout(){
//        auth.signOut()
//    }
}