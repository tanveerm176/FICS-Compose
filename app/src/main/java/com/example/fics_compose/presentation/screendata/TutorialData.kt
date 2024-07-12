package com.example.fics_compose.presentation.screendata

import com.example.fics_compose.R

data class TutorialInfo(
    val title: String,
    val img: Int,
    val description: String
)

object TutorialText {
    val tutorialTextList = listOf(
        TutorialInfo(
            title = "How to Play",
            img = R.drawable.tutorial1,
            description = "Follow the tutorial to learn about the buttons and metrics " +
                    "to make your experience smooth."
        ),
        TutorialInfo(
            "The Goal",
            R.drawable.tutorial2,
            "You have been given \$10,000 and a goal: to make as much money at the " +
                    "end of 12 months as you can. ",
        ),
        TutorialInfo(
            "Key Metrics",
            R.drawable.tutorial3,
            "Note your net worth, investments, federal funds rate, and wallet.",
        ),
        TutorialInfo(
            "Net Worth",
            R.drawable.tutorial4,
            "Your net worth is the combined value of your wallet and investments.",
        ),
        TutorialInfo(
            "Investments",
            R.drawable.tutorial5,
            "Your investments is the sum of the number of bonds multiplied " +
                    "by each of their prices.",
        ),
        TutorialInfo(
            "Wallet",
            R.drawable.tutorial6,
            "Keep track of your wallet, which tells you how much cash you have on " +
                    "hand to invest instantaneously.",
        ),
        /*tutorialInfo(
            "Fed Funds Rate",
            R.drawable.tutorial7,
            "The fed funds rate is the interest rate of the federal government, which indicates
            how well the economy is performing. High fed funds rate means lower bond prices,
            or a weaker economy. Low fed funds rate means higher bond prices, or a strong economy.",
        ),*/
        TutorialInfo(
            "An Intro to Bonds ",
            R.drawable.tutorial8,
            "Bonds provide a way for governments and companies to raise capital for " +
                    "various purposes, such as funding infrastructure projects or " +
                    "expanding business operations. Investors, in turn, are attracted to bonds " +
                    "for their fixed income stream and relative stability compared to more volatile " +
                    "investments like stocks. However, it's important to note that the value " +
                    "of bonds can fluctuate in response to changes in interest rates " +
                    "and other market conditions.",
        ),
        TutorialInfo(
            "Bond Transactions",
            R.drawable.tutorial9,
            "How does a bond transaction work: When an entity issues a bond, " +
                    "it essentially borrows money from bondholders. " +
                    "The bondholder, in turn, becomes a creditor to the issuer. In exchange for " +
                    "lending their money, bondholders receive periodic interest payments " +
                    "(referred to as coupon payments) and, upon maturity, the return of the " +
                    "principal amount they initially invested.",
        ),
        TutorialInfo(
            "How to Invest",
            R.drawable.tutorial10,
            "To invest, select the number of bonds you want to buy and click " +
                    "the buy button."
        ),
        TutorialInfo(
            "Check your portfolio",
            R.drawable.tutorial11,
            "If you want to see your whole portfolio of bonds you already bought, " +
                    "click on the shopping cart. " +
                    "There, you can also sell bonds from your portfolio. " +
                    "That money will go back into your wallet."
        ),
        TutorialInfo(
            "Your Goal",
            R.drawable.tutorial12,
            "The goal is to maximize your net worth by diversifying your portfolio " +
                    "and buying bonds at the best time."
        ),
        TutorialInfo(
            "Need Help?",
            R.drawable.tutorial13,
            "Click on the help button if you want to learn more."
        )
    )
}