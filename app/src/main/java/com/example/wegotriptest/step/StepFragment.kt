package com.example.wegotriptest.step

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.wegotriptest.databinding.FragmentStepBinding

class StepFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val application = requireNotNull(activity).application
        val binding = FragmentStepBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val tour = StepFragmentArgs.fromBundle(requireArguments()).tour
        val stepIndex = StepFragmentArgs.fromBundle(requireArguments()).stepIndex

        val viewModelFactory = StepViewModelFactory(tour, stepIndex)
        binding.viewModel = ViewModelProvider(
            this, viewModelFactory).get(StepViewModel::class.java)
        return binding.root
    }
}