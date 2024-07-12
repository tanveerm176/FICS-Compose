package com.example.fics_compose.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fics_compose.R
import com.example.fics_compose.presentation.ui.theme.lightGray
import com.example.fics_compose.presentation.viewmodel.HistoryViewModel
import com.example.fics_compose.presentation.viewmodel.HistoryViewModelItem


@Composable
fun HistoryScreen(
    onPlayAgainClick: () -> Unit,
    historyViewModel: HistoryViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = lightGray)
    ) {
        Column(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            /* Play Again Button*/
            Button(
                onClick = onPlayAgainClick,
                modifier = Modifier
                    .align(Alignment.End)
                    .border(1.5.dp, Color(0xFF8A191D), RoundedCornerShape(30.dp)),
                elevation = ButtonDefaults.buttonElevation(
                    pressedElevation = 6.dp
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Play Again",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF8A191D),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Text(
                text = "History",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 40.dp, bottom = 20.dp)
            )
            Text(
                text = "Here you can see your performance over time.",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 80.dp, end = 85.dp, bottom = 5.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn {
                items(historyViewModel.historyViewModelList) { historyListItem ->
                    HistoryCard(historyListItem)
                }
            }
        }
    }
}


@Composable
fun HistoryCard(history: HistoryViewModelItem) {
    var isExpanded by remember { mutableStateOf(false) }

    Spacer(modifier = Modifier.height(40.dp))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded }
            .background(
                color = Color.Transparent,
                shape = RectangleShape
            )
            .border(1.dp, Color(0xFF8A191D), RectangleShape)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.2f)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        modifier = Modifier.size(48.dp),
                        color = when {
                            history.roi > 0 -> Color(0xff027148)
                            history.roi < 0.0 -> Color.Red
                            else -> Color.White
                        },
                        shape = CircleShape
                    ) {
                        /* Draw the arrow in the center of the circle*/
                        if (history.roi > 0) {
                            Icon(
                                painter = painterResource(id = R.drawable.roiuparrow),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier
                                    .size(12.dp)
                                    .padding(3.dp),
                            )
                        } else if (history.roi < 0) {
                            Icon(
                                painter = painterResource(id = R.drawable.roidownarrow),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier
                                    .size(12.dp)
                                    .padding(3.dp),
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.blackline),
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.padding(10.dp),
                            )
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(all = 3.dp),
                ) {
                    val sign = if (history.roi > 0) "+" else if (history.roi < 0) "-" else ""
                    Text(
                        /*Display the calculated ROI*/
                        text = "${sign}${history.roi}%",
                        style = MaterialTheme.typography.bodyLarge,
                        color = when {
                            history.roi > 0 -> Color(0xff027148)
                            history.roi < 0.0 -> Color.Red
                            else -> Color.Black
                        },
                    )
                    Text(
                        text = " Return On Investment",
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }

            if (isExpanded) {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 9.dp, bottom = 9.dp)
                )

                /*Final Net Worth UI*/
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(0.4f)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.finalnw),
                                    contentDescription = "Image 1",
                                    modifier = Modifier.size(width = 80.dp, height = 50.dp)
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                            ) {
                                Text(
                                    text = "Final Net Worth",
                                    color = Color(0xFF8A191D),
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(all = 3.dp),
                                )
                                Text(
                                    text = "$${history.netWorth}",
                                    color = Color.Black,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(all = 3.dp),
                                )
                            }
                        }

                        /* Number of Trades UI*/
                        Row(
                            modifier = Modifier
                                .padding(3.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Column(
                                modifier = Modifier
                                    .weight(0.4f)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.numtrades),
                                    contentDescription = "Image 2",
                                    modifier = Modifier.size(width = 80.dp, height = 50.dp)
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                            ) {
                                Text(
                                    text = "Number of Trades",
                                    color = Color(0xFF8A191D),
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(all = 3.dp),

                                    )
                                Text(
                                    text = "${history.trades} trades",
                                    color = Color.Black,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(all = 3.dp),
                                )
                            }
                        }

                        /*Final Wallet UI*/
                        Row(
                            modifier = Modifier
                                .padding(3.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Column(
                                modifier = Modifier
                                    .weight(0.4f)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.finalwallet),
                                    contentDescription = "Image 3",
                                    modifier = Modifier.size(width = 80.dp, height = 50.dp)
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                            ) {
                                Text(
                                    text = "Final Wallet",
                                    color = Color(0xFF8A191D),
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(all = 3.dp),
                                )
                                Text(
                                    text = "$${history.wallet}",
                                    color = Color.Black,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(all = 3.dp),
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
        }
    }
}

@Composable
@Preview
fun HistoryPreview() {
    HistoryScreen(onPlayAgainClick = { Log.d("HistoryButton", "Play Again Clicked") })
}


