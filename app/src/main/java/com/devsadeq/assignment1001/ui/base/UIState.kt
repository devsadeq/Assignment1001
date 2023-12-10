package com.devsadeq.assignment1001.ui.base

import kotlinx.coroutines.flow.StateFlow

interface Stateful<T> {
    val uiState: StateFlow<T>
}