package com.devsadeq.assignment1001.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devsadeq.assignment1001.domain.model.NetworkException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch


abstract class BaseViewModel<E> : ViewModel() {

    private val _uiEffect = MutableSharedFlow<E>()
    val uiEffect = _uiEffect.asSharedFlow().throttleFirst(500L).mapNotNull { it }

    protected fun sendUiEffect(effect: E) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiEffect.emit(effect)
        }
    }

    protected fun <T> tryToExecute(
        action: suspend () -> T,
        onError: (ErrorState) -> Unit,
        onSuccess: (T) -> Unit,
        scope: CoroutineScope = viewModelScope
    ): Job {
        return runWithErrorCheck(
            onError = onError,
            scope = scope,
        ) {
            val result = action()
            onSuccess(result)
        }
    }

    protected fun <T> tryToCollect(
        action: suspend () -> Flow<T>,
        onNewValue: (T) -> Unit,
        onError: (ErrorState) -> Unit,
        onSuccess: (T) -> Unit,
        scope: CoroutineScope = viewModelScope
    ): Job {
        return runWithErrorCheck(
            onError = onError,
            scope = scope,
        ) {
            action().distinctUntilChanged()
                .collectLatest { onNewValue(it) }
        }
    }

    private fun runWithErrorCheck(
        onError: (ErrorState) -> Unit,
        scope: CoroutineScope = viewModelScope,
        dispatchers: CoroutineDispatcher = Dispatchers.IO,
        action: suspend () -> Unit,
    ): Job {
        return scope.launch(dispatchers) {
            try {
                action()
            } catch (e: NetworkException.NoInternetException) {
                onError(ErrorState.NoInternet)
            } catch (e: NetworkException.NotFoundException) {
                onError(ErrorState.NotFound)
            } catch (e: Exception) {
                onError(ErrorState.Unknown)
            }
        }
    }

    private fun <T> Flow<T>.throttleFirst(periodMillis: Long): Flow<T> {
        var lastEmissionTime = 0L
        return flow {
            collect { value ->
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastEmissionTime > periodMillis) {
                    lastEmissionTime = currentTime
                    emit(value)
                }
            }
        }
    }
}