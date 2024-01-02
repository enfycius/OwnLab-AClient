package com.ownlab.ownlab_client.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ownlab.ownlab_client.databinding.RecyclerviewPostItemBinding
import com.ownlab.ownlab_client.models.PostItem

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
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        val binding = RecyclerviewPostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return BoardViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return postItems.size
    }

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        holder.recruitDate.text = postItems[position].start_date + " ~ " + postItems[position].end_date
        holder.title.text = postItems[position].title
        holder.registrationDate.text = postItems[position].registration_date
        holder.assignee.text = postItems[position].assignee
        holder.contacts.text = postItems[position].contacts
        holder.email.text = postItems[position].email
        holder.registrationMethod.text = postItems[position].registration_method
        holder.address.text = postItems[position].address
        holder.detailedLink.text = postItems[position].detailed_link
    }
}