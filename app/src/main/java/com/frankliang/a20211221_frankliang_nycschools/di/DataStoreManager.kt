package com.frankliang.a20211221_frankliang_nycschools.di

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

class DataStoreManager(private val dataStore: DataStore<Preferences>) {
    companion object {
        val PREF_KEY_HAS_DATA = booleanPreferencesKey("has_data")
    }
    suspend fun setHasData(hasData: Boolean) {
        Log.e("Test","has data: $hasData")
        dataStore.edit {preference ->
            preference[PREF_KEY_HAS_DATA] = hasData
        }
    }

    suspend fun getHasData() = dataStore.data.map {preference ->
        Log.e("test", "gethas data: ${preference[PREF_KEY_HAS_DATA]}")
        preference[PREF_KEY_HAS_DATA] ?: false
    }

}

