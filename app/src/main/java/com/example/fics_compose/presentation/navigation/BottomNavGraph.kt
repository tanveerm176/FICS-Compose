package com.example.fics_compose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fics_compose.domain.model.UserInfo
import com.example.fics_compose.presentation.ui.screens.GlossaryScreen
import com.example.fics_compose.presentation.ui.screens.GoTimeScreen
import com.example.fics_compose.presentation.ui.screens.HistoryScreen
import com.example.fics_compose.presentation.ui.screens.IntroductionScreen
import com.example.fics_compose.presentation.ui.screens.LetsInvestScreen
import com.example.fics_compose.presentation.ui.screens.PortfolioScreen
import com.example.fics_compose.presentation.ui.screens.SimulatorScreen
import com.example.fics_compose.presentation.ui.screens.TutorialScreen
import com.example.fics_compose.presentation.ui.screens.WelcomeScreen

@Composable
fun BottomNavGraph(navController: NavHostController) {

    val userInfo = remember {
        UserInfo()
    }

    NavHost(
        navController = navController,
        startDestination = WelcomeNav.Welcome.route

    ) {
        composable(route = BottomNavBar.Glossary.route) {
            GlossaryScreen()
        }

        composable(route = BottomNavBar.Simulator.route) {
            SimulatorScreen(
                userInfo,
                navigateToHistory = {
                    navController.navigate(BottomNavBar.History.route)
                },
                onShoppingCartClick = {
                    navController.navigate(BottomNavBar.Portfolio.route)
                },
                onSkipClick = {
                    navController.navigate(BottomNavBar.Portfolio.route)
                },
                onResetSimClick = {
                    userInfo.reset()
                    navController.navigate(BottomNavBar.Simulator.route) {
                        popUpTo(navController.graph.startDestinationId) {}
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = BottomNavBar.Portfolio.route) {
            PortfolioScreen(userInfo, onReturnToSimulatorClick = {
                navController.navigate(BottomNavBar.Simulator.route)
            })
        }

        composable(route = WelcomeNav.Welcome.route) {
            WelcomeScreen(onGetStartedClick = {
                navController.navigate(WelcomeNav.Introduction.route)
            })
        }

        composable(route = WelcomeNav.Tutorial.route) {
            TutorialScreen(onTutorialSkipClicked = {
                navController.navigate(WelcomeNav.GoTime.route)
            })
        }
        composable(route = WelcomeNav.Introduction.route) {
            IntroductionScreen(onSkipIntroButtonClicked = {
                navController.navigate(WelcomeNav.LetsInvest.route)
            })
        }

        composable(route = WelcomeNav.LetsInvest.route) {
            LetsInvestScreen(onPlayClicked = {
                navController.navigate(WelcomeNav.Tutorial.route)
            })
        }
        composable(route = WelcomeNav.GoTime.route) {
            GoTimeScreen(onInvestClicked = {
                navController.navigate(BottomNavBar.Simulator.route)
            })
        }

        composable(route = BottomNavBar.History.route) {
            HistoryScreen(onPlayAgainClick = {
                userInfo.reset()
                navController.navigate(BottomNavBar.Simulator.route)
            })
        }
    }

}
