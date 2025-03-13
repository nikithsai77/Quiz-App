package com.example.cricut.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cricut.data.Item
import com.example.cricut.data.Question
import com.example.cricut.domain.DataError
import com.example.cricut.domain.GetDataUseCase
import com.example.cricut.domain.Resource
import com.example.cricut.domain.Result
import kotlinx.coroutines.Job
import com.example.cricut.presentation.ClickEvent.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val getDataUseCase: GetDataUseCase): ViewModel() {
    private var job: Job? = null
    private var mCurrentQuestion = 0
    private var mTotalNoOfQuestion = 0
    private var mQuestionList : List<Item> = listOf()
    private val _state = MutableStateFlow<Resource>(Resource.Loading)
    private val _resource = MutableStateFlow<Result<List<Item>, DataError>>(Result.Loading)
    val state = _state.onStart { getData() }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L), Resource.Loading)

    private fun getData() {
        if (job == null) {
            getDataUseCase().onEach { newState ->
                _resource.update {
                    newState
                }
              updateQuestion()
            }.launchIn(viewModelScope)
        }
    }

    private fun updateQuestion() {
        _state.update {
            return@update if (_resource.value is Result.Loading) Resource.Loading
            else if (_resource.value is Result.Error) Resource.Error(error = (_resource.value as Result.Error<DataError>).error)
            else {
                mCurrentQuestion = 0
                mQuestionList = (_resource.value as Result.Success).data
                mTotalNoOfQuestion = (_resource.value as Result.Success).data.size
                Resource.Success(question = Question(item = mQuestionList[mCurrentQuestion], previous = mCurrentQuestion != 0, next = mCurrentQuestion < mTotalNoOfQuestion))
            }
        }
    }

    private fun changeToNextQuestion() {
        mCurrentQuestion++
        if (mCurrentQuestion < mTotalNoOfQuestion) updateToNextQuestion(mCurrentQuestion)
    }

    private fun changeToPreviousQuestion() {
        if (mCurrentQuestion <= mTotalNoOfQuestion) mCurrentQuestion--
        if (mCurrentQuestion >= 0) updateToNextQuestion(mCurrentQuestion)
    }

    fun event(clickEvent: ClickEvent) {
        when(clickEvent) {
            is Reset -> getData()
            is Next -> changeToNextQuestion()
            is Answer -> answered(clickEvent.answer)
            is Previous -> changeToPreviousQuestion()
            is UserResponse -> userResponse(clickEvent.userResponse, clickEvent.isError)
        }
    }

    private fun userResponse(userResponse: String, isError: Boolean) {
        val question = if (_state.value is Resource.Success) (_state.value as Resource.Success).question
        else (_state.value as Resource.Update).question
        val updatedQuestion = question.copy(item = question.item.copy(userTheoryAnswer = userResponse, isError = isError))
        _state.update {
            if (_state.value is Resource.Success) Resource.Update(question = updatedQuestion)
            else  Resource.Success(question = updatedQuestion)
        }
    }

    private fun answered(answer: String) {
        if (_state.value is Resource.Success) {
            val data = (_state.value as Resource.Success).question
            if (data.item.userAnswer.size <= data.item.minimum && !data.item.userAnswer.contains(answer)) {
                if (data.item.userAnswer.size >= data.item.minimum) data.item.userAnswer[data.item.minimum - 1] = answer
                else data.item.userAnswer.add(answer)
                _state.update {
                    Resource.Update(question = data.copy(previous = mCurrentQuestion != 0, next = mCurrentQuestion < mTotalNoOfQuestion))
                }
            }
        } else {
            val data = (_state.value as Resource.Update).question
            if (data.item.userAnswer.size <= data.item.minimum && !data.item.userAnswer.contains(answer)) {
                if (data.item.userAnswer.size >= data.item.minimum) data.item.userAnswer[data.item.minimum - 1] = answer
                else data.item.userAnswer.add(answer)
                _state.update {
                    Resource.Success(question = data.copy(previous = mCurrentQuestion != 0, next = mCurrentQuestion < mTotalNoOfQuestion))
                }
            }
        }
    }

    private fun updateToNextQuestion(mCurrentQuestion: Int) {
        if (_state.value is Resource.Success) {
            val data = (_state.value as Resource.Success).question
            _state.update {
                Resource.Update(question = data.copy(item = mQuestionList[mCurrentQuestion] , previous = mCurrentQuestion != 0, next = mCurrentQuestion < mTotalNoOfQuestion - 1))
            }
        } else {
            val data = (_state.value as Resource.Update).question
            _state.update {
                Resource.Success(question = data.copy(item = mQuestionList[mCurrentQuestion], previous = mCurrentQuestion != 0, next = mCurrentQuestion < mTotalNoOfQuestion - 1))
            }
        }
    }

}