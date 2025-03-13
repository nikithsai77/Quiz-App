package com.example.cricut.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cricut.data.Item
import com.example.cricut.data.Question
import com.example.cricut.presentation.ClickEvent

@Composable
fun DisplayTheDataInfo(modifier: Modifier = Modifier, data: Question, answer: (ClickEvent) -> Unit, success: () -> Unit) {
    Column(modifier.fillMaxSize().padding(5.dp), verticalArrangement = Arrangement.Center) {

        Text(text = data.item.title, color = Color.Red, modifier = Modifier.align(Alignment.CenterHorizontally), fontSize = 25.sp)

        Spacer(modifier = Modifier.fillMaxHeight(fraction = 0.10f))

        Text(text = data.item.question, textAlign = TextAlign.Center)

        data.item.options?.forEach {
            Row {
                RadioButton(selected = data.item.userAnswer.contains(it), onClick = {
                    answer(ClickEvent.Answer(answer = it))
                })

                Spacer(modifier = Modifier.width(5.dp))

                Text(text = it, modifier = Modifier.align(Alignment.CenterVertically))
            }
        }

        Spacer(modifier = Modifier.fillMaxHeight(fraction = 0.1f))

        if (data.item.title == "Type in a below Box") {
            Column {
                TextField(modifier = Modifier.fillMaxWidth(), isError = data.item.isError, maxLines = 6, singleLine = false, value = data.item.userTheoryAnswer,
                    onValueChange = { answer(ClickEvent.UserResponse(userResponse = it, isError = false)) }, label = { Text(text = "Type Your response...")},  keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ), colors = TextFieldDefaults.colors())

                RowButtons(previous = "Previous", previousEnable = data.previous, submit="Submit", submitEnable = true, previousAction = { answer(ClickEvent.Previous) } ) {
                    success()
                }
            }
        } else RowButtons(previous = "Previous", previousEnable = data.previous, submit="Next", submitEnable = data.next, previousAction = { answer(ClickEvent.Previous) } ) { answer(ClickEvent.Next) }
    }
}

@Preview
@Composable
fun MPreview() {
    DisplayTheDataInfo(data = Question(Item(title = "True (or) False", question = "Jetpack Compose is the Declarative UI Toolkit For Building Android Apps?", options = arrayListOf("True", "False"))), answer = {}, success = {})
}