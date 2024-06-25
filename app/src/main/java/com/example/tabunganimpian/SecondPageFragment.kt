package com.example.tabunganimpian

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tabunganimpian.adapter.TabunganAdapter
import com.example.tabunganimpian.databinding.FragmentSecondPageBinding
import com.example.tabunganimpian.dto.TabunganItem
import com.example.tabunganimpian.response.ResultTabungan
import com.example.tabunganimpian.viewmodel.TabunganViewModel

class SecondPageFragment : Fragment() {

    lateinit var binding: FragmentSecondPageBinding
    lateinit var tabunganViewModel: TabunganViewModel
    val adapter = TabunganAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = adapter

        tabunganViewModel = ViewModelProvider(this)[TabunganViewModel::class.java]
        tabunganViewModel.getTabunganEndObserverable().observe(viewLifecycleOwner, Observer<ResultTabungan> {
            if (it.dataTabungan!!.isEmpty()) {
                Toast.makeText(context, "no result found...", Toast.LENGTH_SHORT).show()
            } else {
                adapter.data = it.dataTabungan!!.toMutableList()
                adapter.notifyDataSetChanged()
            }
        })

        tabunganViewModel.getEndData()

        adapter.setOnClickListener(object: TabunganAdapter.OnClickListener {
            override fun onClick(position: Int, model: TabunganItem) {
                val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                    putExtra("id", model.tabunganId)
                }
                startActivity(intent)
            }

        })
    }
}
