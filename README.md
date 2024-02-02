# FICS-Compose

FICS (Fixed Income for College Students) is an interactive Android app that functions as an investment simulation platform for the fixed income market.
Our target audience consists of young adults with a basic understanding of investing, as well as financial educators
looking for engaging resources for their students.

We aim to replicate the world of fixed income investing in order to fill in a large gap in financial education. Users of our
app will first walk through key financial terms/concepts that are important for them to know. Once they’ve read
through these key terms, they will be given a brief explanation of how to use our simulation, and then they will be
prompted to start their investment simulation. They will have one year to invest $10,000 in the fixed income market.
Our simulation will include four major asset types for investing: treasuries, corporate bonds, municipal bonds, TIPS
(treasury inflation protected securities), and securitized (asset-backed) bonds. The vehicles we will provide are mutual
funds and EFTs.

In the simulation, we will go step-by-step, with a new assest type introduced every three months to allow users to understand the differences between asset types.
During the simulation users are able to buy and sell bonds as they wish, allowing them to understand how each decision affects their portfolio.
To further assist in learning about fixed income investing, during the simulation users will have one of their bonds default and be forced to sell that bond, 
allowing them to understand some of the risks associated with fixed income investing.

This app is created using the Android Studio IDE, Kotlin, and Jetpack Compose UI Framework. Aside from the Simulator and Portfolio screens, 
the app follows Model-View-Viewmodel architecture. The app includes a persistent database and uses coroutines.

Users are able to do the following:
[] Navigate Welcome and Tutorial screen which introduce the app, it's features, as well as a brief introduction to Fixed Income Investing
[] Access a Glossary Screen with both formal and informal definitions as well as the ability to search for specific terms
[] Simulate a year of investing in the fixed income market, including being able to buy and sell bonds, respond to bond default risk, and access information about specific bond types
[] After the simulation is complete users are able to access a persistent database via the History Screen to review their performance in their latest simulation as well as past simulation results

The following is required to run FICS:
[] Android Studio Hedgehog | 2023.1.1 Patch 2
[] Android Device or Emulator with API Version 34 or min API Version 31
[] Java Version 1.8

You can view a walkthrough of the app below:
![](https://github.com/tanveerm176/FICS-Compose/blob/main/FICS_Demo2.gif)
