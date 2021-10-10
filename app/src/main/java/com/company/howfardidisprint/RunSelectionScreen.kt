package com.company.howfardidisprint

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.company.howfardidisprint.model.RunDistance
import com.company.howfardidisprint.model.RunViewModel
import com.company.howfardidisprint.model.RunViewModelFactory
import com.company.howfardidisprint.model.SortType
import com.company.howfardidisprint.presentation.components.*
import com.company.howfardidisprint.ui.theme.roboto

@Composable
fun RunSelectionScreen(
    navController: NavController,
    onUpdateDistance: (RunDistance) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        SubHeader("Choose your distance")
        for (distance in RunDistance.values()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                elevation = 2.dp,
                shape = RectangleShape
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                        Text(
                            text = distance.toString(),
                            style = TextStyle(
                                color = Color(80, 80, 80),
                                fontSize = 20.sp,
                                fontFamily = roboto,
                                letterSpacing = 0.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                            ),
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                        Row(modifier = Modifier.width(150.dp)) {
                            WhiteButton("SELECT", onClick = {
                                onUpdateDistance(distance)
                                navController.navigate(Screen.SprintScreen.route)
                            })
                        }
                    }
            }
        }
    }
}
