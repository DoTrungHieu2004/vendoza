package com.hieu10.vendoza.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hieu10.vendoza.ui.theme.VendozaTheme
import com.hieu10.vendoza.utils.validation.evaluatePasswordStrength

@Composable
fun PasswordMeter(password: String, modifier: Modifier = Modifier) {
    val strength = remember(password) { evaluatePasswordStrength(password) }

    Column(modifier = Modifier.fillMaxWidth()) {
        LinearProgressIndicator(
            progress = strength.getProgress(),
            color = strength.getColor(),
            modifier = Modifier.fillMaxWidth().height(4.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = strength.name.lowercase(),
            style = MaterialTheme.typography.labelSmall,
            color = strength.getColor()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PasswordMeterPreviewLight() {
    VendozaTheme(darkTheme = false) {
        PasswordMeter(password = "Password123")
    }
}

@Preview(showBackground = true)
@Composable
private fun PasswordMeterPreviewDark() {
    VendozaTheme(darkTheme = true) {
        PasswordMeter(password = "Password123")
    }
}