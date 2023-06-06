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
    lateinit var filteredList: ArrayList<NewsData> // Only scraped NewsData List

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mypage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        filteredList = ArrayList(data_real.filter { it.scraped })

        var scrap_recycler : RecyclerView = view.findViewById(R.id.scrap_article)

        adapter = MypageScrapedAdapter(filteredList).apply {
            itemClickListener = object : MypageScrapedAdapter.OnItemClickListener {
                override fun OnItemClick(data: NewsData, position: Int) {
                    // Do something when item is clicked
                }

                override fun OnReporterClick(data: NewsData, position: Int) {
                    // Do something when reporter is clicked
                }

                override fun OnScrapClick(data: NewsData, position: Int) { // 추가
                    data.scraped = false
                    filteredList.removeAt(position)
                    notifyItemRemoved(position)
                }
            }
        }

        val dividerItemDecoration = DividerItemDecoration(scrap_recycler.context, LinearLayoutManager(requireContext()).orientation)
        scrap_recycler.addItemDecoration(dividerItemDecoration)
        scrap_recycler.layoutManager = LinearLayoutManager(requireContext())
        scrap_recycler.adapter = adapter


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