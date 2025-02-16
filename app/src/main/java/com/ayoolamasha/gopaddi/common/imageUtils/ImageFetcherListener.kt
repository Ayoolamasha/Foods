package com.ayoolamasha.gopaddi.common.imageUtils

import android.content.Intent
import android.graphics.Bitmap

interface ImageFetcherListener {
    fun startActivityForPicture(
        intent: Intent?,
        READ_REQUEST_CODE: Int,
        TAG: String,
    )

    fun imageBitMap(imageBitMap: Bitmap?)

}
