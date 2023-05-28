package com.example.newsifyprac

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsifyprac.databinding.FragmentReporterBinding

class ReporterFragment : Fragment() {
    var binding: FragmentReporterBinding?=null
    lateinit var adapter:ReporterRecyclerViewAdapter

    val data = arrayListOf<String>("기사1", "기사2", "기사3")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reporter, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var myrecyView : RecyclerView = view.findViewById(R.id.recyclerView)

        adapter = ReporterRecyclerViewAdapter(data)
        adapter.itemClickListener = object :ReporterRecyclerViewAdapter.OnItemClickListener{
            override fun OnItemClick(data: String, positon:Int) {
                adapter.notifyItemChanged(positon)
            }
        }
        myrecyView.layoutManager = LinearLayoutManager(requireContext())
        myrecyView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}