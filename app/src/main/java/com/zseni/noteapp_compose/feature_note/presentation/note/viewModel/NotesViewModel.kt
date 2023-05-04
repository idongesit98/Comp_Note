package com.zseni.noteapp_compose.feature_note.presentation.note.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zseni.noteapp_compose.feature_note.domain.model.Note
import com.zseni.noteapp_compose.feature_note.domain.use_case.NoteUseCases
import com.zseni.noteapp_compose.feature_note.domain.util.NoteOrder
import com.zseni.noteapp_compose.feature_note.domain.util.OrderType
import com.zseni.noteapp_compose.feature_note.presentation.note.NoteEvent
import com.zseni.noteapp_compose.feature_note.presentation.note.NotesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * It takes the results of the useCases put it in a state
 * and displays on the UI in a readable way that will not survive configurations
 * changes
 */

@HiltViewModel
class NotesViewModel @Inject constructor(
  private val noteUseCases: NoteUseCases
):ViewModel() {

  private val _state = mutableStateOf(NotesState())
  val state: State<NotesState> = _state

  private var recentlyDeletedNote: Note? = null //This variable saves a reference of the recently deleted note
  private var getNoteJob:Job? = null
  init {
      getNotes(NoteOrder.Date(OrderType.Descending))
  }

  fun onEvent(event: NoteEvent){
    when(event){
      is NoteEvent.Order ->{
        if (state.value.noteOrder::class == event.noteOrder::class &&
                state.value.noteOrder.orderType == event.noteOrder.orderType
        ){
          return
        }
      getNotes(event.noteOrder)

      }
      is NoteEvent.DeleteNote ->{
        viewModelScope.launch {
          noteUseCases.deleteNotes(event.note)
          recentlyDeletedNote = event.note
        }
      }
      is NoteEvent.RestoreNote ->{
        viewModelScope.launch {
          noteUseCases.addNotes(recentlyDeletedNote ?: return@launch)
          recentlyDeletedNote = null
        }
      }

      is NoteEvent.ToggleOrderSection ->{

        _state.value = state.value.copy(
          isOrderSectionVisible = !state.value.isOrderSectionVisible
        )

      }
    }

  }

  /**
   * This get the note by a given order
   */
    private fun getNotes(noteOrder: NoteOrder){
      getNoteJob?.cancel()
      getNoteJob =  noteUseCases.getNotes(noteOrder)
           .onEach { notes ->
                _state.value = state.value.copy(
                     notes = notes,
                     noteOrder = noteOrder
                )
           }
         .launchIn(viewModelScope)
    }

  }