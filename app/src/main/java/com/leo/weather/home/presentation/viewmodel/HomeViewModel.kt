package com.leo.weather.home.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leo.weather.home.domain.model.WeatherInfo
import com.leo.weather.home.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {
    private val weather = MutableLiveData<WeatherInfo>()
    private val state = MutableLiveData<UiState>()

    val stateInfo : LiveData<UiState>
        get() = state
    val weatherInfo : LiveData<WeatherInfo>
        get() = weather


    fun getWeather(location: String) {
        try {
            state.value = UiState.Loading

            viewModelScope.launch(Dispatchers.IO) {
                repository.getWeather(location).let {
                    weather.postValue(it)
                }
            }
        } catch (e: Exception) {
            state.value = UiState.Error
        }


    }
}

sealed class UiState {
    data class Content(val weather: String) : UiState()
    data object Loading : UiState()
    data object Error : UiState()
}