package com.picpay.desafio.android.di

import androidx.lifecycle.SavedStateHandle
import com.picpay.desafio.android.data.PicPayService
import com.picpay.desafio.android.data.UserRepositoryImpl
import com.picpay.desafio.android.domain.FetchUseCase
import com.picpay.desafio.android.domain.UserRepository
import com.picpay.desafio.android.ui.presentation.UserMapper
import com.picpay.desafio.android.ui.presentation.UserViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val userModule = module {
    factory<PicPayService> { get<Retrofit>().create(PicPayService::class.java) }

    factory { UserMapper() }

    factory { FetchUseCase(get()) }

    factory<UserRepository> { UserRepositoryImpl(get(), com.picpay.desafio.android.domain.UserMapper()) }

    viewModel { UserViewModel(SavedStateHandle(), get(), get()/*, get()*/) }
}