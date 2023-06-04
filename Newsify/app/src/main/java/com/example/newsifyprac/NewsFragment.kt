package com.example.newsifyprac

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import com.example.newsifyprac.databinding.FragmentSettingBinding

class NewsFragment : Fragment() {

    var binding: FragmentSettingBinding?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var searchtextView : TextView = view.findViewById(R.id.search)
        searchtextView.setOnClickListener {
            val fragment = requireActivity().supportFragmentManager.beginTransaction()
            fragment.addToBackStack(null)
            val reporterFragment = ReporterFragment()
            fragment.replace(R.id.frameLayout, reporterFragment)
            fragment.commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}