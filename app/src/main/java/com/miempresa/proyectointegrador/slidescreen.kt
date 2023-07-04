package com.miempresa.proyectointegrador

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager


class slidescreen : AppCompatActivity() {
    private lateinit var slideViewPager: ViewPager
    private lateinit var dotIndicator: LinearLayout
    private lateinit var backButton: Button
    private lateinit var nextButton: Button
    private lateinit var skipButton: Button
    private lateinit var dots: Array<TextView?>
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    private val viewPagerListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

        override fun onPageSelected(position: Int) {
            setDotIndicator(position)

            if (position > 0) {
                backButton.visibility = View.VISIBLE
            } else {
                backButton.visibility = View.INVISIBLE
            }

            if (position == 2) {
                nextButton.text = "Finish"
            } else {
                nextButton.text = "Next"
            }
        }

        override fun onPageScrollStateChanged(state: Int) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slidescreen)

        backButton = findViewById(R.id.backButton)
        nextButton = findViewById(R.id.nextButton)
        skipButton = findViewById(R.id.skipButton)

        backButton.setOnClickListener {
            if (getItem(0) > 0) {
                slideViewPager.setCurrentItem(getItem(-1), true)
            }
        }

        nextButton.setOnClickListener {
            if (getItem(0) < 2) {
                slideViewPager.setCurrentItem(getItem(1), true)
            } else {
                val i = Intent(this@slidescreen, slidescreen2::class.java)
                startActivity(i)
                finish()
            }
        }

        skipButton.setOnClickListener {
            val i = Intent(this@slidescreen, loginb::class.java)
            startActivity(i)
            finish()
        }

        slideViewPager = findViewById(R.id.slideViewPager)
        dotIndicator = findViewById(R.id.dotIndicator)

        viewPagerAdapter = ViewPagerAdapter(this)
        slideViewPager.adapter = viewPagerAdapter

        setDotIndicator(0)
        slideViewPager.addOnPageChangeListener(viewPagerListener)
    }

    private fun setDotIndicator(position: Int) {
        dots = arrayOfNulls<TextView>(3)
        dotIndicator.removeAllViews()

        for (i in dots.indices) {
            dots[i] = TextView(this)
            dots[i]?.text = Html.fromHtml("&#8226;", Html.FROM_HTML_MODE_LEGACY)
            dots[i]?.textSize = 35f
            dots[i]?.setTextColor(ContextCompat.getColor(applicationContext, R.color.grey))
            dotIndicator.addView(dots[i])
        }
        dots[position]?.setTextColor(ContextCompat.getColor(applicationContext, R.color.lavender))
    }

    private fun getItem(i: Int): Int {
        return slideViewPager.currentItem + i
    }
}
