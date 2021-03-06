package org.prepciudadano.prepciudadano

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import android.widget.ImageView
import android.widget.LinearLayout
import org.prepciudadano.prepciudadano.adapters.SliderAdapter

class PresentationActivity : AppCompatActivity() {

    var images:Array<Int> = arrayOf(
            R.drawable.prep,
            R.drawable.camera,
            R.drawable.filldata,
            R.drawable.approve,
            R.drawable.report
    )

    var adapter: PagerAdapter = SliderAdapter(this, images)
    var dotscount:Int = 0
    var dots = mutableListOf<ImageView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_presentation)

        val viewpager: ViewPager = findViewById(R.id.viewpager)
        val tabLayout: LinearLayout = findViewById(R.id.dots)

        viewpager.adapter = adapter
        dotscount = adapter.count

        for ( i in 1 .. dotscount ){
            dots.add(ImageView(this))
        }

        for(dot in dots){
            dot.setImageResource(R.drawable.nonactive_dot)
            tabLayout.addView(dot)

            val params = dot.layoutParams as LinearLayout.LayoutParams
            params.setMargins(8, 0 , 8, 0)
            dot.layoutParams = params
        }

        dots[0].setImageResource(R.drawable.active_dot)

        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                for(dot in dots){
                    dot.setImageResource(R.drawable.nonactive_dot)
                }

                dots[position].setImageResource(R.drawable.active_dot)
            }
        })

    }
}
