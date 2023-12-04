package com.example.fics_compose.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.*
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.fics_compose.ui.theme.FICSComposeTheme
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GlossaryTopAppBar() {
    var scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.secondary,
                ),
                title = {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text("Glossary")
                    }
                }
            )
            scrollBehavior = scrollBehavior
        },
    ) {innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)){
            GlossaryScreen()
        }
    }
}

//@Composable
//fun GlossaryScreen() {
//
//    GlossaryList(GlossaryData.glossaryTopics)
//}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun GlossaryScreen() {
    var searchTerm by remember { mutableStateOf("") }
    var glossary by remember { mutableStateOf(GlossaryData.glossaryTopics) }

    val keyboardController = LocalSoftwareKeyboardController.current

    // Filter glossary based on search term
    val filteredGlossary = glossary.filter { topic ->
        topic.topicName.contains(searchTerm, ignoreCase = true) ||
                topic.terms.any { term -> term.termName.contains(searchTerm, ignoreCase = true) }
    }

    // Display the filtered glossary
    Column {
        // Add a search bar
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            value = searchTerm,
            onValueChange = {
                searchTerm = it
                // Update glossary when the search term changes
                glossary = if (it.isEmpty()) {
                    GlossaryData.glossaryTopics
                } else {
                    filteredGlossary
                }
            },
            placeholder = { Text("Search Glossary") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    // Perform search action if needed
                    // For example, you can trigger a network request to search for the term
                    // or update the glossary based on the local data.
                    keyboardController?.hide()
                }
            )
        )

        // Display the glossary based on the search results
        GlossaryList(filteredGlossary)
    }
}
data class Term(val termName: String, val termDef: String)
data class Topic(val topicName: String, val terms: List<Term>)


@Composable
//fun DefinitionCard(term: Term) {
//    var isExpanded by remember { mutableStateOf(false) }
//    Row(modifier = Modifier.padding(all = 8.dp)) {
//
//        Spacer(modifier = Modifier.width(8.dp))
//
//        Column {
//            Text(
//                text = term.termName,
//                color = MaterialTheme.colorScheme.primary,
//                style = MaterialTheme.typography.titleMedium,
//                maxLines = 1,
//                overflow = TextOverflow.Ellipsis
//            )
//
//            Spacer(modifier = Modifier.height(4.dp))
//
//            Surface(shape = MaterialTheme.shapes.medium, shadowElevation = 5.dp) {
//                Column(
//                    modifier = Modifier
//                        .padding(all = 4.dp)
//                        .animateContentSize()
//                        .heightIn(min = if (isExpanded) 100.dp else 20.dp)
//                ) {
//                    Text(
//                        text = term.termDef,
//                        style = MaterialTheme.typography.bodyMedium,
//                        overflow = TextOverflow.Ellipsis,
//                        maxLines = if (isExpanded) Int.MAX_VALUE else 2,
//                        modifier = Modifier.clickable { isExpanded = !isExpanded }
//                    )
//                }
//            }
//
//        }
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
fun DefinitionCard(term: Term) {
    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f, label = "rotateState"
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        shape = RoundedCornerShape(10.dp),
        onClick = {
            expandedState = !expandedState
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(6f),
                    text = term.termName,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                IconButton(
                    modifier = Modifier
                        .weight(1f)
                        .alpha(ContentAlpha.medium)
                        .rotate(rotationState),
                    onClick = {
                        expandedState = !expandedState
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Drop-Down Arrow"
                    )
                }
            }

            // Use the isExpanded property to decide whether to display the definition
            if (expandedState) {
                // Content of the term definition goes here
                Text(text = term.termDef, modifier = Modifier.padding(1.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableCard(
    topic: Topic,
) {
    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f, label = "rotateState"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        shape = RoundedCornerShape(10.dp),
        onClick = {
            expandedState = !expandedState
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(6f),
                    text = topic.topicName,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                IconButton(
                    modifier = Modifier
                        .weight(1f)
                        .alpha(ContentAlpha.medium)
                        .rotate(rotationState),
                    onClick = {
                        expandedState = !expandedState
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Drop-Down Arrow"
                    )
                }
            }
            if (expandedState) {
//                Text("Expanded Content")
                Column {
                    topic.terms.forEach { term ->
                        DefinitionCard(term)
                    }
                }
            }
        }
    }
}

@Composable
fun GlossaryList(glossary: List<Topic>) {
    LazyColumn {
        items(glossary) { topic ->
            ExpandableCard(topic)
        }
    }
}

object GlossaryData {
    val glossaryTopics = listOf(
        Topic(
            "Topic 1",
            listOf(
                Term("Fixed Income",
                    "Fixed income is a type of investment that pays the investor a fixed amount on a fixed schedule."
                ),
                Term("Fixed Income",
                    "Fixed income is a type of investment that pays the investor a fixed amount on a fixed schedule."
                ),
                Term(
                    "Bond",
                    "In finance, a bond is a type of security under which the issuer owes the holder a debt, " +
                            "and is obliged – depending on the terms – to provide cash flow to the creditor."
                ),
            )
        ),
        Topic(
            "Topic 2",
            listOf(
                Term("Roth IRA",
                    "A Roth IRA is an individual retirement account under United States law that is generally " +
                            "not taxed upon distribution, provided certain conditions are met."
                ),
                Term("Vehicles",
                    "a financial account or product used to create returns. In other words, it generally refers to any " +
                            "container investors use to grow their money. This includes individual securities such as stocks and bonds or pooled investments like mutual funds and ETFS."
                ),
                Term(
                    "Mutual Fund",
                    "A mutual fund is a type of investment vehicle consisting of a portfolio of stocks, bonds, or " +
                            "other securities. It gives small or individual investors access to diversified, professionally managed portfolios. Instead of investing in a single stock or bond, by purchasing shares of a mutual fund, the investor can reap the benefits of a diversified portfolio."
                ),
            )
        ),
        Topic(
            "Topic 3",
            listOf(
                Term("ETF (Exchange-Traded Fund)",
                    "a type of pooled investment security that operates much like a mutual fund, but unlike mutual funds, " +
                            "ETFs can be purchased or sold on a stock exchange the same way that a regular stock can. " +
                            "This means that ETF share prices fluctuate all day as the ETF is bought and sold; this is different from mutual funds, which only trade once a day after the market closes. " +
                            "ETFs generally also have less fees associated with it than mutual funds."
                ),
                Term("Bond",
                    ("A bond is a debt security, which means borrowers issue bonds to raise money from investors willing to " +
                            "lend them money for a certain amount of time. When you buy a bond, you are lending to the issuer, " +
                            "which may be a government, municipality, or corporation. In return, the issuer promises to pay you a " +
                            "specified rate of interest during the life of the bond and to repay the principal, also known as " +
                            "face value or par value of the bond, when it \"matures,\" or comes due after a set period of time.".trim())
                ),
                Term(
                    "Coupon Payment",
                    ("A coupon or coupon payment is the annual interest rate paid on a bond.".trim())
                ),
                Term(
                    "Principal",
                    ("The principal refers to the face value of the bond, that is, the money the investor lent " +
                            "to the bond issuer, which the issuer promises to pay back at the maturity date.").trim()
                ),
                Term(
                    "Maturity Date",
                    ("the date on which the final payment is due on the bond, at which point the principal " +
                            "(and all remaining interest) is due to be paid.").trim()
                ),
            )
        ),
        Topic(
            "Topic 4",
            listOf(
                Term(
                    "Interest Rate Risk",
                    ("Interest rate risk is the potential that a change in overall interest rates " +
                            "(determined by the Federal Reserve) will reduce the value of a bond or other fixed-rate investment. " +
                            "As interest rates rise, bond prices fall, and vice versa.").trim()
                ),
                Term(
                    "Treasury Bond (T-bonds)",
                    ("Treasury bonds (T-bonds) are fixed-rate U.S. government debt securities with a maturity of 20 or 30 years. " +
                            "They are regarded as virtually risk-free since they are backed by the U.S. government's ability to tax its citizens.").trim()
                ),
                Term(
                    "Corporate Bond",
                    ("A corporate bond is debt issued by a company in order for it to raise capital. They are generally seen as riskier than " +
                            "US government bonds and often have higher interest rates to compensate for the higher risk.").trim()
                ),
            )
        ),
        Topic(
            "Topic 5",
            listOf(
                Term(
                    "Asset-Backed Securities (ABS)",
                    "Asset-backed securities (ABSs) are financial securities backed by a pool of income-generating assets such as credit card receivables, " +
                            "home equity loans, student loans, and auto loans. Mortgage-backed securities (MBS) are a type of asset-backed securities " +
                            "where the assets are mortgages. Collateral debt obligations (CDOs) are also a type of asset-backed securities in which the assets are debt obligations."
                ),
                Term(
                    "Inflation",
                    "Inflation is the rise in prices for goods and services, which can be translated as the decline of purchasing power over time. " +
                            "This means that a unit of currency effectively buys less than it did in prior periods."
                ),
            )
        )
    )
}

@Preview
@Composable
fun PreviewGlossary() {
    FICSComposeTheme {
        GlossaryScreen()
    }
}