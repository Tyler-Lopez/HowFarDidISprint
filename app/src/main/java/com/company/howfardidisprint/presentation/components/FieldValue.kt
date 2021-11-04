package com.company.howfardidisprint.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.company.howfardidisprint.ui.theme.roboto

@Composable
fun FieldValue(
    value: String,
    unit: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 1.dp)
            .padding(horizontal = 10.dp)
            .height(100.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = value,
            fontSize = 55.sp,
            fontFamily = roboto,
            fontWeight = FontWeight(410),
            textAlign = TextAlign.Center,
            color = Color(20, 20, 20),
        )
        Text(
            text = " $unit",
            fontSize = 25.sp,
            fontFamily = roboto,
            fontWeight = FontWeight(410),
            textAlign = TextAlign.Center,
            color = Color(40, 40, 40),
        )
    }
}