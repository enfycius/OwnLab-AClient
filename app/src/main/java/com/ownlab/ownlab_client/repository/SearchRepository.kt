package com.ownlab.ownlab_client.repository

import com.ownlab.ownlab_client.data.LocalDataSource
import com.ownlab.ownlab_client.models.PostItem

class SearchRepository(private val localDataSource: LocalDataSource) {

    fun saveSearchQuery(query: String) {
        localDataSource.saveSearchQuery(query)
    }

    fun loadRecentSearches(): List<String> {
        return localDataSource.loadRecentSearches()
    }
}