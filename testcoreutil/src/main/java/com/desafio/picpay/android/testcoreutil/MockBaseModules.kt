package com.desafio.picpay.android.testcoreutil

import android.content.res.Resources
import com.google.gson.Gson
import io.mockk.mockk
import org.koin.dsl.module

object MockBaseModules {

    val baseAppModule = module {
        factory { mockk<Resources>(relaxed = true) }
        factory { Gson() }
    }

    /*
      factory<PicPayService> { get<Retrofit>().create(PicPayService::class.java) }

    factory { UserMapper() }

    factory { FetchUseCase(get()) }

    viewModel { UserViewModel(get(), get()/*, get()*/) }
     */
}