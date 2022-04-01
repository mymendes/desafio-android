package com.picpay.desafio.android.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import com.desafio.picpay.android.testcoreutil.*
import com.picpay.desafio.android.R
import com.picpay.desafio.android.ui.view.MainActivity
import com.squareup.picasso.Picasso
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules

@RunWith(androidx.test.ext.junit.runners.AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val archRule = InstantTaskExecutorRule()

    @get:Rule
    val serverRule = MockServerRule()

    private val picasso = mockk<Picasso>(relaxed = true)

    private fun mockSuccessResponse() {
        serverRule.mockResponse("/users", "users/mock_list_user.json")
    }

    private fun launch() {
        loadKoinModules(listOf(MockServer.module, MockBaseModules.baseAppModule))
        val scenario = launchActivity<MainActivity>()
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Before
    fun setUp() {
        Picasso.setSingletonInstance(picasso)
        mockSuccessResponse()
    }

    @Test
    fun shouldDisplayListItem() {

        loadKoinModules(listOf(MockServer.module, MockBaseModules.baseAppModule))
        val scenario = launchActivity<MainActivity>()

        scenario.onActivity {
            onView(withId(R.id.title))
                .check(matches(isDisplayed()))
            RecyclerViewMatchers.checkRecyclerViewItem(R.id.rvUsers, 0, ViewMatchers.withText("Tom"))
        }
    }
}