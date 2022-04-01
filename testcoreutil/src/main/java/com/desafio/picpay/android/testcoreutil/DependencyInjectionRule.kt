package com.desafio.picpay.android.testcoreutil

import androidx.test.platform.app.InstrumentationRegistry
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class DependencyInjectionRule : TestWatcher() {

    override fun starting(description: Description?) {
        //loadKoinModules(listOf(MockServer.module, MockBaseModules.baseAppModule))
        startKoin {
            androidContext(InstrumentationRegistry.getInstrumentation().targetContext)
            modules(MockServer.module, MockBaseModules.baseAppModule)

        }
    }
}