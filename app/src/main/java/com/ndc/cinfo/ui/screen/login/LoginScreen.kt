package com.ndc.cinfo.ui.screen.login

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.ndc.cinfo.ui.navigation.NavRoute
import com.ndc.core.R
import com.ndc.core.ui.component.button.OutlinedButton
import com.ndc.core.ui.component.button.OutlinedIconButton
import com.ndc.core.ui.component.button.PrimaryButton
import com.ndc.core.ui.component.dialog.DialogChangeServerAddress
import com.ndc.core.ui.component.dialog.DialogLoading
import com.ndc.core.ui.component.textfield.PasswordTextField
import com.ndc.core.ui.component.textfield.PrimaryTextField
import com.ndc.core.ui.component.textfield.TextFieldState
import com.ndc.core.utils.Toast
import com.ndc.core.utils.UiState
import com.ndc.core.utils.isEmailInvalid
import com.ndc.core.utils.rememberRestartActivity

@Composable
fun LoginScreen(
    navHostController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val ctx = LocalContext.current
    val view = LocalView.current
    val darkTheme: Boolean = isSystemInDarkTheme()
    val window = (view.context as Activity).window
    val color = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    val focusManager = LocalFocusManager.current
    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(), onResult = { result ->
            result.data?.let {
                loginViewModel.handleLoginWithGoogle(it)
            }
        }
    )

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
    var loginButtonEnabled by rememberSaveable {
        mutableStateOf(true)
    }
    var loginGoogleButtonEnabled by rememberSaveable {
        mutableStateOf(true)
    }
    var loadingState by rememberSaveable {
        mutableStateOf(false)
    }
    var updateAddressDialogShow by rememberSaveable {
        mutableStateOf(false)
    }
    var updateAddressDialogValue by rememberSaveable {
        mutableStateOf("")
    }
    val restartApp = rememberRestartActivity(activity = (ctx as ComponentActivity))

    WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme

    DialogLoading(visible = loadingState)

    BackHandler {
        (ctx as Activity).finish()
    }

    val loginState = loginViewModel.loginState.collectAsStateWithLifecycle().value
    LaunchedEffect(loginState) {
        when (loginState) {
            UiState.Empty -> {}
            is UiState.Error -> {
                Toast(ctx, loginState.message).long()
                loadingState = false
                loginButtonEnabled = true
                loginGoogleButtonEnabled = true
                loginViewModel.clearState()
            }

            UiState.Loading -> {
                loadingState = true
                loginButtonEnabled = false
                loginGoogleButtonEnabled = false
            }

            is UiState.Success -> {
                loadingState = false
                navHostController.navigate(NavRoute.Home.route) {
                    launchSingleTop = true
                }
            }
        }
    }

    if (updateAddressDialogShow) {
        DialogChangeServerAddress(
            modifier = Modifier.padding(24.dp),
            addressValue = updateAddressDialogValue,
            onAddressChange = {
                updateAddressDialogValue = it
            },
            onClearValue = {
                updateAddressDialogValue = ""
            },
            onDismiss = {
                updateAddressDialogShow = false
            },
            onConfirm = {
                loginViewModel.updateServerAddress(updateAddressDialogValue)
                    .also {
                        restartApp()
                    }
            }
        )
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
                painter = painterResource(id = R.drawable.login_background),
                contentDescription = "",
                modifier = Modifier
                    .height(203.dp)
            )
        }
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
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus(true)
                        }
                    ),
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
                ) {
                    navHostController.navigate(NavRoute.Register.route) {
                        launchSingleTop = true
                    }
                }
                PrimaryButton(
                    text = "Masuk",
                    enabled = loginButtonEnabled && emailState.value !is TextFieldState.Error
                            && passwordState.value !is TextFieldState.Error && emailValue.isNotEmpty()
                            && passwordValue.isNotEmpty(),
                    modifier = Modifier
                        .weight(1f)
                ) {
                    loginViewModel.loginBasic(emailValue, passwordValue)
                }
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
            ) {
                googleSignInLauncher.launch(loginViewModel.loginWithGoogleIntent())
            }
            PrimaryButton(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth(),
                text = "Perbarui Alamat Server"
            ) {
                updateAddressDialogShow = true
            }
        }
    }
}