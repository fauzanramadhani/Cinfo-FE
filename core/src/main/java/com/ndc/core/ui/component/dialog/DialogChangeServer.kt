package com.ndc.core.ui.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ndc.core.ui.component.textfield.PrimaryTextField
import com.ndc.core.ui.component.textfield.TextFieldState

@Composable
fun DialogChangeServerAddress(
    modifier: Modifier = Modifier,
    addressState: TextFieldState = TextFieldState.Empty,
    addressValue: String = "",
    onAddressChange: (value: String) -> Unit = {},
    onClearValue: () -> Unit = {},
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {}
) {
    val typography = MaterialTheme.typography
    val colors = MaterialTheme.colorScheme

    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = modifier
                .clip(RoundedCornerShape(12.dp))
                .fillMaxWidth()
                .background(colors.primaryContainer)
                .padding(24.dp)
        ) {
            Text(
                text = "Perbarui Alamat Server",
                style = typography.titleLarge,
                color = colors.onBackground
            )
            Spacer(modifier = Modifier.padding(top = 12.dp))
            PrimaryTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                textFieldState = addressState,
                placeholder = "cth: 192.168.1.14:3000",
                value = addressValue,
                onValueChange = onAddressChange,
                onClearValue = onClearValue,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
            )
            Spacer(modifier = Modifier.padding(top = 12.dp))
            Text(
                text = "Aplikasi akan di mulai ulang saat anda memberbarui alamat server",
                style = typography.bodySmall,
                color = colors.onBackground
            )
            Spacer(modifier = Modifier.padding(top = 24.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Batal",
                    style = typography.labelLarge,
                    color = colors.error,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable(onClick = onDismiss)
                        .padding(12.dp)
                )
                Text(
                    text = "Perbarui",
                    style = typography.labelLarge,
                    color = colors.primary,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable(onClick = onConfirm)
                        .padding(12.dp)
                )
            }
        }

    }
}