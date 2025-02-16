package com.ayoolamasha.gopaddi.common.imageUtils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.ByteArrayOutputStream
import java.io.InputStream
import javax.inject.Inject

const val MAX_FILE_SIZE_BYTES = 5 * 1024 * 1024
class ImageCompress
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) {
        fun compressImage(imageUri: Uri): Bitmap? {
            try {
                // Check if the image size is above the threshold for compression
                val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
                val imageSizeBytes = inputStream?.available() ?: 0
                inputStream?.close()

                if (imageSizeBytes <= MAX_FILE_SIZE_BYTES) {
                    // Image is already below the threshold, return the original image
                    return BitmapFactory.decodeStream(context.contentResolver.openInputStream(imageUri))
                }

                val options = BitmapFactory.Options()
                options.inJustDecodeBounds = true
                BitmapFactory.decodeStream(
                    context.contentResolver.openInputStream(imageUri),
                    null,
                    options,
                )

                // Calculate the inSampleSize to maintain original image quality
                options.inJustDecodeBounds = false
                options.inSampleSize = 1 // Initialize with no down sampling

                while (calculateSampleSize(options) * imageSizeBytes > MAX_FILE_SIZE_BYTES) {
                    options.inSampleSize *= 2
                }

                // Decode the image with the calculated inSampleSize

                return BitmapFactory.decodeStream(
                    context.contentResolver.openInputStream(imageUri),
                    null,
                    options,
                )
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
        }

        fun compressImageToByteArray(imageUri: Uri): ByteArray? {
            val compressedBitmap = compressImage(imageUri)
            if (compressedBitmap != null) {
                val outputStream = ByteArrayOutputStream()
                compressedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                return outputStream.toByteArray()
            }
            return null
        }

        private fun calculateSampleSize(options: BitmapFactory.Options): Long {
            val imageWidth = options.outWidth.toLong()
            val imageHeight = options.outHeight.toLong()
            val sampleSize = options.inSampleSize.toLong()
            return sampleSize * sampleSize * imageWidth * imageHeight
        }

    }
