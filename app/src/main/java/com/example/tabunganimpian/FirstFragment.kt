package com.example.tabunganimpian

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
    lateinit var detailActivity: DetailActivity
    private lateinit var secondPageFragment: SecondPageFragment

    val adapter = TabunganAdapter()

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = adapter

        binding.normalFAB.setOnClickListener {
            val intent = Intent(requireContext(), NewPageActivity::class.java)
            startActivityForResult(intent, MainActivity.REQUEST_CODE_NEW_PAGE)
        }


        tabunganViewModel = ViewModelProvider(this)[TabunganViewModel::class.java]
        tabunganViewModel.getTabunganItemObserverable().observe(viewLifecycleOwner, Observer<ResultTabungan> {
            if (it.dataTabungan!!.isEmpty()) {
                Toast.makeText(context, "no result found...", Toast.LENGTH_SHORT).show()
            } else {
                adapter.data = it.dataTabungan!!.toMutableList()
                adapter.notifyDataSetChanged()
            }
        })
        tabunganViewModel.getTabunganItem()

        adapter.setOnClickListener(object: TabunganAdapter.OnClickListener {
            override fun onClick(position: Int, model: TabunganItem) {
                val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                    putExtra("id", model.tabunganId)
                }
                startActivity(intent)
            }

        })
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