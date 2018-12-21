package com.example.countries.util

enum class State {
    RUNNING,
    SUCCESS,
    FAILED
}

@Suppress("DataClassPrivateConstructor")
data class LoadState private constructor(
    val status: State
) {
    companion object {
        val LOADED = LoadState(State.SUCCESS)
        val LOADING = LoadState(State.RUNNING)
        val ERROR = LoadState(State.FAILED)
    }
}