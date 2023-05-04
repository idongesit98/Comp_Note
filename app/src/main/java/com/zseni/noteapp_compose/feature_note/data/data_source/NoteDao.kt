package com.zseni.noteapp_compose.feature_note.data.data_source

import androidx.room.*
import com.zseni.noteapp_compose.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

/**
 * This package controls the inserting,updating,deleting
 * and using the ID to update the note(getNotesByID)
 */
@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Query("SELECT * FROM note")
    //This returns a flow hence it is not wrapped in suspend function
    fun getNotes():Flow<List<Note>>

    @Query("SELECT * FROM note WHERE id =:id")
    suspend fun getNotesById(id:Int):Note?

    @Delete
    suspend fun deleteNotes(note: Note)
}