package com.example.fics_compose.presentation.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.fics_compose.presentation.screendata.GlossaryData

class GlossaryViewModel(application: Application) : AndroidViewModel(application) {

    /*private set:
    * still public var but only call it belongs to can modify it*/
    var searchTerm by mutableStateOf("")
        private set

    private val glossary = GlossaryData.glossaryTopics

    var filteredGlossary by mutableStateOf(glossary)
        private set


    /* Show glossary list based on either the topic name or the term name that matches the user's search
    * If no input from user show all terms in glossary */
    fun updateSearchTerm(newSearchTerm: String) {
        searchTerm = newSearchTerm
        filteredGlossary = glossary.filter { topic ->
            topic.topicName.contains(searchTerm, ignoreCase = true) ||
                    topic.terms.any { term ->
                        term.termName.contains(
                            searchTerm,
                            ignoreCase = true
                        )
                    }
        }
    }

}
