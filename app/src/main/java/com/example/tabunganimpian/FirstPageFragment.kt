package com.example.tabunganimpian

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FirstPageFragment : Fragment() {

    private lateinit var textView: TextView
    private lateinit var iconImageView: ImageView
    private lateinit var photoImageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first_page, container, false)
        textView = view.findViewById(R.id.textView)
        iconImageView = view.findViewById(R.id.iconImageView)
        photoImageView = view.findViewById(R.id.photoImageView) // Inisialisasi photoImageView

        // Default text and icon
        iconImageView.visibility = View.VISIBLE // Mengubah visibilitas iconImageView menjadi visible
        textView.text = "Upssss, Belum ada data impian nih"

        // Temukan FAB di layout
        val fab: FloatingActionButton = view.findViewById(R.id.fab)

        // Menangani klik FAB
        fab.setOnClickListener {
            val intent = Intent(requireContext(), NewPageActivity::class.java)
            startActivityForResult(intent, MainActivity.REQUEST_CODE_NEW_PAGE)
        }

        return view
    }

    fun updateContent(title: String?, imageUri: String?) {
        if (!title.isNullOrEmpty()) {
            textView.text = title
            iconImageView.visibility = View.GONE // Menghilangkan icon ketika ada data
        }

        if (!imageUri.isNullOrEmpty()) {
            // Load image from URI using your preferred method
            // For example, Glide or Picasso
            // Glide.with(this).load(imageUri).into(photoImageView)
            photoImageView.visibility = View.VISIBLE // Menampilkan photoImageView ketika gambar diunggah
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MainActivity.REQUEST_CODE_NEW_PAGE && resultCode == Activity.RESULT_OK) {
            val title = data?.getStringExtra("title")
            val imageUri = data?.getStringExtra("imageUri")

            // Update content
            updateContent(title, imageUri)
        }
    }
}
