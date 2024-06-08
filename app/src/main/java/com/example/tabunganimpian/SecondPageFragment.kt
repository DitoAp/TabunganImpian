package com.example.tabunganimpian

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class SecondPageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_second_page, container, false)
        val textView: TextView = view.findViewById(R.id.textView)

        // Default text
        textView.text = "Upss, Belum ada data yang selesai nih"

        return view
    }
}
