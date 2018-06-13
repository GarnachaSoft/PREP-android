package org.prepciudadano.prepciudadano.utils

import android.content.Context

class Config(val context: Context){
    private val SHARED_PREFS_FILE = "PREPrefers"

    val settings = context.getSharedPreferences(SHARED_PREFS_FILE, 0)

    fun get(key: String, value: String):String{
        val getString = settings.getString(key, "")
        return getString
    }

    fun set(key: String, value: String){
        val editor = settings.edit()
        editor.putString(key, value)
        editor.commit()
    }

    fun clear(){

    }
}