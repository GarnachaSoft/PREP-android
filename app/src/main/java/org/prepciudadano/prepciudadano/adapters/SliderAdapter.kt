package org.prepciudadano.prepciudadano.adapters

import android.content.Context
import android.content.Intent
import android.support.constraint.ConstraintLayout
import android.support.v4.view.PagerAdapter
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import org.prepciudadano.prepciudadano.MainActivity
import org.prepciudadano.prepciudadano.R
import org.prepciudadano.prepciudadano.utils.Config

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
        val checkBox:CheckBox
        val config = Config(context)

        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view: View = inflater.inflate(R.layout.slider_image_item, container, false)


        image = view.findViewById(R.id.slider_image)
        image.setBackgroundResource(images[position])
        button = view.findViewById(R.id.slider_button)
        textView = view.findViewById(R.id.slider_text)
        dismiss = view.findViewById(R.id.dismiss_slider)
        checkBox = view.findViewById(R.id.not_show_slider)

        button.visibility = View.INVISIBLE
        dismiss.visibility = View.INVISIBLE

        when( position ){
            0 ->{
                textView.text = "Consulta los resultados en tiempo real"
            }
            1 ->{
                textView.text = "Colabora subiendo las sábanas de resultados presidenciales que puedas"
            }
            2 ->{
                textView.text = "Llena los datos de la sábana presidencial en la applicacion PREP Ciudadano"
            }
            3 ->{
                textView.setText(Html.fromHtml("Califica las fotos de otros usuarios como válidas o inválidas desde la web <a href=\"http://prepciudadano.org\">prepciudadano.org</a>"))
            }
            4 ->{
                button.visibility = View.VISIBLE
                dismiss.visibility = View.VISIBLE
                textView.text = "Reporta las fotos mal calificadas desde el portal web"
            }
        }
        button.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            if (checkBox.isChecked){
                config.set("hide_slide", "true")
            }
            context.startActivity(intent)
        }

        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }
}