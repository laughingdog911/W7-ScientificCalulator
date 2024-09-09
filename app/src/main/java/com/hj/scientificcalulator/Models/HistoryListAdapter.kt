package com.hj.scientificcalulator.Models

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hj.scientificcalulator.Helper.HistoryHelper
import com.hj.scientificcalulator.databinding.FragmentHistoryBinding
import com.hj.scientificcalulator.databinding.PastCalcBinding

class HistoryListAdapter() :
    RecyclerView.Adapter<HistoryListAdapter.ViewHolder>() {

    inner class ViewHolder(
        private val binding: PastCalcBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(historyDataModel: HistoryDataModel) {
            binding.historyXprsn.text = historyDataModel.expression
            binding.historyRslt.text = historyDataModel.result
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PastCalcBinding.inflate(LayoutInflater.from(parent.context), parent, false )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryListAdapter.ViewHolder, position: Int) {
        holder.bind(HistoryHelper.historyList.value!![position])
    }
    override fun getItemCount(): Int = HistoryHelper.historyList.value?.size?: 0
}