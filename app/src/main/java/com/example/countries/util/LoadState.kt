package com.example.countries.util

enum class State {
    RUNNING,
    SUCCESS,
    FAILED
}

@Suppress("DataClassPrivateConstructor")
data class LoadState private constructor(
    val status: State,
    val msg: String? = null
) {
    companion object {
        val LOADED = LoadState(State.SUCCESS)
        val LOADING = LoadState(State.RUNNING)
        fun error(msg: String?) = LoadState(State.FAILED, msg)
    }
}