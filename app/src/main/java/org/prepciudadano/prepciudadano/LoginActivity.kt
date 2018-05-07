package org.prepciudadano.prepciudadano

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.firebase.auth.FirebaseAuth
import org.prepciudadano.prepciudadano.utils.Config
import org.prepciudadano.prepciudadano.utils.GoogleAuth


class LoginActivity : AppCompatActivity() {

    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var gso: GoogleSignInOptions
    var gAuth:GoogleAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val config: Config = Config(this)
        if( config.get("loggedIn", "") != "" ){
            val intent = Intent(this, PrePhotoActivity::class.java)
            startActivity(intent)
        }

        gAuth = GoogleAuth(this)

        //elements
        val loginGoogle : SignInButton = findViewById(R.id.login_google)

        //google sign in options
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        //actions
        loginGoogle.setOnClickListener { gAuth?.doLogin(mGoogleSignInClient) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        gAuth?.checkResult(requestCode, data!!)
    }

}
