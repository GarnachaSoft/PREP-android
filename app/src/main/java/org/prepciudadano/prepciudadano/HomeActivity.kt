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
        val findIntent = Intent(this, FindBoxActivity::class.java)
        val aboutUsIntent = Intent(this, AboutUsActivity::class.java)
        val sliderIntent = Intent(this, PresentationActivity::class.java)

        cards.add(Card("PREP", "Consulta los resultados en tiempo real", R.drawable.prep, mainIntent, this))
        cards.add(Card("Encuentra tu casilla", "Consulta donde debes ir a votar", R.drawable.find, findIntent, this))
        cards.add(Card("Colaborar", "Envíanos los resultados de las votaciones presidenciales", R.drawable.camera, loginIntent, this))
        cards.add(Card("Incidencias", "Envíanos informacion de irregularidades", R.drawable.warning, mainIntent, this))
        cards.add(Card("Sobre nosotros", "Quieres saber quienes somos", R.drawable.garnacha, aboutUsIntent, this))
        cards.add(Card("Gastos", "¿Cuánto cuesta este PREP ciudadano?", R.drawable.money, mainIntent, this))
        cards.add(Card("Donativos", "Gracias por tu apoyo", R.drawable.donate, mainIntent, this))
        cards.add(Card("Presentación", "Ver presentacion inicial", R.drawable.slider, sliderIntent, this))

        val adapter = CardAdapter(cards)

        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
    }
}
