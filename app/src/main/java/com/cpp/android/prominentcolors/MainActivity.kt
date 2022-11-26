package com.cpp.android.prominentcolors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity()
{

    private lateinit var selectImageButton: Button
    private lateinit var useCameraButton: Button

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initializes buttons
        selectImageButton = findViewById(R.id.select_image)
        useCameraButton = findViewById(R.id.camera)

        selectImageButton.setOnClickListener()
        {
            //Open image gallery
        }

        useCameraButton.setOnClickListener()
        {
            //Open camera app
        }
    }
}