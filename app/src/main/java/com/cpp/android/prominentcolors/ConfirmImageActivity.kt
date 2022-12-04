package com.cpp.android.prominentcolors

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

//Probably wont be using this class
//Originally was for a confirm image screen
//Now confirm image screen is merged with MainActivity
class ConfirmImageActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        //Calls confirmation screen xml file
        setContentView(R.layout.confirmation_screen)
    }
}