package com.company.howfardidisprint.presentation.components

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
fun OrangeButton(value: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 10.dp,
                bottom = 4.dp,
                start = 10.dp,
                end = 10.dp
            ),
        onClick = {
            onClick()
        },
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(
                250, 82, 7
            )
        )
    ) {
        Text(
            text = value,
            style = TextStyle(
                color = Color(255, 255, 255),
                fontSize = 30.sp,
                fontFamily = roboto,
                letterSpacing = (-1).sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
            )
        )
    }
}