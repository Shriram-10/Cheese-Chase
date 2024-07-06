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

    data class StateOfRetrival_ObstacleCourse(
        val loading : Boolean = true,
        val value : List<String>? = null,
        val error : String? = null
    )

    data class StateOfRetrival_RandomWord(
        val loading : Boolean = true,
        val value : String? = null,
        val error : String? = null
    )

    data class StateOfRetrival_Theme(
        val loading : Boolean = true,
        val value : Int? = null,
        val error : String? = null
    )

    init {
        if (chooseCollisionSource.value) {
            fetchObstacleLimit()
        }
    }

    fun fetchObstacleCourse(extent : Int){
        viewModelScope.launch {
            try{
                val response = dataService.getObstacleCourse(obstacleCourseRequest(extent = extent))
                _stateOfObstacleCourse.value = _stateOfObstacleCourse.value.copy(
                    loading = false,
                    value = response.obstacleCourse
                )
            } catch (e : Exception) {
                _stateOfObstacleCourse.value = _stateOfObstacleCourse.value.copy(
                    loading = false,
                    error = "Data not loaded.\n${e.localizedMessage}"
                )
            }
        }
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

    fun fetchRandomWord(length: Int) {
        viewModelScope.launch {
            try {
                val response = dataService.getRandomWord(randomWordRequest(length = length))
                _stateOfRandomWord.value = _stateOfRandomWord.value.copy(
                    loading = false,
                    value = response.word
                )
            } catch (e : Exception) {
                _stateOfRandomWord.value = _stateOfRandomWord.value.copy(
                    loading = false,
                    error = "Data not loaded.\n${e.localizedMessage}"
                )
            }
        }
    }

    fun fetchTheme(date : String, time : String) {
        viewModelScope.launch {
            try {
                val response = dataService.getTheme(themeRequest(date = date, time = time))
                _stateOfRandomWord.value = _stateOfRandomWord.value.copy(
                    loading = false,
                    value = response.theme
                )
            } catch (e : Exception) {
                _stateOfRandomWord.value = _stateOfRandomWord.value.copy(
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

    private val _stateOfObstacleCourse = mutableStateOf(StateOfRetrival_ObstacleCourse())
    val stateOfObstacleCourse : State<StateOfRetrival_ObstacleCourse> = _stateOfObstacleCourse

    private val _stateOfRandomWord = mutableStateOf(StateOfRetrival_RandomWord())
    val stateOfRandomWord : State<StateOfRetrival_RandomWord> = _stateOfRandomWord

    private val _stateOfTheme = mutableStateOf(StateOfRetrival_Theme())
    val stateOfTheme : State<StateOfRetrival_Theme> = _stateOfTheme
}