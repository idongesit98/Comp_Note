package com.zseni.noteapp_compose.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zseni.noteapp_compose.ui.theme.*

@Entity
data class Note(
    val title:String,
    val content:String,
    val timeStamp:Long,
    val color:Int,
    @PrimaryKey
    val id:Int? = null
){
    companion object{
        val noteColors = listOf(RedOrange, BabyBlue, RedPink, LightGreen, Violet)
    }
}

class InvalidNoteException(message:String):Exception(message)
