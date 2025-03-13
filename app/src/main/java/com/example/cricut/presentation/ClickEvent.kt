package com.example.cricut.presentation

sealed interface ClickEvent {
    data object Next: ClickEvent
    data object Reset: ClickEvent
    data object Previous: ClickEvent
    data class Answer(val answer: String) : ClickEvent
    data class UserResponse(val userResponse: String, val isError: Boolean) : ClickEvent
}