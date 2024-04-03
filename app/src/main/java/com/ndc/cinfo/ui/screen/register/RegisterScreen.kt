package com.ndc.cinfo.ui.screen.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ndc.cinfo.R
import com.ndc.cinfo.ui.component.button.PrimaryButton
import com.ndc.cinfo.ui.component.textfield.PasswordTextField
import com.ndc.cinfo.ui.component.textfield.PrimaryTextField
import com.ndc.cinfo.ui.component.textfield.TextFieldState
import com.ndc.cinfo.utils.isEmailInvalid

@Composable
fun RegisterScreen(
    navHostController: NavHostController
) {
    val color = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    val focusManager = LocalFocusManager.current

    var emailValue by rememberSaveable {
        mutableStateOf("")
    }
    val emailState = remember {
        mutableStateOf<TextFieldState>(TextFieldState.Empty)
    }
    var passwordValue by rememberSaveable {
        mutableStateOf("")
    }
    val passwordState = remember {
        mutableStateOf<TextFieldState>(TextFieldState.Empty)
    }
    var passwordVisibility by rememberSaveable {
        mutableStateOf(false)
    }
    var passwordConfirmationValue by rememberSaveable {
        mutableStateOf("")
    }
    val passwordConfirmationState = remember {
        mutableStateOf<TextFieldState>(TextFieldState.Empty)
    }
    var passwordConfirmationVisibility by rememberSaveable {
        mutableStateOf(false)
    }
    var registerButtonEnabled by rememberSaveable {
        mutableStateOf(true)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color.background)
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = painterResource(id = R.drawable.register_background),
            contentDescription = "",
            modifier = Modifier
                .height(203.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 88.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_left),
                contentDescription = "",
                tint = color.primary,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .clip(CircleShape)
                    .clickable {
                        navHostController.navigateUp()
                    }
            )
            Row(
                modifier = Modifier
                    .padding(
                        start = 12.dp,
                    )
            ) {
                Text(
                    text = "Daftar ke ",
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
                    textFieldState = emailState.value,
                    placeholder = "Masukan email kamu",
                    value = emailValue,
                    onValueChange = {
                        when {
                            (it.isEmailInvalid() && it.isNotEmpty()) -> {
                                emailState.value = TextFieldState.Error("Email tidak valid")
                            }

                            it.length > 32 -> {
                                emailState.value =
                                    TextFieldState.Error("Email maksimal 32 karakter")
                            }

                            else -> {
                                emailState.value = TextFieldState.Empty
                            }
                        }
                        emailValue = it
                    },
                    onClearValue = {
                        emailValue = ""
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
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
                    textFieldState = passwordState.value,
                    placeholder = "Masukan password kamu",
                    value = passwordValue,
                    onValueChange = {
                        when {
                            it.isNotEmpty() && it.length < 8 -> {
                                passwordState.value =
                                    TextFieldState.Error("Password minimal 8 karakter")
                            }

                            it.length > 32 -> {
                                passwordState.value =
                                    TextFieldState.Error("Password maksimal 32 karakter")
                            }

                            else -> {
                                passwordState.value = TextFieldState.Empty
                            }
                        }
                        passwordValue = it
                    },
                    visible = passwordVisibility,
                    onVisibilityChange = {
                        passwordVisibility = it
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {
                Text(
                    text = "Konfirmasi Password",
                    style = typography.labelLarge,
                    color = color.onBackground
                )
                PasswordTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textFieldState = passwordConfirmationState.value,
                    placeholder = "Masukan password kamu lagi",
                    value = passwordConfirmationValue,
                    onValueChange = {
                        when {
                            it.isNotEmpty() && passwordValue != it -> {
                                passwordConfirmationState.value =
                                    TextFieldState.Error("Konfirmasi password tidak sesuai")
                            }

                            else -> {
                                passwordConfirmationState.value = TextFieldState.Empty
                            }
                        }
                        passwordConfirmationValue = it
                    },
                    visible = passwordConfirmationVisibility,
                    onVisibilityChange = {
                        passwordConfirmationVisibility = it
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus(true)
                        }
                    ),
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .align(Alignment.BottomCenter),
            contentAlignment = Alignment.Center,
        ) {
            PrimaryButton(
                text = "Dafar",
                enabled = registerButtonEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            )
        }
    }
}