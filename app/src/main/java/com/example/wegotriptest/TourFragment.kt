package com.example.wegotriptest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.wegotriptest.databinding.FragmentTourBinding

class TourFragment: Fragment() {
    private val viewModel: TourViewModel by lazy {
        ViewModelProvider(this).get(TourViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val application = requireNotNull(activity).application
        val binding = FragmentTourBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // Image slider
        val stepImageAdapter = StepImageAdapter()
        binding.imageSlider.setSliderAdapter(stepImageAdapter)

        // Mock tour data
        viewModel.initTours(Tour.initTourList(resources))

        // Update step data
        viewModel.stepIndex.observe(viewLifecycleOwner, Observer { stepIndex ->
            if ( null != stepIndex ) {
                binding.stepLabel.text = application.applicationContext.getString(
                    R.string.step_label,stepIndex + 1, viewModel.stepsCount
                )

                binding.tourTitle.text = viewModel.currentStep!!.title

                stepImageAdapter.renewItems(
                    viewModel.currentStep!!.imgUrls
                )
            }
        })

        return binding.root
    }
}