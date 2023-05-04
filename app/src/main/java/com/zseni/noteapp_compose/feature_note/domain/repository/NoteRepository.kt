package com.zseni.noteapp_compose.feature_note.domain.repository

import com.zseni.noteapp_compose.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

/**
 * This repository directly access your dataSources(Usually database,Api)
 * taking the dataSources and determine which to forward directly to the useCases
 */

interface NoteRepository {

    fun getNotes():Flow<List<Note>>

    suspend fun getNoteById(id:Int):Note?

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)
}