package com.example.newsifyprac

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.newsifyprac.databinding.FragmentReporterRecyBinding

class ReporterRecyclerViewAdapter(private val values: ArrayList<String>)
    : RecyclerView.Adapter<ReporterRecyclerViewAdapter.ViewHolder>() {

    interface OnItemClickListener{
        fun OnItemClick(data: String,position: Int)
    }

    var itemClickListener:OnItemClickListener?=null

    inner class ViewHolder(var binding: FragmentReporterRecyBinding) : RecyclerView.ViewHolder(binding.root) {
        init{
            binding.content.setOnClickListener {
                itemClickListener?.OnItemClick(values[adapterPosition],adapterPosition)
            }
        }
        val contentView: TextView = binding.content

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentReporterRecyBinding.inflate(
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
        holder.binding.content.text = values[position]
    }

}