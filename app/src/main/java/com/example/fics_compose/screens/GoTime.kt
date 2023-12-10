package com.example.fics_compose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.fics_compose.BottomNavBar
import com.example.fics_compose.R

@Composable
fun GoTimeScreen(navController: NavController){
    GoTimeCard(navController)
}

@Composable
fun GoTimeCard(navController: NavController){
    Column {
        Text(text = "It's Go Time")
        Text(text = "You have 24 months to make as much money as you can.")

        Button(onClick = { startSimulatorScreen(navController)}) {
            Text(text = "INVEST")
        }

        Image(painter = painterResource(id = R.drawable.ssicon), contentDescription = "None")
    }
}

fun startSimulatorScreen(navController: NavController){
    navController.navigate(BottomNavBar.Simulator.route)
}