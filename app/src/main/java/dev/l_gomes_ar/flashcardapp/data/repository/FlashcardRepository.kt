package dev.l_gomes_ar.flashcardapp.data.repository

import dev.l_gomes_ar.flashcardapp.data.model.Flashcard
import dev.l_gomes_ar.flashcardapp.data.model.Collection

// Class for manipulating the repository of data
class FlashcardRepository {
    private val collections = mutableListOf<Collection>()

    fun addCollection(name: String) {
        collections.add(Collection(name))
    }

    fun getCollection(): List<Collection> = collections

    fun addCardToCollection(collectionName: String, card: Flashcard)  {
        collections.find { it.name == collectionName }?.cards?.add(card)
    }
}