package com.company.howfardidisprint

import androidx.lifecycle.LiveData

class ScoreEntryRepository(private val scoreEntryDao: ScoreEntryDao) {
    val readAllData: LiveData<List<ScoreEntry>> = scoreEntryDao.getScores()

    suspend fun addScore(score: ScoreEntry) {
        scoreEntryDao.insert(score)
    }
  //  suspend fun deleteAllRecords() {
   //     scoreEntryDao.deleteAll()
   // }
}