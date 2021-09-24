package com.example.wegotriptest

import androidx.lifecycle.*

class TourViewModel(private val tour: Tour): ViewModel()  {

    private val _stepIndex = MutableLiveData<Int>()
    val stepIndex: LiveData<Int>
        get() = _stepIndex

    init {
        _stepIndex.value = 0
    }

    val currentStep: TourStep
        get() = tour.steps[stepIndex.value ?: 0]

    var stepsCount: Int = tour.steps.size

}