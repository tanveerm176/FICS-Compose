package com.example.fics_compose.ScreenData

data class Term(val termName: String, val formalDefinition: String, val informalDefinition: String)
data class Topic(val topicName: String, val terms: List<Term>)

object GlossaryData {
    val glossaryTopics = listOf(
        Topic(
            "General",
            listOf(
                Term(
                    "Securities",
                    "Tradable financial instruments that represent ownership or a creditor relationship with an entity. Securities are used by governments, corporations, and other organizations to raise capital. They can be bought, sold, and traded on financial markets.",
                    "Agreements that allow ownership of a tradable asset, such as a loan or a stock."
                ),
                Term(
                    "Fixed Income",
                    "A type of investment in which an investor receives regular interest payments from a principal value of a loan until its maturity date.",
                    "Think of “fixed income” as just what the name implies: a set amount of income that comes in on a regular schedule (could be weekly, quarterly, annually)."
                ),
                Term(
                    "Bond",
                    "A debt security that represents a loan made by an investor to a borrower.",
                    "A bond is a loan.  A bond will have a few characteristics: a principal value, interest rate, and a maturity date."
                ),
                Term(
                    "Principal",
                    "This is the amount of money the bondholder will receive when the bond matures. It is also known as the 'par value' or 'face amount.'",
                    "It is the value of the loan, or the amount of money being lent out."
                ),
                Term(
                    "Interest rate (AKA coupon rate)",
                    "The fixed annual interest rate that the issuer pays to the bondholder. It is expressed as a percentage of the bond's face value. For example, a bond with a face value of $1,000 and a coupon rate of 5% would pay $50 in annual interest ($1,000 * 0.05).",
                    "It is the “fee” associated with borrowing money. The issuer of the bond will give this amount to the bondholder to incentivize them to loan the money to them. The higher the interest rate, the more money will be given to the bondholder, and the more attractive investment to the bondholder."
                ),
                Term(
                    "Coupon Payments",
                    "The interest payments made by the issuer to the bondholder at regular intervals",
                    "The “fee” that the borrower of the bond must pay to the bondholder at set times. Calculated by multiplying the interest rate by the principal amount – but it does NOT decrease the principal that must be paid back at the end of the bond term!"
                ),
                Term(
                    "Issuer",
                    "The entity (government, corporation, or other organization) that borrows money by issuing the bond.",
                    "The one who needed the loan – and got it!"
                ),
                Term(
                    "Bondholder",
                    "Individual or institution that owns one or more bonds issued by a government, municipality, or corporation. When an entity issues a bond, it essentially borrows money from bondholders.",
                    "The one providing money for the loan – they’ll expect an interest rate"
                ),
                Term(
                    "Credit rating",
                    "Bonds are often assigned credit ratings by credit rating agencies. These ratings reflect the issuer's creditworthiness and the likelihood of the issuer defaulting on its debt obligations.",
                    "The general feeling about the bond’s potential to default. The highest credit rating is AAA (the issuer will almost certainly be able to pay back their loan at the end of the term). It can go down to C, which means the bond is nearing default, or D, meaning that the issuer has gone bankrupt"
                ),
                Term(
                    "Portfolio",
                    "Collection of financial assets such as stocks, bonds, cash, and other investments held by an individual, institution, or investment fund. The purpose of creating a portfolio is to diversify risk, optimize returns, and achieve specific financial objectives. Portfolios are managed with the goal of balancing risk and return based on an investor's preferences and investment strategy.",
                    "The current list of investments you’ve made"
                )
            )
        ),
        Topic(
            "Vehicles",
            listOf(
                Term(
                    "Vehicles",
                    "Various investment instruments or structures through which investors can gain exposure to bonds. These vehicles provide a way for investors to buy and sell bonds in the financial markets on a large scale of exposure",
                    "Baskets of bonds picked by portfolio managers"
                ),
                Term(
                    "Mutual Fund",
                    "Bond mutual funds invest in a diversified portfolio of bonds. Investors buy shares in the mutual fund, and the fund manager makes decisions on behalf of the investors. Bond mutual funds can focus on specific types of bonds, such as government bonds, corporate bonds, or municipal bonds.",
                    "Vehicle made up of bonds for people to invest in a lot of different bonds without needing to buy them one by one"
                ),
                Term(
                    "ETF (Exchange-Traded Fund)",
                    "Bond ETFs are similar to bond mutual funds but are traded on stock exchanges like individual stocks. They provide investors with an opportunity to buy and sell bond exposure throughout the trading day at market prices.",
                    "Just like mutual funds, except you can buy/sell them throughout the trading day whereas MF can only be bought/sold at the end of the day. They also follow an index like the S&P 500 or Nasdaq"
                )
            )
        ),
        Topic(
            "Risks",
            listOf(
                Term(
                    "Duration risk (Interest Rate Risk)",
                    "Measure of the sensitivity of the price of a fixed-income security, such as a bond, to changes in interest rates.",
                    "Risk of bond depreciating in value due to changes in the federal interest rate. Federal interest rates and bond prices have an inverse relationship. The higher the interest rate, the lower the price of the bond."
                ),
                Term(
                    "Credit risk (default risk)",
                    "Risk that a borrower may fail to meet its debt obligations, resulting in the non-payment or delayed payment of interest and principal on a loan or debt security.",
                    "Risk of a borrower defaulting, which means they will not be able to pay the bondholder interest payments or principal payments on time or at all"
                )
            )
        ),
        Topic(
            "Treasuries",
            listOf(
                Term(
                    "Treasury bills (T-bills)",
                    "T-Bills are short-term treasuries with maturities ranging from a few days to one year. They are sold at a discount to their face value, and the investor receives the face value when the T-Bill matures. The difference between the purchase price and the face value represents the interest earned.",
                    "Treasuries that mature anywhere from a few days to 52 weeks. Good for quicker money with interest rate payments that come earlier."
                ),
                Term(
                    "Treasury notes (T-notes)",
                    "T-Notes have maturities ranging from two to ten years. They pay a fixed interest rate, also known as the coupon rate, every six months until maturity. At the end of the term, the investor receives the face value of the note.",
                    "Treasuries that mature anywhere from a few days to 52 weeks. Good for moderately quick money with interest rate payments that come earlier"
                ),
                Term(
                    "Treasury bonds (T-bonds)",
                    "T-Bonds have maturities exceeding ten years, typically up to 30 years. Like T-Notes, they pay a fixed interest rate every six months, and the investor receives the face value at maturity.",
                    "Treasuries that mature anywhere from a few days to 52 weeks. Good for slower-paced consistent income with comparatively higher interest rates than bills or notes."
                ),
                Term(
                    "TIPS",
                    "TIPS are designed to protect investors from inflation. The principal amount of TIPS increases with inflation and decreases with deflation, while interest payments are calculated based on the adjusted principal.",
                    "TIPS are the safer investments during times of high inflation. Invest in TIPS during times of volatility or inflated economic conditions."
                )
            )
        ),
        Topic(
            "Other Bonds",
            listOf(
                Term(
                    "Corporate Bond",
                    "Bonds issued by corporations to raise capital for various purposes, such as financing operations, expansion, or acquisitions.",
                    "Loans that companies need to pay for their operations or new developments"
                ),
                Term(
                    "Asset-Backed Securities (ABS)",
                    "Asset-backed securities (ABS) are financial instruments that represent an ownership interest in a pool of assets, typically loans or receivables. These assets can include auto loans, credit card receivables, mortgages, student loans, and other types of debt. The cash flows from the underlying assets are used to make payments to the holders of the asset-backed securities.",
                    "Bonds with collateral attached. If you default on the money amount of the loan, the asset can be seized."
                ),
                Term(
                    "Municipal Bonds (“munis”)",
                    "Bonds issued by local, county and state governments to pay for capital improvements",
                    "Bonds issued by local government and organizations like schools and firehouses to improve operations, like buying new school buses"
                )
            )
        ),
        Topic(
            "Economy",
            listOf(
                Term(
                    "Inflation",
                    "Inflation is the rate at which the general level of prices for goods and services is rising, leading to a decrease in the purchasing power of a currency. In other words, when inflation occurs, each unit of currency buys fewer goods and services than it did before. Inflation is typically expressed as a percentage, representing the annual increase in the average price level of a basket of goods and services.",
                    "Inflation is when the general prices of everything goes higher. Inflation will affect the value of a bond and make it cheaper because the buying power of a dollar has decreased compared to previous times."
                ),
                Term(
                    "Federal Reserve (“fed”)",
                    "Central banking system of the United States. It was created by the U.S. Congress with the passage of the Federal Reserve Act in 1913. The Federal Reserve operates as an independent entity within the government, tasked with several key responsibilities aimed at fostering a stable and effective monetary and financial system in the United States.",
                    "Central bank that controls the flow of money throughout the country. They have control over the federal interest rates, which dictates the rates of treasury instruments."
                ),
                Term(
                    "Federal Interest Rates",
                    "Central banking system of the United States. It was created by the U.S. Congress with the passage of the Federal Reserve Act in 1913. The Federal Reserve operates as an independent entity within the government, tasked with several key responsibilities aimed at fostering a stable and effective monetary and financial system in the United States.",
                    "National bank that controls the flow of money throughout the country. They have control over the federal interest rates, which dictates the rates of treasury instruments."
                )
            )
        )
    )
}
