package com.zseni.noteapp_compose.feature_note.presentation.note

import com.zseni.noteapp_compose.feature_note.domain.model.Note
import com.zseni.noteapp_compose.feature_note.domain.util.NoteOrder

sealed class NoteEvent{
    data class Order(val noteOrder: NoteOrder):NoteEvent()
    data class DeleteNote(val note: Note):NoteEvent()
    object RestoreNote:NoteEvent()
    object ToggleOrderSection:NoteEvent()

}

