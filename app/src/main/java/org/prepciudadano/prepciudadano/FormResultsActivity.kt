package org.prepciudadano.prepciudadano

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import org.prepciudadano.prepciudadano.firebase.Template

class FormResultsActivity : AppCompatActivity() {

    lateinit var mc:EditText
    lateinit var morena:EditText
    lateinit var pan:EditText
    lateinit var panal:EditText
    lateinit var pes:EditText
    lateinit var prd:EditText
    lateinit var pri:EditText
    lateinit var pt:EditText
    lateinit var pvem:EditText

    lateinit var sendData: Button

    //puts
    private var templateId = ""
    private var boxId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_results)

        mc = findViewById(R.id.mc)
        morena = findViewById(R.id.morena)
        pan = findViewById(R.id.pan)
        panal = findViewById(R.id.panal)
        pes = findViewById(R.id.pes)
        prd = findViewById(R.id.prd)
        pri = findViewById(R.id.pri)
        pt = findViewById(R.id.pt)
        pvem = findViewById(R.id.pvem)

        sendData = findViewById(R.id.sendData)

        templateId = intent.getStringExtra("id")
        boxId = intent.getStringExtra("box_id")

        sendData.setOnClickListener {
            if(validateDataEmpty(mc, morena, pan, panal, pes, prd, pri, pt, pvem)) {
                setVotes(mc, morena, pan, panal, pes, prd, pri, pt, pvem)
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun validateDataEmpty(mc:EditText, morena:EditText, pan:EditText, panal:EditText, pes:EditText, prd:EditText, pri:EditText, pt:EditText, pvem:EditText):Boolean{
        if( mc.text.isEmpty() ){
            mc.error = "Este campo no puede ir vacío"
            return false
        }

        if( morena.text.isEmpty() ){
            morena.error = "Este campo no puede ir vacío"
            return false
        }

        if( pan.text.isEmpty() ){
            pan.error = "Este campo no puede ir vacío"
            return false
        }

        if( panal.text.isEmpty() ){
            panal.error = "Este campo no puede ir vacío"
            return false
        }

        if( pes.text.isEmpty() ){
            pes.error = "Este campo no puede ir vacío"
            return false
        }

        if( prd.text.isEmpty() ){
            prd.error = "Este campo no puede ir vacío"
            return false
        }

        if( pri.text.isEmpty() ){
            pri.error = "Este campo no puede ir vacío"
            return false
        }

        if( pt.text.isEmpty() ){
            pt.error = "Este campo no puede ir vacío"
            return false
        }

        if( pvem.text.isEmpty() ){
            pvem.error = "Este campo no puede ir vacío"
            return false
        }

        return true
    }

    private fun setVotes(mc:EditText, morena:EditText, pan:EditText, panal:EditText, pes:EditText, prd:EditText, pri:EditText, pt:EditText, pvem:EditText){
        val dbTemplate = FirebaseDatabase.getInstance().getReference("templates")
        val mcVotes = mc.text.toString().trim()
        val morenaVotes = morena.text.toString().trim()
        val panVotes = pan.text.toString().trim()
        val panalVotes = panal.text.toString().trim()
        val pesVotes = pes.text.toString().trim()
        val prdVotes = prd.text.toString().trim()
        val priVotes = pri.text.toString().trim()
        val ptVotes = pt.text.toString().trim()
        val pvemVotes = pvem.text.toString().trim()

        dbTemplate.child(templateId).child("mc").setValue(mcVotes.toInt())
        dbTemplate.child(templateId).child("morena").setValue(morenaVotes.toInt())
        dbTemplate.child(templateId).child("pan").setValue(panVotes.toInt())
        dbTemplate.child(templateId).child("panal").setValue(panalVotes.toInt())
        dbTemplate.child(templateId).child("pes").setValue(pesVotes.toInt())
        dbTemplate.child(templateId).child("prd").setValue(prdVotes.toInt())
        dbTemplate.child(templateId).child("pri").setValue(priVotes.toInt())
        dbTemplate.child(templateId).child("pt").setValue(ptVotes.toInt())
        dbTemplate.child(templateId).child("pvem").setValue(pvemVotes.toInt())

        Toast.makeText(this@FormResultsActivity, "Información añadida con éxito, Gracias", Toast.LENGTH_LONG).show()
    }
}
