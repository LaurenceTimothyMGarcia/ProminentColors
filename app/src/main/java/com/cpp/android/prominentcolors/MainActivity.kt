package com.cpp.android.prominentcolors

import android.content.Intent
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView

class MainActivity : AppCompatActivity()
{

    //Buttons
    private lateinit var selectImageButton: Button
    private lateinit var useCameraButton: Button

    //Image that user will store
    private lateinit var selectedImage: ImageView

    //constant to compare acitivty code
    private val SELECTED_PICTURE = 200

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initializes buttons
        selectImageButton = findViewById(R.id.select_image)
        useCameraButton = findViewById(R.id.camera)
        selectedImage = findViewById(R.id.selected_image)

        selectImageButton.setOnClickListener()
        {
            fun onClick(v: View)
            {
                selectImage()
            }
        }

        useCameraButton.setOnClickListener()
        {
            //Open camera app
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && data != null)
        {
            var selectImage: Uri? = data.getData()
            selectedImage.setImageURI(selectImage)
        }
    }

    private fun selectImage()
    {
        var i: Intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(intent, 3)
    }
}