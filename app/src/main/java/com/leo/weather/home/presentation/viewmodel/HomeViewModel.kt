package com.leo.weather.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class HomeViewModel : ViewModel() {

}

sealed class UiState {
    data class Content(val weather: String) : UiState()
    data object Loading : UiState()
    data object Error : UiState()
}