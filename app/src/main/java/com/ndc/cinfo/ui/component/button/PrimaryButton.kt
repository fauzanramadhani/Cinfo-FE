package com.ndc.cinfo.ui.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String = "",
    onClick: () -> Unit = {}
) {
    val color = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Button(
        modifier = modifier
            .height(48.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = color.primary,
            contentColor = color.onPrimary,
            disabledContainerColor = color.surfaceVariant,
        ),
        shape = RoundedCornerShape(8.dp),
        enabled = enabled
    ) {
        Text(
            text = text,
            style = typography.labelLarge
        )
    }
}