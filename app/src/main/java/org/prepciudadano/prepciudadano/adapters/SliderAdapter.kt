package org.prepciudadano.prepciudadano.adapters

import android.content.Context
import android.content.Intent
import android.support.constraint.ConstraintLayout
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import org.prepciudadano.prepciudadano.HomeActivity
import org.prepciudadano.prepciudadano.LoginActivity
import org.prepciudadano.prepciudadano.R

class SliderAdapter: PagerAdapter{

    var context:Context
    var images:Array<Int>
    lateinit var inflater: LayoutInflater

    constructor(context:Context, images:Array<Int>):super(){
        this.context = context
        this.images = images
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object` as ConstraintLayout

    override fun getCount(): Int = images.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val image:ImageView
        val button:Button
        val textView:TextView
        val dismiss:LinearLayout

        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view: View = inflater.inflate(R.layout.slider_image_item, container, false)


        image = view.findViewById(R.id.slider_image)
        image.setBackgroundResource(images[position])
        button = view.findViewById(R.id.slider_button)
        textView = view.findViewById(R.id.slider_text)
        dismiss = view.findViewById(R.id.dismiss_slider)

        button.visibility = View.INVISIBLE
        dismiss.visibility = View.INVISIBLE

        when( position ){
            0 ->{
                textView.text = "Este es un texto demo de la posicion 1"
            }
            1 ->{
                textView.text = "Este es un texto demo de la posicion 2"
            }
            2 ->{
                textView.text = "Este es un texto demo de la posicion 3"
            }
            3 ->{
                textView.text = "Este es un texto demo de la posicion 4"
            }
            4 ->{
                button.visibility = View.VISIBLE
                dismiss.visibility = View.VISIBLE
                textView.text = "Este es un texto demo de la posicion 5"
            }
        }
        button.setOnClickListener {
            val intent = Intent(context, HomeActivity::class.java)
            context.startActivity(intent)
        }

        container!!.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container!!.removeView(`object` as ConstraintLayout)
    }
}