package com.example.cricut.presentation.components

import androidx.compose.ui.Modifier
import com.example.cricut.domain.Resource
import androidx.compose.runtime.Composable
import com.example.cricut.presentation.ClickEvent
import com.example.cricut.presentation.getErrorDescription

@Composable
fun DisplayQuestions(
    state: Resource,
    modifier: Modifier = Modifier,
    answer: (ClickEvent) -> Unit,
    success: () -> Unit,
    retry: () -> Unit
) {
    when(state) {
        is Resource.Loading -> LoadingSymbol(modifier)
        is Resource.Error -> Retry(modifier, state.error.getErrorDescription()) { retry() }
        is Resource.Success -> DisplayTheDataInfo(modifier, state.question, answer, success)
        is Resource.Update -> DisplayTheDataInfo(modifier, state.question, answer, success)
    }
}