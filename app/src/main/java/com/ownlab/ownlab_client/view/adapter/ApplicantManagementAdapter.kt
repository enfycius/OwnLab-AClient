package com.ownlab.ownlab_client.view.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ownlab.ownlab_client.R
import com.ownlab.ownlab_client.databinding.RecyclerviewApplicantInfoBinding
import com.ownlab.ownlab_client.databinding.RecyclerviewPostItemBinding
import com.ownlab.ownlab_client.models.ApplicantInfo
import com.ownlab.ownlab_client.models.PostItem
import com.ownlab.ownlab_client.view.interfaces.OnItemClick
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ApplicantManagementAdapter(private val context: Context?, val applicantInfo: List<ApplicantInfo>): RecyclerView.Adapter<ApplicantManagementAdapter.ApplicantManagementViewHolder>() {
    class ApplicantManagementViewHolder(private val binding: RecyclerviewApplicantInfoBinding) : RecyclerView.ViewHolder(binding.root) {
        val applicant = binding.applicant
        val tel = binding.tel
        val email = binding.email
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplicantManagementViewHolder {
        val binding = RecyclerviewApplicantInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ApplicantManagementViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return applicantInfo.size
    }

    override fun onBindViewHolder(holder: ApplicantManagementViewHolder, position: Int) {
        try {
            holder.applicant.text = applicantInfo[position].applicant
            holder.email.text = applicantInfo[position].email
            holder.tel.text = applicantInfo[position].tel
        } catch (e: Exception) {
            Log.e("Error", "Error in onBindViewHolder: ${e.message}")
            e.printStackTrace()
        }
    }
}