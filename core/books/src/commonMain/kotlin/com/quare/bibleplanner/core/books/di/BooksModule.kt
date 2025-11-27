package com.quare.bibleplanner.core.books.di

import com.quare.bibleplanner.core.books.data.BooksRepositoryImpl
import com.quare.bibleplanner.core.books.domain.BooksRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val booksModule = module {
    factoryOf(::BooksRepositoryImpl).bind<BooksRepository>()
}
