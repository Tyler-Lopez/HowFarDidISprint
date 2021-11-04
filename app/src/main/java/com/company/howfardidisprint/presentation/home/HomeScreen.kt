package com.company.howfardidisprint

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.company.howfardidisprint.model.RunViewModel
import com.company.howfardidisprint.model.RunViewModelFactory
import com.company.howfardidisprint.presentation.components.SubHeader
import com.company.howfardidisprint.ui.theme.roboto


@Composable
fun HomeScreen() {
    // Update singleton with active run for purposes of tracking distance
    val context = LocalContext.current
    val mRunViewModel: RunViewModel = viewModel(
        factory = RunViewModelFactory(context.applicationContext as Application)
    )
    val runList = mRunViewModel.data.observeAsState(listOf()).value


    Column(
        modifier = Modifier
            .fillMaxSize(),
           // .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = 3.dp,
            shape = RectangleShape
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            ) {
                SubHeader("RUN EVERYDAY")
                if (runList.isNotEmpty()) {

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        items(runList.size) {
                            val run = runList?.get(it)
                            val dateOfRun = millisecondsToLocalDateTime(run.startTime)
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
                                            text = "${run.totalTime}",
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
        }
    }
}