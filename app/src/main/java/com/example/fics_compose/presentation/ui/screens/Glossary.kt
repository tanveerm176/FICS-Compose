package com.example.fics_compose.presentation.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.*
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.fics_compose.presentation.ui.theme.FICSComposeTheme
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.TextField
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import com.example.fics_compose.presentation.ui.theme.lightGray
import com.example.fics_compose.presentation.ui.theme.yellow
import androidx.compose.material3.Divider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fics_compose.presentation.screendata.Term
import com.example.fics_compose.presentation.screendata.Topic
import com.example.fics_compose.presentation.viewmodel.GlossaryViewModel

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun GlossaryScreen(glossaryViewModel: GlossaryViewModel = viewModel()) {

    val keyboardController = LocalSoftwareKeyboardController.current

    /* Display the filtered glossary*/
    Column(
        modifier = Modifier
            .background(color = lightGray)
            .fillMaxWidth()
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = yellow, shape = RectangleShape)
                .border(1.5.dp, Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Glossary",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 30.dp, bottom = 20.dp)
                )
                Text(
                    text = "Search for key terms",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 30.dp)
                )
                TextField(
                    value = glossaryViewModel.searchTerm,
                    /* If user has not searched, show entire glossary,
                    otherwise show filtered glossary*/
                    onValueChange = {
                        glossaryViewModel.updateSearchTerm(it)
                    },
                    placeholder = { Text("Search Glossary") },

                    /*triggers the search icon to appear in place of ENTER on keyboard*/
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Search
                    ),

                    /*When search icon pressed hide keyboard*/
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            keyboardController?.hide()
                        }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, bottom = 30.dp)
                )
            }
        }
        Text(
            text = "Key Terms",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 18.dp, bottom = 20.dp)
        )
        /* Display the glossary based on the search results*/
        GlossaryList(glossaryViewModel.filteredGlossary)
    }
}

@Composable
fun GlossaryList(glossary: List<Topic>) {
    LazyColumn {
        items(glossary) { topic ->
            TopicCard(topic)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopicCard(topic: Topic) {
    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f, label = "rotateState"
    )

    Card(
        onClick = { expandedState = !expandedState },
        modifier = Modifier
            .fillMaxWidth()
            .border(1.5.dp, Color(0xFF8A191D), RoundedCornerShape(10.dp))
            .padding(3.dp)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        shape = RoundedCornerShape(10.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(5.dp)
            ) {
                Text(
                    modifier = Modifier
                        .weight(6f),
                    text = topic.topicName,
                    color = Color(0xFF8A191D),
                    style = MaterialTheme.typography.bodyLarge,
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
                        contentDescription = "Drop-Down Arrow",
                        tint = Color(0xFF8A191D),
                        modifier = Modifier.size(50.dp)
                    )
                }
            }
            if (expandedState) {
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
        ) {
            /* Add a Divider between terms*/
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 9.dp, bottom = 9.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(6f),
                    text = term.termName,
                    color = Color(0xFF8A191D),
                    style = MaterialTheme.typography.bodyMedium,
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
                        tint = Color(0xFF8A191D),
                        contentDescription = "Drop-Down Arrow"
                    )
                }
            }
            /* Use the isExpanded property to decide whether to display the definition*/
            if (expandedState) {
                var showInformalDefinition by remember { mutableStateOf(false) }
                val labelText: String =
                    if (showInformalDefinition) "FICS Definition" else "Formal Definition"
                val definitionText: String =
                    if (showInformalDefinition) term.informalDefinition else term.formalDefinition
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Switch(
                        checked = showInformalDefinition,
                        onCheckedChange = { showInformalDefinition = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color(0xFF8A191D),
                            checkedTrackColor = Color(0xFFDEB841),
                            uncheckedThumbColor = Color(0xFFDEB841),
                            uncheckedTrackColor = Color(0xFFEEE1B9),
                        ),
                    )

                    Text(
                        text = labelText,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                Text(
                    text = definitionText,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewGlossary() {
    FICSComposeTheme {
        GlossaryScreen()
    }
}