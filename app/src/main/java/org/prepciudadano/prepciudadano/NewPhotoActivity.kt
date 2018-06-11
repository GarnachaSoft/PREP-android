package org.prepciudadano.prepciudadano

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.karumi.dexter.Dexter
import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.support.constraint.ConstraintLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_new_photo.*
import org.prepciudadano.prepciudadano.firebase.Template
import java.io.*
import java.util.*

class NewPhotoActivity : AppCompatActivity() {

    val context = this
    val atx = this

    lateinit var mainContainer:ConstraintLayout
    lateinit var takePhotoButon:Button
    lateinit var uploadPhoto:Button
    lateinit var picture:SimpleDraweeView

    private val REQUEST_CODE_PHOTO_IMAGE = 1787
    private var mCurrentPhotoPath: String = ""

    lateinit var storage: FirebaseStorage
    lateinit var storageReference: StorageReference

    lateinit var file:File
    var section = ""
    var stateId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fresco.initialize(this);
        setContentView(R.layout.activity_new_photo)

        mainContainer = findViewById(R.id.main_container)
        takePhotoButon = findViewById(R.id.buttonPhoto)
        uploadPhoto = findViewById(R.id.uploadImage)
        picture = findViewById(R.id.imgv_photo)

        //firebase init
        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference

        //intents data
        stateId = intent.getStringExtra("state_id")
        section = intent.getStringExtra("section")

        takePhotoButon.setOnClickListener {
            validatePermissions()
        }

        uploadPhoto.setOnClickListener {
            uploadFile()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_PHOTO_IMAGE) {
            processCapturedPhoto()
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun validatePermissions(){
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(object: PermissionListener{
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        launchCamera()
                    }

                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
                        AlertDialog.Builder(this@NewPhotoActivity)
                                .setTitle("Se necesitan permisos de almacenamiento")
                                .setMessage("Para poder tomar fotografías es necesario dar estos permisos")
                                .setNegativeButton(android.R.string.cancel,
                                        { dialog, _ ->
                                            dialog.dismiss()
                                            token?.cancelPermissionRequest()
                                        })
                                .setPositiveButton(android.R.string.ok,
                                        { dialog, _ ->
                                            dialog.dismiss()
                                            token?.continuePermissionRequest()
                                        })
                                .setOnDismissListener({ token?.cancelPermissionRequest() })
                                .show()
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        Snackbar.make(mainContainer!!,
                                "Los permisos necesarios no fueron concedidos",
                                Snackbar.LENGTH_LONG)
                                .show()
                    }
                }).check()
    }

    private fun launchCamera(){
        val values = ContentValues(1)
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")

        val fileUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if(intent.resolveActivity(packageManager) != null) {
            mCurrentPhotoPath = fileUri.toString()
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                    or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            startActivityForResult(intent, REQUEST_CODE_PHOTO_IMAGE)
        }
    }

    private fun processCapturedPhoto() {
        val urip = Uri.parse(mCurrentPhotoPath)
        val array = Array(1) {android.provider.MediaStore.Images.ImageColumns.DATA}
        val cursor = contentResolver.query(urip, array,null, null, null)

        cursor.moveToFirst()
        val photoPath = cursor.getString(0)
        cursor.close()

        file = File(photoPath)
        val uri = Uri.fromFile(file)

        val height = resources.getDimensionPixelSize(R.dimen.photo_height)
        val width = resources.getDimensionPixelSize(R.dimen.photo_width)

        val request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(ResizeOptions(width, height))
                .build()

        val controller = Fresco.newDraweeControllerBuilder()
                .setOldController(imgv_photo.controller)
                .setImageRequest(request)
                .build()

        imgv_photo.controller = controller
    }

    private fun uploadFile(){
        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Subiendo...")
        progressDialog.show()

        //
        val scaled = scaleBitmap(file)
        val image = FileInputStream(scaled)

        val uid = UUID.randomUUID().toString()
        val imageRef = storageReference!!.child("images/${uid}")
        imageRef.putStream(image)
                .addOnSuccessListener {taskSnapshot ->
                    progressDialog.dismiss()
                    Toast.makeText(this, "imagen subida fácil y rápido", Toast.LENGTH_SHORT).show()
                    savePhotoFirebase("${stateId}/${section}", uid)
                }
                .addOnFailureListener{exception ->
                    progressDialog.dismiss()
                    Toast.makeText(this, exception.toString(), Toast.LENGTH_SHORT).show()
                }.addOnProgressListener {taskSnapshot ->
                    val progress = 100.0 * taskSnapshot.bytesTransferred/taskSnapshot.totalByteCount
                    progressDialog.setMessage("Subiendo "+progress.toInt()+"%...")
                }
    }

    fun savePhotoFirebase(boxId:String, urlImage:String){
        val ref = FirebaseDatabase.getInstance().getReference("templates")
        val templateId:String = ref.push().key!!

        val template = Template(templateId, boxId, urlImage, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
        ref.child(templateId).setValue(template).addOnCompleteListener {
            val intent = Intent(this, FormResultsActivity::class.java)
            intent.putExtra("id", templateId)
            intent.putExtra("box_id", boxId)
            startActivity(intent)
            finish()
            Toast.makeText(this, "Imagen guardada en sistema", Toast.LENGTH_SHORT).show()
        }
    }

    fun scaleBitmap(file:File):File{
        val b = BitmapFactory.decodeFile(file.absolutePath)

        val reqHeight = 800
        val currentHeight = b.height
        val currentWidth = b.width
        val scale = currentHeight/reqHeight
        val reqWidth = currentWidth/scale

        Toast.makeText(this, "width: ${reqWidth} - height: ${reqHeight}", Toast.LENGTH_LONG).show()

        val b2 = Bitmap.createScaledBitmap(b, reqWidth, reqHeight, false)
        val stream = ByteArrayOutputStream()
        b2.compress(Bitmap.CompressFormat.JPEG, 100, stream)

        val file = File("${Environment.getExternalStorageDirectory()}${File.separator}test.jpg")
        file.createNewFile()

        val fo = FileOutputStream(file)
        fo.write(stream.toByteArray())
        fo.close()

        return file
    }
}
