package com.ayoolamasha.gopaddi.common.imageUtils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ayoolamasha.gopaddi.common.USE_CAMERA
import com.ayoolamasha.gopaddi.common.USE_GALLERY
import com.ayoolamasha.gopaddi.databinding.LayoutUploadImageBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class UploadImageBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: LayoutUploadImageBottomSheetBinding
    private lateinit var clickListener: BottomSheetOptionListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = LayoutUploadImageBottomSheetBinding.inflate(inflater, container, false)

        binding.useCameraConstraint.setOnClickListener {
            clickListener.getSelectedOption(USE_CAMERA)
            dismiss()
        }

        binding.useGalleryConstraint.setOnClickListener {
            clickListener.getSelectedOption(USE_GALLERY)
            dismiss()
        }

        return binding.root
    }

    interface BottomSheetOptionListener {
        fun getSelectedOption(option: String)
    }

    fun setOnClickListener(clickListener: BottomSheetOptionListener) {
        this.clickListener = clickListener
    }
}