package com.company.howfardidisprint

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class ScoreEntryViewModel(application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<ScoreEntry>>
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
}

class ScoreEntryViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(ScoreEntryViewModel::class.java)) {
            return ScoreEntryViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}