package com.ayoolamasha.gopaddi.common

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.ayoolamasha.gopaddi.R
import com.ayoolamasha.gopaddi.featureTrips.domain.model.FoodImagesUI
import com.ayoolamasha.gopaddi.featureTrips.domain.model.GetCategoryTagsUIData
import com.ayoolamasha.gopaddi.featureTrips.presentation.fragment.CategoryTagsBottomSheet
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


fun ViewGroup.forEach(action: (View) -> Unit) {
    for (i in 0 until childCount) {
        action(getChildAt(i))
    }
}

fun Fragment.showSnackBar(content: String) {
    Snackbar.make(requireView(), content, Snackbar.LENGTH_SHORT)
        .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
        .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.blue))
        .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        .show()

}

//@BindingAdapter("app:loadImage")
//fun loadImage(image: ImageView?, imageString: String?) {
//    if (image != null) {
//        Glide.with(image.context)
//            .load(imageString)
//            .apply(RequestOptions())
//            .placeholder(R.drawable.border_flag)
//            .centerCrop()
//            .into(image)
//    }
//}

@BindingAdapter("app:loadImage")
fun loadImage(imageView: ImageView, imageList: List<FoodImagesUI>?) {
    if (!imageList.isNullOrEmpty()) {
        val firstImage = imageList[0].imageUrl
        Glide.with(imageView.context)
            .load(firstImage)
            .centerCrop()
            .into(imageView)
    }
}



fun Fragment.openCategoryBottomSheet(tagName : List<GetCategoryTagsUIData>){
    val bottomSheet = CategoryTagsBottomSheet(tagName = tagName)
    bottomSheet.show(requireActivity().supportFragmentManager, tag)
}

fun Fragment.bitmapToFile(bitmap: Bitmap): File {
    val file = File(requireContext().cacheDir, "image_${System.currentTimeMillis()}.jpg")
    file.createNewFile()

    ByteArrayOutputStream().use { byteArrayOutputStream ->
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()

        FileOutputStream(file).use { outputStream ->
            outputStream.write(byteArray)
            outputStream.flush()
        }
    }

    return file
}


fun Fragment.requestCameraPermission(): Boolean =
    ContextCompat.checkSelfPermission(
        requireActivity(),
        Manifest.permission.CAMERA,
    ) == PackageManager.PERMISSION_GRANTED

fun Fragment.cameraPermissionRationaleDialog(requestPermission: () -> Unit) {
//    lateinit var dialog: AlertDialog
//
//    val dialogBuilder = AlertDialog.Builder(requireContext(), R.style.Theme_Vale_ErrorAlertDialog)
//
//    val layoutInflater =
//        (requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
//
//    val binding = LayoutAllowCameraDialogBinding.inflate(layoutInflater, null, false)
//
//    binding.okMessage.setOnClickListener {
//        requestPermission.invoke()
//        dialog.dismiss()
//    }
//
//    dialogBuilder.setView(binding.root)
//
//    dialogBuilder.setCancelable(true)
//
//    dialog = dialogBuilder.create()
//
//    dialog.show()
}

fun Fragment.goToSettingsPermissionRationaleDialog() {
//    lateinit var dialog: AlertDialog
//
//    val dialogBuilder = AlertDialog.Builder(requireContext(), R.style.Theme_Vale_ErrorAlertDialog)
//
//    val layoutInflater =
//        (requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
//
//    val binding = LayoutGoToSettingsDialogBinding.inflate(layoutInflater, null, false)
//
//    binding.okMessage.setOnClickListener {
//        dialog.dismiss()
//    }
//
//    dialogBuilder.setView(binding.root)
//
//    dialogBuilder.setCancelable(true)
//
//    dialog = dialogBuilder.create()
//
//    dialog.show()
}

/**
 * IMAGE UPLOADING SOURCE
 */
const val USE_CAMERA = "camera"
const val USE_GALLERY = "gallery"

val REQUIRED_CAMERA_PERMISSIONS_ONLY = Manifest.permission.CAMERA

val REQUIRED_CAMERA_PERMISSIONS =
    arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
    )


fun adjustSoftKeyboardPan(activity: FragmentActivity?) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    } else {
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }
}