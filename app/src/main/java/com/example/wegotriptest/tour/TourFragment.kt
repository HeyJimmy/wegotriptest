package com.example.wegotriptest.tour

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.wegotriptest.R
import com.example.wegotriptest.Tour
import com.example.wegotriptest.databinding.FragmentTourBinding

class TourFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application: Application = requireNotNull(activity).application
        val binding = FragmentTourBinding.inflate(inflater)
        binding.lifecycleOwner = this

        var tour: Tour
        var stepIndex: Int

        try {
            tour = TourFragmentArgs.fromBundle(requireArguments()).tour
            stepIndex = TourFragmentArgs.fromBundle(requireArguments()).stepIndex
        } catch (e: IllegalArgumentException) { // TODO: Make it beautiful
            tour = Tour.initTourList(resources).first()
            stepIndex = 0
        }

        val viewModelFactory = TourViewModelFactory(tour, stepIndex)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(TourViewModel::class.java)
        binding.viewModel = viewModel

        // Image slider
        val stepImageAdapter = StepImageAdapter()
        binding.imageSlider.setSliderAdapter(stepImageAdapter)

        // Click listeners
        binding.audioPlayer.setOnClickListener {
            viewModel.navigateTo("step")
        }

        // Observers
        viewModel.stepIndex.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                binding.stepLabel.text = application.applicationContext.getString(
                    R.string.step_label, it + 1, viewModel.stepsCount
                )

                binding.stepTitle.text = viewModel.currentStep.title
                binding.playerStepTitle.text = viewModel.currentStep.title

                stepImageAdapter.renewItems(
                    viewModel.currentStep.imgUrls
                )
            }
        })

        viewModel.navigateToFragment.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                this.findNavController().navigate(
                    TourFragmentDirections.actionShowStep(tour, viewModel.stepIndex.value!!)
                )
                viewModel.navigateComplete()
            }
        })

        return binding.root
    }
}