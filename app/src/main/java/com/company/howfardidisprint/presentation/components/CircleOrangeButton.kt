package com.company.howfardidisprint.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun CircleOrangeButton(
    imageVector: ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            modifier = Modifier
                .size(100.dp)
                .padding(5.dp)
                .clip(shape = CircleShape)
                .shadow(5.dp),
            onClick = {
                onClick()
            },
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(
                    250, 82, 7
                )
            )
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = imageVector.toString(),
                tint = Color.White,
                modifier = Modifier
                    .weight(2.0f)
                    .size(75.dp)
            )
        }
    }
}