package com.example.wegotriptest.tour

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wegotriptest.Tour

class TourViewModelFactory(private val tour: Tour, private val stepIndex: Int) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TourViewModel::class.java)) {
            return TourViewModel(tour, stepIndex) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}