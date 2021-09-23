package com.example.wegotriptest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.wegotriptest.databinding.FragmentTourBinding

class TourFragment: Fragment() {
    private val viewModel: TourViewModel by lazy {
        ViewModelProvider(this).get(TourViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentTourBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // Mock tour data
        viewModel.initTours(Tour.initTourList(resources))

        return binding.root
    }
}