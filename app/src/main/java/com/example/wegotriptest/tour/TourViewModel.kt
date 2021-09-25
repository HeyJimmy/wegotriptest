package com.example.wegotriptest.tour

import androidx.lifecycle.*
import com.example.wegotriptest.Tour
import com.example.wegotriptest.TourStep

class TourViewModel(val tour: Tour, step: Int = 0): ViewModel()  {

    private val _stepIndex = MutableLiveData<Int>()
    val stepIndex: LiveData<Int>
        get() = _stepIndex

    private val _navigateToFragment = MutableLiveData<String>()
    val navigateToFragment: LiveData<String>
        get() = _navigateToFragment
    init {
        _stepIndex.value = step
    }

    val currentStep: TourStep = tour.steps[stepIndex.value ?: 0]
    var stepsCount: Int = tour.steps.size

    fun navigateTo(fragmentName: String) {
        _navigateToFragment.value = fragmentName
    }

    fun navigateComplete() {
        _navigateToFragment.value = null
    }
}