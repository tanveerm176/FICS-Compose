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
    /*object Welcome:BottomNavBar(
        route = "welcome",
        title = "Welcome",
        icon = Icons.Default.Home
    )*/

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