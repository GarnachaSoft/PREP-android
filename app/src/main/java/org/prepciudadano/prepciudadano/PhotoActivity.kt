package org.prepciudadano.prepciudadano

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream

class PhotoActivity : AppCompatActivity() {

    val CAMERA_REQUEST_CODE = 0
    private val PICK_IMAGE_REQUEST = 1234
    lateinit var imageFilePath: String
    lateinit var photoView:ImageView

    //firebase upload file
    lateinit var filePath: Uri
    lateinit var imageUri:Uri
    var bitmap:Bitmap? = null

    internal var storage:FirebaseStorage?=null
    internal var storageReference:StorageReference?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)

        val cameraButton:Button = findViewById(R.id.cameraButton)
        val chooseButton:Button = findViewById(R.id.chooseButton)
        val uploadImage:Button = findViewById(R.id.uploadImage)
        photoView = findViewById(R.id.photoView)

        cameraButton.setOnClickListener { launchCamera() }
        chooseButton.setOnClickListener{ launchChooser() }
        uploadImage.setOnClickListener { uploadFile() }

        //firebase init
        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference
    }

    fun launchChooser(){
        val intent = Intent()
        intent.type="image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Seleccionar imagen"), PICK_IMAGE_REQUEST)
    }

    fun launchCamera(){
        try {
            val imageFile = createimageFile()
            val callCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            if( callCameraIntent.resolveActivity(packageManager) != null ){
                val authorities = packageName + ".fileprovider"
                imageUri = FileProvider.getUriForFile(this, authorities, imageFile)

                callCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                startActivityForResult(callCameraIntent, CAMERA_REQUEST_CODE)
                Toast.makeText(this, imageUri.toString(), Toast.LENGTH_LONG).show()
            }
        }catch (e:Exception){
            Toast.makeText(this, "No fue posible crear el archivo", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            CAMERA_REQUEST_CODE -> {
                if(resultCode == Activity.RESULT_OK){
                    filePath = imageUri
                    bitmap = setScaledBitmap()
                    photoView.setImageBitmap(setScaledBitmap())
                }
            }
            PICK_IMAGE_REQUEST -> {
                if( resultCode == Activity.RESULT_OK && data != null && data?.data != null){
                    filePath = data.data
                    bitmap = setScaledBitmap()
                    try {
                        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                        photoView.setImageBitmap(bitmap)
                    }catch (e: Exception){
                        Toast.makeText(this, "No fue posible seleccionar el archivo", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else ->{
                Toast.makeText(this, "Unrecognized request code", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uploadFile(){
        if( bitmap != null ){
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Subiendo...")
            progressDialog.show()

            //convert bitmap to bytearray
            val stream = ByteArrayOutputStream()
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            val image = stream.toByteArray()

            val imageRef = storageReference!!.child("images/"+UUID.randomUUID().toString())
            imageRef.putBytes(image)
                    .addOnSuccessListener {
                        progressDialog.dismiss()
                        Toast.makeText(this, "Imagen subida", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener{exception ->
                        progressDialog.dismiss()
                        //Toast.makeText(this, "Hubo un fallo al subir la imagen", Toast.LENGTH_SHORT).show()
                        Toast.makeText(this, exception.toString(), Toast.LENGTH_SHORT).show()
                    }.addOnProgressListener {taskSnapshot ->
                        val progress = 100.0 * taskSnapshot.bytesTransferred/taskSnapshot.totalByteCount
                        progressDialog.setMessage("Subiendo "+progress.toInt()+"%...")
                    }
        }
    }

    @Throws(IOException::class)
    fun createimageFile(): File {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val imageFileName = "JPEG_" + timestamp + "_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        if(!storageDir.exists()){
            storageDir.mkdirs()
        }
        val imageFile = createTempFile(imageFileName, ".jpg", storageDir)
        imageFilePath = imageFile.absolutePath

        return imageFile
    }

    fun setScaledBitmap(): Bitmap{
        val imageViewWidth = photoView.width
        val imageViewHeight = photoView.height

        val bmOptions = BitmapFactory.Options()
        bmOptions.inJustDecodeBounds = true
        BitmapFactory.decodeFile(imageFilePath, bmOptions)
        val bitmapWidth = bmOptions.outWidth
        val bitmapHeight = bmOptions.outHeight

        //Toast.makeText(this, "Altura:${bitmapHeight.toString()}, Ancho: ${bitmapWidth.toString()} = Altura:${imageViewHeight.toString()}, Ancho: ${imageViewWidth.toString()}", Toast.LENGTH_SHORT).show()
        //val scaleFactor = Math.min(bitmapWidth/imageViewWidth, bitmapHeight/imageViewHeight)
        val scaleFactor = bitmapHeight/800
        bmOptions.inSampleSize = scaleFactor
        bmOptions.inJustDecodeBounds = false

        return BitmapFactory.decodeFile(imageFilePath, bmOptions)
    }
}
