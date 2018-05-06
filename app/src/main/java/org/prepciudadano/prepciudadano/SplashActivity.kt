package org.prepciudadano.prepciudadano

import android.support.v7.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, PresentationActivity::class.java)
        startActivity(intent)
        finish()
    }
}
