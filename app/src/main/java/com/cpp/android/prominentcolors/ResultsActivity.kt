package com.cpp.android.prominentcolors

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ResultsActivity : AppCompatActivity()
{
    //UI Elements
    //Most prominent color
    //use a white square and change its rgb value
    private lateinit var mostUsedColor: ImageView
    //Hexcode Text
    private lateinit var hexCodeText: TextView
    //Buttons
    private lateinit var copyToClipboard: Button
    private lateinit var useAppAgain: Button

    private val TAG = "ResultsActivity"

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

        //Gets hexcode text from bundle in loading
        hexCodeText.text = intent.getStringExtra("HexCode")
        Log.d(TAG, "HexCode: " + intent.getStringExtra("HexCode"))

        //Set mostUsedColor to white box
        //Initialize white box to hexcode value
        mostUsedColor.setImageResource(R.drawable.color_hex)
        //Set argb value
        mostUsedColor.setColorFilter(intent.getIntExtra("HexCodeVal", 1))
        Log.d(TAG, "Color Filter: " + intent.getIntExtra("HexCodeVal", 1))

        //If clicks button, copy hexcode to clipboard
        copyToClipboard.setOnClickListener()
        {
            copyHexcodeToClipboard()
        }

        //If click button, go to MainActivity
        useAppAgain.setOnClickListener()
        {
            switchToMain()
        }
    }

    //Function to copy hexcode to clipbard
    private fun copyHexcodeToClipboard()
    {
        val hexCodeCopy = hexCodeText.text

        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("hexcode", hexCodeCopy)
        clipboardManager.setPrimaryClip(clipData)

        Toast.makeText(this, "Hexcode copied to clipboard", Toast.LENGTH_LONG).show()
    }

    //Function to go back to MainActivity
    private fun switchToMain()
    {
        val switchToMain = Intent(this, MainActivity::class.java)
        startActivity(switchToMain)
    }

}