package org.prepciudadano.prepciudadano

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    lateinit var signOut: Button
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        mAuth = FirebaseAuth.getInstance()

        signOut = findViewById(R.id.signout)

        val account:String = intent.getStringExtra("account_name")
        val account_name:TextView = findViewById(R.id.display_text)
        account_name.text = account

        signOut.setOnClickListener {
            logout()
        }
        */
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        //val currentUser = mAuth!!.currentUser
        //Toast.makeText(this, currentUser.toString(), Toast.LENGTH_LONG).show()
    }

    private fun logout(){
        // sign out Firebase
        //mAuth!!.signOut()
        // sign out Google
        //Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback {  }
    }
}
