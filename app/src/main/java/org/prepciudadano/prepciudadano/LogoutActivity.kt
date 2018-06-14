package org.prepciudadano.prepciudadano

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_logout.*
import org.prepciudadano.prepciudadano.utils.Config
import org.prepciudadano.prepciudadano.utils.GoogleAuth

class LogoutActivity : AppCompatActivity() {

    lateinit var config: Config

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logout)

        val gAuth = GoogleAuth(this)
        config = Config(this)

        logout.setOnClickListener {
            gAuth.logout()
            Toast.makeText(this, "Te has desconectado con éxito", Toast.LENGTH_SHORT).show()
            config.set("loggedIn", "")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}