package com.example.exceptions

import java.io.IOException

sealed class TestHotelsAppException(val code: Int, message: String) : IOException(message) {

    class NoInternetConnectionException(message: String = "") : TestHotelsAppException(NO_INTERNET_CONNECTION, message)

    class ServiceUnavailableException(message: String = "") : TestHotelsAppException(SERVICE_UNAVAILABLE, message)

    companion object {
        const val NO_INTERNET_CONNECTION = 1001
        const val SERVICE_UNAVAILABLE = 1002
    }
}

