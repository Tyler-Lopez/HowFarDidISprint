package com.company.howfardidisprint

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.company.howfardidisprint.presentation.components.*
import com.company.howfardidisprint.ui.theme.roboto

@Composable
fun HistoryScreen(navController: NavController) {
    //  val state = viewModel.state.value
    val context = LocalContext.current
    val mScoreEntryViewModel: ScoreEntryViewModel = viewModel(
        factory = ScoreEntryViewModelFactory(context.applicationContext as Application)
    )
    var leaderBoards =
        mScoreEntryViewModel.readAllData.observeAsState(listOf()).value

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    ) {
        SubHeader("400 METER SPRINT HISTORY")
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            items(leaderBoards.size) {
                val score = leaderBoards?.get(it)
                val dateOfRun = millisecondsToLocalDateTime(score.date)
                val day = dateOfRun.dayOfMonth
                val month = dateOfRun.monthValue
                val year = dateOfRun.year
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
                        Arrangement.SpaceAround
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            Arrangement.Center,
                            Alignment.CenterVertically
                        ) {
                            Text(
                                text = score.time.toString(),
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
                            Text(
                                text = " s",
                                style = TextStyle(
                                    color = Color(80, 80, 80),
                                    fontSize = 15.sp,
                                    fontFamily = roboto,
                                    letterSpacing = 0.sp,
                                    fontWeight = FontWeight.Medium,
                                    textAlign = TextAlign.Center,
                                ),
                            )
                        }
                        Text(
                            text = "$day-$month-$year",
                            style = TextStyle(
                                color = Color(80, 80, 80),
                                fontSize = 20.sp,
                                fontFamily = roboto,
                                letterSpacing = 0.sp,
                                fontWeight = FontWeight.Light,
                                textAlign = TextAlign.Center,
                            ),
                            modifier = Modifier
                                .padding(vertical = 10.dp)
                                .weight(1f)
                        )
                    }
                }
            }
        }
    }
}