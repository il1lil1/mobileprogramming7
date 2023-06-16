package com.example.newsifyprac

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.newsifyprac.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {
    private lateinit var myViewModel: MyViewModel

    var binding: FragmentSettingBinding?=null

    var spindata:ArrayList<String> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainActivity = requireActivity() as MainActivity
        myViewModel = mainActivity.myViewModel


        val spinadapter = ArrayAdapter<String>(requireContext(),
            android.R.layout.simple_spinner_dropdown_item,spindata)

        var myspinview : Spinner = view.findViewById(R.id.spinner)
        myspinview.adapter = spinadapter

        spinadapter.add("10")
        spinadapter.add("12")
        spinadapter.add("14")

        myspinview.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(parent?.context, parent?.getItemAtPosition(position).toString(),
                    Toast.LENGTH_SHORT).show()

                var what = spinadapter.getItem(position).toString().toInt()
                myViewModel.TextSize = what

            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        val selectedIndex = spinadapter.getPosition(myViewModel.TextSize.toString())
        myspinview.setSelection(selectedIndex)


        var check2 : CheckBox = view.findViewById(R.id.check2)
        var check3 : CheckBox  = view.findViewById(R.id.check3)
        var check4 : CheckBox  = view.findViewById(R.id.check4)
        var check5 : CheckBox  = view.findViewById(R.id.check5)
        var check6 : CheckBox  = view.findViewById(R.id.check6)
        var check7 : CheckBox  = view.findViewById(R.id.check7)
        var check8 : CheckBox  = view.findViewById(R.id.check8)
        var check9 : CheckBox  = view.findViewById(R.id.check9)
        var check10 : CheckBox  = view.findViewById(R.id.check10)
        var check11 : CheckBox  = view.findViewById(R.id.check11)

        check2.isChecked = myViewModel.checkboxStates[1]
        check3.isChecked = myViewModel.checkboxStates[2]
        check4.isChecked = myViewModel.checkboxStates[3]
        check5.isChecked = myViewModel.checkboxStates[4]
        check6.isChecked = myViewModel.checkboxStates[5]
        check7.isChecked = myViewModel.checkboxStates[6]
        check8.isChecked = myViewModel.checkboxStates[7]
        check9.isChecked = myViewModel.checkboxStates[8]
        check10.isChecked = myViewModel.checkboxStates[9]
        check11.isChecked = myViewModel.checkboxStates[10]

        check2.setOnCheckedChangeListener { button, isChecked ->
            if(isChecked){
                data_real = newsDataManager.getNewsByCategoryDifferentWay("politics",true)
                myViewModel.checkboxStates[1] = true
            }else{
                data_real = newsDataManager.getNewsByCategoryDifferentWay("politics", false)
                myViewModel.checkboxStates[1] = false
            }
        }
        check3.setOnCheckedChangeListener { button, isChecked ->
            if(isChecked){
                data_real = newsDataManager.getNewsByCategoryDifferentWay("economy",true)
                myViewModel.checkboxStates[2] = true
            }else{
                data_real = newsDataManager.getNewsByCategoryDifferentWay("economy", false)
                myViewModel.checkboxStates[2] = false
            }
        }
        check4.setOnCheckedChangeListener { button, isChecked ->
            if(isChecked){
                data_real = newsDataManager.getNewsByCategoryDifferentWay("society",true)
                myViewModel.checkboxStates[3] = true
            }else{
                data_real = newsDataManager.getNewsByCategoryDifferentWay("society", false)
                myViewModel.checkboxStates[3] = false
            }
        }
        check5.setOnCheckedChangeListener { button, isChecked ->
            if(isChecked){
                data_real = newsDataManager.getNewsByCategoryDifferentWay("international",true)
                myViewModel.checkboxStates[4] = true
            }else{
                data_real = newsDataManager.getNewsByCategoryDifferentWay("international", false)
                myViewModel.checkboxStates[4] = false
            }
        }
        check6.setOnCheckedChangeListener { button, isChecked ->
            if(isChecked){
                data_real = newsDataManager.getNewsByCategoryDifferentWay("culture",true)
                myViewModel.checkboxStates[5] = true
            }else{
                data_real = newsDataManager.getNewsByCategoryDifferentWay("culture", false)
                myViewModel.checkboxStates[5] = false
            }
        }
        check7.setOnCheckedChangeListener { button, isChecked ->
            if(isChecked){
                data_real = newsDataManager.getNewsByCategoryDifferentWay("entertainment",true)
                myViewModel.checkboxStates[6] = true
            }else{
                data_real = newsDataManager.getNewsByCategoryDifferentWay("entertainment", false)
                myViewModel.checkboxStates[6] = false
            }
        }
        check8.setOnCheckedChangeListener { button, isChecked ->
            if(isChecked){
                data_real = newsDataManager.getNewsByCategoryDifferentWay("sports",true)
                myViewModel.checkboxStates[7] = true
            }else{
                data_real = newsDataManager.getNewsByCategoryDifferentWay("sports", false)
                myViewModel.checkboxStates[7] = false
            }
        }


        check9.setOnCheckedChangeListener { button, isChecked ->
            if(isChecked){
                data_real = newsDataManager.getNewsByBroadcasterDifferentWay("SBS",true)
                myViewModel.checkboxStates[8] = true
            }else{
                data_real = newsDataManager.getNewsByBroadcasterDifferentWay("SBS", false)
                myViewModel.checkboxStates[8] = false
            }
        }
        check10.setOnCheckedChangeListener { button, isChecked ->
            if(isChecked){
                data_real = newsDataManager.getNewsByBroadcasterDifferentWay("JTBC",true)
                myViewModel.checkboxStates[9] = true
            }else{
                data_real = newsDataManager.getNewsByBroadcasterDifferentWay("JTBC", false)
                myViewModel.checkboxStates[9] = false
            }
        }
        check11.setOnCheckedChangeListener { button, isChecked ->
            if(isChecked){
                data_real = newsDataManager.getNewsByBroadcasterDifferentWay("조선일보",true)
                myViewModel.checkboxStates[10] = true
            }else{
                data_real = newsDataManager.getNewsByBroadcasterDifferentWay("조선일보", false)
                myViewModel.checkboxStates[10] = false
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}