package com.cpp.android.prominentcolors

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import androidx.core.view.drawToBitmap
import androidx.lifecycle.lifecycleScope
import java.io.ByteArrayOutputStream
import java.io.File

class MainActivity : AppCompatActivity()
{

    //Buttons
    private lateinit var selectImageButton: Button
    private lateinit var useCameraButton: Button
    private lateinit var confirmImage: Button

    //Image that user will store
    private lateinit var selectedImage: ImageView
    //Data container holding the image
    private lateinit var imageStored: SelectedImage

    //Uri for the image taken by the camera
    private var tempUri: Uri? = null

    //constant to compare activity code
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
                imageStored = SelectedImage(selectedImage)
            }
        )

        val openCamera = registerForActivityResult(
            ActivityResultContracts.TakePicture(),
            ActivityResultCallback {
                if (it) {
                    selectedImage.setImageURI(tempUri)
                    imageStored = SelectedImage(selectedImage)
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
        //Currently clicking this button breaks the app
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

        //confirm image button
        //make this only show up once image is selected
        //Goes to loading screena activity
        confirmImage.setOnClickListener()
        {
            //ideally should check if image is there if not then send toast
            if (imageStored != null)
            {
                switchToLoading()
            }
            else
            {
                Toast.makeText(applicationContext, getString(R.string.no_image), LENGTH_SHORT)
            }
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
        //Want to transfer the current image here to the next screen
        var switchToLoading : Intent = Intent(this, LoadingScreenActivity::class.java)
        //When sending to loading, we create a bitmap version of the map
        var stream: ByteArrayOutputStream = ByteArrayOutputStream()
        selectedImage.drawToBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream)

        //Convert the bitmap version into a byte array
        val byteArray = stream.toByteArray()

        //Place the byte array with the intent as an "extra"
        switchToLoading.putExtra("selectedImage", byteArray)

        //Switches activity to loading screen
        startActivity(switchToLoading)
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