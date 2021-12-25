package com.samwise.imdbmoviesapp.ui.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.samwise.imdbmoviesapp.R
import com.samwise.imdbmoviesapp.databinding.GalleryFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "GalleryFragment"

@AndroidEntryPoint
class MoviesFragment : Fragment(R.layout.gallery_fragment) {

    private val viewModel: MoviesViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = GalleryFragmentBinding.bind(view)
        val adapter = MoviesParentAdapter()
        binding.apply {
            parentRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            parentRecyclerView.adapter = adapter
        }

        viewModel.moviesFlow.observe(viewLifecycleOwner){
              adapter.submitList( it)
        }
    }
}