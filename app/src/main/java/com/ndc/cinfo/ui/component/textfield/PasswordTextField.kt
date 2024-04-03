package com.ndc.cinfo.ui.component.textfield

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.ndc.cinfo.R

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    placeholder: String = "",
    enabled: Boolean = true,
    error: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    leadingIcon: @Composable (() -> Unit)? = null,
    visible: Boolean = true,
    onVisibilityChange: (visible: Boolean) -> Unit = {},
    value: String = "",
    onValueChange: (String) -> Unit = {},
) {
    val color = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    BaseTextField(
        modifier = modifier,
        placeholder = {
            Text(
                text = placeholder,
                style = typography.bodyMedium,
                color = color.onSurfaceVariant
            )
        },
        leadingIcon = leadingIcon,
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        isError = error,
        trailingIcon = {
            IconButton(onClick = {
                onVisibilityChange(!visible)
            }) {
                Icon(
                    painter = painterResource(id = if (visible) R.drawable.ic_eye_open else R.drawable.ic_eye_closed),
                    contentDescription = "",
                    modifier = Modifier
                        .size(24.dp),
                    tint = color.secondary
                )
            }
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
    )
}