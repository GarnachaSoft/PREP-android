package org.prepciudadano.prepciudadano

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.prepciudadano.prepciudadano.firebase.Template
import com.github.mikephil.charting.utils.ColorTemplate
import android.text.style.ForegroundColorSpan
import android.graphics.Typeface
import android.text.style.StyleSpan
import android.text.style.RelativeSizeSpan
import android.text.SpannableString

class MainActivity : AppCompatActivity() {

    lateinit var signOut: Button
    private var mAuth: FirebaseAuth? = null
    lateinit var ref: DatabaseReference
    lateinit var chart:PieChart
    val div = 100000f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chart = findViewById(R.id.piechart)

        val entries = ArrayList<PieEntry>()

        ref = FirebaseDatabase.getInstance().getReference("templates")

        ref.orderByChild("status").equalTo(1.0).addValueEventListener(object: ValueEventListener{
            override fun onDataChange(ds: DataSnapshot) {
                if(ds.exists()){
                    var morena = 0f; var pan = 0f; var panal = 0f; var prd = 0f; var pri = 0f; var pvem = 0f; var pes = 0f; var pt = 0f; var mc = 0f

                    for ( i in ds.children ){
                        val template = i.getValue(Template::class.java)
                        morena += template!!.morena.toFloat()
                        pan += template!!.pan.toFloat()
                        pri += template!!.pri.toFloat()
                        prd += template!!.prd.toFloat()
                        mc += template!!.mc.toFloat()
                        pvem += template!!.pvem.toFloat()
                        pt += template!!.pt.toFloat()
                        panal += template!!.panal.toFloat()
                        pes += template!!.pes.toFloat()
                    }

                    val entries = ArrayList<PieEntry>()

                    entries.add( PieEntry(morena/div, "MORENA"))
                    entries.add( PieEntry(pan/div, "PAN"))
                    entries.add( PieEntry(pri/div, "PRI"))
                    entries.add( PieEntry(prd/div, "PRD"))
                    entries.add( PieEntry(mc/div, "MC"))
                    entries.add( PieEntry(pvem/div, "PVEM"))
                    entries.add( PieEntry(pt/div, "PT"))
                    entries.add( PieEntry(panal/div, "PANAL"))
                    entries.add( PieEntry(pes/div, "PES"))

                    entriesChart(entries)
                }
            }
            override fun onCancelled(p0: DatabaseError) {}
        })
    }

    private fun entriesChart(entries: ArrayList<PieEntry>){
        drawChart(entries)
    }

    private fun drawChart(entries:ArrayList<PieEntry>){
        val dataSet = PieDataSet(entries, "")
        dataSet.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        dataSet.valueLinePart1OffsetPercentage = 80f
        dataSet.valueLinePart1Length = 0.4f
        dataSet.valueLinePart2Length = 0.2f
        dataSet.valueTextColor = Color.WHITE
        dataSet.valueTextSize = 15f

        val data = PieData(dataSet)
        chart.description.text = ""
        chart.setCenterTextSize(10f)
        chart.setEntryLabelColor(Color.BLACK)
        chart.setExtraOffsets(25f, 10f, 25f, 10f)
        chart.setRotationAngle(10f)

        //legends
        val l = chart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)

        dataSet.colors = setColorsChart()

        chart.setCenterText(spanable());
        chart.data = data
        chart.invalidate()
    }

    private fun setColorsChart():ArrayList<Int>{
        val colors = ArrayList<Int>()
        colors.add(Color.argb(200,191,49,25))//morena
        colors.add(Color.argb(200, 6, 51, 142))//pan
        colors.add(Color.argb(200, 1, 150, 66))//pri
        colors.add(Color.argb(200, 254, 215, 16))//prd
        colors.add(Color.argb(200, 243, 128, 35))//mc
        colors.add(Color.argb(200, 86, 174, 51))//pvem
        colors.add(Color.argb(200, 231, 4, 23))//pt
        colors.add(Color.argb(200, 0, 171, 179))//panal
        colors.add(Color.argb(200, 85, 37, 95))//pes

        return colors
    }

    private fun spanable():SpannableString{
        val s = SpannableString("PREP Ciudadano\nGarnacha Soft")
        s.setSpan(RelativeSizeSpan(1.7f), 0, 14, 0)
        s.setSpan(ForegroundColorSpan(Color.GRAY), 14, s.length - 12, 0)
        s.setSpan(StyleSpan(Typeface.ITALIC), s.length - 14, s.length, 0)
        s.setSpan(RelativeSizeSpan(1.5f), s.length - 14, s.length, 0)
        s.setSpan(ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length - 14, s.length, 0)
        return s
    }

    private fun logout(){
        // sign out Firebase
        //mAuth!!.signOut()
        // sign out Google
        //Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback {  }
    }
}