package com.example.wegotriptest.tour

import androidx.lifecycle.*
import com.example.wegotriptest.Tour
import com.example.wegotriptest.TourStep

class TourViewModel(tour: Tour, step: Int = 0): ViewModel()  {

    private val _stepIndex = MutableLiveData<Int>()
    val stepIndex: LiveData<Int>
        get() = _stepIndex

    private val _navigateToStepFragment = MutableLiveData<Int>()
    val navigateToStepFragment: LiveData<Int>
        get() = _navigateToStepFragment

    init {
        _stepIndex.value = step
    }

    val currentStep: TourStep = tour.steps[stepIndex.value ?: 0]
    var stepsCount: Int = tour.steps.size

    fun displayStepTour() {
        _navigateToStepFragment.value = stepIndex.value
    }

    fun displayStepTourComplete() {
        _navigateToStepFragment.value = null
    }
}