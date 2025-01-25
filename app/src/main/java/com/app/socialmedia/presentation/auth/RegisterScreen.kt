package com.app.socialmedia.presentation.auth

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.socialmedia.presentation.components.SnackbarComponent
import com.app.socialmedia.presentation.navigation.Screen


@Composable
fun RegisterScreen(
      registerViewModel: RegisterViewModel = hiltViewModel(),
      navController: NavController
) {
    val uiState = registerViewModel.state.value
    val uiEvents = registerViewModel::onRegisterScreenEvent
    val snackbarHostState = remember { SnackbarHostState() }
    if(uiState.error != null){
        SnackbarComponent(
            snackbarHostState = snackbarHostState,
            snackbarDuration =SnackbarDuration.Long,
            text = uiState.error,
            backgroundColor = Color.Red,
            contentColor = Color.White
            )



    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome Slidee 👋",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Sign up and enjoy our community")

        Spacer(modifier = Modifier.height(24.dp))

        // Username Input Field
        val isUserNameErrors = uiState.usernameErrors.isNotEmpty()
        val usernameErrors = uiState.usernameErrors
        OutlinedTextField(
            value = uiState.name,
            onValueChange = {registerViewModel.onRegisterScreenEvent(RegisterScreenEvents.onNameChange(it))},
            label = { Text("Username") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            isError = isUserNameErrors,
            supportingText ={
                Column{
                if(isUserNameErrors){
                    usernameErrors.forEach { error ->
                        Text(text = error)

                    }
                }
            }
            }
        )

        Spacer(modifier = Modifier.height(5.dp))

        // Email Input Field
        val isEmailError = uiState.emailErrors.isNotEmpty()
        val emailErrors = uiState.emailErrors
        OutlinedTextField(
            value = uiState.email,
            onValueChange = {uiEvents(RegisterScreenEvents.onEmailChange(it))},
            label = { Text("Your Email") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            isError = isEmailError,
            supportingText ={
                Column {


                if(isEmailError){
                    emailErrors.forEach { error ->
                        Text(text = error)

                    }
                }
                }
            }
        )

        Spacer(modifier = Modifier.height(5.dp))


        // Password Input Field
        val isPasswordError = uiState.passwordErrors.isNotEmpty()
        val passwordErrors = uiState.passwordErrors
        OutlinedTextField(
            value = uiState.password,
            onValueChange = {uiEvents(RegisterScreenEvents.onPasswordChange(it))},
            label = { Text("Password") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            trailingIcon = { Icon(Icons.Default.Clear, contentDescription = null) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            isError = isPasswordError,
            supportingText ={
                Column{
                if(isPasswordError){
                   passwordErrors.forEach { error ->
                       Text(text = error)
                   }
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(5.dp))


        // Confirm Password Input Field

        OutlinedTextField(
            value = uiState.passwordConfirmation,
            onValueChange = {uiEvents(RegisterScreenEvents.onPasswordConfirmChange(it))},
            label = { Text("Confirm Password") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            trailingIcon = { Icon(Icons.Default.Clear, contentDescription = null) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(5.dp))
9

        // Terms of Service Text
        Text(
            text = "By continuing you agree to our Terms of Service and Privacy Policy",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Create Account Button
        Button(
            onClick = { uiEvents(RegisterScreenEvents.onRegisterButtonClick) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors( Color.Blue)
        ) {
            Text("Create Account")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sign In Link
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Already Have an Account?")
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Sign In",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {navController.navigate(Screen.LoginScreen.route) }
            )
        }
    }
}
