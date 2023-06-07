package com.example.newsifyprac

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.newsifyprac.databinding.FragmentSearchBinding
import java.util.Date




class SearchFragment : Fragment() {
    var binding: FragmentSearchBinding?=null
    val model:MyViewModel by activityViewModels()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentSearchBinding.inflate(layoutInflater,container,false)
        return binding!!.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding!!.apply {
            val searchView = searchView


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

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}