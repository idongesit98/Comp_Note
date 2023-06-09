package com.zseni.noteapp_compose.feature_note.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zseni.noteapp_compose.core.util.TestTag
import com.zseni.noteapp_compose.di.AppModule
import com.zseni.noteapp_compose.feature_note.presentation.add_edit_note.AddNoteScreen
import com.zseni.noteapp_compose.feature_note.presentation.note.NoteScreen
import com.zseni.noteapp_compose.feature_note.presentation.util.Screen
import com.zseni.noteapp_compose.ui.theme.NoteApp_ComposeTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NotesEndToEndTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @ExperimentalAnimationApi
    @Before
    fun setUp(){
        hiltRule.inject()
        composeRule.setContent {
            NoteApp_ComposeTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.NoteScreen.route
                ){
                    composable(route = Screen.NoteScreen.route){
                        NoteScreen(navController = navController)
                    }
                    composable(
                        route = Screen.AddEditNoteScreen.route +
                                "?noteId={noteId}&noteColor={noteColor}",
                        arguments = listOf(
                            navArgument(
                                name = "noteId"
                            ){
                                type = NavType.IntType
                                defaultValue = -1
                            },
                            navArgument(
                                name = "noteColor"
                            ){
                                type = NavType.IntType
                                defaultValue = -1
                            },
                        )
                    ){
                        val color = it.arguments?.getInt("noteColor")?: -1
                        AddNoteScreen(
                            navController = navController,
                            noteColor = color)
                    }

                }
            }
        }
    }

    @Test
    fun saveNewNote_editAfterWards(){
        // Click to FAB to get to add note screen
        composeRule.onNodeWithContentDescription("Add note").performClick()

        //Enter Text in title and content TextField
        composeRule
            .onNodeWithTag(TestTag.TITLE_TEXT_FIELD)
            .performTextInput("test-title")

        composeRule
            .onNodeWithTag(TestTag.CONTENT_TEXT_FIELD)
            .performTextInput("test-content")

        composeRule.onNodeWithContentDescription("Save note").performClick()

        //Make sure there is a note in the list with our title and content
        composeRule.onNodeWithText("test-title").assertIsDisplayed()

        //Click on Note to edit it
        composeRule.onNodeWithText("test-title").performClick()

        // Make sure title and content text fields contain note title and content
        composeRule
            .onNodeWithTag(TestTag.TITLE_TEXT_FIELD)
            .assertTextEquals("test-title")

        composeRule
            .onNodeWithTag(TestTag.CONTENT_TEXT_FIELD)
            .assertTextEquals("test-content")

        //Add the text "2" to the title text field
        composeRule
            .onNodeWithTag(TestTag.TITLE_TEXT_FIELD)
            .performTextInput("2")

        //Update the note
        composeRule.onNodeWithContentDescription("Save").performClick()

        //Make sure the update was applied to the list
        composeRule.onNodeWithText("test-title2").assertIsDisplayed()

    }

    @Test
    fun saveNewNotes_orderByTitleDescending(){

        for(i in 1..3){
            //Click on FAB to get to add note screen
            composeRule.onNodeWithContentDescription("Add note").performClick()

            //Enter texts in title and content text fields
            composeRule
                .onNodeWithTag(TestTag.TITLE_TEXT_FIELD)
                .performTextInput(i.toString())

            composeRule
                .onNodeWithTag(TestTag.CONTENT_TEXT_FIELD)
                .performTextInput(i.toString())

            //Save the new note
            composeRule.onNodeWithContentDescription("Save note").performClick()
        }

        composeRule.onNodeWithText("1").assertIsDisplayed()
        composeRule.onNodeWithText("2").assertIsDisplayed()
        composeRule.onNodeWithText("3").assertIsDisplayed()

        composeRule
            .onNodeWithContentDescription("Sort")
            .performClick()

        composeRule
            .onNodeWithContentDescription("Title")
            .performClick()

        composeRule
            .onNodeWithContentDescription("Descending")
            .performClick()

        composeRule.onAllNodesWithTag(TestTag.NOTE_ITEM)[0]
            .assertTextContains("3")

        composeRule.onAllNodesWithTag(TestTag.NOTE_ITEM)[1]
            .assertTextContains("2")

        composeRule.onAllNodesWithTag(TestTag.NOTE_ITEM)[2]
            .assertTextContains("1")






    }
}