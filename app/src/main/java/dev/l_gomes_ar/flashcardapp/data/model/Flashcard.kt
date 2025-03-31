package dev.l_gomes_ar.flashcardapp.data.model

// Data classes for flashcard and collections
data class Flashcard(val word: String, val definition: String)

data class Collection(val name: String) {
    val cards: MutableList<Flashcard> = mutableListOf()
}