package com.samwise.imdbmoviesapp.ui.details

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.samwise.imdbmoviesapp.data.ReviewDetail
import com.samwise.imdbmoviesapp.databinding.ReviewItemBinding

private const val TAG = "ReviewsAdapter"

class ReviewsAdapter(val listener: OnItemClickListener): ListAdapter<ReviewDetail, ReviewsAdapter.ReviewsViewHolder>(DiffUtil())

{


    override fun onBindViewHolder(holder: ReviewsViewHolder, position: Int) {
        val currentItem = getItem(position)
        if(currentItem != null){
            holder.bind(currentItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsViewHolder {
        val binding = ReviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewsViewHolder(binding)
    }

    inner class ReviewsViewHolder(private val binding: ReviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener{
                val position = bindingAdapterPosition
                if(position != RecyclerView.NO_POSITION){
                    val item = getItem(position)
                    if(item != null){
                        listener.onReviewClick(item)
                    }
                }
            }
        }

        fun bind(review: ReviewDetail) {
            binding.apply {
                contentTxtView.ellipsize
                contentTxtView.text = buildSpannedString {
                    bold {
                        append(review.title)
                    }
                    append("\n\n")
                    append(review.content)
                }
                Log.d(TAG, "bottom = ${contentTxtView.bottom}")
                nameTxtView.text = review.username
                Log.d(TAG, "rate = " + review.rate)
                Log.d(TAG, "userUrl = " + review.userUrl)
                Log.d(TAG, "reviewLink = " + review.reviewLink)
                dateTxtView.text = review.date
                if(review.rate != ""){
                    rateTextView.text = review.rate + "/10"
                }
                else{
                    rateTextView.visibility = View.GONE
                    imageView2.visibility = View.GONE
                }
            }
        }


    }

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<ReviewDetail>() {
        override fun areItemsTheSame(oldItem: ReviewDetail, newItem: ReviewDetail): Boolean {

            return oldItem.userUrl == newItem.userUrl
        }

        override fun areContentsTheSame(oldItem: ReviewDetail, newItem: ReviewDetail): Boolean {
            return oldItem == newItem
        }

    }

    interface OnItemClickListener{
        fun onReviewClick(review: ReviewDetail)
    }




}