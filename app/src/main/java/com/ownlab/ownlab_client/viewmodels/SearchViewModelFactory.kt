package com.ownlab.ownlab_client.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ownlab.ownlab_client.data.LocalDataSource
import com.ownlab.ownlab_client.repository.SearchRepository

class SearchViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            val sharedPreferences = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
            val localDataSource = LocalDataSource(sharedPreferences)
            val repository = SearchRepository(localDataSource) // LocalDataSource를 repository에 제공
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}