package com.example.thecheesechaseapplication

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    data class StateOfRetrival_obstacleLimit(
        val loading : Boolean = true,
        val value : Int = 0,
        val error : String? = null
    )

    init {
        fetchObstacleLimit()
    }

    private fun fetchObstacleLimit() {
        viewModelScope.launch {
            try{
                val response = dataService.getObstacleLimit()
                _state.value = _state.value.copy(
                    loading = false,
                    value = response.obstacleLimit
                )
            } catch (e : Exception) {
                _state.value = _state.value.copy(
                    loading = false,
                    error = "Data not loaded.\n${e.localizedMessage}"
                )
            }
        }
    }

    private val _state = mutableStateOf(StateOfRetrival_obstacleLimit())
    val state : State<StateOfRetrival_obstacleLimit> = _state
}