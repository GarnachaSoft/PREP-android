package org.prepciudadano.prepciudadano

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar

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