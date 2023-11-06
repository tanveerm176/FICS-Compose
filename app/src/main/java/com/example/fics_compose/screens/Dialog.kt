package com.example.fics_compose.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.fics_compose.ui.theme.FICSComposeTheme

@Composable
fun SimulatorDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    title: String,
    info: String,
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = { onDismissRequest() },
        ) {
            // Draw a rectangle shape with rounded corners inside the dialog
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center,
                    )
                    Text(
                        text = info,
                        modifier = Modifier.padding(16.dp),
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.Bottom,
                    ) {
                        Button(
                            onClick = { onDismissRequest() },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text("SKIP")
                        }
                        Button(
                            onClick = { onConfirmation() },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text("NEXT")
                        }
                    }
                }
            }
        }
    }
}

data class DialogContent(
    val title: String,
    val info: String
)

@Preview
@Composable
fun PreviewDialog() {
    var showDialog by remember { mutableStateOf(true) }
    val dialogs = listOf(
        DialogContent(
            title = "Welcome to the FICS simulation!",
            info = "Click next for some helpful terms for you to know."
        ),
        DialogContent(
            title = "Fixed Income",
            info = "Fixed income is a type of investment that pays the investor a fixed amount on a fixed schedule."
        ),
    )
    var currentDialog = 0

    FICSComposeTheme {

        if (currentDialog < dialogs.size) {
            val currentDialogContent = dialogs[currentDialog]

            SimulatorDialog(
                showDialog = true,
                onDismissRequest = { showDialog = false },
                onConfirmation = {
                    // Increment the currentDialog index to show the next dialog
                    currentDialog++
                },
                title = currentDialogContent.title,
                info = currentDialogContent.info
            )
        }
    }
}