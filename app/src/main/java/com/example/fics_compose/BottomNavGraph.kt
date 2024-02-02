package com.example.fics_compose

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.fics_compose.screens.GlossaryScreen
import com.example.fics_compose.screens.GoTimeScreen
import com.example.fics_compose.screens.HistoryScreen
import com.example.fics_compose.screens.IntroductionScreen
import com.example.fics_compose.screens.LetsInvestScreen
import com.example.fics_compose.screens.PortfolioScreen
import com.example.fics_compose.screens.SimulatorScreen
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
            SimulatorScreen(navController)
        }

        composable(route=WelcomeNav.Welcome.route){
            WelcomeScreen(onGetStartedClick = {
                navController.navigate(WelcomeNav.Introduction.route)
            })
        }
        
        composable(route = WelcomeNav.Tutorial.route){
            TutorialScreen(onTutorialSkipClicked = {
                navController.navigate(WelcomeNav.GoTime.route)
            })
        }
        composable(route = WelcomeNav.Introduction.route){
            IntroductionScreen(onSkipIntroButtonClicked = {
                navController.navigate(WelcomeNav.LetsInvest.route)
            })
        }

        composable(route = WelcomeNav.LetsInvest.route){
            LetsInvestScreen(onPlayClicked = {
                navController.navigate(WelcomeNav.Tutorial.route)
            })
        }
        composable(route = WelcomeNav.GoTime.route){
            GoTimeScreen(onInvestClicked = {
                navController.navigate(BottomNavBar.Simulator.route)
            })
        }

        composable(route=BottomNavBar.History.route){
            HistoryScreen(onPlayAgainClick = {
                navController.navigate(BottomNavBar.Simulator.route)
            })
        }
        
        composable(route=InternalNav.Portfolio.route){
            val result = navController.previousBackStackEntry?.savedStateHandle?.get<UserInfo>("portfolio")
            PortfolioScreen(result, navController)
        }

        composable(route=InternalNav.Simulator.route){
            val user = navController.previousBackStackEntry?.savedStateHandle?.get<UserInfo>("user")
            SimulatorScreen(navController, user)
        }
    }

}