package com.quare.bibleplanner.core.utils.di

import com.quare.bibleplanner.core.utils.date.LocalDateTimeProvider
import org.koin.dsl.module

val utilsModule = module {
    factory { LocalDateTimeProvider() }
}
