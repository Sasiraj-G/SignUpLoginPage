package com.example.loginsignuppage

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlin.random.Random

@Composable
fun SignUpPage(modifier: Modifier = Modifier,navController: NavController,authViewModel: AuthViewModel) {
    var email by remember {
        mutableStateOf("")
    }
    var pass by remember {
        mutableStateOf("")
    }
    var context = LocalContext.current
    var passwordVisibility by remember {
        mutableStateOf(false)
    }
    val icon= if (passwordVisibility){
        painterResource(id = R.drawable.eye_open_icon)
    }else{
        painterResource(id = R.drawable._eye_hidden_icon)
    }




    val authState= authViewModel.authState.observeAsState()
    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Authenticated -> navController.navigate("home")
            is AuthState.Error -> Toast.makeText(context,
                (authState.value as AuthState.Error).message,Toast.LENGTH_SHORT).show()
            else -> Unit
        }

    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = "SignUp Page", fontSize = 35.sp)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = email,
            onValueChange ={
            email=it
        }, placeholder = { Text(text = "Password")},label = {
            Text(text = "Email")
        })
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = pass, onValueChange ={
            pass=it
        }, placeholder = { Text(text = "Password")},
            label = { Text(text = "Password") },
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(painter = icon, contentDescription ="visiblity icon",Modifier.size(28.dp) )

                }

            }, visualTransformation = if(passwordVisibility) VisualTransformation.None
            else PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(25.dp))
        Button(onClick = {
            authViewModel.signup(email,pass)

        }, enabled = authState.value != AuthState.Loading) {
            Text(text = "Create account")
        }
        Spacer(modifier = Modifier.height(20.dp))
        TextButton(onClick = {
            navController.navigate("login")
        }) {
            Text(text = "Already have an account, Login")

        }

    }
}