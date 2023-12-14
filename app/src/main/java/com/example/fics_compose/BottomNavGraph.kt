package com.example.fics_compose

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.fics_compose.screens.GlossaryScreen
import com.example.fics_compose.screens.GoTimeCard
import com.example.fics_compose.screens.GoTimeScreen
import com.example.fics_compose.screens.HistoryTopAppBar
import com.example.fics_compose.screens.IntroductionScreen
import com.example.fics_compose.screens.LetsInvestScreen
import com.example.fics_compose.screens.PortfolioTopAppBar
import com.example.fics_compose.screens.SimulatorTopAppBar
import com.example.fics_compose.screens.TutorialScreen
import com.example.fics_compose.screens.WelcomeScreen

@Composable
fun BottomNavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = WelcomeNav.Welcome.route

    ){
        composable(route=BottomNavBar.Glossary.route){
            GlossaryScreen()
        }

        composable(route=BottomNavBar.Simulator.route){
            SimulatorTopAppBar(navController)
        }

        composable(route=WelcomeNav.Welcome.route){
            WelcomeScreen(navController)
        }
        
        composable(route = WelcomeNav.Tutorial.route){
            TutorialScreen(navController)
        }
        composable(route = WelcomeNav.Introduction.route){
            IntroductionScreen(navController)
        }

        composable(route = WelcomeNav.LetsInvest.route){
            LetsInvestScreen(navController)
        }
        composable(route = WelcomeNav.GoTime.route){
            GoTimeScreen(navController)
        }


        composable(route=BottomNavBar.History.route){
            val result = navController.previousBackStackEntry?.savedStateHandle?.get<usrInfo>("port")
            LaunchedEffect(key1 = it ){
                Log.d("wallet", "${result?.wallet}")

            }
            HistoryTopAppBar(result)

        }
        
        composable(route=InternalNav.Portfolio.route){
            val result = navController.previousBackStackEntry?.savedStateHandle?.get<usrInfo>("portfolio")
            PortfolioTopAppBar(result, navController)
        }

        composable(route=InternalNav.Simulator.route){
            val user = navController.previousBackStackEntry?.savedStateHandle?.get<usrInfo>("user")
            SimulatorTopAppBar(navController, user)
        }
    }

}