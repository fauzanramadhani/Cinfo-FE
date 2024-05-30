package com.ndc.core.ui.component.sheet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ndc.core.ui.theme.CinfoTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    onDismiss: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit = {},
) {
    ModalBottomSheet(
        modifier = modifier,
        sheetState = sheetState,
        containerColor = Color.White,
        shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
        onDismissRequest = onDismiss,
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun BaseBottomSheetPreview() {
    val bottomSheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    CinfoTheme {
        if (bottomSheetState.isVisible) {
            BaseBottomSheet(
                onDismiss = {
                    scope.launch {
                        bottomSheetState.hide()
                    }
                },
                content = {
                    Text(text = "Test 1")
                    Text(text = "Test 2")
                }
            )
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = {
                scope.launch {
                    bottomSheetState.show()
                }
            }) {
                Text(text = "Open")
            }
        }
    }
}