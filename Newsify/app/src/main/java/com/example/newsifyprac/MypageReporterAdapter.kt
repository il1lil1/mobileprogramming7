package com.example.newsifyprac

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsifyprac.databinding.ScrapReporterBinding


class MypageReporterAdapter (private val values: ArrayList<NewsData>)
    : RecyclerView.Adapter<MypageReporterAdapter.ViewHolder>() {

    interface OnItemClickListener{
        fun OnItemClick(data: NewsData,position: Int)
        fun OnReporterClick(data: NewsData, position: Int)
    }

    var itemClickListener:OnItemClickListener?=null

    inner class ViewHolder(var binding: ScrapReporterBinding) : RecyclerView.ViewHolder(binding.root)  {
        init{
            binding.reporterFavorite.setOnClickListener {
                itemClickListener?.OnItemClick(values[adapterPosition],adapterPosition)
            }
            binding.reporterName.setOnClickListener {
                itemClickListener?.OnReporterClick(values[adapterPosition],adapterPosition)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ScrapReporterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = values.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.reporterName.text = values[position].reporter
        holder.binding.reporterFrom.text = values[position].broadcaster
        if (values[position].favorited == true) {
            holder.binding.reporterFavorite.setImageResource(R.drawable.favorite_after)
        } else {
            holder.binding.reporterFavorite.setImageResource(R.drawable.favorite_before)
        }
    }
}
