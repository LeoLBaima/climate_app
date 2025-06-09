package com.leo.weather.home.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leo.weather.commons.components.NimbusText
import com.leo.weather.home.presentation.viewmodel.HomeViewModel
import com.leo.weather.ui.theme.Background
import com.leo.weather.ui.theme.ClimateTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    private val viewModel : HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ClimateTheme {
                HomeContent()
            }
        }
    }
}


@Composable
fun HomeContent() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = Background
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            NimbusText(
                text = "Fortaleza",
                fontSize = 26.sp
            )
            Box(
                modifier = Modifier.height(4.dp)
            )
            NimbusText(
                text = "8, June",
                fontWeight = FontWeight.Light,
                fontSize = 18.sp
            )
        }
    }
}