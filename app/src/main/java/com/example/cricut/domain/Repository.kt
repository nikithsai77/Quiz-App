package com.example.cricut.domain

import com.example.cricut.data.Item
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getItems() : Flow<Result<List<Item>, DataError>>
}