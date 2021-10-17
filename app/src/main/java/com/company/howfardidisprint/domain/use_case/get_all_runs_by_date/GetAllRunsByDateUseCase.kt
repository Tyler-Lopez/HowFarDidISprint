package com.company.howfardidisprint.domain.use_case.get_all_runs_by_date

import com.company.howfardidisprint.domain.repository.RunDao
import javax.inject.Inject

class GetAllRunsByDateUseCase @Inject constructor(
    private val runDao: RunDao
) {
}