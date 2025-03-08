package com.example.exceptions

import java.io.IOException

sealed class TestHotelsAppException(val code: Int, message: String) : IOException(message) {

    class NoInternetConnectionException(message: String = "") : TestHotelsAppException(NO_INTERNET_CONNECTION, message)

    class NoDataFoundException(message: String = "") : TestHotelsAppException(NO_DATA_FOUND, message)

    companion object {
        const val NO_INTERNET_CONNECTION = 1001
        const val NO_DATA_FOUND = 1002
    }
}

