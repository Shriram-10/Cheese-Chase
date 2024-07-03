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

    data class StateOfRetrival_HitHindrance(
        val loading : Boolean = true,
        val value : hitHindrance? = null,
        val error : String? = null
    )

    init {
        fetchObstacleLimit()
    }

    fun fetchObstacleLimit() {
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

    fun fetchHitHindrance(){
        viewModelScope.launch {
            try {
                val response = dataService.getHitHindrance()
                _stateOfHitHindrance.value = _stateOfHitHindrance.value.copy(
                    loading = false,
                    value = response
                )
            } catch (e : Exception) {
                _stateOfHitHindrance.value = _stateOfHitHindrance.value.copy(
                    loading = false,
                    error = "Data not loaded.\n${e.localizedMessage}"
                )
            }
        }
    }

    private val _state = mutableStateOf(StateOfRetrival_obstacleLimit())
    val state : State<StateOfRetrival_obstacleLimit> = _state

    private val _stateOfHitHindrance = mutableStateOf(StateOfRetrival_HitHindrance())
    val stateOfHitHindrance : State<StateOfRetrival_HitHindrance> = _stateOfHitHindrance
}