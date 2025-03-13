package com.example.cricut.domain

import com.example.cricut.data.Question

sealed interface Resource {
    data object Loading: Resource
    data class Success(val question: Question): Resource
    data class Update(val question: Question): Resource
    data class Error(val error: DataError): Resource
}