package org.prepciudadano.prepciudadano

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView

class DonationsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donations)
    }

    override fun onStart() {
        super.onStart()
        val webView:WebView = findViewById(R.id.paypal)
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webView.loadUrl("https://www.paypal.me/memoadian")
    }
}
