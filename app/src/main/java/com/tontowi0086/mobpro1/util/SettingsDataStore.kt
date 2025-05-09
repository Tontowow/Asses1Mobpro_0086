package com.tontowi0086.mobpro1.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings_preference"
)

class SettingsDataStore(private val context: Context) {

    companion object {
        private val IS_LIST = booleanPreferencesKey("is_list")
        private val THEME_COLOR_INDEX = intPreferencesKey("theme_color_index")
    }

    val layoutFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[IS_LIST] ?: true
        }

    suspend fun saveLayout(isList: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_LIST] = isList
        }
    }

    val themeColorFlow: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[THEME_COLOR_INDEX] ?: 2 // default biru (index 2)
        }

    suspend fun saveThemeColor(index: Int) {
        context.dataStore.edit { preferences ->
            preferences[THEME_COLOR_INDEX] = index
        }
    }

    private val colorINDEX = intPreferencesKey("color_index")

    val colorIndexFlow: Flow<Int> = context.dataStore.data
        .map { preferences -> preferences[colorINDEX] ?: 0 }

    suspend fun saveColorIndex(index: Int) {
        context.dataStore.edit { preferences -> preferences[colorINDEX] = index }
    }

}
