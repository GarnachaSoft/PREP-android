package org.prepciudadano.prepciudadano

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class PrePhotoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pre_photo)

        val cameraBtn:Button = findViewById(R.id.camera_button)

        cameraBtn.setOnClickListener {
            val intent: Intent = Intent(this, PhotoActivity::class.java)
            startActivity(intent)
        }
    }
}
