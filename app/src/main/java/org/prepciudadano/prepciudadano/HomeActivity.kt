package org.prepciudadano.prepciudadano

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import org.prepciudadano.prepciudadano.adapters.CardAdapter
import org.prepciudadano.prepciudadano.classes.Card

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

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
}
