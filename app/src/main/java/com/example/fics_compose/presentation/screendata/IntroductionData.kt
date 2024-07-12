package com.example.fics_compose.presentation.screendata

import com.example.fics_compose.R

data class IntroductionData(
    val title: String,
    val description: String,
    val img: Int
)

object IntroductionText {
    val introTextList = listOf(
        IntroductionData(
            title = "What is FICS?",
            img = R.drawable.intro1,
            description = "Welcome to the new way for college students to learn about the biggest investment market in the world: the Fixed Income market."
        ),
        IntroductionData(
            title = "Why Now?",
            img = R.drawable.intro2,
            description = "With the global fixed income market reaching almost $130 trillion, there is more opportunity than ever for you to generate income from investing."
        ),
        IntroductionData(
            title = "FICS is Here",
            img = R.drawable.intro3,
            description = "We offer a fun, interactive trading simulation for you to learn firsthand how to invest in bonds – without real money. "
        ),
        IntroductionData(
            title = "Glossary",
            img = R.drawable.intro4,
            description = "Check out the glossary page anytime to read about key terms to help you in our simulator or after you’ve started investing for a refresher."
        ),
    )

}