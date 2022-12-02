package com.cpp.android.prominentcolors

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Bitmap
import android.graphics.Color
import androidx.annotation.ColorInt
import kotlin.math.max

class LoadingScreenActivity : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Calls loading screen screen xml file
        setContentView(R.layout.loading_screen)
    }

    //Not sure if this should go here or if y'all mean for it to be in a different activity
    //not finished yet
    private fun mostProminentColor(image: Bitmap): Color
    {
        val dist = 10
        val pixels = Array<Color>(image.width * image.height) {Color()}
        val ind = 0
        for (x in 0..image.width)
        {
            for (y in 0..image.height)
            {
                pixels[ind] = image.getColor(x, y)
            }
        }

        val rgbs = Array<Triple<Float, Float, Float>>(image.width * image.height) {Triple(0f, 0f, 0f)}
        for (i in pixels.indices)
        {
            rgbs[i] = Triple(pixels[i].red() * pixels[i].alpha(),
                pixels[i].green() * pixels[i].alpha(), pixels[i].blue() * pixels[i].alpha())
        }

        val colors = ArrayList<Color>()
        val colorFreqs = ArrayList<Int>()
        var inFreqs = false
        for(i in pixels.indices)
        {
            for (j in colors.indices)
            {
                if (colorDistance(pixels[i].alpha(), rgbs[i].first, rgbs[i].second, rgbs[i].third,
                        colors[j].alpha(), colors[j].red(), colors[j].green(), colors[j].blue()) <= dist)
                {
                    colorFreqs[j] += 1
                    inFreqs = true
                }
            }

            if (!inFreqs)
            {
                colors.add(pixels[i])
            }
        }

        var maxFreq = 0
        var maxColor = Color()
        for (i in colorFreqs.indices)
        {
            if (colorFreqs[i] > maxFreq)
            {
                maxFreq = colorFreqs[i]
                maxColor = colors[i]
            }
        }

        return maxColor
    }

    //helps prominent color function
    private fun colorDistance(a1: Float, r1: Float, g1: Float, b1: Float, a2: Float, r2: Float, g2: Float, b2: Float):  Float
    {
        return 0f;
    }
}