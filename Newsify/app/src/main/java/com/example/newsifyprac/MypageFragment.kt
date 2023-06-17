package com.example.newsifyprac

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsifyprac.databinding.FragmentMypageBinding
import com.example.newsifyprac.databinding.FragmentSettingBinding

class MypageFragment : Fragment() {

    var binding: FragmentMypageBinding?=null
    lateinit var adapter:MypageScrapedAdapter
    lateinit var adapter2:MypageReporterAdapter
    lateinit var filteredList: ArrayList<NewsData> // Only scraped NewsData List
    lateinit var filteredList2: ArrayList<NewsData>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mypage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        filteredList = ArrayList(data_real.filter { it.scraped })
        filteredList2 = ArrayList(data_real.filter { it.favorited }.distinctBy { it.reporter })

        var scrap_recycler : RecyclerView = view.findViewById(R.id.scrap_article)
        var reporter_recycler : RecyclerView = view.findViewById(R.id.subscribe_reporter)

        adapter = MypageScrapedAdapter(filteredList).apply {
            itemClickListener = object : MypageScrapedAdapter.OnItemClickListener {
                override fun OnItemClick(data: NewsData, position: Int) {
                    // Do something when item is clicked
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

                override fun OnScrapClick(data: NewsData, position: Int) {
                    data.scraped = false
                    filteredList.removeAt(position)
                    notifyItemRemoved(position)
                }
            }
        }

        scrap_recycler.layoutManager = LinearLayoutManager(requireContext())
        scrap_recycler.adapter = adapter


        adapter2 = MypageReporterAdapter(filteredList2).apply {
            itemClickListener = object : MypageReporterAdapter.OnItemClickListener {
                override fun OnItemClick(data: NewsData, position: Int) {
                    val reporterName = data.reporter
                    data_real.forEach { item ->
                        if (item.reporter == reporterName) {
                            item.favorited = false
                        }
                    }
                    filteredList2.removeAt(position)
                    notifyItemRemoved(position)
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
        }

        reporter_recycler.layoutManager = LinearLayoutManager(requireContext())
        reporter_recycler.adapter = adapter2

    }

    override fun onResume() {
        super.onResume()

        // Update the filteredList every time the fragment becomes active
        filteredList.clear()
        filteredList.addAll(data_real.filter { it.scraped })
        adapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}