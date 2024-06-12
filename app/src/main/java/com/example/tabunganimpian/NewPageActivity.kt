package com.example.tabunganimpian

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.MultiAutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import java.io.IOException

class NewPageActivity : AppCompatActivity() {

    private lateinit var photoImageView: ImageView
    private lateinit var saveButton: Button
    private lateinit var tipeAutoCompleteTextView: MultiAutoCompleteTextView
    private lateinit var nameEditText: TextInputEditText
    private lateinit var targetEditText: TextInputEditText
    private lateinit var menabungEditText: TextInputEditText
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_page)

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
            saveDataAndFinish()
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
            try {
                val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                photoImageView.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Save data and finish the activity
    // Save data and finish the activity
    private fun saveDataAndFinish() {
        val name = nameEditText.text.toString()
        val target = targetEditText.text.toString()
        val saving = menabungEditText.text.toString()
        val tipe = tipeAutoCompleteTextView.text.toString() // Ambil teks dari tipeAutoCompleteTextView

        if (name.isNotEmpty() && target.isNotEmpty() && saving.isNotEmpty() && imageUri != null) {
            val intent = Intent().apply {
                putExtra("title", name)
                putExtra("target", target)
                putExtra("saving", saving)
                putExtra("tipe", tipe) // Tambahkan tipe ke dalam intent sebagai String
                putExtra("imageUri", imageUri.toString())
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        } else {
            Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show()
        }
    }


    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }
}
