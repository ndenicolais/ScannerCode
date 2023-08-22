package com.denicks21.scannercode.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.denicks21.scannercode.R

@Composable
fun CameraDialog(
    onContinue: () -> Unit,
    onExit: () -> Unit
) {
    Dialog(
        onDismissRequest = onExit,
        properties = DialogProperties(dismissOnClickOutside = false)
    ) {
        CameraRequiredContent(
            onContinue = onContinue,
            onExit = onExit
        )
    }
}

@Composable
private fun CameraRequiredContent(
    onContinue: () -> Unit,
    onExit: () -> Unit
) {
    Card {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = null
            )
            Text(
                text = stringResource(id = R.string.camera_required_message),
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = onExit,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colors.error
                    ),
                    border = BorderStroke(
                        width = 2.dp,
                        color = MaterialTheme.colors.error
                    ),
                    modifier = Modifier.weight(0.5f)
                ) {
                    Text(
                        text = stringResource(id = R.string.camera_required_exit_btn),
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Button(
                    onClick = onContinue,
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White
                    ),
                    modifier = Modifier.weight(0.5f)
                ) {
                    Text(
                        text = stringResource(id = R.string.camera_required_continue_btn),
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}