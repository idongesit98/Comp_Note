package com.zseni.noteapp_compose.feature_note.domain.use_case
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class AddNoteUseCaseTest{
    private lateinit var addNoteUseCase:AddNoteUseCase
    private lateinit var addRepository:FakeNotesRepository

    @Before
    fun addUp(){
        addRepository = FakeNotesRepository()
        addNoteUseCase = AddNoteUseCase(addRepository)
    }

    @Test
    fun testNoteTitle(){
        val note = Note("RandomTitle").apply { content = "" }
        val saved = try {
            addNoteUseCase(note)
            true
        }catch (err:InvalidNoteException){
            false
        }
        assertThat(saved)
    }


    private class FakeNotesRepository{fun insertNote(note: Note){}}
    private data class AddNoteUseCase(val repo:FakeNotesRepository){
        @Throws(InvalidNoteException::class)
        operator fun invoke(note: Note){
            if (note.title.isBlank()) throw InvalidNoteException(".....")
            if (note.content.isBlank()) throw InvalidNoteException(".....")
        }
    }

    private data class Note(var title:String){var content: String = ""}
    private class InvalidNoteException(override val message: String?): Exception(message)


}