package com.task.catganisation.di

import android.app.Application
import com.task.data.repository.MainRepository
import com.task.data.source.RemoteDataSource
import com.task.catganisation.BuildConfig
import com.task.catganisation.data.api.ApiClient
import com.task.catganisation.data.api.ApiRemoteDataSource
import com.task.catganisation.data.api.AuthenticatorInterceptor
import com.task.catganisation.parcel.CatBreed
import com.task.catganisation.ui.breeds.BreedDetailFragment
import com.task.catganisation.ui.breeds.BreedDetailViewModel
import com.task.catganisation.ui.breeds.BreedsListFragment
import com.task.catganisation.ui.breeds.BreedsViewModel
import com.task.catganisation.ui.common.SharedViewModel
import com.task.catganisation.ui.login.LoginFragment
import com.task.catganisation.ui.login.LoginViewModel
import com.task.usecases.breeds.GetCatBreedsByCountryUseCase
import com.task.usecases.breeds.GetCatBreedsUseCase
import com.task.usecases.login.LoginUseCase
import com.task.usecases.login.ValidatePasswordUseCase
import com.task.usecases.login.ValidateUsernameUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun Application.initDI() {
    startKoin {
        androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
        androidContext(this@initDI)
        modules(appModule, dataModule, scopesModule)
    }
}

private val appModule = module {
    factory<RemoteDataSource> { ApiRemoteDataSource(get()) }
    factory { AuthenticatorInterceptor(get(named("apiKey"))) }
    single(named("baseUrl")) { BuildConfig.BASE_URL }
    single(named("apiKey")) { BuildConfig.API_KEY }
    single { ApiClient(get(named("baseUrl")), get()).service }
}

private val dataModule = module {
    factory { MainRepository(get()) }
}

private val scopesModule = module {
    scope<LoginFragment> {
        viewModel { LoginViewModel(get(), get(), get()) }
        scoped { LoginUseCase(get()) }
        scoped { ValidateUsernameUseCase() }
        scoped { ValidatePasswordUseCase() }
    }

    scope<BreedsListFragment> {
        viewModel { BreedsViewModel(get(), get()) }
        scoped { GetCatBreedsUseCase(get()) }
        scoped { GetCatBreedsByCountryUseCase(get()) }
    }
    scope<BreedDetailFragment> {
        viewModel { (catBreed: CatBreed) -> BreedDetailViewModel(catBreed) }
    }

    viewModel { SharedViewModel() }
}