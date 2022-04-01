package com.picpay.desafio.android.domain
interface UserRepository {

    suspend fun getListUser() : List<User>
}