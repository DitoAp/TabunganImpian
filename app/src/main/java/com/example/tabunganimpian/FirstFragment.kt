package com.example.tabunganimpian

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.tabunganimpian.adapter.TabunganAdapter
import com.example.tabunganimpian.databinding.FragmentFirstBinding
import com.example.tabunganimpian.dto.TabunganItem
import com.example.tabunganimpian.response.ResultTabungan
import com.example.tabunganimpian.viewmodel.TabunganViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FirstFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FirstFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var binding: FragmentFirstBinding
    lateinit var tabunganViewModel: TabunganViewModel
    lateinit var tabunganAdapter: TabunganAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        binding.recyclerView.adapter = tabunganAdapter
        initViewModel()

        return binding.root
    }

    fun initViewModel() {
        tabunganViewModel.getTabunganItemObserverable().observe(viewLifecycleOwner, Observer<ResultTabungan> {
            if (it == null) {
                Toast.makeText(context, "no result found...", Toast.LENGTH_SHORT).show()
            } else {
                tabunganAdapter.data = it.dataTabungan!!.toMutableList()
                tabunganAdapter.notifyDataSetChanged()
            }
        })
        tabunganViewModel.getTabunganItem()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FirstFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FirstFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}