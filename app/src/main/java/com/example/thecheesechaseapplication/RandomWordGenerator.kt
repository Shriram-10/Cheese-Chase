package com.example.thecheesechaseapplication

import androidx.compose.runtime.getValue

fun randomWordGenerator(dataViewModel: MainViewModel) : String? {

    val length = (5..15).random()
    val viewStateOfWord by dataViewModel.stateOfRandomWord

    dataViewModel.fetchRandomWord(length)

    return viewStateOfWord.value
}