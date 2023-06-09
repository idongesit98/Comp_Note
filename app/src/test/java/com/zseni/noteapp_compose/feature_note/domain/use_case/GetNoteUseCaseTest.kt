package com.zseni.noteapp_compose.feature_note.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.zseni.noteapp_compose.feature_note.data.repository.FakeNotesRepository
import com.zseni.noteapp_compose.feature_note.domain.model.Note
import com.zseni.noteapp_compose.feature_note.domain.util.NoteOrder
import com.zseni.noteapp_compose.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNoteUseCaseTest{
    private lateinit var getNoteUseCase: GetNoteUseCase
    private lateinit var fakeRepository:FakeNotesRepository

    @Before
    fun setUp(){
        fakeRepository = FakeNotesRepository()
        getNoteUseCase = GetNoteUseCase(fakeRepository)

        val notesToInsert = mutableListOf<Note>()
        ('a'..'z').forEachIndexed{ index, c ->
        notesToInsert.add(
            Note(
                title = c.toString(),
                content = c.toString(),
                timeStamp = index.toLong(),
                color = index
            )
        )

        }
        notesToInsert.shuffle()
        runBlocking {
            notesToInsert.forEach{fakeRepository.insertNote(it)}
        }
    }

    @Test
    fun `Order notes by title ascending, correct order`() = runBlocking{
        val notes = getNoteUseCase(NoteOrder.Title(OrderType.Ascending)).first()

        for (i in 0..notes.size-2){
            assertThat(notes[i].title).isLessThan(notes[i+1].title)

        }
    }

    @Test
    fun `Order notes by title descending, correct order`() = runBlocking{
        val notes = getNoteUseCase(NoteOrder.Title(OrderType.Descending)).first()

        for (i in 0..notes.size-2){
            assertThat(notes[i].title).isGreaterThan(notes[i+1].title)

        }
    }

    @Test
    fun `Order notes by date ascending, correct order`() = runBlocking{
        val notes = getNoteUseCase(NoteOrder.Date(OrderType.Ascending)).first()

        for (i in 0..notes.size-2){
            assertThat(notes[i].timeStamp).isLessThan(notes[i+1].timeStamp)

        }
    }

    @Test
    fun `Order notes by date descending, correct order`() = runBlocking{
        val notes = getNoteUseCase(NoteOrder.Date(OrderType.Descending)).first()

        for (i in 0..notes.size-2){
            assertThat(notes[i].timeStamp).isGreaterThan(notes[i+1].timeStamp)

        }
    }

    @Test
    fun `Order notes by color ascending, correct order`() = runBlocking{
        val notes = getNoteUseCase(NoteOrder.Color(OrderType.Ascending)).first()

        for (i in 0..notes.size-2){
            assertThat(notes[i].color).isLessThan(notes[i+1].color)

        }
    }

    @Test
    fun `Order notes by color descending, correct order`() = runBlocking{
        val notes = getNoteUseCase(NoteOrder.Color(OrderType.Descending)).first()

        for (i in 0..notes.size-2){
            assertThat(notes[i].color).isGreaterThan(notes[i+1].color)

        }
    }


}
