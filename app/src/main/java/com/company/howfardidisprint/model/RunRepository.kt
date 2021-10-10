package com.company.howfardidisprint.model

import androidx.lifecycle.LiveData


class RunRepository(private val runDao: RunDao) {
    val readAllDataSortDate: LiveData<List<Run>> = runDao.getAllRunsByDate()

    fun getDataByDistanceSortSpeed(runDistance: RunDistance): LiveData<List<Run>> {
        return runDao.getRunsOfDistanceBySpeed(runDistance = runDistance)
    }

    fun getDataByDistanceSortDate(runDistance: RunDistance): LiveData<List<Run>> {
        return runDao.getRunsOfDistanceByDate(runDistance = runDistance)
    }

    suspend fun addRun(run: Run) {
        runDao.insert(run)
    }
    //  suspend fun deleteAllRecords() {
    //     scoreEntryDao.deleteAll()
    // }
}