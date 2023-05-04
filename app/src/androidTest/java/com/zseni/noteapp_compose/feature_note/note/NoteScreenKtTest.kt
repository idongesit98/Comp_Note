package com.zseni.noteapp_compose.feature_note.note

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zseni.noteapp_compose.core.util.TestTag
import com.zseni.noteapp_compose.di.AppModule
import com.zseni.noteapp_compose.feature_note.presentation.MainActivity
import com.zseni.noteapp_compose.feature_note.presentation.note.NoteScreen
import com.zseni.noteapp_compose.feature_note.presentation.util.Screen
import com.zseni.noteapp_compose.ui.theme.NoteApp_ComposeTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NoteScreenKtTest{

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp(){
        hiltRule.inject()
        composeRule.setContent{
            val navController = rememberNavController()
            NoteApp_ComposeTheme {
                NavHost(navController = navController,
                        startDestination = Screen.NoteScreen.route
                ){
                    composable(route = Screen.NoteScreen.route){
                        NoteScreen(navController = navController)
                    }
                }

            }

        }
    }

    @Test
    fun clickToggleOrderSection_isVisible(){
        composeRule.onNodeWithTag(TestTag.ORDER_SECTION).assertDoesNotExist()
        composeRule.onNodeWithContentDescription( "Sort").performClick()
        composeRule.onNodeWithTag(TestTag.ORDER_SECTION).assertIsDisplayed()

    }

}