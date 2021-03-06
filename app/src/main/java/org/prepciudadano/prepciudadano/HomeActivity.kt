package org.prepciudadano.prepciudadano

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import org.prepciudadano.prepciudadano.adapters.CardAdapter
import org.prepciudadano.prepciudadano.classes.Card

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val toolbar: Toolbar = findViewById(R.id.include)
        setSupportActionBar(toolbar)

        val recyclerView: androidx.recyclerview.widget.RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        val cards = ArrayList<Card>()

        val mainIntent = Intent(this, MainActivity::class.java)
        val findIntent = Intent(this, FindBoxActivity::class.java)
        val loginIntent = Intent(this, LoginActivity::class.java)
        val incidencesIntent = Intent(this, IncidencesActivity::class.java)
        val aboutUsIntent = Intent(this, AboutUsActivity::class.java)
        val donationsIntent = Intent(this, DonationsActivity::class.java)
        val sliderIntent = Intent(this, PresentationActivity::class.java)

        cards.add(Card("PREP", "Consulta los resultados en tiempo real", R.drawable.prep, mainIntent, this))

        val adapter = CardAdapter(cards)

        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }
}
