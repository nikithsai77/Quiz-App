package com.example.cricut.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RowButtons(previous: String, previousEnable: Boolean, submit: String, submitEnable: Boolean, previousAction: () -> Unit, submitAction: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Button(onClick = {
            previousAction()
        }, enabled = previousEnable) {
            Text(text = previous)
        }

        Button(onClick = {
            submitAction()
        }, enabled = submitEnable) {
            Text(text = submit)
        }
    }
}