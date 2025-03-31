package dev.l_gomes_ar.flashcardapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.l_gomes_ar.flashcardapp.R

// Screen for adding a collection
@Composable
fun AddCollectionScreen(
    onSaveButtonClicked: (String) -> Unit,
    onClickAddCard: () -> Unit,
    modifier: Modifier = Modifier
) {
    var collectionName by remember {
        mutableStateOf("")
    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {
        TextField(
            value = collectionName,
            onValueChange = {
                collectionName = it
            },
            singleLine = true,
            label = {
                Text(stringResource(R.string.collection_name))
            },
            modifier = Modifier.fillMaxWidth().padding(16.dp),
        )
        Button(
            enabled = collectionName.isNotBlank(),
            onClick = {
                onSaveButtonClicked(collectionName)
                collectionName = ""
            },
            modifier = Modifier.padding(16.dp).fillMaxWidth()
        ) {
            Text(stringResource(R.string.add_collection), fontSize = 16.sp)
        }
        OutlinedButton(
            onClick = onClickAddCard,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
           Text(
               text = stringResource(R.string.add_card),
               fontSize = 16.sp
           )
       }
    }
}

@Preview
@Composable
private fun PreviewAddCollectionScreen() {
    AddCollectionScreen(
        onSaveButtonClicked = {},
        onClickAddCard = {}
    )
}