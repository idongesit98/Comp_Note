package com.zseni.noteapp_compose.feature_note.domain.use_case

/**
 * A class that contains all the useCases to make the code
 * cleaner when passing the useCases to the viewModel
 */
data class NoteUseCases(
    val getNotes:GetNoteUseCase,
    val deleteNotes:DeleteNoteUseCase,
    val addNotes:AddNoteUseCase,
    val collectNotes:GetNote
)
