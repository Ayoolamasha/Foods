package com.ayoolamasha.gopaddi.common

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.ayoolamasha.gopaddi.R


private lateinit var dialog: AlertDialog


fun Fragment.showProgressPopUpDialog(cancellable: Boolean = false) {
    val dialogBuilder = AlertDialog.Builder(requireContext(), R.style.Theme_AlertDialog)

    val layoutInflater =
        (requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)

    val view: View = layoutInflater.inflate(R.layout.layout_loading, null, false)

    dialogBuilder.setView(view)

    dialogBuilder.setCancelable(cancellable)

    dialog = dialogBuilder.create()

    dialog.show()
}

fun Fragment.stopProgressLoading() {
    dialog.dismiss()
}

//fun Fragment.showErrorPopUpDialog(
//    @StringRes errorMessage: Int,
//    @StringRes actionMessage: Int,
//    action: () -> Unit,
//) {
//    val dialogBuilder = AlertDialog.Builder(requireContext(), R.style.Theme_FCBMobile_AlertDialog)
//
//    val layoutInflater =
//        (requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
//
//    val binding = LayoutDialogBinding.inflate(layoutInflater, null, false)
//    binding.errorMessage.setText(errorMessage)
//    binding.actionButton.setText(actionMessage)
//
//    binding.actionButton.setOnClickListener {
//        action.invoke()
//        dialog.dismiss()
//    }
//    binding.closeButton.setOnClickListener { dialog.dismiss() }
//
//
//    dialogBuilder.setView(binding.root)
//
//    dialogBuilder.setCancelable(true)
//
//    dialog = dialogBuilder.create()
//
//    dialog.show()
//}
//
//fun Fragment.showErrorPopUpDialog(
//    errorMessage: String?,
//    actionMessage: String,
//    action: () -> Unit,
//) {
//    val dialogBuilder = AlertDialog.Builder(requireContext(), R.style.Theme_FCBMobile_AlertDialog)
//
//    val layoutInflater =
//        (requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
//    val binding = LayoutDialogBinding.inflate(layoutInflater, null, false)
//    binding.errorMessage.text = errorMessage
//    binding.actionButton.text = actionMessage
//
//    binding.actionButton.setOnClickListener {
//        action.invoke()
//        dialog.dismiss()
//    }
//
//    binding.closeButton.setOnClickListener { dialog.dismiss() }
//
//    dialogBuilder.setView(binding.root)
//
//    dialogBuilder.setCancelable(true)
//
//    dialog = dialogBuilder.create()
//
//    dialog.show()
//}



