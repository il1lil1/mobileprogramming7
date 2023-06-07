package com.example.newsifyprac

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsifyprac.databinding.FragmentNewsRecyBinding

class SearchResultAdapter(private val items: ArrayList<NewsData>): RecyclerView.Adapter<SearchResultAdapter.MyViewHolder>(){


    interface OnItemClickListener{
        fun OnItemClick(data: NewsData,position: Int)
        fun OnReporterClick(data: NewsData, position: Int)
    }
    var itemClickListener:OnItemClickListener?=null

    inner class  MyViewHolder(val binding: FragmentNewsRecyBinding) : RecyclerView.ViewHolder(binding.root){
        init{

            binding.newsSave.setOnClickListener {
                itemClickListener?.OnItemClick(items[adapterPosition],adapterPosition)
            }
            binding.newsReporter.setOnClickListener {
                itemClickListener?.OnReporterClick(items[adapterPosition],adapterPosition)
            }

            /*binding.searchResultTitle.setOnClickListener {
                itemClickListener?.OnItemClick(items[adapterPosition].toString(), adapterPosition)
            }*/
        }
        val contentView: TextView = binding.newsTittle

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultAdapter.MyViewHolder {
        val view = FragmentNewsRecyBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(view)
    }



    override fun onBindViewHolder(holder: SearchResultAdapter.MyViewHolder, position: Int) {

                holder.binding.newsTittle.text = items[position].title
                holder.binding.newsCompany.text = items[position].broadcaster
                holder.binding.newsReporter.text = items[position].reporter

                holder.binding.newslayout.visibility = View.VISIBLE

        if (items[position].scraped == true) {
            holder.binding.newsSave.setImageResource(R.drawable.after_save)
        } else {
            holder.binding.newsSave.setImageResource(R.drawable.before_save)
        }

        if(items[position].broadcasterSelect){
            holder.binding.newslayout.visibility = View.VISIBLE
        }else{
            holder.binding.newslayout.visibility = View.GONE
            val layoutParams = holder.binding.newslayout.layoutParams as RecyclerView.LayoutParams
            layoutParams.height = 0
            holder.binding.newslayout.layoutParams = layoutParams
        }

        if(items[position].categorySelect){
            holder.binding.newslayout.visibility = View.VISIBLE
        }else{
            holder.binding.newslayout.visibility = View.GONE
            val layoutParams = holder.binding.newslayout.layoutParams as RecyclerView.LayoutParams
            layoutParams.height = 0
            holder.binding.newslayout.layoutParams = layoutParams
        }




        }




    override fun getItemCount(): Int {
        return items.size
    }

}