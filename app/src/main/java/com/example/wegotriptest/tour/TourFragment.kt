package com.example.wegotriptest.tour

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.wegotriptest.R
import com.example.wegotriptest.StepImageAdapter
import com.example.wegotriptest.Tour
import com.example.wegotriptest.databinding.FragmentTourBinding

class TourFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val application: Application = requireNotNull(activity).application
        val binding = FragmentTourBinding.inflate(inflater)

        binding.lifecycleOwner = this

        // For now just take the first tour
        val tour = Tour.initTourList(resources).first()

        val viewModelFactory = TourViewModelFactory(tour)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(TourViewModel::class.java)
        binding.viewModel = viewModel

        // Image slider
        val stepImageAdapter = StepImageAdapter()
        binding.imageSlider.setSliderAdapter(stepImageAdapter)

        // Update step data
        viewModel.stepIndex.observe(viewLifecycleOwner, Observer { stepIndex ->
            if ( null != stepIndex ) {
                binding.stepLabel.text = application.applicationContext.getString(
                    R.string.step_label,stepIndex + 1, viewModel.stepsCount
                )

                binding.stepTitle.text = viewModel.currentStep.title
                binding.playerStepTitle.text = viewModel.currentStep.title

                stepImageAdapter.renewItems(
                    viewModel.currentStep.imgUrls
                )
            }
        })

        // Click listeners
        binding.audioPlayer.setOnClickListener {
            viewModel.displayStepTourDetails()
        }

        viewModel.navigateToStepFragment.observe(viewLifecycleOwner, Observer {
            if ( null != it ) {
                this.findNavController().navigate(
                    TourFragmentDirections.actionShowStep(tour, it)
                )
                viewModel.displayStepTourDetailsComplete()
            }
        })

        return binding.root
    }
}