package com.example.cricut.domain

sealed interface DataError {
    enum class LocalError: DataError {
        Unauthorized,
        NotFound,
        SOMETHING_WENT_WRONG_TRY_AGAIN_LATER
    }
}