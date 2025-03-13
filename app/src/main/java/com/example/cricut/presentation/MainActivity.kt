package com.example.cricut.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cricut.domain.Resource
import com.example.cricut.presentation.components.DisplayQuestions
import com.example.cricut.presentation.components.SampleDialog
import com.example.cricut.presentation.components.TopAppBar
import com.example.cricut.presentation.theme.CricutTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val keyboardController = LocalSoftwareKeyboardController.current
            CricutTheme {
                Scaffold(topBar = { TopAppBar() }, modifier = Modifier.fillMaxSize()) { innerPadding ->
                    var dialog by remember { mutableStateOf(value = false) }
                    val itemViewModel: MainActivityViewModel = hiltViewModel()
                    val state by itemViewModel.state.collectAsStateWithLifecycle()
                    if (dialog == true) {
                        SampleDialog(onSuccess = {
                            itemViewModel.event(ClickEvent.Reset)
                            dialog = false
                        }, dialogText = "You Completed The Test", dialogTitle = "Congratulation", icon = Icons.Default.Info)
                    } else {
                        DisplayQuestions(state = state, modifier = Modifier.fillMaxSize().padding(innerPadding), answer = {
                            itemViewModel.event(it)
                        }, retry = {
                            itemViewModel.event(ClickEvent.Reset)
                        }, success =  {
                            if(state is Resource.Success || state is Resource.Update) {
                                keyboardController?.hide()
                                val question = if (state is Resource.Success) (state as Resource.Success).question
                                else (state as Resource.Update).question
                                if (question.item.userTheoryAnswer.isNotBlank()) dialog = true
                                else itemViewModel.event(ClickEvent.UserResponse(userResponse = "", isError = true))
                            }
                        })
                    }
                }
        }
    } }
}