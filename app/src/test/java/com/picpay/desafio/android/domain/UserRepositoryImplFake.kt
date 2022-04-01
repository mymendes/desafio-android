package com.picpay.desafio.android.domain

class UserRepositoryImplFake : UserRepository {
    override suspend fun getListUser(): List<User> {
        return listOf(
            User(
                id = 1,
                img = "imag_1",
                name = "Maria",
                username = "@M"
            ),
            User(
                id = 2,
                img = "imag_2",
                name = "João",
                username = "@J"
            ),
            User(
                id = 3,
                img = "imag_3",
                name = "Ana",
                username = "@A"
            )
        )
    }
}