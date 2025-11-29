package com.quare.bibleplanner.feature.day.di

import com.quare.bibleplanner.feature.day.data.datasource.DayLocalDataSource
import com.quare.bibleplanner.feature.day.data.mapper.DayEntityToModelMapper
import com.quare.bibleplanner.feature.day.data.repository.DayRepositoryImpl
import com.quare.bibleplanner.feature.day.domain.repository.DayRepository
import com.quare.bibleplanner.feature.day.domain.usecase.GetDayDetailsUseCase
import com.quare.bibleplanner.feature.day.domain.usecase.UpdateChapterReadStatusUseCase
import com.quare.bibleplanner.feature.day.domain.usecase.UpdateDayReadStatusUseCase
import com.quare.bibleplanner.feature.day.domain.usecase.UpdateDayReadTimestampUseCase
import com.quare.bibleplanner.feature.day.presentation.viewmodel.DayViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dayModule = module {
    // Data sources
    singleOf(::DayLocalDataSource)

    // Mappers
    factoryOf(::DayEntityToModelMapper)

    // Repository
    singleOf(::DayRepositoryImpl).bind<DayRepository>()

    // Use cases
    factoryOf(::GetDayDetailsUseCase)
    factoryOf(::UpdateDayReadStatusUseCase)
    factoryOf(::UpdateChapterReadStatusUseCase)
    factoryOf(::UpdateDayReadTimestampUseCase)

    // ViewModel
    viewModelOf(::DayViewModel)
}
