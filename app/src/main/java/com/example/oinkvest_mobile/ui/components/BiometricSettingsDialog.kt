package com.example.oinkvest_mobile.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun EnableBiometricDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Login por Digital") },
        text = { Text(text = "Deseja usar sua digital para entrar mais rápido da próxima vez?") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Sim, por favor!")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Não, obrigado")
            }
        }
    )
}

@Composable
fun BiometricSettingsDialog(
    isCurrentlyEnabled: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    // Define os textos dinamicamente com base no estado atual
    val title = if (isCurrentlyEnabled) "Desativar Login por Digital" else "Ativar Login por Digital"
    val text = if (isCurrentlyEnabled) "Você tem certeza que deseja não usar mais a sua digital para entrar no app?" else "Deseja usar sua digital para entrar de forma rápida e segura da próxima vez?"
    val confirmButtonText = if (isCurrentlyEnabled) "Sim, desativar" else "Sim, ativar"

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title) },
        text = { Text(text = text) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(confirmButtonText)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}


@Preview
@Composable
fun EnableBiometricDialogPreview() {
    EnableBiometricDialog(onConfirm = {}, onDismiss = {})
}
