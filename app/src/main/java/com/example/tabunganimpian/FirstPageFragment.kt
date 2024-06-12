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
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FirstPageFragment : Fragment() {

    private lateinit var textView: TextView
    private lateinit var photoImageView: ImageView
    private var currentTitle: String? = null
    private var currentTarget: String? = null
    private var currentSaving: String? = null
    private var currentTipe: String? = null
    private var currentImageUri: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first_page, container, false)
        textView = view.findViewById(R.id.textView)
        photoImageView = view.findViewById(R.id.photoImageView)

        // Default text and icon
        photoImageView.visibility = View.VISIBLE
        textView.text = "Upssss, Belum ada data impian nih"

        // Temukan FAB di layout
        val fab: FloatingActionButton = view.findViewById(R.id.fab)

        // Menangani klik FAB
        fab.setOnClickListener {
            val intent = Intent(requireContext(), NewPageActivity::class.java)
            startActivityForResult(intent, MainActivity.REQUEST_CODE_NEW_PAGE)
        }

        // Menangani klik pada textView
        textView.setOnClickListener {
            if (currentTitle != null && currentTarget != null && currentSaving != null && currentTipe != null && currentImageUri != null) {
                val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                    putExtra("title", currentTitle)
                    putExtra("target", currentTarget)
                    putExtra("saving", currentSaving)
                    putExtra("tipe", currentTipe)
                    putExtra("imageUri", currentImageUri)
                }
                startActivity(intent)
            }
        }

        return view
    }

    fun updateContent(title: String?, target: String?, saving: String?, tipe: String?, imageUri: String?) {
        currentTitle = title
        currentTarget = target
        currentSaving = saving
        currentTipe = tipe
        currentImageUri = imageUri

        if (!title.isNullOrEmpty()) {
            textView.text = title
            photoImageView.visibility = View.GONE
        }

        // Update target, saving, dan tipe, serta tampilkan gambar menggunakan imageUri
        if (!target.isNullOrEmpty() && !saving.isNullOrEmpty() && !tipe.isNullOrEmpty() && !imageUri.isNullOrEmpty()) {
            // Tampilkan data sesuai kebutuhan
            val targetText = "Target: $target"
            val savingText = "Menabung: $saving"
            val tipeText = "Tipe: $tipe"
            textView.text = "$title\n$targetText\n$savingText\n$tipeText"
            // Tampilkan gambar menggunakan Glide
            Glide.with(this).load(imageUri).into(photoImageView)
            photoImageView.visibility = View.VISIBLE
        } else {
            photoImageView.visibility = View.GONE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MainActivity.REQUEST_CODE_NEW_PAGE && resultCode == Activity.RESULT_OK) {
            val title = data?.getStringExtra("title")
            val target = data?.getStringExtra("target")
            val saving = data?.getStringExtra("saving")
            val tipe = data?.getStringExtra("tipe")
            val imageUri = data?.getStringExtra("imageUri")

            // Update content
            updateContent(title, target, saving, tipe, imageUri)
        }
    }
}
