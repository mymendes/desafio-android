package com.picpay.desafio.android.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.picpay.desafio.android.data.PicPayService
import com.picpay.desafio.android.data.UserRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.coVerify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FetchUseCaseTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var subject: FetchUseCase

    private val service: PicPayService = mockk(relaxed = true)
    private val mapper: UserMapper = mockk(relaxed = true)
    private val repository: UserRepository = mockk(relaxed = true)
    private val repositoryFake = UserRepositoryImplFake()


    @Before
    fun setUp() {
        subject = FetchUseCase(repository)
    }

    /**
     * exemplo usando fake
     */
    @ExperimentalCoroutinesApi
    @Test
    fun `given execute call when success response then should return result success fake`() =
        runBlockingTest {
            val result = subject.execute()
            assert(result is Result.Success)
        }

    /**
     * exemplo usando stub
     */
    @ExperimentalCoroutinesApi
    @Test
    fun `given execute call when success response then should return result success stub`() =

        runBlocking {
            coEvery {
                repository.getListUser()
            } coAnswers {
                mockk(relaxed = true)
            }
            val result = subject.execute()
            assert(result is Result.Success)
        }

    /**
     * exemplo usando mock
     */
    @ExperimentalCoroutinesApi
    @Test
    fun `given execute call when success response then should return result success mock`() {
        runBlockingTest {
            subject.execute()
        }
        coVerify {
            repository.getListUser()
        }
    }

    /**
     * exemplo usando spy
     */
    @ExperimentalCoroutinesApi
    @Test
    fun `given execute call when success response then should return result success spy`() {


        runBlockingTest {

            val repository = spyk(UserRepositoryImpl(service, mapper))
            val useCase = FetchUseCase(repository)

            useCase.execute()

            coEvery {
                service.getUsers()
            } coAnswers {
                mockk(relaxed = true)
            }

            val result = useCase.execute()
            assert(result is Result.Success)
        }

    }

    @Test
    fun `given execute call when success response then should return result error`() {
        coEvery {
            service.getUsers()
        } coAnswers {
            throw Exception()
        }

        val result = runBlocking {
            subject.execute()
        }

        assert(result is Result.Error)
    }
}