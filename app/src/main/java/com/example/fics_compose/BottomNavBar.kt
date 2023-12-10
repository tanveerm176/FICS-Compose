package com.example.fics_compose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavBar(
    val route: String,
    val title: String,
    val icon: ImageVector
) {

    object Simulator:BottomNavBar(
        route = "simulator",
        title = "Simulator",
        icon = Icons.Default.Home
    )

    object Glossary:BottomNavBar(
        route = "glossary",
        title = "Glossary",
        icon = Icons.Default.List
    )

    object History:BottomNavBar(
        route = "history",
        title = "History",
        icon = Icons.Default.DateRange
    )

}

sealed class WelcomeNav(
    val route: String,
    val title: String
){
    object Welcome: WelcomeNav(
        route = "welcome",
        title = "Welcome"
    )

    object Tutorial:WelcomeNav(
        route = "tutorial",
        title = "Tutorial"
    )

    object Introduction:WelcomeNav(
        route = "introduction",
        title = "Introduction"
    )

    object LetsInvest:WelcomeNav(
        route = "invest",
        title = "invest"
    )
    object GoTime:WelcomeNav(
        route = "go",
        title = "Go"
    )

}

sealed class InternalNav(
    val route: String,
    val title: String,
){
    object Portfolio: InternalNav(
        route = "portfolio",
        title = "Portfolio"
    )

    object Simulator: InternalNav(
        route = "simulator",
        title = "Simulator"
    )

}