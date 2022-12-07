package com.cpp.android.prominentcolors

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import java.lang.String
import java.util.*

class LoadingScreenActivity : AppCompatActivity()
{
    //Image that user will store
    private lateinit var selectedImage: ImageView

    private lateinit var loadingBar: ProgressBar

    //Data container holding the image
    //private lateinit var imageStored: SelectedImage

    //Want to use a hashmap for keeping track of all colors
    //Key would be rgb value, value would be an int of the count
    private lateinit var colorMap: HashMap<Color, Int>

    //Pair is used to hold most prominent color
    //Replace with an array/list later
    private lateinit var prominentCol: Pair<Color, Int>

    private val TAG = "LoadingScreenActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Loading Create")

        //Calls loading screen screen xml file
        setContentView(R.layout.loading_screen)

        //Set Image to button and loading bar if we are using that one
        selectedImage = findViewById(R.id.selected_image)
        //loadingBar = findViewById(R.id.determinate_bar)   //commented out cuz using radial bar rn

        //Initialize HashMap
        colorMap = HashMap<Color, Int>()

        //Reads byte array data and transfers to bitmap
        val tempImageByteArray = intent.getByteArrayExtra("selectedImage")
        val bmp: Bitmap? =
            tempImageByteArray?.let { BitmapFactory.decodeByteArray(tempImageByteArray, 0, it.size) }

        //Places image into the loading
        selectedImage.setImageBitmap(bmp)


        //Debugging button to go to result page
        //Remove in final app
        selectedImage.setOnClickListener()
        {
            switchToResults()
        }
    }

    //Runs code for color scanner after view is created
    //currently crashes due to bitmap issue
    override fun onStart()
    {
        super.onStart()
        Log.d(TAG, "Loading Start")

        val tempImageByteArray = intent.getByteArrayExtra("selectedImage")
        val bmp: Bitmap? =
            tempImageByteArray?.let { BitmapFactory.decodeByteArray(tempImageByteArray, 0, it.size) }

        if (bmp != null)
        {
            scanImage(bmp)
        }

        Log.d(TAG, "COLORMAP: " + colorMap.toString())
        switchToResults()
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


    //Going to test my own version of the color picker algo - larry
    //Uses hashmap mapped <Color, Count (int)>
    //that way we get a list of all the colors and can select the top 5 if needed
    private fun scanImage(image: Bitmap)
    {
        //Get array of pixels of the image
        var pixels = Array<Color>(image.width * image.height){Color()}
        var pixelInd = 0

        //Debugging statements
        Log.d(TAG, "Image Width: " + image.width)
        Log.d(TAG, "Image Height: " + image.height)
        Log.d(TAG, "Pixel Amount: ${pixels.size}")

        //Loops through the entire image to transfer to array
        for (x in 0..(image.width - 1))
        {
            for (y in 0..(image.height - 1))
            {
                //Error y must be < bitmap.height()
                //Log.d(TAG, "X: " + x)
                //Log.d(TAG, "Y: " + y)

                //Log.d(TAG, "Image Width: " + image.width)
                //Log.d(TAG, "Image Height: " + image.height)
                pixels[pixelInd] = image.getColor(x, y)
                pixelInd++
            }
        }

        //Sets all array values to hashmap
        colorMapCounter(pixels)

        //Find top 3 best colors
        //currently only finds highest color
        prominentCol = prominentColors()
        Log.d(TAG, "PromintentColor: $prominentCol")

    }

    //Goes through the pixel array and adds to colorMap
    private fun colorMapCounter(pixels: Array<Color>)
    {
        for (col in 0..(pixels.size - 1))
        {
            //If the key exists, add one to the key counter
            if (colorMap.containsKey(pixels[col]))
            {
                val pixelCount: Int? = colorMap[pixels[col]]?.plus(1)
                if (pixelCount != null)
                {
                    colorMap.replace(pixels[col], pixelCount)
                }
            }
            //If the key doesnt exist, set it and initialize count to 1
            else
            {
                colorMap[pixels[col]] = 1
            }
        }
    }

    //Goes through hashmap and gets largest values
    //Can adjust later to have a certain range
    private fun prominentColors(): Pair<Color, Int>
    {
        //Search for most used colors
        //Probably assign it to a list/array
        //var prominentColors: Array<Pair<Color, Int>> = Array<Pair<Color, Int>>(6)

        //Testing out just one for now to make sure it works
        //Need to initialize this with the first pair, but isnt working
        var prominentColor: Pair<Color, Int>

        prominentColor = Pair(Color(), 0)

        for (mutableEntry in colorMap)
        {
            //looks for if next color is higher than other
            if (mutableEntry.value > prominentColor.second)
            {
                prominentColor = mutableEntry.toPair()
            }
        }

        return prominentColor
    }


    //
    private fun switchToResults()
    {
        var switchToResults : Intent = Intent(this, ResultsActivity::class.java)

        val hexColor = String.format("#%06X", 0xFFFFFF and prominentCol.first.toArgb())

        //want to place an extra with the hexvalue
        switchToResults.putExtra("HexCode", hexColor)
        switchToResults.putExtra("HexCodeVal", prominentCol.first.toArgb())
        switchToResults.putExtra("Count", prominentCol.second)

        Log.d(TAG, "HEXCODE COUNT: ${prominentCol.second}")

        startActivity(switchToResults)
    }
}