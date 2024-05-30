package com.ndc.core.ui.component.textfield

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ndc.core.ui.theme.CinfoTheme

@Composable
fun BaseBasicTextField(
    modifier: Modifier = Modifier,
    label: String = "",
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    value: String = "",
    onValueChange: (String) -> Unit = {},
) {
    val color = MaterialTheme.colorScheme

    Box(
        modifier = modifier
    ) {
        BasicTextField(
            modifier = Modifier
                .fillMaxSize(),
            value = value,
            onValueChange = onValueChange,
            textStyle = textStyle,
            cursorBrush = SolidColor(textColor),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions
        )
        if (value.isEmpty()) {
            Text(
                text = label,
                style = textStyle,
                color = color.secondary
            )
        }
    }

}

@Preview
@Composable
fun BaseBasicTextFieldPreview() {
    var value by remember {
        mutableStateOf("")
    }
    CinfoTheme {
        Column(
            modifier = Modifier
                .padding(vertical = 48.dp)
        ) {
            BaseBasicTextField(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth(),
                label = "Ketik Judul",
                value = value,
                onValueChange = {
                    value = it
                }
            )
        }
    }
}