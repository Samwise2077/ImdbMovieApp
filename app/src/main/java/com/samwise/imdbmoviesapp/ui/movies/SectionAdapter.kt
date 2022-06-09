package com.samwise.imdbmoviesapp.ui.movies

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.samwise.imdbmoviesapp.data.Movie
import com.samwise.imdbmoviesapp.databinding.GalleryFragmentItemTitleBinding



class SectionAdapter() : ListAdapter<String, SectionAdapter.SectionViewHolder>(DiffUtil()){


    class SectionViewHolder(private val binding: GalleryFragmentItemTitleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(title : String){
            binding.apply {
                type.text = title
                Log.d("TAG", "title = $title")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        val binding = GalleryFragmentItemTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SectionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
         val currentItem = getItem(position)
        if(currentItem != null){
            holder.bind(currentItem)
        }
    }

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem


        override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem

    }

}