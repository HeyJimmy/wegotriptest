package com.example.wegotriptest.step

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wegotriptest.Tour
import com.example.wegotriptest.list.ListViewModel

class ListViewModelFactory(private val tour: Tour, private val stepIndex: Int) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            return ListViewModel(tour, stepIndex) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}