package com.example.newsifyprac

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.newsifyprac.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {

    var binding: FragmentSettingBinding?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var myTextView : TextView = view.findViewById(R.id.setting)
        myTextView.setOnClickListener {
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