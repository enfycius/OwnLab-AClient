package com.ownlab.ownlab_client.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ownlab.ownlab_client.R
import com.ownlab.ownlab_client.databinding.RecyclerviewPostItemBinding
import com.ownlab.ownlab_client.models.PostItem
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class BoardAdapter(private val postItems: List<PostItem>): RecyclerView.Adapter<BoardAdapter.BoardViewHolder>() {

    class BoardViewHolder(private val binding: RecyclerviewPostItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val recruitDate = binding.recruitDate
        val title = binding.title
        val registrationDate = binding.registrationDate
        val assignee = binding.assignee
        val contacts = binding.contacts
        val email = binding.email
        val registrationMethod = binding.registrationMethod
        val address = binding.address
        val detailedLink = binding.detailedLink
        val recruitmentDates = binding.recruitmentDates
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        val binding = RecyclerviewPostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return BoardViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return postItems.size
    }

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        try {
            val recruitDateString =
                postItems[position].start_date + " ~ " + postItems[position].end_date
            Log.d("RecruitDate", "Recruit date string at position $position: $recruitDateString")

            val sdf = SimpleDateFormat("yyyy.M.d", Locale.getDefault())
            val today = Calendar.getInstance().time

            val dates = recruitDateString.split(" ~ ")

            holder.recruitDate.text =
                postItems[position].start_date + " ~ " + postItems[position].end_date
            holder.title.text = postItems[position].title
            holder.registrationDate.text = postItems[position].registration_date
            holder.assignee.text = postItems[position].assignee
            holder.contacts.text = postItems[position].contacts
            holder.email.text = postItems[position].email
            holder.registrationMethod.text = postItems[position].registration_method
            holder.address.text = postItems[position].address
            holder.detailedLink.text = postItems[position].detailed_link

            if (dates.size == 2) {
                val startDate = sdf.parse(dates[0])
                val endDate = sdf.parse(dates[1])

                if (startDate != null && endDate != null && today in startDate..endDate) {
                    holder.recruitmentDates.text = "진행중"
                    val resources = holder.itemView.context.resources
                    val drawable = resources.getDrawable(R.drawable.circle)
                    holder.recruitmentDates.background = drawable
                } else {
                    holder.recruitmentDates.text = "마감"
                }
            } else {
                holder.recruitmentDates.text = "날짜 형식 오류"
            }
        } catch (e: Exception) {
            Log.e("Error", "Error in onBindViewHolder: ${e.message}")
            e.printStackTrace()
        }
    }
}