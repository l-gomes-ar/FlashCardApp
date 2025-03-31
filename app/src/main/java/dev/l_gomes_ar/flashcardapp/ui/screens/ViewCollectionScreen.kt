package dev.l_gomes_ar.flashcardapp.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.l_gomes_ar.flashcardapp.R
import dev.l_gomes_ar.flashcardapp.data.model.Collection
import dev.l_gomes_ar.flashcardapp.data.model.Flashcard

// Screen to display the cards by set (collection)
@Composable
fun ViewCollectionScreen(
    collections: List<Collection>,
    modifier: Modifier = Modifier
) {
    var collectionName by remember {
        mutableStateOf("")
    }

    var collection: Collection by remember {
        mutableStateOf(Collection(""))
    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CollectionsDropDown(
            dropDownItems = collections.map { it.name },
            onItemClick = {
                collectionName = it
                collection = collections.find { collection -> collection.name == collectionName }!!
            },
            modifier = Modifier.padding(16.dp)
        )
        if (collection.cards.isNotEmpty()) {
            LazyColumn {
                collection.cards.forEach {
                    item {
                        FlipCard(it)
                    }
                }
            }
        } else {
            Text(
                text = stringResource(R.string.collection_empty),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontStyle = FontStyle.Italic,
                color = Color.Gray,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

// Set up the flip card composable with animation
@Composable
fun FlipCard(
    card: Flashcard,
    modifier: Modifier = Modifier
) {
    // Rotation animation
    var isFlipped by remember { mutableStateOf(false) }

    val rotation by animateFloatAsState(
        // If flipped, target rotation is 180 degrees
        // When isFlipped changes, when recomposing, animation gradually increases updates
        targetValue = if (isFlipped) 180f else 0f,
        // Interpolated transition (smooth) 0 to 180 in 600 millis
        animationSpec = tween(durationMillis = 600)
    )

    // Check which side is visible
    val isFrontVisible = rotation < 90f

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = Modifier
            .padding(16.dp)
            // Clickable modifier with no ripple effect, also prevents default interactions
            // Flips card when clicked (triggers state change, recomposition)
            .clickable(indication = null, interactionSource = null) { isFlipped = !isFlipped }
            // Graphic transformation
            .graphicsLayer {
                // Rotate along Y axis based on rotation value
                rotationY = rotation
                // Simulate 3D depth, ensure proper scaling with density
                cameraDistance = 12f * density
            }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .height(150.dp)
                .fillMaxSize()
        ) {
            if (isFrontVisible) {
                CardFace(text = card.word, backgroundColor = Color.White)
            } else {
                CardFace(
                    text = card.definition,
                    backgroundColor = Color.LightGray,
                    rotationY = 180f
                )
            }
        }
    }
}

// Set up the face of the flipcard (ensuring it changes smoothly depending on the rotation of card)
@Composable
fun CardFace(
    text: String,
    backgroundColor: Color,
    rotationY: Float = 0f,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            // Handle individual card rotation
            .graphicsLayer { this.rotationY = rotationY }
            .background(backgroundColor)
    ) {
        Text(
            text = text,
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview
@Composable
private fun PreviewViewCollectionScreen() {
    ViewCollectionScreen(
        collections = emptyList()
    )
}