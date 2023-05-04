package com.zseni.noteapp_compose.feature_note.domain.use_case

import com.zseni.noteapp_compose.feature_note.domain.model.Note
import com.zseni.noteapp_compose.feature_note.domain.repository.NoteRepository
import com.zseni.noteapp_compose.feature_note.domain.util.NoteOrder
import com.zseni.noteapp_compose.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * This UseCase determines the ordering the ordering of notes using
 * colour,ascending,descending.......
 *Map takes everything the list of results taking it to a newList
 */

class GetNoteUseCase (private val repository:NoteRepository
){
    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ): Flow<List<Note>>{
        return repository.getNotes().map { notes ->
            when(noteOrder.orderType){
                is OrderType.Ascending ->{
                    when(noteOrder){
                        is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
                        is NoteOrder.Date -> notes.sortedBy { it.timeStamp}
                        is NoteOrder.Color -> notes.sortedBy { it.color }
                    }
                }
                is OrderType.Descending ->{
                    when(noteOrder){
                        is NoteOrder.Title -> notes.sortedByDescending { it.title.lowercase() }
                        is NoteOrder.Date -> notes.sortedByDescending { it.timeStamp }
                        is NoteOrder.Color -> notes.sortedByDescending { it.color }
                    }

                }
            }

        }

    }
}