package com.github.ilyashvetsov.playlistmaker.library.playlists.ui.createplaylist

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.ilyashvetsov.playlistmaker.databinding.FragmentCreatePlaylistBinding
import com.github.ilyashvetsov.playlistmaker.util.MyTextWatcher
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream
import java.util.Date

class CreatePlaylistFragment : Fragment() {
    private lateinit var binding: FragmentCreatePlaylistBinding
    private val viewModel by viewModel<CreatePlaylistViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreatePlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireContext()
        val pickMedia = registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ) { uri ->
            if (uri != null) {
                val imagePath = saveImageToPrivateStorage(uri)
                viewModel.setImagePath(imagePath)
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    onBackPressed()
                }
            }
        )

        with(binding) {
            buttonBackArrow.setOnClickListener {
                onBackPressed()
            }

            playlistImage.setOnClickListener {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }

            nameField.addTextChangedListener(
                MyTextWatcher { text, _, _, _ -> viewModel.onNameChanged(text.toString()) }
            )

            descriptionField.addTextChangedListener(
                MyTextWatcher { text, _, _, _ -> viewModel.onDescriptionChanged(text.toString()) }
            )

            createButton.setOnClickListener {
                viewModel.onCreateButtonClicked()
                val playlistName = viewModel.screenState.value?.name.orEmpty()
                Toast.makeText(requireContext(), "Плейлист $playlistName создан", Toast.LENGTH_LONG).show()
                findNavController().navigateUp()
            }

            viewModel.screenState.observe(viewLifecycleOwner) { state ->
                createButton.isEnabled = state.isButtonEnabled
                state.imagePath?.let {
                    val image = File(it)
                    playlistImage.setImageURI(image.toUri())
                }
            }
        }
    }

    private fun onBackPressed() {
        if (viewModel.screenState.value?.isNotEmpty() == true) {
            showDialog()
        } else {
            findNavController().navigateUp()
        }
    }

    private fun showDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Завершить создание плейлиста?")
            .setMessage("Все несохраненные данные будут потеряны")
            .setPositiveButton("Завершить") { _, _ ->
                findNavController().navigateUp()
            }
            .setNegativeButton("Отмена") { _, _ -> }
            .show()
    }

    private fun saveImageToPrivateStorage(uri: Uri): String {
        val filePath = File(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "playlists")
        if (filePath.exists().not()) {
            filePath.mkdirs()
        }

        val imageName = "${System.currentTimeMillis()}.jpg"
        val file = File(filePath, imageName)

        val inputStream = requireContext().contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)

        BitmapFactory
            .decodeStream(inputStream)
            .compress(Bitmap.CompressFormat.JPEG, 30, outputStream)

        return file.path
    }
}
