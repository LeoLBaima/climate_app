package com.leo.weather.home.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leo.weather.R
import com.leo.weather.commons.components.NimbusText
import com.leo.weather.home.domain.model.WeatherInfo
import com.leo.weather.home.presentation.viewmodel.HomeViewModel
import com.leo.weather.home.presentation.viewmodel.UiState
import com.leo.weather.ui.theme.Background
import com.leo.weather.ui.theme.ClimateTheme
import com.leo.weather.ui.theme.SunnyLight
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ClimateTheme {
                val uiState by viewModel.stateInfo.observeAsState()
                when (uiState) {
                    is UiState.Content -> HomeContent((uiState as UiState.Content).weather)
                    UiState.Initial -> SearchView(viewModel)
                    UiState.Loading -> LoadingContent()
                    else -> ErrorContent()
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(weather: WeatherInfo) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = Background,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Background
                ),
                title = { NimbusText(text = weather.location.name, fontSize = 26.sp) },
                actions = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.search),
                            contentDescription = "Search icon"
                        )
                    }
                },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            NimbusText(
                text = weather.location.localTime,
                fontWeight = FontWeight.Light,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun LoadingContent() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = Background,
        ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            CircularProgressIndicator(
                color = SunnyLight,
            )
        }
    }
}

@Composable
fun SearchView(
    viewModel: HomeViewModel
) {
    val city by lazy { mutableStateOf("") }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = Background,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.weight(1f),
            ) {
                OutlinedTextField(
                    value = city.value,
                    onValueChange = {
                        city.value = it
                    },
                    label = { NimbusText(text = "City") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SunnyLight,
                        unfocusedBorderColor = SunnyLight
                    )
                )
            }
            Button(
                enabled = city.value.isNotEmpty(),
                onClick = {
                    viewModel.getWeather(city.value)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = SunnyLight,
                )
            ) {
                NimbusText("Confirmar")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorContent() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = Background,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Background
                ),
                title = { NimbusText(text = "", fontSize = 26.sp) },
                actions = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.search),
                            contentDescription = "Search icon"
                        )
                    }
                },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NimbusText(
                text = "Error, try searching again",
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            )
        }
    }
}