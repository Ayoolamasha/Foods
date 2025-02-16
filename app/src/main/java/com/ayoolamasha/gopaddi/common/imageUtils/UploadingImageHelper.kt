package com.ayoolamasha.gopaddi.common.imageUtils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.ayoolamasha.gopaddi.R
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.FragmentScoped
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@FragmentScoped
class UploadingImageHelper
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
        // private val imageCompressor: ImageCompressor,
        private val imageCompress: ImageCompress,
    ) {
        private val TAG: String = UploadingImageHelper::class.java.simpleName

        private var currentPhotoPath: String = ""
        private var mSelectedImageName: String = ""
        private var errorMesage: String? = ""
        private lateinit var mPhotoFile: File
        private var mPhotoURI: Uri = Uri.EMPTY
        private lateinit var mSelectedImage: Uri
        private var imageBitMap: Bitmap? = null
        private lateinit var cameraBitMap: Bitmap
        private val REQUEST_CAMERA = 0
        private val REQUEST_PHOTO = 1
        private val REQUEST_STORAGE = 123
        private val REQUEST_CAMERA_PERMISSION = 100

        internal var listener: ImageFetcherListener? = null

        fun selectGalleryOnly(activity: Activity) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_STORAGE,
                )
            } else {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                listener?.startActivityForPicture(intent, REQUEST_PHOTO, TAG)
            }
        }

        fun selectImageCameraOnly(activity: Activity) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.CAMERA,
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(android.Manifest.permission.CAMERA),
                    REQUEST_CAMERA_PERMISSION,
                )
            } else {
                val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (takePicture.resolveActivity(context.packageManager) != null) {
                    try {
                        mPhotoFile = createImageFile()
                        mPhotoURI =
                            FileProvider.getUriForFile(
                                context,
                                "${context.packageName}.provider",
                                mPhotoFile,
                            )
                        //                      store image
                        takePicture.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoURI)
                        listener?.startActivityForPicture(takePicture, REQUEST_CAMERA, TAG)
                    } catch (e: IOException) {
                        Timber.tag(TAG).i(e)
                    }
                }
            }
        }

        @Throws(IOException::class)
        private fun createImageFile(): File {
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val imageFileName = "img_profile_image_$timeStamp"
            val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val image: File =
                File.createTempFile(
                    imageFileName,
                    ".jpeg",
                    storageDir,
                )
            currentPhotoPath = image.absolutePath
            return image
        }

        fun photoManipulation(data: Intent?) {
            try {
                mSelectedImage = data?.data!!
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    currentPhotoPath =
                        ImageRealPathUtils.getRealPathFromURI_API19(context, mSelectedImage).toString()
                    imageBitMap = imageCompress.compressImage(mSelectedImage)
                    listener?.imageBitMap(imageBitMap)
                } else {
                    currentPhotoPath =
                        ImageRealPathUtils.getRealPathFromURI_API19(context, mSelectedImage).toString()
                    imageBitMap = imageCompress.compressImage(mSelectedImage)
                    listener?.imageBitMap(imageBitMap)
                }
            } catch (e: Exception) {
                errorMesage = e.localizedMessage
            }
        }

        fun populateCameraUpload() {
            try {
                Timber.tag("Camera URI").d(mPhotoURI.toString())
                val bitmap = imageCompress.compressImage(mPhotoURI)
                listener?.imageBitMap(bitmap)
            } catch (e: Exception) {
                errorMesage = e.printStackTrace().toString()
            }
        }

        fun garbageCollector() {
            listener = null
        }
    }
