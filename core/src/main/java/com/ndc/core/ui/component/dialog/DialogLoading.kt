package com.ndc.core.ui.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ndc.core.ui.theme.CinfoTheme

@Composable
fun DialogLoading(
    modifier: Modifier = Modifier,
    visible: Boolean = false
) {
    val typography = MaterialTheme.typography
    if (visible) {
        Dialog(onDismissRequest = {}) {
            Column(
                modifier = modifier
                    .background(Color.Transparent),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    strokeCap = StrokeCap.Round,
                    strokeWidth = 8.dp,
                    color = Color.White,
                )
                Text(
                    text = "Silahkan tunggu...",
                    style = typography.titleMedium,
                    color = Color.White
                )
            }
        }
    }
}

@Preview
@Composable
fun DialogLoadingPreview() {
    CinfoTheme {
        DialogLoading(visible = true)
    }
}