package com.example.newsifyprac

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsifyprac.databinding.FragmentSearchResultBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class SearchResultFragment : Fragment() {
    val scope = CoroutineScope(Dispatchers.IO)
    var binding: FragmentSearchResultBinding?=null
    val model:MyViewModel by activityViewModels()
    lateinit var SearchedList: ArrayList<NewsData>

    lateinit var adapter: SearchResultAdapter





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchResultBinding.inflate(layoutInflater,container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchterm = model.searchterm.value

        SearchedList = ArrayList(data_real.filter { it.title.contains(searchterm!!) })
        var SearchRecyclerView : RecyclerView = binding!!.searchRecycler
        adapter = SearchResultAdapter(SearchedList)

        adapter.itemClickListener = object :SearchResultAdapter.OnItemClickListener
            {
            override fun OnItemClick(data: NewsData, positon:Int) {
                SearchedList[positon].scraped = !SearchedList[positon].scraped
                adapter.notifyItemChanged(positon)
            }

            override fun OnReporterClick(data: NewsData, position: Int) {
                val fragment = requireActivity().supportFragmentManager.beginTransaction()
                val reporterFragment = ReporterFragment()
                reporterFragment.reporterName = data.reporter
                reporterFragment.broadcasterName = data.broadcaster
                fragment.replace(R.id.frameLayout, reporterFragment)
                fragment.commit()
            }
        }
        SearchRecyclerView.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)
        SearchRecyclerView.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(binding!!.searchRecycler.context, LinearLayoutManager(requireContext()).orientation)
        binding!!.searchRecycler.addItemDecoration(dividerItemDecoration)


        binding!!.searchtitle.text = "\"${searchterm}\"에 대한 검색결과"
        for (data in newsDataManager.newsList) {
            if(data.title.contains(searchterm!!)) {
                Log.d("DEBUG.LOG", data.title)
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}