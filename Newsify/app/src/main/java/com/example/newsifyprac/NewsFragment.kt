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
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsifyprac.databinding.FragmentNewsBinding
import com.example.newsifyprac.databinding.FragmentSearchBinding
import com.example.newsifyprac.databinding.FragmentSettingBinding

class NewsFragment : Fragment() {

    var binding: FragmentNewsBinding?=null
    lateinit var adapter:NewsRecyclerViewAdapter
    val model:MyViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewsBinding.inflate(layoutInflater,container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var myrecyView : RecyclerView = view.findViewById(R.id.recyclerView)




        adapter = NewsRecyclerViewAdapter(data_real)
        adapter.itemClickListener = object :NewsRecyclerViewAdapter.OnItemClickListener{
            override fun OnItemClick(data: NewsData, positon:Int) {
                data_real[positon].scraped = !data_real[positon].scraped
                adapter.notifyItemChanged(positon)
            }

            override fun OnReporterClick(data: NewsData, position: Int) {
                val fragment = requireActivity().supportFragmentManager.beginTransaction()
                val reporterFragment = ReporterFragment()
                fragment.addToBackStack(null)
                reporterFragment.favorited = data.favorited
                reporterFragment.reporterName = data.reporter
                reporterFragment.broadcasterName = data.broadcaster
                fragment.replace(R.id.frameLayout, reporterFragment)
                fragment.commit()
            }

            override fun OnTittleClick(data: NewsData, position: Int) {
                val fragment = requireActivity().supportFragmentManager.beginTransaction()
                val contentFragment = NewsContentFragment()
//                fragment.addToBackStack(null)
                contentFragment.contentData = data
                fragment.replace(R.id.frameLayout, contentFragment)
                fragment.commit()
            }
        }


        val searchView = binding!!.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val searchterm = binding!!.searchView.query.toString()
                model.setLiveData(searchterm)
                val fragment = requireActivity().supportFragmentManager.beginTransaction()
                fragment.addToBackStack(null)
                val searchresultFragment = SearchResultFragment()
                fragment.replace(R.id.frameLayout, searchresultFragment)
                fragment.commit()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { query ->

                }
                return true
            }
        })
        myrecyView.layoutManager = LinearLayoutManager(requireContext())
        myrecyView.adapter = adapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}