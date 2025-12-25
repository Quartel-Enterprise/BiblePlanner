package com.quare.bibleplanner.core.plan.data.repository

import com.quare.bibleplanner.core.model.plan.ReadingPlanType
import com.quare.bibleplanner.core.model.plan.WeekPlanModel
import com.quare.bibleplanner.core.plan.data.datasource.PlanLocalDataSource
import com.quare.bibleplanner.core.plan.data.mapper.WeekPlanDtoToModelMapper
import com.quare.bibleplanner.core.plan.domain.repository.PlanRepository
import com.quare.bibleplanner.core.utils.date.LocalDateTimeProvider
import kotlinx.datetime.LocalDate
import kotlin.time.Clock

class PlanRepositoryImpl(
    private val planLocalDataSource: PlanLocalDataSource,
    private val weekPlanDtoToModelMapper: WeekPlanDtoToModelMapper,
    private val localDateTimeProvider: LocalDateTimeProvider,
) : PlanRepository {
    override suspend fun getPlans(readingPlanType: ReadingPlanType): List<WeekPlanModel> {
        val startDate = getPlanStartDate()
        return planLocalDataSource
            .getPlans(readingPlanType)
            .map {
                weekPlanDtoToModelMapper.map(
                    weekPlanDto = it,
                    startDate = startDate,
                )
            }
    }

    private suspend fun getPlanStartDate(): LocalDate = localDateTimeProvider
        .getLocalDateTime(
            planLocalDataSource.getPlanStartTimeStamp() ?: Clock.System.now().toEpochMilliseconds().also {
                setPlanStartTimestamp(it)
            },
        ).date

    private suspend fun setPlanStartTimestamp(epoch: Long) {
        planLocalDataSource.setPlanStartTimestamp(epoch)
    }
}
