package com.company.howfardidisprint.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.company.howfardidisprint.ui.theme.roboto

@Composable
fun WhiteButton(value: String, onClick: () -> Unit) {
    Button(
        onClick = {
            onClick()
        },
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp, start = 10.dp, end = 10.dp)
            .border(
                1.dp,
                Color(215, 99, 58),
                shape = RoundedCornerShape(5.dp)
            ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(
                255, 255, 255
            )
        ),
    ) {
        Text(
            text = value,
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = roboto,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                color = Color(215, 99, 58),
                letterSpacing = (-0.5).sp,
            )
        )
    }
}