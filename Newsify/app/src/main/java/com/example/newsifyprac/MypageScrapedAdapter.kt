package com.example.newsifyprac

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.newsifyprac.databinding.FragmentNewsRecyBinding

class MypageScrapedAdapter(private val values: ArrayList<NewsData>)
    : RecyclerView.Adapter<MypageScrapedAdapter.ViewHolder>() {

    interface OnItemClickListener{
        fun OnItemClick(data: NewsData,position: Int)
        fun OnReporterClick(data: NewsData, position: Int)
        fun OnScrapClick(data: NewsData, position: Int) // 추가

    }

    var itemClickListener:OnItemClickListener?=null

    inner class ViewHolder(var binding: FragmentNewsRecyBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener  {
        init{
            binding.newsSave.setOnClickListener {
                itemClickListener?.OnItemClick(values[adapterPosition],adapterPosition)
                itemClickListener?.OnScrapClick(values[adapterPosition], adapterPosition) // 추가

            }
            binding.newsReporter.setOnClickListener {
                itemClickListener?.OnReporterClick(values[adapterPosition],adapterPosition)
            }
            binding.newsMemo.setOnClickListener(this)
        }
        val contentView: TextView = binding.newsTittle

        override fun onClick(v: View?) {
            v?.context?.let { showMemoDialog(it, adapterPosition) }
        }

        private fun showMemoDialog(context: Context, position: Int) {
            val dialogView = LayoutInflater.from(context).inflate(R.layout.scrap_memo, null)
            val editText = dialogView.findViewById<EditText>(R.id.dialogMemo)
            editText.setText(values[position].memo)

            AlertDialog.Builder(context)
                .setView(dialogView)
                .setPositiveButton("저장") { _, _ ->
                    values[position].memo = editText.text.toString()
                }
                .setNegativeButton("취소", null)
                .show()
        }
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

        holder.binding.newsTittle.text = values[position].title
        holder.binding.newsCompany.text = values[position].broadcaster
        holder.binding.newsReporter.text = values[position].reporter
        if (values[position].scraped == true) {
            holder.binding.newsSave.setImageResource(R.drawable.after_save)
        } else {
            holder.binding.newsSave.setImageResource(R.drawable.before_save)
            values[position].memo = ""
        }


    }
}
