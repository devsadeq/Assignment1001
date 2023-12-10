package com.devsadeq.assignment1001.ui.base

sealed interface ErrorState {
    data object NoInternet : ErrorState
    data object NotFound : ErrorState
    data object Unknown : ErrorState
}