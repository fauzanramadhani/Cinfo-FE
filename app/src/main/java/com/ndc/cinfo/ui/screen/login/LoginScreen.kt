package com.ndc.cinfo.ui.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ndc.cinfo.R
import com.ndc.cinfo.ui.component.button.OutlinedButton
import com.ndc.cinfo.ui.component.button.OutlinedIconButton
import com.ndc.cinfo.ui.component.button.PrimaryButton
import com.ndc.cinfo.ui.component.textfield.PasswordTextField
import com.ndc.cinfo.ui.component.textfield.PrimaryTextField

@Composable
fun LoginScreen(
    navHostController: NavHostController
) {
    val color = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    val focusManager = LocalFocusManager.current

    var emailValue by rememberSaveable {
        mutableStateOf("")
    }
    var emailError by rememberSaveable {
        mutableStateOf(false)
    }
    var passwordValue by rememberSaveable {
        mutableStateOf("")
    }
    var passwordError by rememberSaveable {
        mutableStateOf(false)
    }
    var passwordVisibility by rememberSaveable {
        mutableStateOf(false)
    }
    var loginButtonEnabled by rememberSaveable {
        mutableStateOf(true)
    }
    var loginGoogleButtonEnabled by rememberSaveable {
        mutableStateOf(true)
    }

    Box(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .background(color.background)
            .safeDrawingPadding()
    ) {
        Image(
            painter = painterResource(id = R.drawable.login_background),
            contentDescription = "",
            modifier = Modifier
                .height(203.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(
                        start = 12.dp,
                    )
            ) {
                Text(
                    text = "Masuk ke ",
                    style = typography.titleLarge,
                    color = color.onBackground
                )
                Text(
                    text = "Cinfo",
                    style = typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = color.primary
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {
                Text(
                    text = "Email",
                    style = typography.labelLarge,
                    color = color.onBackground
                )
                PrimaryTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    placeholder = "Masukan email kamu",
                    value = emailValue,
                    onValueChange = {
                        emailValue = it
                    },
                    onClearValue = {
                        emailValue = ""
                    },
                    error = emailError,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {
                Text(
                    text = "Password",
                    style = typography.labelLarge,
                    color = color.onBackground
                )
                PasswordTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    placeholder = "Masukan password kamu",
                    value = passwordValue,
                    onValueChange = {
                        passwordValue = it
                    },
                    visible = passwordVisibility,
                    onVisibilityChange = {
                        passwordVisibility = it
                    },
                    error = passwordError,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus(true)
                        }
                    )
                )
            }
            Text(
                text = "Lupa password?",
                style = typography.labelLarge,
                color = color.primary,
                modifier = Modifier
                    .padding(start = 12.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier
                    .padding(horizontal = 12.dp)
            ) {
                OutlinedButton(
                    text = "Daftar",
                    modifier = Modifier
                        .weight(1f)
                )
                PrimaryButton(
                    text = "Masuk",
                    enabled = loginButtonEnabled,
                    modifier = Modifier
                        .weight(1f)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Divider(
                    color = color.outline,
                    thickness = 0.5.dp,
                    modifier = Modifier
                        .weight(1f)
                )
                Text(
                    text = "ATAU",
                    style = typography.bodySmall,
                    color = color.onSurfaceVariant,
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                )
                Divider(
                    color = color.outline,
                    thickness = 0.5.dp,
                    modifier = Modifier
                        .weight(1f)
                )
            }
            OutlinedIconButton(
                enabled = loginGoogleButtonEnabled,
                text = "Masuk dengan google",
                icon = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_google),
                        contentDescription = ""
                    )
                },
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth()
            )
        }
    }
}