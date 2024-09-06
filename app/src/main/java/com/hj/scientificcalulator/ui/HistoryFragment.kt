package com.hj.scientificcalulator.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.hj.scientificcalulator.Helper.HistoryHelper
import com.hj.scientificcalulator.Models.HistoryListAdapter
import com.hj.scientificcalulator.R
import com.hj.scientificcalulator.databinding.FragmentHistoryBinding


class HistoryFragment() : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var adapter: HistoryListAdapter

    private val historyHelper = HistoryHelper()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)
        binding.lifecycleOwner = this
        binding.view = this

        inHistoryRecyclerView()

        return binding.root
    }

    private fun inHistoryRecyclerView(){
        adapter = HistoryListAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context as MainActivity)

    }

    fun onClick(v: View){

        when (v) {
            binding.btnBin -> {
                historyHelper.historyList.value?.let{
                    it.clear()
                }
            }

            binding.btnBack -> {
                (activity as MainActivity).supportFragmentManager.popBackStack()
            }
        }
    }
}