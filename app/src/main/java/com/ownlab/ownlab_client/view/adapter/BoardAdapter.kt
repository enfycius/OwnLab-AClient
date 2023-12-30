package com.ownlab.ownlab_client.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ownlab.ownlab_client.databinding.RecyclerviewPostItemBinding
import com.ownlab.ownlab_client.models.PostItem

class BoardAdapter(private val postItems: List<PostItem>): RecyclerView.Adapter<BoardAdapter.BoardViewHolder>() {

    class BoardViewHolder(private val binding: RecyclerviewPostItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.title
        val content = binding.content
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        val binding = RecyclerviewPostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return BoardViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return postItems.size
    }

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        holder.title.text = postItems[position].title
        holder.content.text = postItems[position].content
    }
}