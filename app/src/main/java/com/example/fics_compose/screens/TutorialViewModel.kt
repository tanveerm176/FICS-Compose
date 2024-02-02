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
    var currentPage by mutableIntStateOf(0)
        private set
    var currentContent by mutableStateOf(displayText[currentPage])
        private set


    fun previousPage() {
        if (currentPage>0){
            updatePage(currentPage-1)
        }
    }

    fun nextPage() {
        if(currentPage<maxSlides){
            updatePage(currentPage+1)
        }
    }

    fun updatePage(page: Int) {
        currentPage = page
        currentContent = displayText[currentPage]
    }


}