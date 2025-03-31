package dev.l_gomes_ar.flashcardapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.l_gomes_ar.flashcardapp.R

// Start (home) screen on the app
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onClickViewCollection: () -> Unit,
    onClickAddCollectionsScreen: () -> Unit,

) {
    Box(
        modifier = Modifier.fillMaxSize().background(Color.White),
        contentAlignment = Alignment.Center,
    ) {
        Column {
            Text(
                text = stringResource(R.string.app_name),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
            )
            Button(
                onClick = onClickAddCollectionsScreen,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.add_collection),
                    fontSize = 16.sp
                )
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
}

@Preview
@Composable
private fun PreviewHomeScreen() {
    HomeScreen(
        onClickAddCollectionsScreen = {},
        onClickViewCollection = {},
    )
}