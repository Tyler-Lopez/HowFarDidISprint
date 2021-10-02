package com.company.howfardidisprint

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class ScoreEntryViewModel(application: Application): AndroidViewModel(application) {
    private val readAllData: LiveData<List<ScoreEntry>>
    private val repository: ScoreEntryRepository

    init {
        val scoreEntryDao = ScoreEntryRoomDatabase.getDatabase(application).ScoreEntryDao()
        repository = ScoreEntryRepository(scoreEntryDao)
        readAllData = repository.readAllData
    }

    fun insertScore(score: ScoreEntry) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addScore(score)
        }
    }

    fun getData(): List<ScoreEntry> = readAllData.value ?: listOf<ScoreEntry>()
}

