package com.cpp.android.prominentcolors

import android.content.Intent
import android.graphics.Bitmap
import android.media.Image
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity()
{

    //Buttons
    private lateinit var selectImageButton: Button
    private lateinit var useCameraButton: Button

    //Image that user will store
    private lateinit var selectedImage: ImageView

    //constant to compare acitivty code
    companion object
    {
        private const val pic_id = 123
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initializes buttons
        selectImageButton = findViewById(R.id.select_image)
        useCameraButton = findViewById(R.id.camera)
        selectedImage = findViewById(R.id.selected_image)


        // startActivityForResult has been deprecated, this opens file explorer to pull image
        val getImage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                selectedImage.setImageURI(it)
            }
        )

        selectImageButton.setOnClickListener()
        {
            getImage.launch("image/*")
        }

        useCameraButton.setOnClickListener()
        {
            //Open camera app
            val camera_intent = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE)
            {
                Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            } else {
                TODO("VERSION.SDK_INT < CUPCAKE")
            }

            startActivityForResult(camera_intent, pic_id)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //Match request pic id with request code
        if (requestCode == pic_id)
        {
            val photo = data!!.extras!!["data"] as Bitmap?
            selectedImage.setImageBitmap(photo)
        }
    }

    private fun selectImage()
    {
        var i: Intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 3)
    }
}