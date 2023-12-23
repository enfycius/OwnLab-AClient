package com.ownlab.ownlab_client.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ownlab.ownlab_client.databinding.RecyclerviewSurveyItemBinding
import com.ownlab.ownlab_client.models.SurveyItem
import com.ownlab.ownlab_client.models.SurveyItemResponse
import com.ownlab.ownlab_client.models.SurveyResult

class MainAdapter(private val surveyItems: List<SurveyItem>): RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    private var data = mutableListOf<SurveyResult>()

    class MainViewHolder(private val binding: RecyclerviewSurveyItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val nameText = binding.nameText
        val check = binding.check
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = RecyclerviewSurveyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MainViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return surveyItems.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.nameText.text = surveyItems[position].question
        holder.check.setOnCheckedChangeListener { _, _isChecked ->
            data.add(SurveyResult(_isChecked, surveyItems[position].id))
        }
    }

    fun getSurveyResults(): List<SurveyResult> {
        return data
            .reversed()
            .distinctBy {
                it.id
            }
    }
}