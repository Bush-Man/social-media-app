package com.app.socialmedia.presentation.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(3.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {


                Text(
                    text = "Welcome Slidee 👋",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Login to your account")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Email Input Field
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Email") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text(text = "Password")},
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            trailingIcon = {Icon(Icons.Default.Clear, contentDescription = null)},
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = false, onCheckedChange = {})
                Text(text = "Remember Me")
            }
            Text(text = "Forgot Password ?")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
            Text(text = "Don't have an account ? ", textAlign = TextAlign.Center)
            TextButton(onClick = { /*TODO*/ }) {
                Text(text = "Register", textDecoration = TextDecoration.Underline, color = Color.Blue)
                
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        LoginButton(text = "log in", onClick = {})
        Spacer(modifier = Modifier.height(10.dp))
         Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
             Text(
                 text = "or ", textAlign = TextAlign.Center)
         }

        Spacer(modifier = Modifier.height(10.dp))
        LoginButton(text = "log in with Google", onClick = {})




    }

}

@Composable
private fun LoginButton(
    text:String,
    onClick:()->Unit
){
    Button(
        onClick = {  },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(Color.Blue),

    ) {
        Text(text)
    }
}