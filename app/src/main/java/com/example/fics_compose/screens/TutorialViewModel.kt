package com.example.fics_compose.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.fics_compose.ScreenData.TutorialInfo
import com.example.fics_compose.ScreenData.TutorialText
import kotlin.math.max
import kotlin.math.min

class TutorialViewModel : ViewModel() {
    val displayText: List<TutorialInfo> = TutorialText.tutorialTextList

    val maxSlides = displayText.size - 1
    var tutorialSlidesIndex by mutableIntStateOf(0)
    var currentPage by mutableIntStateOf(0)
    var currentText by mutableStateOf(displayText[tutorialSlidesIndex])


    fun previousPage() {
        currentPage -= 1
        currentText = displayText[currentPage]
    }

    fun nextPage() {
        currentPage += 1
        currentText = displayText[currentPage]
    }

    fun displayCurrentText(currentPage: Int) {
        currentText = displayText[currentPage]
    }


}