package com.example.oinkvest_mobile.main.login


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.oinkvest_mobile.R
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.oinkvest_mobile.presentation.viewmodel.AuthViewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.VisualTransformation
import com.example.oinkvest_mobile.presentation.components.EnableBiometricDialog
import androidx.fragment.app.FragmentActivity
import com.example.oinkvest_mobile.data.local.TokenManager
import com.example.oinkvest_mobile.presentation.components.AnimatedBlurredBackground

@Composable
fun LoginScreen(navController: NavController, viewModel: AuthViewModel = viewModel()) {

    val loginResult by viewModel.loginResult.collectAsState()
    val showBiometricDialog by viewModel.showBiometricDialog.collectAsState()
    val navigateToHome by viewModel.navigateToHome.collectAsState()

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    val context = LocalContext.current
    val activity = context as FragmentActivity

    AnimatedBlurredBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()

                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,

            ) {
            Text(
                text = "Faça login na\nOinkvest", style =
                    typography.displayMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = colors.onBackground
                    ),
                modifier = Modifier.padding(bottom = 24.dp)
            )
            InputLoginText("E-mail", "Digite seu E-mail", email, { email = it })

            InputLoginText(
                "Senha",
                "Digite sua Senha",
                password,
                { password = it },
                isPassword = true
            )

            ButtonLogin("ENTRAR", {
                viewModel.login(email, password, context)
            })

            if (showBiometricDialog) {
                EnableBiometricDialog(
                    onConfirm = { viewModel.onBiometricPromptConfirmed(context) },
                    onDismiss = { viewModel.onBiometricPromptDismissed(context) }
                )
            }

            loginResult?.let { message ->
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                Log.i("LoginScreen", "mensagem: $message")
                viewModel.onNavigationComplete()
            }

            LaunchedEffect(Unit) {
                // Verifica se a biometria está habilitada E se existe um token salvo
                if (TokenManager.isBiometricEnabled(context) && TokenManager.getToken(context) != null) {
                    viewModel.attemptBiometricLogin(activity)
                }
            }

            LaunchedEffect(navigateToHome) {
                if (navigateToHome) {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                    viewModel.onNavigationComplete()
                }
            }

            Text(
                text = "Esqueceu sua senha?",
                color = colors.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = {

                        }),
                textAlign = TextAlign.Center,
                style = typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(40.dp))

            var enableLoginButton by remember { mutableStateOf(true) }
            ButtonLoginGoogle(
                "Continue com Google",
                { enableLoginButton = false },
                enableLoginButton
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    "Não tem uma conta? ",
                    color = colors.onBackground,
                    style = typography.bodyLarge
                )

                Text(
                    text = "Cadastre-se",
                    style = typography.bodyLarge,
                    color = colors.onBackground,
                    modifier = Modifier
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = {
                                navController.navigate("register")
                            }),
                )
            }
        }
    }
}


@Composable
fun InputLoginText(
    title: String,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false
) {

    Text(
        text = title,
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
    )
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = if (isPassword) 4.dp else 20.dp)
    )
}

@Composable
fun ButtonLogin(
    text: String,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp, top = 36.dp)
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
    ) {
        Text(
            text = text,
            color = Color.White,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun ButtonLoginGoogle(
    text: String,
    onClick: () -> Unit,
    enable: Boolean
) {


    Button(
        onClick = onClick,
        enabled = enable,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_google),
            contentDescription = "Google Icon",
            modifier = Modifier.size(20.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            color = MaterialTheme.colorScheme.surface,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    LoginScreen(navController = rememberNavController())
}