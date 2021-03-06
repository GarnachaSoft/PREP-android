package org.prepciudadano.prepciudadano

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.util.*
import androidx.core.app.ActivityCompat
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult

class PrePhotoActivity : AppCompatActivity() {

    private val permisseCL = android.Manifest.permission.ACCESS_COARSE_LOCATION
    private val permisseFL = android.Manifest.permission.ACCESS_FINE_LOCATION
    private val CSP = 100

    lateinit var fLPClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var callback: LocationCallback

    lateinit var state:TextView
    lateinit var cameraBtn:Button
    lateinit var sectionEt:EditText
    lateinit var progressDialog: ProgressDialog
    var stateId:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pre_photo)
    }

    override fun onStart() {
        super.onStart()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Detectando Ubicación...")
        progressDialog.show()

        fLPClient = FusedLocationProviderClient(this)
        initLocationRequest()

        cameraBtn = findViewById(R.id.camera_button)
        sectionEt = findViewById(R.id.section)
        state = findViewById(R.id.state)

        cameraBtn.setOnClickListener {
            val section = sectionEt.text.toString()
            if( section.isEmpty() ){
                sectionEt.error = "Por favor coloca una sección"
            }else{
                val sectionLength = section.toString()
                if( sectionLength.length != 4 ){
                    sectionEt.error = "La sección debe contener exactamente 4 números"
                }else{
                    val intent: Intent = Intent(this, NewPhotoActivity::class.java)
                    intent.putExtra("section", section)
                    intent.putExtra("state_id", stateId)
                    startActivity(intent)
                }
            }
        }

        if( checkPermsLocation() ){
            getLocation()
        }else{
            getPerms()
        }
    }

    private fun initLocationRequest(){
        locationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private fun checkPermsLocation():Boolean{
        val locationCoarse = ActivityCompat.checkSelfPermission(this, permisseCL) == PackageManager.PERMISSION_GRANTED
        val locationFine = ActivityCompat.checkSelfPermission(this, permisseFL) == PackageManager.PERMISSION_GRANTED

        return locationCoarse&&locationFine
    }

    private fun getPerms(){
        val explain = ActivityCompat.shouldShowRequestPermissionRationale(this, permisseFL)

        if( explain ){
            requestPerms()
        }else{
            requestPerms()
        }
    }

    private fun requestPerms(){
        ActivityCompat.requestPermissions(this, arrayOf(permisseFL, permisseCL), CSP)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            CSP -> {
                if( grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                    getLocation()
                }else{
                    Toast.makeText(this, "Es necesario aprobar este permiso", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation(){
        var stateName:String
        var lat:Double = 0.0
        var lng:Double = 0.0

        callback = object: LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)

                for(loc in locationResult?.locations!!){
                    lat = loc.latitude
                    lng = loc.longitude

                    val gcd: Geocoder = Geocoder(this@PrePhotoActivity, Locale.getDefault())
                    var addresses = gcd.getFromLocation(lat, lng, 1)

                    if (addresses.size > 0){
                        try {
                            Toast.makeText(this@PrePhotoActivity, "${addresses.get(0).adminArea}", Toast.LENGTH_SHORT).show()
                            stateName = addresses.get(0).adminArea
                            state.text = stateName
                            stateId = getStateId(stateName)
                            stopLocation()
                        }catch (e:Exception){
                            Toast.makeText(this@PrePhotoActivity, e.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        fLPClient.requestLocationUpdates(locationRequest, callback, null)
    }

    private fun getStateId(stateName:String):String{
        return when(stateName){
            "Aguascalientes" -> "01"
            "Baja California" -> "02"
            "Baja California Sur" -> "03"
            "Campeche" -> "04"
            "Chiapas" -> "05"
            "Chihuahua" -> "06"
            "Ciudad de México" -> "07"
            "Coahuila de Zaragoza" -> "08"
            "Colima" -> "09"
            "Durango" -> "10"
            "Guanajuato" -> "11"
            "Guerrero" -> "12"
            "Hidalgo" -> "13"
            "Jalisco" -> "14"
            "Estado de Mexico" -> "15"
            "Michoacán de Ocampo" -> "16"
            "Morelos" -> "17"
            "Nayarit" -> "18"
            "Nuevo León" -> "19"
            "Oaxaca" -> "20"
            "Puebla" -> "21"
            "Querétaro" -> "22"
            "Quintana Roo" -> "23"
            "San Luis Potosí" -> "24"
            "Sinaloa" -> "25"
            "Sonora" -> "26"
            "Tabasco" -> "27"
            "Tamaulipas" -> "28"
            "Tlaxcala" -> "29"
            "Veracruz" -> "30"
            "Yucatán" -> "31"
            "Zacatecas" -> "32"
            else -> "0"
        }
    }

    private fun stopLocation(){
        progressDialog.dismiss()
        fLPClient.removeLocationUpdates(callback)
    }
}
