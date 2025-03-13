package com.example.cricut.presentation

import androidx.compose.runtime.Composable
import com.example.cricut.domain.DataError

@Composable
fun DataError.getErrorDescription(): String {
    return when(this) {
        DataError.LocalError.Unauthorized -> DataError.LocalError.Unauthorized.name
        DataError.LocalError.NotFound -> DataError.LocalError.NotFound.name
        DataError.LocalError.SOMETHING_WENT_WRONG_TRY_AGAIN_LATER -> DataError.LocalError.SOMETHING_WENT_WRONG_TRY_AGAIN_LATER.name
    }
}