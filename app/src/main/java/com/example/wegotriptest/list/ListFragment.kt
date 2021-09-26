package com.example.wegotriptest.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.wegotriptest.R
import com.example.wegotriptest.databinding.FragmentListBinding
import com.example.wegotriptest.tour.TourViewModel
import com.example.wegotriptest.tour.TourViewModelFactory

class ListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentListBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val tour = ListFragmentArgs.fromBundle(requireArguments()).tour
        val stepIndex = ListFragmentArgs.fromBundle(requireArguments()).stepIndex

        val viewModelFactory = TourViewModelFactory(tour, stepIndex)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(TourViewModel::class.java)
        binding.viewModel = viewModel

        (activity as AppCompatActivity).let {
            it.setSupportActionBar(binding.appBar)
            it.supportActionBar?.apply {
                setCustomView(R.layout.actionbar_list)
                setDisplayShowTitleEnabled(false)
                setDisplayShowCustomEnabled(true)
            }
        }

        val stepListAdapter =
            StepListAdapter(viewModel.tour.steps, StepListAdapter.OnClickListener {
                viewModel.changeStep(it)
                viewModel.navigateTo("tour")
            })

        binding.stepList.adapter = stepListAdapter

        // Click listeners
        binding.appBar.setNavigationOnClickListener {
            viewModel.navigateTo("step")
        }

        val tourButton = binding.appBar.findViewById<ImageButton>(R.id.return_to_tour_button)
        tourButton.setOnClickListener {
            viewModel.navigateTo("tour")
        }

        // Observers
        viewModel.stepIndex.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                val tourTitle = binding.appBar.findViewById<TextView>(R.id.tour_title)
                tourTitle.text = viewModel.tour.title

                stepListAdapter.renewItems(viewModel.tour.steps)
            }
        })

        viewModel.navigateToFragment.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                this.findNavController().navigate(
                    when (it) {
                        "tour" -> ListFragmentDirections.actionReturnToTour(
                            tour,
                            viewModel.stepIndex.value!!
                        )
                        "step" -> ListFragmentDirections.actionReturnToStep(
                            tour,
                            viewModel.stepIndex.value!!
                        )
                        else -> ListFragmentDirections.actionReturnToTour(
                            tour,
                            viewModel.stepIndex.value!!
                        )
                    }
                )
                viewModel.navigateComplete()
            }
        })

        return binding.root
    }
}