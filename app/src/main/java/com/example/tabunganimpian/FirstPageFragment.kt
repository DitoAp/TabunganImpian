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

        return view
    }

    fun updateContent(title: String?, target: String?, saving: String?, tipe: String?, imageUri: String?) {
        if (!title.isNullOrEmpty()) {
            textView.text = title
            photoImageView.visibility = View.GONE
        }

        // Update target, saving, tipe, serta tampilkan gambar menggunakan imageUri
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
            val tipe = data?.getStringExtra("tipe") // tambahkan parameter tipe
            val imageUri = data?.getStringExtra("imageUri")

            // Update content
            updateContent(title, target, saving, tipe, imageUri)
        }
    }
}
