package com.zseni.noteapp_compose.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.zseni.noteapp_compose.R

// Set of Material typography styles to start with
private val Cursive = FontFamily(
    Font(R.font.adamina,FontWeight.Light)
)
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Cursive,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    h4 = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Bold
    ),
    h6 = TextStyle(
        fontFamily = Cursive,
        fontWeight = FontWeight.W100,
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)