package org.prepciudadano.prepciudadano

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar

class IncidencesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_incidences)

        //toolbar
        val toolbar: Toolbar = findViewById(R.id.include)
        toolbar.title = "Reportar Incidencia"
        setSupportActionBar(toolbar)
    }
}