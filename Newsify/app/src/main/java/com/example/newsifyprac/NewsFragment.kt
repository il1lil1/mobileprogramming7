package com.example.newsifyprac

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsifyprac.databinding.FragmentSettingBinding

class NewsFragment : Fragment() {

    var binding: FragmentSettingBinding?=null
    lateinit var adapter:NewsRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var myrecyView : RecyclerView = view.findViewById(R.id.recyclerView)

        var searchbtn : ImageView = view.findViewById(R.id.search_button)
        searchbtn.setOnClickListener {
            val fragment = requireActivity().supportFragmentManager.beginTransaction()
            fragment.addToBackStack(null)
            val searchFragment = SearchFragment()
            fragment.replace(R.id.frameLayout, searchFragment)
            fragment.commit()
        }

        adapter = NewsRecyclerViewAdapter(data_real)
        adapter.itemClickListener = object :NewsRecyclerViewAdapter.OnItemClickListener{
            override fun OnItemClick(data: NewsData, positon:Int) {
                data_real[positon].scraped = !data_real[positon].scraped
                adapter.notifyItemChanged(positon)
            }

            override fun OnReporterClick(data: NewsData, position: Int) {
                val fragment = requireActivity().supportFragmentManager.beginTransaction()
                fragment.addToBackStack(null)
                val reporterFragment = ReporterFragment()
                reporterFragment.favorited = data.favorited
                reporterFragment.reporterName = data.reporter
                reporterFragment.broadcasterName = data.broadcaster
                fragment.replace(R.id.frameLayout, reporterFragment)
                fragment.commit()
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