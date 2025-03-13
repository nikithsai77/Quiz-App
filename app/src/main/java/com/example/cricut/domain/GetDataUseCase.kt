package com.example.cricut.domain

import com.example.cricut.data.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetDataUseCase(private val repository: Repository) {

    operator fun invoke() : Flow<Result<List<Item>, DataError>> = flow {
        repository.getItems().collect { it ->
            emit(it)
        }
    }

}