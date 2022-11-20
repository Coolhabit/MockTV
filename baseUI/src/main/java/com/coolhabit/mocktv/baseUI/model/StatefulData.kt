package com.coolhabit.mocktv.baseUI.model

sealed class StatefulData<T>() {

    class Loading<T>() : StatefulData<T>()
    class Success<T>(val value: T) : StatefulData<T>()
    class Error<T>(val throwable: Throwable) : StatefulData<T>()

    val isLoading: Boolean get() = this is Loading

    companion object {
        fun <T> loading(): StatefulData<T> = Loading()
        fun <T> success(value: T): StatefulData<T> = Success(value)
        fun <T> failure(throwable: Throwable): Error<T> = Error(throwable)
    }

    fun isSuccessful(operation: (T) -> Unit) {
        when (this) {
            is Loading -> {
            }
            is Error -> {
            }
            is Success -> operation(value)
        }
    }

    fun isLoading(operation: () -> Unit) {
        when (this) {
            is Loading -> operation()
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
