package com.example.fics_compose.screens

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GlossaryViewModel: ViewModel() {

    private val _glossaryUiState = MutableStateFlow(GlossaryUiState())

    /*to be accessed by screen composable funcs*/
    val glossaryUiState: StateFlow<GlossaryUiState> = _glossaryUiState.asStateFlow()

    fun showDefinitionLabel(showInformalDefinition: Boolean): String{
        val definitionLabel =
            if (showInformalDefinition) "FICS Definition" else "Formal Definition"
        return definitionLabel
    }

    fun showDefinitionText(showInformalLabel: Boolean, term: Term): String{
        val termDefinition =
            if (showInformalLabel) term.informalDefinition else term.formalDefinition
        return termDefinition
    }

    fun expandCard(): Boolean{
        _glossaryUiState.update { currentState ->
            currentState.copy(expandedState = true)
        }
        return _glossaryUiState.value.expandedState
    }

}

data class GlossaryUiState(
    val showInformalDefinition: Boolean = false,
    val showInformalLabel: Boolean  = false,
    val expandedState: Boolean = false
)