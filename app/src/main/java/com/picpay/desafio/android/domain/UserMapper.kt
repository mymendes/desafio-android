package com.picpay.desafio.android.domain

import com.picpay.desafio.android.data.UserResponse

class UserMapper {

    fun map(users : List<UserResponse>) : List<User> {

        val userList : MutableList<User> = mutableListOf()

        users.forEach{ user ->
            userList.add(
                User(
                    id = user.id,
                    name = user.name ?: "",
                    username = user.username?: "",
                    img = user.img?: ""
                )
            )
        }
        return userList

    }
}