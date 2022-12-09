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
    //Uses shows rank of color
    private lateinit var colorRank: TextView
    //use a white square and change its rgb value
    private lateinit var mostUsedColor: ImageView
    //Hexcode Text
    private lateinit var hexCodeText: TextView
    //HexCode Count (mostly for debugging)
    //Maybe change to percentage later on
    private lateinit var hexCodeCount: TextView

    //Buttons
    private lateinit var colorRankBack: Button
    private lateinit var colorRankFoward: Button
    private lateinit var copyToClipboard: Button
    private lateinit var useAppAgain: Button

    //Arrays from bundles
    private lateinit var hexColorString: Array<String>
    private lateinit var hexColorValue: IntArray
    private lateinit var hexColorCount: IntArray
    private var hexCodeRank: Int = 0

    private val TAG = "ResultsActivity"

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        //Loading XML file
        setContentView(R.layout.results)

        //Sync UI var to UI elements
        colorRank = findViewById(R.id.color_rank)
        mostUsedColor = findViewById(R.id.color_image)
        hexCodeText = findViewById(R.id.color_hexcode)
        copyToClipboard = findViewById(R.id.copy_hexcode)
        useAppAgain = findViewById(R.id.use_again)
        hexCodeCount = findViewById(R.id.color_count)
        colorRankBack = findViewById(R.id.back_rank_button)
        colorRankFoward = findViewById(R.id.forward_rank_button)


        //GETS VALUES FROM BUNDLES
        hexColorString = intent.getStringArrayExtra("HexCodeString")!!
        hexColorValue = intent.getIntArrayExtra("HexCodeValue")!!
        hexColorCount = intent.getIntArrayExtra("HexCodeCount")!!

        //Create textview to show rank of color
        colorRank.text = (hexCodeRank + 1).toString()

        //Gets hexcode text from bundle in loading
        hexCodeText.text = hexColorString[hexCodeRank]

        //Set mostUsedColor to white box
        //Initialize white box to hexcode value
        mostUsedColor.setImageResource(R.drawable.color_hex)
        //Set argb value
        mostUsedColor.setColorFilter(hexColorValue[hexCodeRank])

        //Shows Count
        hexCodeCount.text = "Count: ${hexColorCount[hexCodeRank].toString()}"


        //Create buttons on left and right to rotate side of array
        //Moves up rank
        colorRankBack.setOnClickListener()
        {
            hexCodeRank--

            if (hexCodeRank < 0)
            {
                hexCodeRank = hexColorString.size - 1
            }

            //Set all variables
            colorRank.text = (hexCodeRank + 1).toString()
            hexCodeText.text = hexColorString[hexCodeRank]
            mostUsedColor.setColorFilter(hexColorValue[hexCodeRank])
            hexCodeCount.text = "Count: ${hexColorCount[hexCodeRank].toString()}"
        }

        //Moves down rank
        colorRankFoward.setOnClickListener()
        {
            hexCodeRank++

            if (hexCodeRank >= hexColorString.size)
            {
                hexCodeRank = 0
            }

            //Set all variables
            colorRank.text = (hexCodeRank + 1).toString()
            hexCodeText.text = hexColorString[hexCodeRank]
            mostUsedColor.setColorFilter(hexColorValue[hexCodeRank])
            hexCodeCount.text = "Count: ${hexColorCount[hexCodeRank].toString()}"
        }

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