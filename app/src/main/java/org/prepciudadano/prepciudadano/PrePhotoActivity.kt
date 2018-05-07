package org.prepciudadano.prepciudadano

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class PrePhotoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pre_photo)

        val cameraBtn:Button = findViewById(R.id.camera_button)
        val sectionEt:EditText = findViewById(R.id.section)

        cameraBtn.setOnClickListener {
            val section = sectionEt.text.toString()
            if( section.isEmpty() ){
                sectionEt.error = "Por favor coloca una seccion"
            }else{
                val intent: Intent = Intent(this, PhotoActivity::class.java)
                intent.putExtra("section", section)
                startActivity(intent)
            }
        }
    }
}
