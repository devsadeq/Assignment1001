package com.devsadeq.assignment1001.ui.base

sealed interface ErrorState {
    object NoInternet : ErrorState
    object Unknown : ErrorState
}