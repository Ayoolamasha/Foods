package com.ayoolamasha.gopaddi.common

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import com.ayoolamasha.gopaddi.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Date

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

@BindingAdapter("app:loadImage")
fun loadImage(image: ImageView?, imageString: String?) {
    if (image != null) {
        Glide.with(image.context)
            .load(imageString)
            .apply(RequestOptions())
            .placeholder(R.drawable.border_flag)
            .centerCrop()
            .into(image)
    }
}

//@BindingAdapter("app:loadImage")
//fun loadImage(imageView: ImageView, imageString: String?) {
//    if (imageString != null) {
//        imageView.load(imageString) {
//            crossfade(true)
//            placeholder(R.drawable.border_flag)
//        }
//    }
//
//}

fun Fragment.showMaterialDataPickerDialog(
    chosenDate: (String) -> Unit
) {
    val datePicker = MaterialDatePicker.Builder.datePicker()
        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
        .setTheme(R.style.Theme_App_DatePicker)
        .build()
    datePicker.show(requireActivity().supportFragmentManager, "MATERIAL_DATE_PICKER")



    datePicker.addOnPositiveButtonClickListener {
        val format1 = SimpleDateFormat("dd-MM-yyyy")
        chosenDate.invoke(format1.format(Date(it)))

    }

    datePicker.addOnNegativeButtonClickListener {


    }

    datePicker.addOnCancelListener {

    }

}