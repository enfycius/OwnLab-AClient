package com.ownlab.ownlab_client.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ownlab.ownlab_client.repository.SearchRepository

class SearchViewModel(private val repository: SearchRepository) : ViewModel() {

    private val _recentSearches = MutableLiveData<List<String>>()
    val recentSearches: LiveData<List<String>> get() = _recentSearches

    fun saveSearchQuery(query: String) {
        repository.saveSearchQuery(query)
        loadRecentSearches() // 저장 후 업데이트
    }

    fun loadRecentSearches() {
        _recentSearches.value = repository.loadRecentSearches()
    }
}