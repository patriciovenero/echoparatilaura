package com.miempresa.proyectointegrador

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.airbnb.lottie.LottieAnimationView

class ViewPagerAdapter(private val context: Context) : PagerAdapter() {

    private val sliderAllJsons = arrayOf(
        R.raw.saludando,
        R.raw.hotelcolor,
        R.raw.splash
    )

    private val sliderAllTitle = intArrayOf(
        R.string.screen1,
        R.string.screen2,
        R.string.screen3
    )

    private val sliderAllDesc = intArrayOf(
        R.string.screen1desc,
        R.string.screen2desc,
        R.string.screen3desc
    )

    override fun getCount(): Int {
        return sliderAllTitle.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.sliderscreen, container, false)

        val sliderAnimation: LottieAnimationView = view.findViewById(R.id.sliderImage)
        val sliderTitle: TextView = view.findViewById(R.id.sliderTitle)
        val sliderDesc: TextView = view.findViewById(R.id.sliderDesc)

        // Establecer la carpeta de imágenes antes de cargar la animación
        sliderAnimation.setImageAssetsFolder("images/")

        sliderAnimation.setAnimation(sliderAllJsons[position])
        sliderAnimation.playAnimation()

        sliderTitle.setText(sliderAllTitle[position])
        sliderDesc.setText(sliderAllDesc[position])

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }
}
