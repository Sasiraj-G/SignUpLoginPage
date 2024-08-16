package com.example.loginsignuppage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.loginsignuppage.BottombarPage.Home
import com.example.loginsignuppage.BottombarPage.NavItem
import com.example.loginsignuppage.BottombarPage.Notification
import com.example.loginsignuppage.BottombarPage.Settings

@Composable
fun HomePage(modifier: Modifier = Modifier,navController: NavController,authViewModel: AuthViewModel) {
 val authState=authViewModel.authState.observeAsState()

 LaunchedEffect(authState.value) {
     when(authState.value){
         is AuthState.Unauthenticated -> navController.navigate("login")
         else -> Unit
     }

 }

    Column(
        modifier=Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "SignOut", fontSize = 35.sp)
        TextButton(onClick = {
            authViewModel.signout()
        }) {
            Text(text = "Sign Out")

        }
        BottomScreen()

    }
}

@Composable
fun BottomScreen(){
    var selectedIndex by remember {
        mutableIntStateOf(0)
    }
    val navItemList= listOf(
        NavItem("Home", Icons.Default.Home),
        NavItem("Notification",Icons.Default.Notifications),
        NavItem("Settings",Icons.Default.Settings)
    )
        Scaffold(modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                navItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                        },
                        icon = { 
                            Icon(imageVector = navItem.icon, contentDescription ="Icon" )
                        }, label = {
                            Text(text = navItem.label)
                        })
                }
            }
        }
        ) { innerPadding ->
            ContentScreen(modifier = Modifier.padding(innerPadding),selectedIndex)
        
    }
    
}
@Composable
fun ContentScreen(modifier: Modifier = Modifier,selectedIndex : Int){
    when(selectedIndex){
        0 -> Home()
        1 -> Notification()
        2 -> Settings()
    }
    
}