package com.company.howfardidisprint.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.company.howfardidisprint.ui.theme.roboto

@Composable
fun FullWidthCard(string: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        elevation = 3.dp,
        shape = RectangleShape
    ) {
        Text(
            text = string,
            style = TextStyle(
                color = Color(80, 80, 80),
                fontSize = 20.sp,
                fontFamily = roboto,
                letterSpacing = 0.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
            ),
            modifier = Modifier.padding(vertical = 10.dp)
        )
    }
}