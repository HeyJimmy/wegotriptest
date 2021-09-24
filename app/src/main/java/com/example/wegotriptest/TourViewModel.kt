package com.example.wegotriptest

import androidx.lifecycle.*

class TourViewModel: ViewModel()  {

    private val _tour = MutableLiveData<Tour>()
    val tour: LiveData<Tour>
        get() = _tour

    private val _stepIndex = MutableLiveData<Int>()
    val stepIndex: LiveData<Int>
        get() = _stepIndex

    val currentStep: TourStep?
        get() = tour.value?.steps?.get(stepIndex.value ?: 0)

    val stepsCount: Int
        get() = tour.value?.steps?.size ?: 0

    // Working with mock objects
    // TODO: make a repository
    fun initTours(tourList: List<Tour>) {
        _tour.value = tourList[0]
        _stepIndex.value = 0
    }
}