package com.ndc.cinfo.ui.screen.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.ndc.cinfo.ui.navigation.NavRoute
import com.ndc.core.R
import com.ndc.core.ui.component.button.PrimaryButton
import com.ndc.core.ui.component.dialog.DialogLoading
import com.ndc.core.ui.component.textfield.PasswordTextField
import com.ndc.core.ui.component.textfield.PrimaryTextField
import com.ndc.core.ui.component.textfield.TextFieldState
import com.ndc.core.utils.Toast
import com.ndc.core.utils.UiState
import com.ndc.core.utils.isEmailInvalid

@Composable
fun RegisterScreen(
    navHostController: NavHostController,
    registerViewModel: RegisterViewModel = hiltViewModel()
) {
    val ctx = LocalContext.current
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
    var loadingState by rememberSaveable {
        mutableStateOf(false)
    }

    DialogLoading(visible = loadingState)

    val registerState = registerViewModel.registerState.collectAsStateWithLifecycle().value
    LaunchedEffect(registerState) {
        when (registerState) {
            UiState.Empty -> {}
            is UiState.Error -> {
                loadingState = false
                registerButtonEnabled = true
                Toast(ctx, registerState.message).long()
                registerViewModel.clearState()
            }

            UiState.Loading -> {
                loadingState = true
                registerButtonEnabled = false
            }

            is UiState.Success -> {
                loadingState = false
                navHostController.navigate(NavRoute.Home.route) {
                    launchSingleTop = true
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color.background)
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
    ) {
        if (!isSystemInDarkTheme()) {
            Image(
                painter = painterResource(id = R.drawable.register_background),
                contentDescription = "",
                modifier = Modifier
                    .height(203.dp)
            )
        }
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
                                passwordConfirmationState.value = TextFieldState.Empty
                            }

                            it.length > 32 -> {
                                passwordState.value =
                                    TextFieldState.Error("Password maksimal 32 karakter")
                                passwordConfirmationState.value = TextFieldState.Empty
                            }

                            it.isNotEmpty() && passwordConfirmationValue.isNotEmpty()
                                    && it != passwordConfirmationValue -> {
                                passwordState.value = TextFieldState.Empty
                                passwordConfirmationState.value =
                                    TextFieldState.Error("Konfirmasi password tidak sesuai")
                            }

                            else -> {
                                passwordState.value = TextFieldState.Empty
                                passwordConfirmationState.value = TextFieldState.Empty
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
                text = "Daftar",
                enabled = registerButtonEnabled && emailValue.isNotEmpty() && passwordValue.isNotEmpty()
                        && passwordConfirmationValue.isNotEmpty() && passwordValue == passwordConfirmationValue
                        && emailState.value !is TextFieldState.Error && passwordState.value !is TextFieldState.Error
                        && passwordConfirmationState.value !is TextFieldState.Error,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {
                registerViewModel.register(emailValue, passwordValue)
            }
        }
    }
}