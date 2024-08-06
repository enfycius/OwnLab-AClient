package com.ownlab.ownlab_client.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ownlab.ownlab_client.models.PostItem
import com.ownlab.ownlab_client.repository.SearchRepository
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: SearchRepository) : ViewModel() {

    private val _recentSearches = MutableLiveData<List<String>>(emptyList())
    val recentSearches: LiveData<List<String>> get() = _recentSearches

    fun saveSearchQuery(query: String) {
        repository.saveSearchQuery(query)
        loadRecentSearches() // 저장 후 업데이트
    }

    fun loadRecentSearches() {
        _recentSearches.value = repository.loadRecentSearches()
    }
}