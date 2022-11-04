package com.example.firebaseloginapplication.signup

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaseloginapplication.databinding.ActivitySignUpBinding
import com.example.firebaseloginapplication.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySignUpBinding.inflate(layoutInflater) }
    private lateinit var auth: FirebaseAuth
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = Firebase.auth
        binding.btSubmit.setOnClickListener { createUser() }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        currentUser?.reload()
    }

    private fun createUser() {
        name = binding.etName.text.toString().trim()
        email = binding.etEmail.text.toString().trim()
        password = binding.etPassword.text.toString().trim()
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            updateUser()
            startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
            finish()
        }.addOnFailureListener { Log.w(TAG, "createUserWithEmail:failure", it.cause) }
    }

    private fun updateUser() {
        val user = auth.currentUser
        val profileUpdates = userProfileChangeRequest {
            displayName = name
        }
        user!!.updateProfile(profileUpdates)
            .addOnSuccessListener { Log.d(TAG, "User profile updated.") }

    }
}