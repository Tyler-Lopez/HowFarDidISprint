package com.company.howfardidisprint.model

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class RunViewModel(application: Application): AndroidViewModel(application) {
    var data: LiveData<List<Run>>
    private val repository: RunRepository

    init {
        val runDao = RunDatabase.getDatabase(application).runDao()
        repository = RunRepository(runDao)
        data = repository.readAllDataSortDate
    }

    fun filterDistance(runDistance: RunDistance, sortType: SortType) {
        data = when (sortType) {
            SortType.BY_DATE -> repository.getDataByDistanceSortDate(runDistance)
            SortType.BY_SPEED -> repository.getDataByDistanceSortSpeed(runDistance)
        }
    }

    fun insertRun(run: Run) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addRun(run)
        }
    }
}

class RunViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(RunViewModel::class.java)) {
            return RunViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}