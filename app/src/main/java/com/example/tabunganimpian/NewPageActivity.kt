package com.example.tabunganimpian

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.media.RemoteControlClient
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.MultiAutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.isInvisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.tabunganimpian.databinding.ActivityNewPageBinding
import com.example.tabunganimpian.dto.TabunganItem
import com.example.tabunganimpian.models.TabunganModel
import com.example.tabunganimpian.network.ApiService
import com.example.tabunganimpian.request.UploadRequestBody
import com.example.tabunganimpian.response.ResultStatus
import com.example.tabunganimpian.response.TabunganResponse
import com.example.tabunganimpian.viewmodel.TabunganViewModel
import com.google.android.material.textfield.TextInputEditText
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class NewPageActivity : AppCompatActivity(), UploadRequestBody.UploadCallback {

    private lateinit var binding: ActivityNewPageBinding

    private lateinit var photoImageView: ImageView
    private lateinit var saveButton: Button
    private lateinit var tipeAutoCompleteTextView: MultiAutoCompleteTextView
    private lateinit var nameEditText: TextInputEditText
    private lateinit var targetEditText: TextInputEditText
    private lateinit var menabungEditText: TextInputEditText
    private var imageUri: Uri? = null

    lateinit var tabunganViewModel: TabunganViewModel
    var isUpdate : Boolean = false
    var total: Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        val id = intent.getStringExtra("id")
        if (id != null) {
            loadTabungan(id)
            isUpdate = true
            binding.photoImageView.visibility = View.INVISIBLE
            binding.photoCardView.visibility = View.INVISIBLE
        }
        createObservable()

        // Initialize views
        photoImageView = findViewById(R.id.photoImageView)
        saveButton = findViewById(R.id.saveButton)
        tipeAutoCompleteTextView = findViewById(R.id.tipeAutoCompleteTextView)
        nameEditText = findViewById(R.id.nameEditText)
        targetEditText = findViewById(R.id.targetEditText)
        menabungEditText = findViewById(R.id.menabungEditText)

        // Set click listener to open image chooser
        photoImageView.setOnClickListener {
            openImageChooser()
        }

        // Set click listener to save data and finish activity
        saveButton.setOnClickListener {
            saveDataAndFinish(id.toString())
        }

        // Set up autocomplete options for tipeAutoCompleteTextView
        val tipeOptions = arrayOf("Harian", "Mingguan", "Bulanan", "Tahunan")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, tipeOptions)
        tipeAutoCompleteTextView.setAdapter(adapter)
        tipeAutoCompleteTextView.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())

    }

    // Open image chooser to select an image
    private fun openImageChooser() {
        val intent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_photo)), PICK_IMAGE_REQUEST)
    }

    // Handle result from image chooser
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            imageUri = data.data
            binding.photoImageView.setImageURI(imageUri)
        }
    }

    // Save data and finish the activity
    // Save data and finish the activity
    private fun saveDataAndFinish(id: String) {
        val name = nameEditText.text.toString()
        val target = targetEditText.text.toString()
        val saving = menabungEditText.text.toString()
        val tipe = tipeAutoCompleteTextView.text.toString() // Ambil teks dari tipeAutoCompleteTextView

        if (name.isNotEmpty() && target.isNotEmpty() && saving.isNotEmpty()) {
            if (!isUpdate) {
                if (imageUri == null) {
                    Toast.makeText(this, "Masukkan Gambar terlebih dahulu !!", Toast.LENGTH_SHORT).show()
                }
                val parcelFileDescriptor = contentResolver.openFileDescriptor(imageUri!!, "r", null)?:return
                val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
                val file = File(cacheDir, contentResolver.getFileName(imageUri!!))
                val outputStream = FileOutputStream(file)
                inputStream.copyTo(outputStream)

                val image = UploadRequestBody(file, "image", this)
                tabunganViewModel.createTabunganItem(RequestBody.create("multipart/form-data".toMediaTypeOrNull(), name), RequestBody.create("multipart/form-data".toMediaTypeOrNull(), tipe), RequestBody.create("multipart/form-data".toMediaTypeOrNull(), target), RequestBody.create("multipart/form-data".toMediaTypeOrNull(), "0.0"), RequestBody.create("multipart/form-data".toMediaTypeOrNull(), saving), MultipartBody.Part.createFormData("image", file.name, image), RequestBody.create(
                    "multipart/form-data".toMediaTypeOrNull(), "POST"
                ))
            } else {
                tabunganViewModel.updateTabunganItem(id, name, tipe, target, total, saving)
            }
        } else {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initViewModel() {
        tabunganViewModel = ViewModelProvider(this)[TabunganViewModel::class.java]
    }

    private fun createObservable() {
        tabunganViewModel.getCreateTabunganObserverable().observe(this, Observer<ResultStatus> {
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })
    }

    private fun loadTabungan(id: String) {
        tabunganViewModel.getDetailTabunganObserverable().observe(this, Observer<TabunganResponse> {
            binding.nameEditText.setText(it.data?.name)
            binding.targetEditText.setText(it.data?.target)
            binding.tipeAutoCompleteTextView.setText(it.data?.tipe)
            binding.menabungEditText.setText(it.data?.menabung)
            total += it.data?.total!!.toDouble()
            GlideApp.with(baseContext)
                .load("http://67.67.67.150:8000"+it.data?.image)
                .into(binding.photoImageView)
        })

        tabunganViewModel.getDetailTabungan(id)
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }

    override fun onProgressUpdate(percentage: Int) {
    }
}

private fun ContentResolver.getFileName(imageUri: Uri): String {
    var name = ""
    val returnCursor = this.query(imageUri, null, null, null, null)
    if (returnCursor != null) {
        val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        name = returnCursor.getString(nameIndex)
        returnCursor.close()
    }
    return name
}
