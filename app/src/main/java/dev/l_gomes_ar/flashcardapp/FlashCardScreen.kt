package dev.l_gomes_ar.flashcardapp

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.l_gomes_ar.flashcardapp.data.model.Flashcard
import dev.l_gomes_ar.flashcardapp.data.repository.FlashcardRepository
import dev.l_gomes_ar.flashcardapp.ui.screens.AddCardScreen
import dev.l_gomes_ar.flashcardapp.ui.screens.AddCollectionScreen
import dev.l_gomes_ar.flashcardapp.ui.screens.HomeScreen
import dev.l_gomes_ar.flashcardapp.ui.screens.ViewCollectionScreen
import kotlinx.coroutines.launch

// Initiate instance of the flashcard repository
private val flashcardRepository = FlashcardRepository()

// Set up class for the routes
enum class FlashCardScreen(@StringRes val title: Int) {
    Home(title = R.string.app_name),
    ViewCollections(title = R.string.view_collection),
    AddCollection(title = R.string.add_collection),
    AddCard(title = R.string.add_card),
}

// Code for the top bar on the screens (shows back button and title)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashcardAppBar(
    currentScreen: FlashCardScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

// Set ups the app, the navigation, and the screen, as well as the navigation logic,
// and data manipulation
@Composable
fun FlashCardApp(
    navController: NavHostController = rememberNavController(),
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = FlashCardScreen.valueOf(
        backStackEntry?.destination?.route ?: FlashCardScreen.Home.name
    )

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            FlashcardAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = FlashCardScreen.Home.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = FlashCardScreen.Home.name) {
                HomeScreen(
                    onClickAddCollectionsScreen = { navController.navigate(FlashCardScreen.AddCollection.name) },
                    onClickViewCollection = { navController.navigate(FlashCardScreen.ViewCollections.name) },
                )
            }
            composable(route = FlashCardScreen.AddCollection.name) {
                val message = stringResource(R.string.collection_saved)
                AddCollectionScreen(
                    onClickAddCard = { navController.navigate(FlashCardScreen.AddCard.name) },
                    onSaveButtonClicked = {collectionName ->
                        flashcardRepository.addCollection(collectionName)
                        scope.launch {
                            snackBarHostState.showSnackbar(
                                message = message,
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                )
            }
            composable(route = FlashCardScreen.AddCard.name) {
                val message = stringResource(R.string.card_saved)
                AddCardScreen(
                    collections = flashcardRepository.getCollection(),
                    onClickViewCollection = { navController.navigate(FlashCardScreen.ViewCollections.name) },
                    onSaveButtonClicked = { collectionName, cardWord, cardDefinition ->
                        val card = Flashcard(cardWord, cardDefinition)
                        flashcardRepository.addCardToCollection(collectionName, card)
                        scope.launch {
                            snackBarHostState.showSnackbar(
                                message = message,
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                )
            }
            composable(route = FlashCardScreen.ViewCollections.name) {
                ViewCollectionScreen(
                    collections = flashcardRepository.getCollection() ?: emptyList(),
                )
            }
        }
    }
}