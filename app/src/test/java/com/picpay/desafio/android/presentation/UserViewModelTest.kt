package com.picpay.desafio.android.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.desafio.picpay.android.testcoreutil.CoroutineRule
import com.picpay.desafio.android.data.UserResponse
import com.picpay.desafio.android.domain.FetchUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import com.picpay.desafio.android.domain.Result
import com.picpay.desafio.android.domain.UseCaseError
import com.picpay.desafio.android.domain.User
import com.picpay.desafio.android.ui.presentation.UserMapper
import com.picpay.desafio.android.ui.presentation.UserViewModel
import com.picpay.desafio.android.ui.presentation.UserViewObject
import com.picpay.desafio.android.ui.presentation.ViewState
import io.mockk.*


@RunWith(JUnit4::class)
class UserViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineRule = CoroutineRule()

    private lateinit var subject: UserViewModel

    private val useCase: FetchUseCase = mockk(relaxed = true)
    private val mapper: UserMapper = mockk(relaxed = true)
    private val savedStateHandle: SavedStateHandle = mockk(relaxed = true)
    private val stateObserver: Observer<ViewState<ArrayList<UserViewObject>>> = mockk(relaxed = true)




    @Before
    fun setUp() {
        subject = UserViewModel(savedStateHandle, mapper, useCase)
        subject.userState.observeForever(stateObserver)

    }

    /**
     * exemplo de teste usando objeto de simulação stub
     */

    @Test
    fun `given a user list when the use case is called then should return success status`() {

        coEvery { useCase.execute() } coAnswers
                { mockUser() }

        subject.userState.observeForever { }

        subject.fetchUsers()

        Assert.assertTrue(subject.userState.value is ViewState.Success)
    }

    /**
     * exemplo usando objeto de simulacao mock
     */

    @Test
    fun `given a user list when the use case is called then should return success status 2`() {

        val viewObject = arrayListOf(UserViewObject(name = "Maria", userName = "@M", imageProfile = "http://"))

        val mockResponse = Result.Success(listOf(User(id = 1, name = "Maria", username = "@M", img = "http://")))

        val state = ViewState.Success(viewObject)

        every {
            mapper.responseToViewObject(mockResponse.data)
        }returns viewObject

        coEvery { useCase.execute() } coAnswers
                { mockResponse }

        subject.fetchUsers()

        verify {
            stateObserver.onChanged(state)
        }

    }

    /*
    internal class GetMaaSWebViewUrlUseCaseTest {
    private val repository = mockk<MaaSWebViewRepository>(relaxed = true)
    private val useCase = GetMaaSWebViewUrlUseCase(repository)

    @Test
    fun `invoke should call repository with correct callback url`() {
        // Given
        val callbackUrl = "picpay://picpay/wallet"

        // When
        useCase()

        // Then
        verify { repository.getMaaSWebViewUrl(callbackUrl) }
    }
}
     */

    @Test
    fun `given a user list when the use case is called then should return success status 4`() {

        subject.fetchUsers()

        coVerify {
            useCase.execute()
        }

    }


    @Test
    fun `given a user list when the use case is called then should return success status 3`() {

        val viewObject = arrayListOf(UserViewObject(name = "Maria", userName = "@M", imageProfile = "http://"))

        val mockResponse = Result.Success(listOf(User(id = 1, name = "Maria", username = "@M", img = "http://")))

        val state = ViewState.Success(viewObject)

        every {
            mapper.responseToViewObject(mockResponse.data)
        }returns viewObject

        coEvery { useCase.execute() } coAnswers {mockResponse}

        subject.fetchUsers()

        verify {
            stateObserver.onChanged(state)
        }
    }

    @Test
    fun `given an unexpected error when the use case is called should return error status`() {
        coEvery { useCase.execute() } coAnswers
                { Result.Error(UseCaseError(""))}

        subject.userState.observeForever { }
        subject.fetchUsers()

        Assert.assertTrue(subject.userState.value is ViewState.Error)
    }

    private fun mockUser() =
        Result.Success(listOf(User(id = 1, name = "Maria", username = "@M", img = "http://")))
}