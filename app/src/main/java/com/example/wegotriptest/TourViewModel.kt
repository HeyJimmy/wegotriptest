package com.example.wegotriptest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TourViewModel: ViewModel()  {
    private val _tours = MutableLiveData<List<Tour>>()
    val tours: LiveData<List<Tour>>
        get() = _tours

    val _tour = MutableLiveData<Tour>()
    val tour: LiveData<Tour>
        get() = _tour

    val _currentStep = MutableLiveData<TourStep>()
    val currentStep: LiveData<TourStep>
        get() = _currentStep

    fun initTours(tourList: List<Tour>) {
        _tours.value = tourList
        _tour.value = tourList[0]
        _currentStep.value = tourList[0].steps[0]
    }
}