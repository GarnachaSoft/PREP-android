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
        val loginIntent = Intent(this, LoginActivity::class.java)

        cards.add(Card("PREP", "Consulta los resultados en tiempo real", R.drawable.cat, mainIntent, this))
        cards.add(Card("Encuentra tu casilla", "Consulta los resultados en tiempo real", R.drawable.bird, mainIntent, this))
        cards.add(Card("Colaborar", "Consulta los resultados en tiempo real", R.drawable.chicken, loginIntent, this))
        cards.add(Card("Incidencias", "Consulta los resultados en tiempo real", R.drawable.monkey, mainIntent, this))
        cards.add(Card("Sobre nosotros", "Consulta los resultados en tiempo real", R.drawable.trunk, mainIntent, this))
        cards.add(Card("Gastos", "Consulta los resultados en tiempo real", R.drawable.cat, mainIntent, this))
        cards.add(Card("Donativos", "Consulta los resultados en tiempo real", R.drawable.bird, mainIntent, this))

        val adapter = CardAdapter(cards)

        recyclerView.adapter = adapter
    }
}
