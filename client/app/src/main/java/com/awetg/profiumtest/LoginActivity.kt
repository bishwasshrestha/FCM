package com.awetg.profiumtest

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.loginactivity_layout.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

val TAG = "Debug"

class LoginActivity : AppCompatActivity(), View.OnClickListener{

    lateinit var database:FirebaseDatabase
    lateinit var myRef:DatabaseReference
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loginactivity_layout)
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        register.setOnClickListener(this)
        signIn.setOnClickListener(this)
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(user: FirebaseUser?){
        if(user!=null){
            Toast.makeText(
                baseContext, "Welcome to profium",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun createAccount(email:String, password:String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    updateUI(user)
                    Log.d(TAG, "createUserWithEmail:success")
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }

    private fun signIn(email:String, password:String){

        if(validateForm()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        Toast.makeText(
                            baseContext, "Sign In Success!",
                            Toast.LENGTH_SHORT
                        ).show()
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        updateUI(null)
                    }
                }
        }
    }

    override fun onClick(v: View?) {
        val i = v!!.id
        when (i) {
            R.id.register -> createAccount(userEmail.text.toString(), userPassword.text.toString())
            R.id.signIn-> signIn(userEmail.text.toString(), userPassword.text.toString())
        }
    }



      private fun validateForm(): Boolean {
        var valid = true

        val email = userEmail.text.toString()
        if (TextUtils.isEmpty(email)) {
            userEmail.error = "Required."
            valid = false
        } else {
            userEmail.error = null
        }

        val password = userPassword.text.toString()
        if (TextUtils.isEmpty(password)) {
            userPassword.error = "Required."
            valid = false
        } else {
            userPassword.error = null
        }

        return valid
    }
}