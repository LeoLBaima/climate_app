package com.leo.weather.home.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leo.weather.R
import com.leo.weather.commons.components.NimbusText
import com.leo.weather.commons.extensions.getDayAndMonthFromDate
import com.leo.weather.commons.extensions.getHourFromDate
import com.leo.weather.commons.extensions.getWeekdayFromDate
import com.leo.weather.home.domain.model.WeatherInfo
import com.leo.weather.home.presentation.viewmodel.HomeViewModel
import com.leo.weather.home.presentation.viewmodel.UiState
import com.leo.weather.ui.theme.Background
import com.leo.weather.ui.theme.ClimateTheme
import com.leo.weather.ui.theme.SunnyLight
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

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
                    is UiState.Content -> HomeContent(
                        (uiState as UiState.Content).weather,
                        viewModel
                    )

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
fun HomeContent(weather: WeatherInfo, viewModel: HomeViewModel) {
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
                        onClick = {
                            viewModel.startSearch()
                        }
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
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                NimbusText(
                    text = weather.location.localTime.getDayAndMonthFromDate(),
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Light,
                    fontSize = 18.sp
                )
            }
            Spacer(
                modifier = Modifier.height(40.dp)
            )
            Image(
                modifier = Modifier
                    .height(height = 120.dp)
                    .width(width = 120.dp),
                painter = painterResource(R.drawable.cloudy),
                contentDescription = "condition icon"
            )
            NimbusText(
                text = "${weather.currentWeatherInfo.temperature.toInt()}°",
                fontSize = 26.sp
            )
            NimbusText(
                text = weather.currentWeatherInfo.condition.condition,
                fontSize = 22.sp
            )
            Spacer(
                modifier = Modifier.height(44.dp)
            )
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val currentHour = weather.location.localTime.getHourFromDate().toInt()
                val futureWeatherListingItems = weather.forecast.forecastDays[0].hoursInterval
                    .drop(currentHour + 1) +
                        weather.forecast.forecastDays[1].hoursInterval.take(currentHour + 1)

                itemsIndexed(futureWeatherListingItems.take(24)) { index, item -> // limit to 24 if needed
                    Column(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        NimbusText("${getHourText((currentHour + 1 + index) % 24)}:00")
                        NimbusText("${item.temperature}°")
                    }
                }
            }
            Spacer(
                modifier = Modifier.height(16.dp)
            )
            HorizontalDivider(
                color = SunnyLight
            )
            Spacer(
                modifier = Modifier.height(16.dp)
            )
            LazyColumn(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                items(weather.forecast.forecastDays) {item ->
                    Row {
                        NimbusText(
                            modifier = Modifier.weight(1f),
                            text = item.date.getWeekdayFromDate()
                                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
                        )
                        NimbusText(
                            modifier = Modifier.weight(2f),
                            text = item.day.condition.condition
                        )
                        NimbusText(text = "${item.day.maxTemp.toInt()}°/${item.day.minTemp.toInt()}")
                    }
                }
            }
        }
    }
}

fun getHourText(number: Int): String {
    return if (number < 10) {
        "0$number"
    } else {
        number.toString()
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