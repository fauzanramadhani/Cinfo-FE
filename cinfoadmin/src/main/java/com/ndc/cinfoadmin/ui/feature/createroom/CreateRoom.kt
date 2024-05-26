package com.ndc.cinfoadmin.ui.feature.createroom

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
import com.ndc.core.ui.component.dialog.DialogLoading
import com.ndc.core.ui.component.textfield.PrimaryTextField
import com.ndc.core.ui.theme.CinfoTheme
import com.ndc.core.utils.Toast

@Composable
fun CreateRoom(
    navHostController: NavHostController,
    viewModel: CreateRoomViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val effect by viewModel.onEffect.collectAsStateWithLifecycle(
        initialValue = CreateRoomEffect.None
    )
    val color = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    val ctx = LocalContext.current
    val view = LocalView.current
    val darkTheme: Boolean = isSystemInDarkTheme()
    val window = (view.context as Activity).window

    WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme

    when (effect) {
        CreateRoomEffect.None -> {}
        CreateRoomEffect.OnCreateRoomSuccess -> navHostController.navigateUp()
    }

    DialogLoading(
        visible = state.createRoomLoading
    )

    state.createRoomError?.let {
        Toast(ctx, it).short()
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
            Icon(
                painter = painterResource(id = R.drawable.ic_done),
                contentDescription = "",
                tint = if (state.roomName.isEmpty() || state.additional.isEmpty())
                    color.surfaceVariant else color.primary,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(
                        enabled = state.roomName.isNotEmpty() && state.additional.isNotEmpty() && !state.createRoomLoading,
                        onClick = {
                            viewModel.onAction(CreateRoomAction.CreateRoom)
                        }
                    )
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Tambah Jurusan",
                style = typography.titleLarge,
                color = color.onBackground
            )
            Text(
                text = "Dengan menambahkan angkatan, Anda dapat mengumumkan pengumuman kepada angkatan tertentu saja",
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
                    viewModel.onAction(CreateRoomAction.OnRoomNameValueChange(""))
                },
                value = state.roomName,
                onValueChange = {
                    viewModel.onAction(CreateRoomAction.OnRoomNameValueChange(it))
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
                    viewModel.onAction(CreateRoomAction.OnAdditionalValueChange(""))
                },
                value = state.additional,
                onValueChange = {
                    viewModel.onAction(CreateRoomAction.OnAdditionalValueChange(it))
                }
            )
        }
    }
}

@Preview
@Composable
fun CreateRoomPreview() {
    CinfoTheme {
        CreateRoom(
            navHostController = rememberNavController(),
            viewModel = viewModel()
        )
    }
}