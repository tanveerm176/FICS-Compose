package com.example.fics_compose

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.fics_compose.screens.GlossaryTopAppBar
import com.example.fics_compose.screens.HistoryTopAppBar
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
            SimulatorTopAppBar(navController)
        }


        composable(route=BottomNavBar.History.route){
            val result = navController.previousBackStackEntry?.savedStateHandle?.get<usrInfo>("port")
            LaunchedEffect(key1 = it ){
                Log.d("wallet", "${result?.wallet}")

            }
            HistoryTopAppBar(result)

        }
    }

}