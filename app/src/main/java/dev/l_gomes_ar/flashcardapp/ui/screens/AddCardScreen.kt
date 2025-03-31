package dev.l_gomes_ar.flashcardapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.l_gomes_ar.flashcardapp.R
import dev.l_gomes_ar.flashcardapp.data.model.Collection

// Screen for adding a card to collection
@Composable
fun AddCardScreen(
    collections: List<Collection>,
    onSaveButtonClicked: (String, String, String) -> Unit,
    onClickViewCollection: () -> Unit,
    modifier: Modifier = Modifier
) {

    var collectionName by remember {
        mutableStateOf("")
    }

    var cardWord by remember {
        mutableStateOf("")
    }

    var cardDefinition by remember {
        mutableStateOf("")
    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CollectionsDropDown(
            dropDownItems = collections.map { it.name },
            onItemClick = { collectionName = it },
            modifier = Modifier.padding(16.dp)
        )
        TextField(
            value = cardWord,
            onValueChange = {
                cardWord = it
            },
            singleLine = true,
            label = {
                Text(stringResource(R.string.card_word))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        )
        TextField(
            value = cardDefinition,
            onValueChange = {
                cardDefinition = it
            },
            singleLine = false,
            maxLines = 5,
            label = {
                Text(stringResource(R.string.card_definition))
            },
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .padding(16.dp),
        )
        Button(
            onClick = {
                onSaveButtonClicked(collectionName, cardWord, cardDefinition)
                cardWord = ""
                cardDefinition = ""
            },
            enabled = collectionName.isNotEmpty() && cardWord.isNotEmpty() && cardDefinition.isNotEmpty(),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(stringResource(R.string.add_card), fontSize = 16.sp)
        }
        OutlinedButton(
            onClick = onClickViewCollection,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.view_collection),
                fontSize = 16.sp
            )
        }

    }
}

@Composable
fun CollectionsDropDown(
    dropDownItems: List<String>,
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit,
) {
    val chooseCollection = stringResource(R.string.choose_collection)
    val noCollections = stringResource(R.string.no_collections)

    var collectionName by remember {
        mutableStateOf(if (dropDownItems.isNotEmpty()) chooseCollection else noCollections)
    }

    var isContextMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(true, onClick = {
                    isContextMenuVisible = true
                })
                .padding(16.dp)
        ) {
            Text(text = collectionName)
        }
        DropdownMenu(
            expanded = isContextMenuVisible,
            onDismissRequest = { isContextMenuVisible = false },
        ) {
            dropDownItems.forEach { item ->
               DropdownMenuItem(
                   text = { Text(item) },
                   onClick = {
                       onItemClick(item)
                       collectionName = item
                       isContextMenuVisible = false
                   },
                   modifier = Modifier.fillMaxWidth()
               )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewAddCardScreen() {
    AddCardScreen(
        collections = emptyList(),
        onSaveButtonClicked = { collectionName: String, cardWord: String, cardDefinition: String -> },
        onClickViewCollection = {},
    )
}