package com.devsadeq.assignment1001.domain.model

sealed class NetworkException : Exception() {

    object UnAuthorizedException : NetworkException()

    object NoInternetException : NetworkException()

    object TimeoutException : NetworkException()

    object NotFoundException : NetworkException()

    object ApiKeyExpiredException : NetworkException()

}