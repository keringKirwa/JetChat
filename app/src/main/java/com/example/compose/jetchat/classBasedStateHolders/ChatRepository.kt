package com.example.compose.jetchat.classBasedStateHolders

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ChatRepository {
    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: Flow<List<ChatMessage>> get() = _messages.asStateFlow()

    private val messagesList = listOf<Int>().toMutableList()

    suspend fun sendMessage(message: ChatMessage) {

        _messages.emit(_messages.value + message)

        /*TODO: The + operator above is used for creating a new list by concatenating immutable lists to other lists:We then send the message to the other
             user too using FCM or web sockets.
             NB: the .add() method is for the mutableList Interface in Kotlin only*/
    }

}

data class ChatMessage(val sender: String, val content: String)


suspend fun main() {
    val chatRepository = ChatRepository()

    runBlocking {

        /*The launch  is used in fire-and-forget scenarios: actions that should not return a result.It returns a Job representing the coroutine itself*/

        val jobCoroutine = launch(Dispatchers.IO) {
            delay(4000L)
            chatRepository.sendMessage(ChatMessage(sender = "User1", content = "Hello, User2!"))

        }
        launch(Dispatchers.IO) {
            delay(5000L)
            chatRepository.sendMessage(ChatMessage(sender = "User2", content = "Hi, User1!"))

        }
        val deferredResult: Deferred<String> = async {
            // Asynchronous computation : the await() suspends the coroutine  until the result is ready
            "Result"
        }

        val result: String = deferredResult.await()

    }

    // Observe messages
    chatRepository.messages.collect { messages ->
        println("Received messages: $messages")
    }

}
