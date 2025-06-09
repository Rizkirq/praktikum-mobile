package com.example.BookApp.repository

import com.example.BookApp.dao.UserDao
import com.example.BookApp.models.LoginRequest
import com.example.BookApp.models.User
import com.example.BookApp.networks.LoginApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton
import com.example.BookApp.helper.Result
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException

@Singleton
class AuthRepository @Inject constructor(
    private val loginApi: LoginApi,
    private val userDao: UserDao
) {
    suspend fun login(email: String, password: String): Flow<Result<User>> = flow {
        emit(Result.Loading)
        try {
            val response = loginApi.login(LoginRequest(email, password))
            if (response.error) {
                emit(Result.Error(response.message))
            } else {
                val user = User(
                    userId = response.loginResult.userId,
                    name = response.loginResult.name,
                    token = response.loginResult.token
                )
                userDao.insertUser(user)
                emit(Result.Success(user))
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val message = parseErrorMessage(errorBody)
            emit(Result.Error(message))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown error"))
        }
    }

    private fun parseErrorMessage(errorBody: String?): String {
        return try {
            if (errorBody.isNullOrEmpty()) {
                "Unknown error"
            } else {
                val jsonObject = JSONObject(errorBody)
                jsonObject.optString("message", "Unknown error")
            }
        } catch (e: Exception) {
            "Unknown error"
        }
    }
}
