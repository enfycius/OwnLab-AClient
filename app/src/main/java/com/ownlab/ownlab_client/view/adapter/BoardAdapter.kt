package com.ownlab.ownlab_client.view.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ownlab.ownlab_client.R
import com.ownlab.ownlab_client.databinding.RecyclerviewPostItemBinding
import com.ownlab.ownlab_client.models.PostItem
import com.ownlab.ownlab_client.view.BoardFragmentDirections
import com.ownlab.ownlab_client.view.interfaces.OnItemClick
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class BoardAdapter(private val context: Context?, val postItems: List<PostItem>, listener : OnItemClick): RecyclerView.Adapter<BoardAdapter.BoardViewHolder>() {
    private val mCallback = listener
    class BoardViewHolder(private val binding: RecyclerviewPostItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val recruitDate = binding.recruitDate
        val title = binding.title
        val registrationDate = binding.registrationDate
        val assignee = binding.assignee
//        val contacts = binding.contacts
//        val email = binding.email
//        val registrationMethod = binding.registrationMethod
//        val address = binding.address
//        val detailedLink = binding.detailedLink
//        val recruitmentDates = binding.recruitmentDates
        val applyBtn = binding.applyBtn
        val borderOfApplyBtn = binding.borderOfApplyBtn
        val bookMark = binding.bookMark
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
//            holder.contacts.text = postItems[position].contacts
//            holder.email.text = postItems[position].email
//            holder.registrationMethod.text = postItems[position].registration_method
//            holder.address.text = postItems[position].address
//            holder.detailedLink.text = postItems[position].detailed_link
            holder.applyBtn.text = "지원자 " + postItems[position].count.toString() + " / " + postItems[position].limitation.toString()


            holder.bookMark.setImageResource(
                if (postItems[position].isBookmarked)
                    R.drawable.icon_bookmark_filled__1_
                else
                    R.drawable.icon_bookmark_filled
            )

            holder.bookMark.setOnClickListener {
                postItems[position].isBookmarked = !postItems[position].isBookmarked
                holder.bookMark.setImageResource(
                    if (postItems[position].isBookmarked)
                        R.drawable.icon_bookmark_filled__1_
                    else
                        R.drawable.icon_bookmark_filled
                )
            }

            holder.itemView.setOnClickListener {
                val action = BoardFragmentDirections.actionBoardFragmentToDetailFragment(postItems[position])
                it.findNavController().navigate(action)
            }

            holder.applyBtn.setOnClickListener {
//                Log.d("Test", holder.email.text.toString())
                Log.d("Test", postItems[position].id.toString())

//                mCallback.applyInfo(postItems[position].id, holder.email.text.toString())
            }

            Log.d("Test", dates.toString())
            Log.d("Test", dates.size.toString())

            if (postItems[position].start_date != "" && postItems[position].end_date != "") {
                val startDate = sdf.parse(dates[0])
                val endDate = sdf.parse(dates[1])

                if (startDate != null && endDate != null && today in startDate..endDate && postItems[position].count < postItems[position].limitation) {
//                    holder.recruitmentDates.text = "진행중"
                    val resources = holder.itemView.context.resources
                    val drawable = resources.getDrawable(R.drawable.circle)
//                    holder.recruitmentDates.background = drawable
//                    holder.applyBtn.isEnabled = true
                   holder.borderOfApplyBtn.setBackgroundDrawable(context!!.getDrawable(R.drawable.blue_box))
                } else {
//                    holder.recruitmentDates.text = "마감"
//                    holder.applyBtn.isEnabled = false
                 holder.borderOfApplyBtn.setBackgroundDrawable(context!!.getDrawable(R.drawable.box))
                }
            } else {
//                holder.recruitmentDates.text = "날짜 형식 오류"
//                holder.applyBtn.isEnabled = false
              holder.borderOfApplyBtn.setBackgroundDrawable(context!!.getDrawable(R.drawable.box))
            }
        } catch (e: Exception) {
            Log.e("Error", "Error in onBindViewHolder: ${e.message}")
            e.printStackTrace()
        }
    }
}