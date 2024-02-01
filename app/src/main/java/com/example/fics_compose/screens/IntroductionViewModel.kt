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

class IntroductionViewModel: ViewModel(){
//    val maxSlides: Int = IntroductionText.introTextList.size -1

    val displayText: List<IntroductionData> = IntroductionText.introTextList

    val maxSlides: Int = displayText.size - 1
    val introListIndex by mutableIntStateOf(0)
    var currentPage by mutableIntStateOf(0)
    var currentText by mutableStateOf(displayText[introListIndex])
/*    private val _introductionUiState = MutableStateFlow(IntroductionUiState())

    val introductionUiState: StateFlow<IntroductionUiState> = _introductionUiState.asStateFlow()*/

    fun previousPage(){
        currentPage -= 1
        currentText = displayText[currentPage]
    }

    fun nextPage(){
        currentPage += 1
        currentText = displayText[currentPage]
    }

    fun displayCurrentText(currentPage: Int){
        currentText = displayText[currentPage]
    }
}

/*
data class IntroductionUiState(
    val maxSlides: Int = IntroductionText.introTextList.size -1,

)*/
