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
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import java.io.File

class MainActivity : AppCompatActivity()
{

    //Buttons
    private lateinit var selectImageButton: Button
    private lateinit var useCameraButton: Button
    private lateinit var confirmImage: Button

    //Image that user will store
    private lateinit var selectedImage: ImageView

    //Uri for the image taken by the camera
    private var tempUri: Uri? = null

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
        confirmImage = findViewById(R.id.confirm_button)
        selectedImage = findViewById(R.id.selected_image)


        // startActivityForResult has been deprecated
        // this opens file explorer to pull image
        val getImage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                selectedImage.setImageURI(it)
            }
        )

        val openCamera = registerForActivityResult(
            ActivityResultContracts.TakePicture(),
            ActivityResultCallback {
                if (it) {
                    selectedImage.setImageURI(tempUri)
                }
            }
        )

        // Pressing the select image button
        selectImageButton.setOnClickListener()
        {
            getImage.launch("image/*")

            //Set button to selectable after picking image
            confirmImage.visibility = View.VISIBLE
        }

        // Pressing the camera button opens camera
        useCameraButton.setOnClickListener()
        {
            //Open camera app
            lifecycleScope.launchWhenStarted {
                getTmpFileUri().let { uri ->
                    tempUri = uri
                    openCamera.launch(uri)
                }
            }
            confirmImage.visibility = View.VISIBLE
        }

        //confirm image
        //make this only show up once image is selected
        confirmImage.setOnClickListener()
        {
            switchToLoading()

            //ideally should check if image is there if not then send toast
            //current crashes lmao
            /*if (selectedImage.drawable != null)
            {
                switchToLoading()
            }
            else
            {
                Toast.makeText(applicationContext, getString(R.string.no_image), LENGTH_SHORT)
            }*/
        }
    }

    //Creates temporary file to store image taken from camera and returns file uri
    private fun getTmpFileUri(): Uri {
        val tmpFile = File.createTempFile("tmp_image_file", ".png", cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }
        return FileProvider.getUriForFile(applicationContext, "${BuildConfig.APPLICATION_ID}.provider", tmpFile)
    }

    //Switch activities to confirm image
    private fun switchToLoading()
    {
        var switchToLoading : Intent = Intent(this, LoadingScreenActivity::class.java)
        startActivity(switchToLoading)
    }

    private fun checkImageSelected()
    {
        //Checks if
        if (selectedImage.drawable != null)
        {
            switchToLoading()
        }
    }

    //Both of these functions are depreicated
    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
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
    }*/
}