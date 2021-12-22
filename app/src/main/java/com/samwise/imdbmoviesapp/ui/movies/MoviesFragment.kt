package com.samwise.imdbmoviesapp.ui.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import com.samwise.imdbmoviesapp.R
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "GalleryFragment"

@AndroidEntryPoint
class MoviesFragment : Fragment(R.layout.gallery_fragment) {

    private val viewModel: MoviesViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}