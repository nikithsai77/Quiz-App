package com.example.cricut.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign

@Composable
fun SampleDialog(onSuccess: () -> Unit, dialogTitle: String, dialogText: String, icon: ImageVector) {
    AlertDialog(
        modifier = Modifier.fillMaxWidth(),
        icon = {
            Icon(imageVector = icon, contentDescription = "Dialog Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        },
        onDismissRequest = { onSuccess.invoke() },
        confirmButton = {
            TextButton(onClick = { onSuccess.invoke() }) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = { onSuccess.invoke() }) {
                Text(text = "Dismiss")
            }
        }
    )
}