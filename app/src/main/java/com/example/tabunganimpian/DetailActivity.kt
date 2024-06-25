// DetailActivity.kt
package com.example.tabunganimpian

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.tabunganimpian.adapter.ItemAdapter
import com.example.tabunganimpian.databinding.ActivityDetailBinding
import com.example.tabunganimpian.dto.TabunganItem
import com.example.tabunganimpian.network.NetworkConfig
import com.example.tabunganimpian.response.ResultItem
import com.example.tabunganimpian.response.ResultStatus
import com.example.tabunganimpian.response.ResultTabungan
import com.example.tabunganimpian.response.TabunganResponse
import com.example.tabunganimpian.viewmodel.ItemsViewModel
import com.example.tabunganimpian.viewmodel.TabunganViewModel

class DetailActivity : AppCompatActivity() {

    lateinit var tabunganViewModel: TabunganViewModel
    lateinit var binding: ActivityDetailBinding

    private lateinit var detailSaving: TextView
    private lateinit var detailImage: ImageView
//    private lateinit var textTerkumpul: TextView
//    private lateinit var textKurang: TextView

    val adapter = ItemAdapter()

    private var nominal: String = String()

    var terkumpul: Int = 0
    var kurang: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.adapter = adapter
        setSupportActionBar(binding.toolbar)

        binding.back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        initViewModel()
        val tabungan = intent.getStringExtra("id")
        loadTabungan(tabungan.toString())
        createSavingObservable(tabungan.toString())
        tabunganViewModel.getItemsObserverable().observe(this, Observer<ResultItem> {
            if (it.data!!.isEmpty()) {
                Toast.makeText(this, "no result found...", Toast.LENGTH_SHORT).show()
            } else {
                adapter.itemsData = it.data!!.toMutableList()
                adapter.notifyDataSetChanged()
            }
        })

        tabunganViewModel.getItems(tabungan.toString())

        binding.update.setOnClickListener {
            val intent = Intent(this, NewPageActivity::class.java).apply {
                putExtra("id", tabungan.toString())
            }
            startActivity(intent)
        }

        binding.delete.setOnClickListener {
            showDeleteDialog(tabungan.toString())
        }

        binding.menabung.setOnClickListener {
            showNominalInputPopup(tabungan.toString())
        }

//        Glide.with(this).load(imageUri).into(detailImage)

//        targetAmount = target
//        updateTerkumpulKurang()

//        btnYukNabung.setOnClickListener {
//            showNominalInputPopup(true)
//        }
//
//        btnAmbilTabungan.setOnClickListener {
//            showNominalInputPopup(false)
//        }
    }

    private fun initViewModel() {
        tabunganViewModel = ViewModelProvider(this).get(TabunganViewModel::class.java)
    }

    private fun loadTabungan(id: String) {
        tabunganViewModel.getDetailTabunganObserverable().observe(this, Observer<TabunganResponse> {
            binding.detailTitle.text = it.data?.name
            binding.detailTarget.text = "Target: ${it.data?.target}"
            binding.detailSaving.text = "Menabung: ${it.data?.menabung}"
            binding.detailTipe.text = "Tipe: ${it.data?.tipe}"
            nominal = it.data?.menabung.toString()
            kurang = it.data?.target!!.toInt() - it.data?.total!!.toInt()
            terkumpul = it.data?.total!!.toInt()
            binding.textTerkumpul.text = "Terkumpul ${terkumpul.toString()}"
            if (kurang < 0) {
                binding.textKurang.text = "Kurang ${0}"
            } else {
                binding.textKurang.text = "Kurang ${kurang.toString()}"
            }
            GlideApp.with(baseContext)
                .load("http://67.67.67.150:8000"+it.data?.image)
                .into(binding.detailImage)
        })

        tabunganViewModel.getDetailTabungan(id)
    }

    private fun deleteTabungan(id: String) {
        tabunganViewModel.deleteTabunganObserverable().observe(this, Observer<ResultStatus> {
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })
        tabunganViewModel.deleteTabunganItem(id)
    }

    private fun showDeleteDialog(id: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder
            .setMessage("Apakah anda yakin ingin menghapus data ?")
            .setTitle("Delete")
            .setPositiveButton("Delete") { dialog, which ->
                deleteTabungan(id)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
        val dialog : AlertDialog = builder.create()
        dialog.show()
    }

    private fun showNominalInputPopup(id: String) {
        val builder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView: View = inflater.inflate(R.layout.popup_input_nominal, null)
        builder.setView(dialogView)

        val etNominal = dialogView.findViewById<EditText>(R.id.etNominal)
        val btnSubmit = dialogView.findViewById<Button>(R.id.btnSubmit)
        etNominal.setText(nominal)
        val dialog = builder.create()

        btnSubmit.setOnClickListener {
            tabunganViewModel.createItem(id, etNominal.text.toString().toDouble(), "menabung")
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun createSavingObservable(id: String) {
        tabunganViewModel.getCreateSavingObserverable().observe(this, Observer<ResultStatus> {
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra("id", id)
            }
            startActivity(intent)
        })
    }

//    private fun updateTerkumpulKurang() {
//        textTerkumpul.text = "Terkumpul: $currentSaving"
//        textKurang.text = "Kurang: ${targetAmount - currentSaving}"
//    }

    private fun showError(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }

}
