package com.example.newsifyprac

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.newsifyprac.databinding.FragmentNewsRecyBinding


class NewsRecyclerViewAdapter(private val values: ArrayList<NewsData>)
    : RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder>() {

    interface OnItemClickListener{
        fun OnItemClick(data: NewsData,position: Int)
        fun OnReporterClick(data: NewsData, position: Int)
    }

    var itemClickListener:OnItemClickListener?=null

    inner class ViewHolder(var binding: FragmentNewsRecyBinding) : RecyclerView.ViewHolder(binding.root) {
        init{
            binding.newsSave.setOnClickListener {
                itemClickListener?.OnItemClick(values[adapterPosition],adapterPosition)
            }
            binding.newsReporter.setOnClickListener {
                itemClickListener?.OnReporterClick(values[adapterPosition],adapterPosition)
            }
        }
        val contentView: TextView = binding.newsTittle

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentNewsRecyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = values.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //val item = values[position]
        //holder.contentView.text = item

        holder.binding.newsTittle.text = values[position].title
        holder.binding.newsCompany.text = values[position].broadcaster
        holder.binding.newsReporter.text = values[position].reporter
        holder.binding.newsTime.text = values[position].date
        if (values[position].scraped == true) {
            holder.binding.newsSave.setImageResource(R.drawable.after_save)
            holder.binding.newsMemo.visibility = View.INVISIBLE
        } else {
            holder.binding.newsSave.setImageResource(R.drawable.before_save)
            holder.binding.newsMemo.visibility = View.INVISIBLE
        }

        if(values[position].broadcasterSelect){
            holder.binding.newslayout.visibility = View.VISIBLE
        }else{
            holder.binding.newslayout.visibility = View.GONE
            val layoutParams = holder.binding.newslayout.layoutParams as RecyclerView.LayoutParams
            layoutParams.height = 0
            holder.binding.newslayout.layoutParams = layoutParams
        }

        if(values[position].categorySelect){
            holder.binding.newslayout.visibility = View.VISIBLE
        }else{
            holder.binding.newslayout.visibility = View.GONE
            val layoutParams = holder.binding.newslayout.layoutParams as RecyclerView.LayoutParams
            layoutParams.height = 0
            holder.binding.newslayout.layoutParams = layoutParams
        }

//        if(values[position].reporterSelect) {
//            holder.binding.newslayout.visibility = View.VISIBLE
//        }
//        else{
//            holder.binding.newslayout.visibility = View.GONE
//            val layoutParams = holder.binding.newslayout.layoutParams as RecyclerView.LayoutParams
//            layoutParams.height = 0
//            holder.binding.newslayout.layoutParams = layoutParams
//        }

    }

}