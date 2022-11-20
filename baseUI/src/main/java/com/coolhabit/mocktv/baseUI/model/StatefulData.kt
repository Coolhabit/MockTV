package com.coolhabit.mocktv.baseUI.model

sealed class StatefulData<T> {

    class Loading<T>() : StatefulData<T>()
    class Success<T>(val value: T) : StatefulData<T>()
    class Error<T>(val throwable: Throwable) : StatefulData<T>()

    val isLoading: Boolean get() = this is Loading

    fun isSuccessful(get: (T) -> Unit) {
        when (this) {
            is Loading -> {
            }
            is Error -> {
            }
            is Success -> get(value)
        }
    }

    fun isLoading(get: () -> Unit) {
        when (this) {
            is Loading -> get()
            is Error -> {
            }
            is Success -> {
            }
        }
    }

    fun isError() {
        when (this) {
            is Loading -> {
            }
            is Error -> println(throwable.message)
            is Success -> {
            }
        }
    }
}
