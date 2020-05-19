package com.bridge.androidtechnicaltest.di

import com.bridge.androidtechnicaltest.db.IPupilRepository
import com.bridge.androidtechnicaltest.db.PupilRepository
import com.bridge.androidtechnicaltest.ui.pupillist.viewmodel.PupilListViewModel
import org.koin.android.viewmodel.dsl.viewModel

import org.koin.dsl.module

val networkModule = module {
    factory { PupilAPIFactory.retrofitPupil() }
}

val databaseModule = module {
    factory { DatabaseFactory.getDBInstance(get()) }
    single<IPupilRepository>{ PupilRepository(get(), get()) }
}

val pupilListModule = module {
    viewModel { PupilListViewModel(get()) }
}

