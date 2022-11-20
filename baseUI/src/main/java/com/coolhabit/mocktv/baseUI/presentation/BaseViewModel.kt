package com.coolhabit.mocktv.baseUI.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coolhabit.mocktv.baseUI.model.NavCommand
import com.coolhabit.mocktv.baseUI.model.StatefulData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    private val _navigationCommand = MutableSharedFlow<NavCommand>(
        0, 0, BufferOverflow.SUSPEND)
    val navigationCommand: Flow<NavCommand> = _navigationCommand

    protected fun navigateTo(command: NavCommand) {
        viewModelScope.launch(Dispatchers.IO) {
            _navigationCommand.emit(command)
        }
    }

    fun navigateBack(popBackStack: Int = -1) {
        viewModelScope.launch(Dispatchers.IO) {
            _navigationCommand.emit(NavCommand.GoBack(popBackStack))
        }
    }

    protected fun <T> statefulSharedFlow(): MutableSharedFlow<StatefulData<T>> =
        MutableSharedFlow<StatefulData<T>>(
            replay = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
            .apply {
                viewModelScope.launch {
                    emit(StatefulData.loading())
                }
                distinctUntilChanged()
            }

    protected fun <T> getData(
        block: suspend () -> T,
    ): Flow<StatefulData<T>> = flow {
        try {
            emit(StatefulData.loading())
            emit(StatefulData.success(block()))
        } catch (error: Throwable) {
            emit(StatefulData.failure(error))
        }
    }

    fun <T> MutableSharedFlow<StatefulData<T>>.fetch(
        block: suspend () -> T,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            getData(block).collect {
                emit(it)
            }
        }
    }
}
