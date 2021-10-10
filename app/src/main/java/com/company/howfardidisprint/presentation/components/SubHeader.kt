package com.company.howfardidisprint.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.company.howfardidisprint.ui.theme.roboto

@Composable
fun SubHeader(text: String) {
    Text(
        text = text,
        fontSize = 14.sp,
        fontFamily = roboto,
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(
            horizontal = 20.dp,
            vertical = 5.dp
        ),
        color = Color(75, 70, 70),
    )
}