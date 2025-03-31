package dev.l_gomes_ar.flashcardapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dev.l_gomes_ar.flashcardapp.ui.theme.FlashcardAppTheme

// Runs main activity (starts the flashcard app)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlashcardAppTheme {
                FlashCardApp()
            }
        }
    }
}