package com.example.newsifyprac

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsifyprac.databinding.FragmentReporterBinding

class ReporterFragment : Fragment() {
    var binding: FragmentReporterBinding?=null
    lateinit var adapter:ReporterRecyclerViewAdapter

    var favorited: Boolean? = null
    var reporterName: String? = null
    var broadcasterName: String? = null

    val data = arrayListOf<String>("기사1", "기사2", "기사3")
//    var data_real = arrayListOf<NewsData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reporter, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //data_real이라고 mainactivity에 전역변수 선언해놓았음
        data_real = newsDataManager.getNewsByReporterDifferentWay(reporterName.toString())


        var myrecyView : RecyclerView = view.findViewById(R.id.recyclerView)
        var Favorited : ImageView = view.findViewById(R.id.favorite)
        var Reporter : TextView = view.findViewById(R.id.reporter)
        var where : TextView = view.findViewById(R.id.where)

        if(favorited == true){
            Favorited.setImageResource(R.drawable.favorite_after)
        }
        else{
            Favorited.setImageResource(R.drawable.favorite_before)
        }
        Reporter.text = reporterName
        where.text = broadcasterName

        Favorited.setOnClickListener {
            favorited = !favorited!!
            data_real = newsDataManager.getFavorited(reporterName!!)
            if(favorited == true) {
                Favorited.setImageResource(R.drawable.favorite_after)
            }
            else {
                Favorited.setImageResource(R.drawable.favorite_before)
            }
        }

        adapter = ReporterRecyclerViewAdapter(data_real)
        adapter.itemClickListener = object :ReporterRecyclerViewAdapter.OnItemClickListener{
            override fun OnItemClick(data: NewsData, positon:Int) {
                data_real[positon].scraped = !data_real[positon].scraped
                adapter.notifyItemChanged(positon)
            }

            override fun OnTitleClick(data: NewsData, position: Int) {
                val fragment = requireActivity().supportFragmentManager.beginTransaction()
                val contentFragment = NewsContentFragment()
                fragment.addToBackStack(null)
                contentFragment.contentData = data
                fragment.replace(R.id.frameLayout, contentFragment)
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