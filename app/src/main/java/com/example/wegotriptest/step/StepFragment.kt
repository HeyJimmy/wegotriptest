package com.example.wegotriptest.step

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.wegotriptest.R
import com.example.wegotriptest.databinding.FragmentStepBinding
import com.example.wegotriptest.tour.TourViewModel
import com.example.wegotriptest.tour.TourViewModelFactory

class StepFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        Log.i("CREATION", "Create step fragment")
        val binding = FragmentStepBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val tour = StepFragmentArgs.fromBundle(requireArguments()).tour
        val stepIndex = StepFragmentArgs.fromBundle(requireArguments()).stepIndex

        val viewModelFactory = TourViewModelFactory(tour, stepIndex)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(TourViewModel::class.java)
        binding.viewModel = viewModel

        (activity as AppCompatActivity).let {
            it.setSupportActionBar(binding.appBar)
            it.supportActionBar?.apply {
                setCustomView(R.layout.actionbar_step)
                setDisplayShowTitleEnabled(false)
                setDisplayShowCustomEnabled(true)
            }
        }

        binding.appBar.setNavigationOnClickListener {
            viewModel.navigateTo("tour")
        }

        val listButton = binding.appBar.findViewById<ImageButton>(R.id.list_button)
        listButton.setOnClickListener {
            viewModel.navigateTo("list")
        }

        viewModel.navigateToFragment.observe(viewLifecycleOwner, Observer {
            if ( null != it ) {
                this.findNavController().navigate(
                    when(it) {
                        "tour" -> StepFragmentDirections.actionReturnToTour(tour, stepIndex)
                        "list" -> StepFragmentDirections.actionShowList(tour, stepIndex)
                        else -> StepFragmentDirections.actionReturnToTour(tour, stepIndex)
                    }
                )
                viewModel.navigateComplete()
            }
        })

        return binding.root
    }

}