package com.cpp.android.prominentcolors

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultsActivity : AppCompatActivity()
{
    //UI Elements
    //Most prominent color
    private lateinit var mostUsedColor: ImageView
    //Hexcode Text
    private lateinit var hexCodeText: TextView
    //Buttons
    private lateinit var copyToClipboard: Button
    private lateinit var useAppAgain: Button

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        //Loading XML file
        setContentView(R.layout.results)

        //Sync UI var to UI elements
        mostUsedColor = findViewById(R.id.color_image)
        hexCodeText = findViewById(R.id.color_hexcode)
        copyToClipboard = findViewById(R.id.copy_hexcode)
        useAppAgain = findViewById(R.id.use_again)


        //If clicks button, copy hexcode to clipboard
        copyToClipboard.setOnClickListener()
        {

        }
    }

    //Function to copy hexcode to clipbard
    private fun copyHexcodeToClipboard()
    {
        val hexCodeCopy = hexCodeText.text


    }

}