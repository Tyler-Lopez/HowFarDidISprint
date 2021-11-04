package com.company.howfardidisprint.model

import androidx.lifecycle.LiveData
import com.company.howfardidisprint.domain.repository.RunDao


class RunRepository(private val runDao: RunDao) {
    val readAllDataSortDate: LiveData<List<Run>> = runDao.getAllRunsByDate()

    /*fun getDataByDistanceSortSpeed(runDistance: Long): LiveData<List<Run>> {
        return runDao.getRunsOfDistanceBySpeed(runDistance = runDistance)
    }

    fun getDataByDistanceSortDate(runDistance: Long): LiveData<List<Run>> {
        return runDao.getRunsOfDistanceByDate(runDistance = runDistance)
    }*/

    suspend fun addRun(run: Run) {
        runDao.insert(run)
    }
    //  suspend fun deleteAllRecords() {
    //     scoreEntryDao.deleteAll()
    // }
}