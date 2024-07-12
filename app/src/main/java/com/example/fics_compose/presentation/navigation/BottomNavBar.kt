package com.example.fics_compose.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavBar(
    val route: String,
    val title: String,
    val icon: ImageVector
) {

    object Simulator : BottomNavBar(
        route = "simulator",
        title = "Simulator",
        icon = Icons.Default.Home
    )

    object Portfolio {
        val route = "portfolio"
        val title = "Portfolio"
    }

    object Glossary : BottomNavBar(
        route = "glossary",
        title = "Glossary",
        icon = Icons.Default.List
    )

    object History : BottomNavBar(
        route = "history",
        title = "History",
        icon = Icons.Default.DateRange
    )

}

sealed class WelcomeNav(
    val route: String,
    val title: String
) {
    object Welcome : WelcomeNav(
        route = "welcome",
        title = "Welcome"
    )

    object Tutorial : WelcomeNav(
        route = "tutorial",
        title = "Tutorial"
    )

    object Introduction : WelcomeNav(
        route = "introduction",
        title = "Introduction"
    )

    object LetsInvest : WelcomeNav(
        route = "invest",
        title = "invest"
    )

    object GoTime : WelcomeNav(
        route = "go",
        title = "Go"
    )

}
