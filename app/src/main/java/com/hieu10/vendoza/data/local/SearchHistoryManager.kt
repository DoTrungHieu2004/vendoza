package com.hieu10.vendoza.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("search_history")

class SearchHistoryManager(private val context: Context) {
    private val gson = Gson()
    private val historyKey = stringPreferencesKey("recent_queries")
    private val maxSize = 10

    val history: Flow<List<String>> = context.dataStore.data
        .map { preferences ->
            val json = preferences[historyKey] ?: "[]"
            val type = object : TypeToken<List<String>>() {}.type
            gson.fromJson(json, type)
        }

    suspend fun addQuery(query: String) {
        if (query.isBlank()) return
        context.dataStore.edit { preferences ->
            val json = preferences[historyKey] ?: "[]"
            val type = object : TypeToken<MutableList<String>>() {}.type
            val list = gson.fromJson<MutableList<String>>(json, type)
            list.remove(query)  // remove if exists to avoid duplicates
            list.add(0, query) // add to front
            if (list.size > maxSize) list.removeAt(list.lastIndex)
            preferences[historyKey] = gson.toJson(list)
        }
    }

    suspend fun clearHistory() {
        context.dataStore.edit { preferences ->
            preferences.remove(historyKey)
        }
    }
}