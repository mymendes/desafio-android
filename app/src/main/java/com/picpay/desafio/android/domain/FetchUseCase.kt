package com.picpay.desafio.android.domain

class FetchUseCase(private val repository: UserRepository) {

    suspend fun execute(): Result<List<User>> {

        return try {
            val result = repository.getListUser()
            Result.Success(result)
        } catch (exception: Exception) {
            Result.Error(UseCaseError(""))
        }
    }
}