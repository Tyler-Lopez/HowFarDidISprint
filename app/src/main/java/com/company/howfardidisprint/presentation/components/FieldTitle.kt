package com.company.howfardidisprint.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.company.howfardidisprint.ui.theme.roboto

@Composable
fun FieldTitle(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 1.dp)
            .background(Color(240, 241, 243))
            .border(1.dp, Color(229, 224, 221))
            .padding(horizontal = 5.dp)
            .height(100.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Text(
            text = "$text ",
            fontSize = 25.sp,
            fontFamily = roboto,
            fontWeight = FontWeight(450),
            color = Color(0, 0, 0),
        )
    }
}