package com.example.compose.jetchat.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class DummyViewModel : ViewModel() {

    val name: String = "Hello"

    init {
        if (name.isBlank()) {
            print(name)

        }
    }

    val chatMessageFLow: StateFlow<String> = flow {
        val initialMessage: String = getUserChatMessages()
        emit(initialMessage)

    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        ""
    )

    private suspend fun getUserChatMessages(): String {
        delay(2000L)
        return "Hello There Kelvin Kering"

    }

    private val _drawerShouldBeOpened = MutableStateFlow(false)
    val drawerShouldBeOpened = _drawerShouldBeOpened.asStateFlow()

    fun openDrawer() {
        _drawerShouldBeOpened.value = true
    }

    fun resetOpenDrawerAction() {
        _drawerShouldBeOpened.value = false
    }
}