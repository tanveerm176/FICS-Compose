package com.example.fics_compose.screens

import android.graphics.Paint
import android.graphics.PointF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fics_compose.ui.theme.FICSComposeTheme
import com.example.fics_compose.usrInfo

@Composable
fun Graph(
//    modifier: Modifier,
    xVals: List<Int>,
    yVals: List<Int>,
//    points: List<Float>,
    paddingSpace: Dp = 16.dp,
    verticalStep: Int
){
    val controlPoints1 = mutableListOf<PointF>()
    val controlPoints2 = mutableListOf<PointF>()
    val coordinates = mutableListOf<PointF>()
    val density = LocalDensity.current
    val textPaint = remember(density) {
        Paint().apply {
            color = android.graphics.Color.BLACK
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(horizontal = 8.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    )
    {
        Canvas(modifier = Modifier.fillMaxSize()){

        }
    }
}

@Preview
@Composable
fun PreviewGraph() {
    FICSComposeTheme{
        val investment:List<Int> = listOf(10000,12500,10500,11000,16000)
        val months:List<Int> = listOf(1,2,3,4,5)
        Graph(xVals = investment, yVals = months, verticalStep = 5000)
    }
}