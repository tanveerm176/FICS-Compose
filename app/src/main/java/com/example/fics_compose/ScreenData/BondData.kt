package com.example.fics_compose.ScreenData

import com.example.fics_compose.R


data class BondOption(
    val title: String,
    val price: Double,
    val interestRate: Double,
    val img: Int
)
object BondData{
    val BondDataList = listOf(
        BondOption(
            title = "Treasury Bill",
            img = R.drawable.treasuries,
            price = 100.00,
            interestRate = 2.00
        ),
        BondOption(
            title = "Treasury Note",
            img = R.drawable.treasuries,
            price = 200.00,
            interestRate = 3.00,
        ),
        BondOption(
            title = "Treasury Bond",
            img = R.drawable.treasuries,
            price = 500.00,
            interestRate = 0.5,
        ),
        BondOption(
            title = "Apple",
            img = R.drawable.apple,
            price = 500.00,
            interestRate = 0.5,
        ),
        BondOption(
            title = "Twitter",
            img = R.drawable.twitter,
            price = 500.00,
            interestRate = 0.5,
        ),
        BondOption(
            title = "AT&T",
            img = R.drawable.att,
            price = 500.00,
            interestRate = 0.5,
        ),
        BondOption(
            title = "FTX",
            img = R.drawable.fedinterestrateup,
            price = 500.00,
            interestRate = 0.5,
        ),
        BondOption(
            title = "Asset Backed",
            img = R.drawable.assetbacked,
            price = 500.00,
            interestRate = 0.5,
        ),
        BondOption(
            title = "TIPS",
            img = R.drawable.tips,
            price = 500.00,
            interestRate = 0.5,
        ),
        BondOption(
            title = "Municipal Bond",
            img = R.drawable.mutualfunds,
            price = 500.00,
            interestRate = 0.5,
        ),
        BondOption(
            title = "Munis",
            img = R.drawable.munis,
            price = 500.00,
            interestRate = 0.5,
        ),
        BondOption(
            title = "ETFs",
            img = R.drawable.etf,
            price = 500.00,
            interestRate = 0.5,
        ),
    )
}