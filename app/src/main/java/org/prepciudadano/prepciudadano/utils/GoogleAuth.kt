package org.prepciudadano.prepciudadano.utils

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import org.prepciudadano.prepciudadano.MainActivity
import org.prepciudadano.prepciudadano.PrePhotoActivity
import org.prepciudadano.prepciudadano.R

class GoogleAuth(var activity: AppCompatActivity){

    val mAuth = FirebaseAuth.getInstance()
    val RC_SIGN_IN: Int = 1

    init { }

    fun doLogin(mGSO:GoogleSignInClient){
        val intent: Intent = mGSO.signInIntent
        activity.startActivityForResult(intent, RC_SIGN_IN)
    }

    fun checkResult(requestCode:Int, data:Intent){
        if(requestCode == RC_SIGN_IN){
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if( result.isSuccess ){
                val account = result.signInAccount
                firebaseAuthWithGoogle(account!!)
                val intent = Intent(activity, PrePhotoActivity::class.java)
                activity.startActivity(intent)
            }else{

            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        //Log.e(TAG, "firebaseAuthWithGoogle():" + acct.id!!)
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth!!.signInWithCredential(credential).addOnCompleteListener {task ->
            if(task.isSuccessful){
                //Sign in success
                val user = mAuth!!.currentUser
                //Toast.makeText(this, user.toString(), Toast.LENGTH_LONG).show()
                updateUI(user)
            }else{
                updateUI(null)
            }
        }
    }

    private fun updateUI(user: FirebaseUser?){
        val tvStatus:TextView = activity.findViewById(R.id.tvStatus)
        val tvDetail:TextView = activity.findViewById(R.id.tvDetail)
        if (user != null) {
            tvStatus.text = "Google User email: " + user.email!!
            tvDetail.text = "Firebase User ID: " + user.uid
        } else {
            tvStatus.text = "Signed Out"
            tvDetail.text = null
        }
    }
}