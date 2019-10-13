package org.prepciudadano.prepciudadano.utils

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import org.prepciudadano.prepciudadano.PrePhotoActivity
import org.prepciudadano.prepciudadano.R

class GoogleAuth(var activity: AppCompatActivity){

    val mAuth = FirebaseAuth.getInstance()
    val RC_SIGN_IN: Int = 1
    val config: Config = Config(activity)

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
                activity.finish()
                config.set("loggedIn", "true")
            }else{
                Toast.makeText(activity, result.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        //Log.e(TAG, "firebaseAuthWithGoogle():" + acct.id!!)
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth!!.signInWithCredential(credential).addOnCompleteListener {task ->
            if(task.isSuccessful){
                //Sign in success
                val user = mAuth.currentUser
                //Toast.makeText(activity, user.toString(), Toast.LENGTH_LONG).show()
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
            config.set("userId", user.uid)
        } else {
            tvStatus.text = "Signed Out"
            tvDetail.text = null
        }
    }

    fun logout(){
        // sign out Firebase
        mAuth!!.signOut()
        // sign out Google
        //Auth.GoogleSignInApi.signOut().setResultCallback {  }
    }
}