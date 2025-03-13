package com.example.cricut.data

import com.example.cricut.domain.Result
import com.example.cricut.domain.DataError
import com.example.cricut.domain.Repository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class LocalRepository: Repository {

    override suspend fun getItems(): Flow<Result<List<Item>, DataError>> = flow {
        emit(Result.Loading)
        delay(timeMillis = 1000L)
        emit(Result.Success(data = getQuestion()) as Result<List<Item>, DataError>)
    }.catch {
        emit(Result.Error(error = DataError.LocalError.SOMETHING_WENT_WRONG_TRY_AGAIN_LATER))
    }

    private fun getQuestion(): List<Item> {
        return listOf(Item(title = "True (or) False", question = "Jetpack Compose is the Declarative UI Toolkit For Building Android Apps?",
            minimum = 1, options = arrayListOf("True", "False")),
            Item(title = "True (or) False", question = "Is Jetpack Compose can be used to Implement the UI in XML way", minimum = 1, options = arrayListOf("True", "False")),
            Item(title = "A Multiple Choice Question with 4 options but only accepts a single answer", question = "Which Statement is Right For StateFlow", minimum = 2, options = arrayListOf(
                    "It has a Thread-safe updates & Supports Reactive Programming",
                    "It is a Hot Flow",
                    "It holds the data only when it has at-least one active subscribe",
                    "Kotlin coroutine is required to get the Data"
            )),
            Item(title = "Type in a below Box", question = "Which Social Media App You Like Most and Use Frequently?"))
    }

}