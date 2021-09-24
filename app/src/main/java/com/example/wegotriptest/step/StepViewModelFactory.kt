package com.example.wegotriptest.step

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wegotriptest.Tour

class StepViewModelFactory(private val tour: Tour, private val stepIndex: Int) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StepViewModel::class.java)) {
            return StepViewModel(tour, stepIndex) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}