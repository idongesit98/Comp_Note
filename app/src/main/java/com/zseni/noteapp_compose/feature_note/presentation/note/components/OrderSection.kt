package com.zseni.noteapp_compose.feature_note.presentation.note.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.zseni.noteapp_compose.R
import com.zseni.noteapp_compose.feature_note.domain.util.NoteOrder
import com.zseni.noteapp_compose.feature_note.domain.util.OrderType

@Composable
fun OrderSection(
    modifier:Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    onOrderChange:(NoteOrder) -> Unit
){
    Column(modifier = modifier)
    {
        Row(modifier = Modifier.fillMaxWidth()
        ){
            DefaultRadioButton(
                text = stringResource(id = R.string.title) ,
                Selected = noteOrder is NoteOrder.Title,
                onSelect = { onOrderChange(NoteOrder.Title(noteOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = stringResource(id = R.string.date ) ,
                Selected = noteOrder is NoteOrder.Date,
                onSelect = { onOrderChange(NoteOrder.Date(noteOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))

            DefaultRadioButton(
                text = stringResource(id = R.string.color) ,
                Selected = noteOrder is NoteOrder.Color  ,
                onSelect = { onOrderChange(NoteOrder.Color(noteOrder.orderType)) }
            )
        }

        Row(modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = stringResource(id = R.string.ascending),
                Selected = noteOrder.orderType is OrderType.Ascending,
                onSelect = {
                    onOrderChange(noteOrder.copy(OrderType.Ascending))
                }
            )

            DefaultRadioButton(
                text = stringResource(id = R.string.descending),
                Selected = noteOrder.orderType is OrderType.Descending,
                onSelect = { onOrderChange(noteOrder.copy(OrderType.Descending)) }
            )
        }
    }
}