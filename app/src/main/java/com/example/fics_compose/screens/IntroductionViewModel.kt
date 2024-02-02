package com.example.fics_compose.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.fics_compose.ScreenData.IntroductionData
import com.example.fics_compose.ScreenData.IntroductionText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class IntroductionViewModel : ViewModel() {

    private val displayText: List<IntroductionData> = IntroductionText.introTextList

    val maxSlides: Int = displayText.size - 1
    var currentPage by mutableIntStateOf(0)
        private set
    var currentContent by mutableStateOf(displayText[currentPage])
        private set

    fun previousPage() {
        if (currentPage > 0) {
            updatePage(currentPage - 1)
        }
    }

    fun nextPage() {
        if (currentPage < maxSlides) {
            updatePage(currentPage + 1)
        }
    }


    fun updatePage(page: Int) {
        currentPage = page
        currentContent = displayText[currentPage]

    }
}

