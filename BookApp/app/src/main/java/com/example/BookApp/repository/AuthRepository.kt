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
import com.example.BookApp.models.RegisterRequest
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

    suspend fun register(name: String, email: String, password: String, confirmPassword: String): Flow<Result<User>> = flow {
        emit(Result.Loading)
        try {
            val response = loginApi.register(RegisterRequest(name, email, password, confirmPassword))
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
                "Unknown error (empty body)"
            } else {
                val jsonObject = JSONObject(errorBody)
                if (jsonObject.has("errors")) {
                    val errorsObject = jsonObject.getJSONObject("errors")
                    val messages = mutableListOf<String>()
                    errorsObject.keys().forEach { key ->
                        val fieldErrors = errorsObject.getJSONArray(key)
                        for (i in 0 until fieldErrors.length()) {
                            messages.add(fieldErrors.getString(i))
                        }
                    }
                    if (messages.isNotEmpty()) {
                        messages.joinToString("\n")
                    } else {
                        jsonObject.optString("message", "Validation failed (no specific errors found)")
                    }
                } else {
                    jsonObject.optString("message", "Unknown error")
                }
            }
        } catch (e: Exception) {
            "Unknown error (parsing failed)"
        }
    }
}
