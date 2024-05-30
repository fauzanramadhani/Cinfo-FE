package com.ndc.cinfoadmin.ui.feature.editroom

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ndc.core.R
import com.ndc.core.ui.component.textfield.PrimaryTextField
import com.ndc.core.ui.theme.CinfoTheme

@Composable
fun EditRoomScreen(
    navHostController: NavHostController,
    viewModel: EditRoomViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val effect by viewModel.onEffect.collectAsStateWithLifecycle(
        initialValue = EditRoomEffect.None
    )
    val color = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    val ctx = LocalContext.current
    val view = LocalView.current
    val darkTheme: Boolean = isSystemInDarkTheme()
    val window = (view.context as Activity).window

    WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme

    LaunchedEffect(effect) {
        when (effect) {
            EditRoomEffect.None -> {}
            EditRoomEffect.OnEditRoomSuccess -> navHostController.navigateUp()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color.background)
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_left),
                contentDescription = "",
                tint = color.primary,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        navHostController.navigateUp()
                    }
            )
            val isSame =
                state.roomNameValue == state.currentRoomName && state.additionalValue == state.currentAdditional
            val enabled = state.roomNameValue.isNotEmpty() && state.additionalValue.isNotEmpty() && !state.loading && !isSame
            Icon(
                painter = painterResource(id = R.drawable.ic_done),
                contentDescription = "",
                tint = if (enabled)
                    color.primary else color.surfaceVariant,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(
                        enabled = enabled,
                        onClick = {
                            viewModel.onAction(EditRoomAction.EditRoom)
                        }
                    )
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Ubah Informasi Jurusan",
                style = typography.titleLarge,
                color = color.onBackground
            )
            Text(
                text = "Anda dapat mengubah informasi jurusan seperti nama jurusan dan nama angkatan",
                style = typography.bodyMedium,
                color = color.secondary
            )
        }
        Divider(
            thickness = 1.dp,
            color = color.outline
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Nama Jurusan",
                style = typography.labelLarge,
                color = color.onBackground
            )
            PrimaryTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = "Contoh: Teknik Informatika",
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                onClearValue = {
                    viewModel.onAction(EditRoomAction.OnRoomNameValueChange(""))
                },
                value = state.roomNameValue,
                onValueChange = {
                    viewModel.onAction(EditRoomAction.OnRoomNameValueChange(it))
                }
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Tahun Angkatan",
                style = typography.labelLarge,
                color = color.onBackground
            )
            PrimaryTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = "Contoh: Angkatan 2020",
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                onClearValue = {
                    viewModel.onAction(EditRoomAction.OnAdditionalValueChange(""))
                },
                value = state.additionalValue,
                onValueChange = {
                    viewModel.onAction(EditRoomAction.OnAdditionalValueChange(it))
                }
            )
        }
    }
}

@Preview
@Composable
fun CreateRoomPreview() {
    CinfoTheme {
        EditRoomScreen(
            navHostController = rememberNavController(),
            viewModel = viewModel()
        )
    }
}