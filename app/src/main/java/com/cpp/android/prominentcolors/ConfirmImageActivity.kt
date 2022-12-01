package com.cpp.android.prominentcolors

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ConfirmImageActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        //Calls confirmation screen xml file
        setContentView(R.layout.confirmation_screen)
    }
}