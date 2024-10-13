package dev.supersam.di

import KoinScopeWith
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


@KoinScopeWith(scopeName = "HomeViewModelScope")
class HomeViewModel : ViewModel() {
    var message by mutableStateOf("initial viewmodel value")
}