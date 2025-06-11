package com.example.BookApp.repository

import com.example.BookApp.helper.Result
import com.example.BookApp.models.Book
import com.example.BookApp.models.BookResponse
import com.example.BookApp.networks.BookApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookRepository @Inject constructor(
    private val bookApi: BookApi // Hilt akan menyediakan BookApi
) {
    suspend fun getBooks(): Flow<Result<List<Book>>> = flow {
        emit(Result.Loading)
        try {
            val response: BookResponse = bookApi.getBooks()
            if (response.data.isNotEmpty()) {
                emit(Result.Success(response.data, "Books loaded successfully"))
            } else {
                emit(Result.Success(emptyList(), "No books found"))
            }
        } catch (e: HttpException) {
            val message = parseErrorMessage(e.response()?.errorBody()?.string())
            emit(Result.Error(message))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown error occurred"))
        }
    }

    suspend fun getBookById(bookId: Int): Flow<Result<Book>> = flow {
        emit(Result.Loading)
        try {
            val response = bookApi.getBookById(bookId)
            emit(Result.Success(response.data, "Book details loaded successfully"))
        } catch (e: HttpException) {
            val message = parseErrorMessage(e.response()?.errorBody()?.string())
            emit(Result.Error(message))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown error occurred"))
        }
    }

    private fun parseErrorMessage(errorBody: String?): String {
        return try {
            if (errorBody.isNullOrEmpty()) {
                "Unknown error"
            } else {
                val jsonObject = JSONObject(errorBody)
                if (jsonObject.has("message")) {
                    jsonObject.optString("message", "An error occurred")
                } else if (jsonObject.has("errors")) {
                    val errorsObject = jsonObject.getJSONObject("errors")
                    val messages = mutableListOf<String>()
                    errorsObject.keys().forEach { key ->
                        val fieldErrors = errorsObject.getJSONArray(key)
                        for (i in 0 until fieldErrors.length()) {
                            messages.add(fieldErrors.getString(i))
                        }
                    }
                    if (messages.isNotEmpty()) messages.joinToString("\n") else "Validation failed"
                } else {
                    "An error occurred"
                }
            }
        } catch (e: Exception) {
            "An error occurred"
        }
    }
}