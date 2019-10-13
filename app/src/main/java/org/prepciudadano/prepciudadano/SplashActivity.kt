package org.prepciudadano.prepciudadano

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import org.prepciudadano.prepciudadano.utils.Config

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val config: Config = Config(this)
        val hide = config.get("hide_slide", "")
        if( hide != "" ){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            val intent = Intent(this, PresentationActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
