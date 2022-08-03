package com.samwise.imdbmoviesapp.ui.details

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.samwise.imdbmoviesapp.data.ActorShort
import com.samwise.imdbmoviesapp.databinding.ActorItemBinding

private const val TAG = "ActorsAdapter"

class ActorsAdapter : ListAdapter<ActorShort, ActorsAdapter.ActorsViewHolder>(DiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorsViewHolder {
        val binding = ActorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActorsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActorsViewHolder, position: Int) {
        val currentItem = getItem(position)
        if(currentItem != null)
          holder.bind(currentItem)
    }

    class ActorsViewHolder(private val binding: ActorItemBinding) : RecyclerView.ViewHolder(binding.root) {
          fun bind(actor: ActorShort){
              binding.apply {
                  Log.d(TAG, "it's working")
                  Log.d(TAG, actor.name)
                  nameTxtView.text = actor.name
                  characterTextView.text = actor.asCharacter
                  Glide.with(itemView)
                      .load(actor.image)
                      .centerCrop()
                      .circleCrop()
                      .transition(DrawableTransitionOptions.withCrossFade())
                      .into(poster)
              }
          }
    }

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<ActorShort>() {
        override fun areItemsTheSame(oldItem: ActorShort, newItem: ActorShort): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ActorShort, newItem: ActorShort): Boolean {
            return oldItem == newItem
        }

    }



}