package com.example.fics_compose.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

data class AssestInfo(
    val netWorthList: List<Double>
)

val LightOlive = Color(0xFFacc8b2)
val LightCarmin = Color(0xFFe7978b)

@Composable
fun PerfomanceChart(modifier: Modifier = Modifier, list: List<Double> = mockData.netWorthList) {
    var floatList: List<Float> = list.map { it.toFloat() }

    val zipList: List<Pair<Float, Float>> = floatList.zipWithNext()
Card {
    Row {
        val max = floatList.max()
        val min = floatList.min()

        val lineColor = if (floatList.last() > floatList.first()) LightOlive else LightCarmin

        for (pair in zipList) {

            val fromValuePercentage = getValuePercentageForRange(pair.first, max, min)
            val tovaluePercentage = getValuePercentageForRange(pair.second, max, min)

            Canvas(
                modifier = Modifier
                    .fillMaxHeight(),
                onDraw = {
                    val fromPoint = Offset(x = 0f, y = size.height.times(1 - fromValuePercentage))
                    val toPoint =
                        Offset(x = size.width, y = size.height.times(1 - tovaluePercentage))

                    drawLine(
                        color = lineColor,
                        start = fromPoint,
                        end = toPoint,
                        strokeWidth = 3f
                    )
                }
            )
        }

    }

}

}

private fun getValuePercentageForRange(value: Float, max: Float, min: Float) =
    (value - min) / (max - min)

val mockData = AssestInfo(
    listOf(
        10000.00,
        10500.00,
        10700.00,
        10900.00,
        12000.00,
        12300.00,
        15600.00,
        14600.00,
        13300.00,
        12100.00,
        13200.00,
        11100.00


    )
)

@Composable
@Preview
fun PreviewChart(){
    PerfomanceChart()
}