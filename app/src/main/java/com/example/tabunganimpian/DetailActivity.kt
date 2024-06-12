// DetailActivity.kt
package com.example.tabunganimpian

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    private lateinit var detailTitle: TextView
    private lateinit var detailTarget: TextView
    private lateinit var detailSaving: TextView
    private lateinit var detailTipe: TextView
    private lateinit var detailImage: ImageView
    private lateinit var textTerkumpul: TextView
    private lateinit var textKurang: TextView
    private lateinit var btnYukNabung: Button
    private lateinit var btnAmbilTabungan: Button

    private var currentSaving: Int = 0
    private var targetAmount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        detailTitle = findViewById(R.id.detailTitle)
        detailTarget = findViewById(R.id.detailTarget)
        detailSaving = findViewById(R.id.detailSaving)
        detailTipe = findViewById(R.id.detailTipe)
        detailImage = findViewById(R.id.detailImage)
        textTerkumpul = findViewById(R.id.textTerkumpul)
        textKurang = findViewById(R.id.textKurang)
        btnYukNabung = findViewById(R.id.btnYukNabung)
        btnAmbilTabungan = findViewById(R.id.btnAmbilTabungan)

        val title = intent.getStringExtra("title")
        val target = intent.getStringExtra("target")?.toIntOrNull() ?: 0
        val saving = intent.getStringExtra("saving")?.toIntOrNull() ?: 0
        val tipe = intent.getStringExtra("tipe")
        val imageUri = intent.getStringExtra("imageUri")

        detailTitle.text = title
        detailTarget.text = "Target: $target"
        detailSaving.text = "Menabung: $saving"
        detailTipe.text = "Tipe: $tipe"
        Glide.with(this).load(imageUri).into(detailImage)

        targetAmount = target
        updateTerkumpulKurang()

        btnYukNabung.setOnClickListener {
            showNominalInputPopup(true)
        }

        btnAmbilTabungan.setOnClickListener {
            showNominalInputPopup(false)
        }
    }

    private fun showNominalInputPopup(isDeposit: Boolean) {
        val builder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView: View = inflater.inflate(R.layout.popup_input_nominal, null)
        builder.setView(dialogView)

        val etNominal = dialogView.findViewById<EditText>(R.id.etNominal)
        val btnSubmit = dialogView.findViewById<Button>(R.id.btnSubmit)

        val dialog = builder.create()

        btnSubmit.setOnClickListener {
            val nominal = etNominal.text.toString().toIntOrNull() ?: -1
            if (nominal <= 0) {
                showError("Masukkan nominal yang benar")
            } else {
                if (isDeposit) {
                    currentSaving += nominal
                } else {
                    if (currentSaving >= nominal) {
                        currentSaving -= nominal
                    } else {
                        // Show error message if not enough savings
                        showError("Jumlah tabungan tidak mencukupi")
                    }
                }
                updateTerkumpulKurang()
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun updateTerkumpulKurang() {
        textTerkumpul.text = "Terkumpul: $currentSaving"
        textKurang.text = "Kurang: ${targetAmount - currentSaving}"
    }

    private fun showError(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}
