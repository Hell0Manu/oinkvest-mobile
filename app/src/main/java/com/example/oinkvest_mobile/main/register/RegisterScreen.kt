package com.example.oinkvest_mobile.main.register

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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.oinkvest_mobile.R
import com.example.oinkvest_mobile.presentation.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(navController: NavController, viewModel: AuthViewModel = viewModel()){

    val registerResult by viewModel.registerResult.collectAsState()

    var name by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var passwordConfirm by remember {
        mutableStateOf("")
    }

    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,

        ) {
        Text(
            text = "Comece com a\nOinkvest", style =
                typography.displayMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = colors.onBackground
                ),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        InputRegisterText("Nome", "Digite seu Nome", name, { name = it })
        InputRegisterText("E-mail", "Digite seu E-mail", email, { email = it })
        InputRegisterText("Senha", "Digite sua Senha", password, { password = it }, isPassword = true)
        InputRegisterText("", "Confirme sua Senha", passwordConfirm, { passwordConfirm = it }, isPassword = true)

        ButtonRegister("CRIAR CONTA", { viewModel.register(name, email, password) })

        registerResult ?.let {
            Spacer(modifier = Modifier.height(16.dp))
            if (it == "success") {
                LaunchedEffect(Unit) {
                    navController.navigate("login") {
                        popUpTo("register") { inclusive = true }
                    }
                }
            } else {
                Text(it, color = MaterialTheme.colorScheme.error)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        var enableRegisterButton by remember { mutableStateOf(true) }
        ButtonRegisterGoogle("Continue com Google", { enableRegisterButton = false }, enableRegisterButton)

        Spacer(modifier = Modifier.height(12.dp))

        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
            Text("Já tem uma conta? ",
                color = colors.onBackground,
                style = typography.bodyLarge
            )

            Text(
                text = "Faça login",
                style = typography.bodyLarge,
                color = colors.onBackground,
                modifier = Modifier
                    .clickable(indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = {
                            navController.navigate("login")
                        }),
            )
        }
    }

}

@Composable
fun InputRegisterText(
    title: String,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false
) {
    if(title.isNotBlank()) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = if (isPassword) 4.dp else 15.dp)
    )
}


@Composable
fun ButtonRegister(
    text: String,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp, top = 14.dp)
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
fun ButtonRegisterGoogle(
    text: String,
    onClick: () -> Unit,
    enable: Boolean
){


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
    RegisterScreen(navController = rememberNavController())
}