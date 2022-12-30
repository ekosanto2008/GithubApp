package com.takeshi.gouda.setting

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>){

    private val FLOAT_TOGGLE = booleanPreferencesKey("save_setting")

    fun getFloatBlack(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[FLOAT_TOGGLE] ?: false
        }
    }

    suspend fun saveFloatBlack(isFavoriteModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[FLOAT_TOGGLE] = isFavoriteModeActive
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}