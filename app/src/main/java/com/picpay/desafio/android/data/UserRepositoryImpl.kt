package com.picpay.desafio.android.data

import com.picpay.desafio.android.domain.User
import com.picpay.desafio.android.domain.UserMapper
import com.picpay.desafio.android.domain.UserRepository

class UserRepositoryImpl(private val picPayService: PicPayService, private val mapper: UserMapper): UserRepository {

    override suspend fun getListUser(): List<User> {
        return mapper.map(picPayService.getUsers())
    }
}