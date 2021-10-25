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
fun SettingsScreen(
    navController: NavController,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        SubHeader("Settings ")
    }
}
