package com.cpp.android.prominentcolors

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class LoadingScreenActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Calls loading screen screen xml file
        setContentView(R.layout.loading_screen)
    }
}