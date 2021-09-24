package com.example.wegotriptest.tour

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wegotriptest.Tour

class TourViewModelFactory(private val tour: Tour) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TourViewModel::class.java)) {
            return TourViewModel(tour) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}