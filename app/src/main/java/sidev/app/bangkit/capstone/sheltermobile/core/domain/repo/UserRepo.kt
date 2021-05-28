package sidev.app.bangkit.capstone.sheltermobile.core.domain.repo

import sidev.app.bangkit.capstone.sheltermobile.core.domain.model.User

interface UserRepo {
    suspend fun getUser(email: String): Result<User>
    suspend fun saveUser(data: User): Result<Boolean>
    suspend fun savePassword(password: String): Result<Boolean>
    suspend fun getPassword(): Result<String>
}