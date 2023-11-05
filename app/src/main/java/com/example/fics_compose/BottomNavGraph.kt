package com.example.fics_compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.fics_compose.screens.GlossaryTopAppBar
import com.example.fics_compose.screens.HistoryTopAppBar
import com.example.fics_compose.screens.SimulatorDialog
import com.example.fics_compose.screens.SimulatorTopAppBar

@Composable
fun BottomNavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = BottomNavBar.Simulator.route
    ){
        composable(route=BottomNavBar.Glossary.route){
            GlossaryTopAppBar()
        }

        /*composable(route=BottomNavBar.Welcome.route){
            WelcomeScreen()
        }*/

        composable(route=BottomNavBar.Simulator.route){
            SimulatorTopAppBar()
        }


        composable(route=BottomNavBar.History.route){
            HistoryTopAppBar()
        }
    }

}