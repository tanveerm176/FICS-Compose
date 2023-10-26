package com.example.fics_compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fics_compose.screens.GlossaryScreen
import com.example.fics_compose.screens.HistoryScreen
import com.example.fics_compose.screens.SimulatorScreen
import com.example.fics_compose.screens.WelcomeScreen

@Composable
fun BottomNavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = BottomNavBar.Welcome.route
    ){
        composable(route=BottomNavBar.Welcome.route){
            WelcomeScreen()
        }

        composable(route=BottomNavBar.Simulator.route){
            SimulatorScreen()
        }

        composable(route=BottomNavBar.Glossary.route){
            GlossaryScreen()
        }

        composable(route=BottomNavBar.History.route){
            HistoryScreen()
        }
    }

}