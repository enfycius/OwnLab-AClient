package com.ownlab.ownlab_client.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ownlab.ownlab_client.R
import com.ownlab.ownlab_client.databinding.RecyclerviewSurveyItemBinding
import com.ownlab.ownlab_client.models.SurveyItem
import com.ownlab.ownlab_client.models.SurveyResult

class MainAdapter(private val surveyItems: List<SurveyItem>) :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    private var data = mutableListOf<SurveyResult>()

    class MainViewHolder(private val binding: RecyclerviewSurveyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val nameText = binding.nameText
        val check = binding.check
        val checkBoxLayout = binding.checkBoxLayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = RecyclerviewSurveyItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return MainViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return surveyItems.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) = try {
        holder.nameText.text = surveyItems[position].question

        val currentItem = surveyItems[position]
        holder.nameText.text = currentItem.question

        holder.checkBoxLayout.setOnClickListener {
            holder.check.isChecked = !holder.check.isChecked
        }
        holder.check.setOnCheckedChangeListener { _, isChecked ->
            data.add(SurveyResult(isChecked, surveyItems[position].id))
            if (isChecked) {
                holder.checkBoxLayout.setBackgroundResource(R.drawable.blue_rounded_box)
                holder.nameText.setTextColor(android.graphics.Color.BLUE)
            } else {
                holder.checkBoxLayout.setBackgroundResource(R.drawable.empty_rounded_box)
                holder.nameText.setTextColor(android.graphics.Color.GRAY)
            }
        }

    } catch (e: Exception) {
        Log.e("Error", "Error in onBindViewHolder: ${e.message}")
        e.printStackTrace()
    }

    fun getSurveyResults(): List<SurveyResult> {
        return data
            .reversed()
            .distinctBy {
                it.id
            }
    }
}