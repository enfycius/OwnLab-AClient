package com.ownlab.ownlab_client.data

import android.content.SharedPreferences

class LocalDataSource(private val sharedPreferences: SharedPreferences) {

    fun saveSearchQuery(query: String) {
        val recentSearches = sharedPreferences.getStringSet("recent_searches", mutableSetOf()) ?: mutableSetOf()
        if (recentSearches.contains(query)) {
            recentSearches.remove(query)
        }
        recentSearches.add(query)
        if (recentSearches.size > 10) { // 최근 검색어 최대 10개 유지
            recentSearches.remove(recentSearches.first())
        }
        sharedPreferences.edit().putStringSet("recent_searches", recentSearches).apply()
    }

    fun loadRecentSearches(): List<String> {
        return sharedPreferences.getStringSet("recent_searches", mutableSetOf())?.toList() ?: listOf()
    }
}