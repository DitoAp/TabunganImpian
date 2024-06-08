package com.example.tabunganimpian;
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tabunganimpian.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class DetailPageActivity : AppCompatActivity() {
    private lateinit var nameTextView: TextView
    private lateinit var targetTextView: TextView
    private lateinit var menabungTextView: TextView
    private lateinit var photoImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_page)

        nameTextView = findViewById(R.id.nameTextView)
        targetTextView = findViewById(R.id.targetTextView)
        menabungTextView = findViewById(R.id.menabungTextView)
        photoImageView = findViewById(R.id.photoImageView)

        // Mendapatkan data dari intent
        val name = intent.getStringExtra("name")
        val target = intent.getStringExtra("target")
        val menabung = intent.getStringExtra("menabung")
        val imageUriString = intent.getStringExtra("imageUri")

        // Menampilkan data di UI
        nameTextView.text = "Nama: $name"
        targetTextView.text = "Target: $target"
        menabungTextView.text = "Menabung: $menabung"

        // Mengambil gambar secara asinkron
        if (imageUriString != null) {
            val imageUri = Uri.parse(imageUriString)
            loadImageFromUri(imageUri)
        }
    }

    // Method untuk memuat gambar dari URI secara asinkron
    private fun loadImageFromUri(uri: Uri) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val inputStream = contentResolver.openInputStream(uri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                // Menggunakan withContext untuk memastikan pengaturan UI dilakukan di Main thread
                withContext(Dispatchers.Main) {
                    photoImageView.setImageBitmap(bitmap)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
