package com.zseni.noteapp_compose.feature_note.presentation.note

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zseni.noteapp_compose.R
import com.zseni.noteapp_compose.core.util.TestTag
import com.zseni.noteapp_compose.feature_note.presentation.note.components.NoteItem
import com.zseni.noteapp_compose.feature_note.presentation.note.components.OrderSection
import com.zseni.noteapp_compose.feature_note.presentation.note.viewModel.NotesViewModel
import com.zseni.noteapp_compose.feature_note.presentation.util.Screen
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NoteScreen(
    navController: NavController,
    viewModel:NotesViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditNoteScreen.route)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    modifier = Modifier.size(50.dp),
                    imageVector = Icons.Default.ArrowRight,
                    contentDescription = "Add note",
                    tint = MaterialTheme.colors.background)
            }
        },
        scaffoldState = scaffoldState,
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Notes",
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.primary,
                fontSize = 24.sp
                )
                
                IconButton(
                    onClick = {
                        viewModel.onEvent(NoteEvent.ToggleOrderSection)
                     },
                ) {
                   Icon(imageVector = Icons.Default.Sort,
                       contentDescription = stringResource(id = R.string.icon_name) )
                }

            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit =  fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .testTag(TestTag.ORDER_SECTION),

                    noteOrder = state.noteOrder,
                    onOrderChange = {
                        viewModel.onEvent(NoteEvent.Order(it))
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
             LazyColumn(modifier = Modifier.fillMaxSize()){
                 items(state.notes){ note ->
                     NoteItem(
                         note = note,
                         modifier = Modifier
                             .fillMaxWidth()
                             .clickable {
                                 navController.navigate(
                                     Screen.AddEditNoteScreen.route +
                                             "?noteId=${note.id}&noteColor=${note.color}"
                                 )

                             },
                         onDeleteClick = {
                             viewModel.onEvent(NoteEvent.DeleteNote(note))
                             scope.launch {
                                   val result = scaffoldState.snackbarHostState.showSnackbar(
                                       message = "Note deleted",
                                       actionLabel = "Undo"
                                   )
                                 if (result == SnackbarResult.ActionPerformed){
                                     viewModel.onEvent(NoteEvent.RestoreNote)
                                 }
                             }
                         }
                     )
                     Spacer(modifier = Modifier.height(16.dp))
                 }
             }
        }
    }
    
}