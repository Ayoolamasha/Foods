package com.ayoolamasha.gopaddi.featureTrips.presentation.fragment


import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ayoolamasha.gopaddi.R
import com.ayoolamasha.gopaddi.common.REQUIRED_CAMERA_PERMISSIONS
import com.ayoolamasha.gopaddi.common.REQUIRED_CAMERA_PERMISSIONS_ONLY
import com.ayoolamasha.gopaddi.common.USE_CAMERA
import com.ayoolamasha.gopaddi.common.USE_GALLERY
import com.ayoolamasha.gopaddi.common.adjustSoftKeyboardPan
import com.ayoolamasha.gopaddi.common.bitmapToFile
import com.ayoolamasha.gopaddi.common.cameraPermissionRationaleDialog
import com.ayoolamasha.gopaddi.common.goToSettingsPermissionRationaleDialog
import com.ayoolamasha.gopaddi.common.imageUtils.ImageCompressor
import com.ayoolamasha.gopaddi.common.imageUtils.ImageFetcherListener
import com.ayoolamasha.gopaddi.common.imageUtils.UploadImageBottomSheet
import com.ayoolamasha.gopaddi.common.imageUtils.UploadingImageHelper
import com.ayoolamasha.gopaddi.common.openCategoryBottomSheet
import com.ayoolamasha.gopaddi.common.requestCameraPermission
import com.ayoolamasha.gopaddi.common.showProgressPopUpDialog
import com.ayoolamasha.gopaddi.common.showSnackBar
import com.ayoolamasha.gopaddi.common.stopProgressLoading
import com.ayoolamasha.gopaddi.databinding.FragmentAddFoodBinding
import com.ayoolamasha.gopaddi.featureTrips.domain.model.GetCategoryTagsUIData
import com.ayoolamasha.gopaddi.featureTrips.presentation.adapter.ImageAdapter
import com.ayoolamasha.gopaddi.featureTrips.presentation.state.CategoryTagsState
import com.ayoolamasha.gopaddi.featureTrips.presentation.state.CreateFoodState
import com.ayoolamasha.gopaddi.featureTrips.presentation.viewmodel.FoodsSharedActivityViewModel
import com.ayoolamasha.gopaddi.featureTrips.presentation.viewmodel.FoodsViewModel
import com.github.dhaval2404.imagepicker.ImagePicker.Companion.REQUEST_CODE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@AndroidEntryPoint
class AddFoodFragment : Fragment(), ImageFetcherListener {
    private lateinit var binding: FragmentAddFoodBinding
    private val foodsViewModel: FoodsViewModel by viewModels()
    private val foodsSharedActivityViewModel: FoodsSharedActivityViewModel by activityViewModels()
    private var isNameEntered  = false
    private var isDescriptionEntered = false
    private var isCategoryChosen = false
    private var isCaloriesEntered = false
    private var isTagSelected = false
    private var extractedName = ""
    private var extractedDesc = ""
    private var extractedCalories = ""
    private var extractedCategory = ""
    private var extractedTags = ""
    private  var getCategoryUIData : List<GetCategoryTagsUIData> = emptyList()
    private  var getTagsUIData : List<GetCategoryTagsUIData> = emptyList()
    private val selectedTags = mutableListOf<String>()
    private lateinit var imageAdapter: ImageAdapter
    private val imageList = mutableListOf<Bitmap>()
    private val imageListFile = mutableListOf<File>()
    private var tagsList: List<String> = emptyList()
    private var REQUEST_FOR_PROOF_IMAGE = 0
    @Inject
    lateinit var uploadingImageHelper: UploadingImageHelper
    @Inject
    lateinit var imageCompressor: ImageCompressor
    private var requestImage = 0
    private var permissionCount = 0
    private var categoryId = 0
    private var tagMap: Map<String, Int> = emptyMap()
    private var tagNames: List<String> = emptyList()

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                try {
                    val bitmap = imageCompressor.compressImage(uri)
                    imageBitMapPicker(bitmap)
                } catch (e: Exception) {
                    Timber.d(e.printStackTrace().toString())
                }
            } else {
                Timber.tag("PhotoPicker").d("No media selected")
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddFoodBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adjustSoftKeyboardPan(requireActivity())
        foodsViewModel.makeGetCategoryCall()
        foodsViewModel.makeGetTagsCall()

        /**
         * GET CATEGORY STATE
         */
        viewLifecycleOwner.lifecycleScope.launch {
            foodsViewModel.getCategoryState
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { getCategoryState(it) }
        }

        /**
         * GET TAGS STATE
         */
        viewLifecycleOwner.lifecycleScope.launch {
            foodsViewModel.getTagState
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { getTagState(it) }
        }

        /**
         * CREATE FOOD STATE
         */
        viewLifecycleOwner.lifecycleScope.launch {
            foodsViewModel.createFoodState
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { getCreateFoodState(it) }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            foodsSharedActivityViewModel.selectedTag
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    if (it.name.isNullOrEmpty().not()){
                        isCategoryChosen = true
                        binding.categoryName.text = it.name
                        categoryId = it.id ?: 1
                        enableButtonCheck()
                    }
                }
        }

        binding.categoryConstraint.setOnClickListener {
            if (getCategoryUIData.isEmpty().not()){
                openCategoryBottomSheet(tagName = getCategoryUIData)
            }
        }

       val tagsList = listOf(
            "Spicy", "Sweet", "Savory", "Low Carb", "High Protein",
            "Keto", "Paleo", "Comfort Food", "Gourmet", "Street Food",
            "Fusion", "Seasonal", "Farm-to-Table", "Ethnic", "Homemade"
        )

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, tagsList)
        binding.autoCompleteTextView.setAdapter(adapter)
        val selectedTagIds = mutableListOf<Int>()
        binding.autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
            val selectedTag = adapter.getItem(position) ?: return@setOnItemClickListener
            if (!selectedTags.contains(selectedTag)) {
                selectedTags.add(selectedTag)
               selectedTagIds.add(position)
                addTagChip(selectedTag)
            }
            isTagSelected = true
            enableButtonCheck()
            binding.autoCompleteTextView.text.clear()
        }

        binding.takePhotoLinear.setOnClickListener {
            checkPermissions()
        }

        binding.addFoodButton.setOnClickListener {
            foodsViewModel.makeCreateFoodCall(
                name = extractedName,
                desc = extractedName,
                categoryId = categoryId.toString(),
                calories = binding.enterCalories.text.toString(),
                tags = selectedTagIds.map { it.toString() },
                imageFiles = imageListFile
            )
        }

        binding.backArrow.setOnClickListener{navigateToHome()}

        imageRecycler()

        nameValidationListener()
        descValidationListener()
        caloriesValidationListener()
    }

    //INPUT VALIDATOR
    private fun enableButtonCheck(){
        if (isNameEntered && isCaloriesEntered && isDescriptionEntered && isTagSelected && isCategoryChosen) {
            binding.addFoodButton.isEnabled = true
        }
    }
    private fun nameValidationListener() {
        binding.enterFoodName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.enterFoodName.text.hashCode() == p0.hashCode()) {
                    isNameEntered = true
                }
                enableButtonCheck()
            }

            override fun afterTextChanged(p0: Editable?) {
                extractedName = binding.enterFoodName.editableText.toString()
                if (p0 == binding.enterFoodName.editableText && p0.toString().trim().isEmpty()) {
                    isNameEntered = false
                    binding.addFoodButton.isEnabled = false
                }
            }

        })
    }

    private fun descValidationListener() {
        binding.enterDesc.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.enterDesc.text.hashCode() == p0.hashCode()) {
                    isDescriptionEntered = true
                }
                enableButtonCheck()
            }

            override fun afterTextChanged(p0: Editable?) {
                extractedDesc = binding.enterDesc.editableText.toString()
                if (p0 == binding.enterDesc.editableText && p0.toString().trim().isEmpty()) {
                    isDescriptionEntered = false
                    binding.addFoodButton.isEnabled = false
                }
            }

        })
    }

    private fun caloriesValidationListener() {
        binding.enterCalories.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.enterCalories.text.hashCode() == p0.hashCode()) {
                    isCaloriesEntered = true
                }
                enableButtonCheck()
            }

            override fun afterTextChanged(p0: Editable?) {
                extractedCalories = binding.enterCalories.editableText.toString()
                if (p0 == binding.enterFoodName.editableText && p0.toString().trim().isEmpty()) {
                    isCaloriesEntered = false
                    binding.addFoodButton.isEnabled = false
                }
            }

        })
    }

    private fun getCategoryState(categoryTagsState: CategoryTagsState) {
        if (categoryTagsState.isLoading) {
            binding.progressBar.isVisible = true
        } else if (categoryTagsState.isError) {
            binding.progressBar.isVisible = false
        } else if (categoryTagsState.isSuccess) {
            binding.progressBar.isVisible = false
            binding.openCategories.isVisible = true
            getCategoryUIData =  categoryTagsState.getCategoryTagsUiData ?: emptyList()

        }
    }

    private fun getTagState(categoryTagsState: CategoryTagsState) {
        if (categoryTagsState.isLoading) {
            binding.autoCompleteTextView.isEnabled = false
        } else if (categoryTagsState.isError) {
            binding.autoCompleteTextView.isEnabled = false
        } else if (categoryTagsState.isSuccess) {
            binding.autoCompleteTextView.isEnabled = true
            getTagsUIData = categoryTagsState.getCategoryTagsUiData ?: emptyList()
            tagsList = getTagsUIData.mapNotNull { it.name }
            tagMap= getTagsUIData.associate { it.name!! to it.id!! }
            tagNames  = tagMap.keys.toList()
        }
    }


    private fun getCreateFoodState(createFoodState: CreateFoodState) {
        if (createFoodState.isLoading) {
            showProgressPopUpDialog()
        } else if (createFoodState.isError) {
            stopProgressLoading()
            showSnackBar(content = createFoodState.error.toString() )
        } else if (createFoodState.isSuccess) {
            stopProgressLoading()
            showSnackBar(content = createFoodState.createFoodUiData?.message.toString() )
            navigateToHome()


        }
    }

    private fun navigateToHome(){
        binding.root.findNavController().navigate(R.id.action_addFoodFragment_to_homeFragment)
    }
    private fun addTagChip(tag: String) {
        val tagView = LayoutInflater.from(requireContext()).inflate(R.layout.tag_item, binding.tagContainer, false) as TextView
        tagView.text = "$tag ✕"
        tagView.setOnClickListener {
            selectedTags.remove(tag)
            binding.tagContainer.removeView(tagView)
        }
        binding.tagContainer.addView(tagView)
    }

    private fun imageRecycler() {
        imageAdapter = ImageAdapter(imageList) { bitmap ->

            imageAdapter.removeItem(bitmap)
        }
        binding.uploadedImageRecycler.layoutManager = GridLayoutManager(requireContext(), 4)
        binding.uploadedImageRecycler.adapter = imageAdapter
    }

    private fun checkPermissions() {
        when {
            requestCameraPermission() -> {
                displayUploadOptionBottomSheet()
            }

            shouldShowRequestPermissionRationale(REQUIRED_CAMERA_PERMISSIONS_ONLY) -> {
                cameraPermissionRationaleDialog { checkPermissions() }
            }

            else -> {
                requestPermissions(REQUIRED_CAMERA_PERMISSIONS, REQUEST_CODE)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        when (requestCode) {
            REQUEST_CODE -> {
                if ((
                            grantResults.isNotEmpty() &&
                                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                            )
                ) {
                    displayUploadOptionBottomSheet()
                } else {
                    if (permissionCount == 2) {
                        goToSettingsPermissionRationaleDialog()
                    } else {
                        permissionCount++
                        cameraPermissionRationaleDialog { checkPermissions() }
                    }
                }
                return
            }

            else -> {
            }
        }
    }

    private var addImageResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode != Activity.RESULT_CANCELED) {
                try {
                    when (requestImage) {
                        0 ->
                            if (result.resultCode == Activity.RESULT_OK) {
                                uploadingImageHelper.populateCameraUpload()
                            }

                        1 ->
                            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                                uploadingImageHelper.photoManipulation(result.data)
                            }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

    private fun displayUploadOptionBottomSheet() {
       val uploadImageBottomSheet = UploadImageBottomSheet()
        uploadImageBottomSheet.show(requireActivity().supportFragmentManager, tag)

        uploadImageBottomSheet.setOnClickListener(
            object :
                UploadImageBottomSheet.BottomSheetOptionListener {
                override fun getSelectedOption(option: String) {
                    when (option) {
                        USE_CAMERA -> {
                            requestImage = 0
                            uploadingImageHelper.selectImageCameraOnly(activity = requireActivity())
                        }

                        USE_GALLERY -> {
                            requestImage = 1
                            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
                                uploadingImageHelper.selectGalleryOnly(activity = requireActivity())
                            } else {
                                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                            }
                        }
                    }
                }
            },
        )
    }

    override fun startActivityForPicture(intent: Intent?, READ_REQUEST_CODE: Int, TAG: String) {
        REQUEST_FOR_PROOF_IMAGE = READ_REQUEST_CODE
        if (TAG == UploadingImageHelper::class.java.simpleName) {
            addImageResultLauncher.launch(intent)
        }
    }

    override fun imageBitMap(imageBitMap: Bitmap?) {
        imageBitMap?.let {
            imageList.add(it)
            imageAdapter.setData(imageList)

            val imageFile = bitmapToFile(it)
            imageListFile.add(imageFile)
        }
    }

    private fun imageBitMapPicker(imageBitMap: Bitmap) {
        imageList.add(imageBitMap)
        imageAdapter.setData(imageList)

        val imageFile = bitmapToFile(imageBitMap)
        imageListFile.add(imageFile)
    }
}
