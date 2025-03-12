package com.example.exceptions

import com.example.common.ManageResources
import com.example.exceptions.TestHotelsAppException.Companion.SERVICE_UNAVAILABLE
import com.example.exceptions.TestHotelsAppException.Companion.NO_INTERNET_CONNECTION
import java.io.IOException
import javax.inject.Inject

class HandleNetworkException @Inject constructor(private val manageResources: ManageResources) : HandleError {
    override fun handle(error: Exception): Throwable {
        return when (error) {
            is TestHotelsAppException -> {
                when (error.code) {
                    NO_INTERNET_CONNECTION -> TestHotelsAppException.NoInternetConnectionException(
                        manageResources.string(R.string.no_internet_connection)
                    )

                    SERVICE_UNAVAILABLE -> TestHotelsAppException.ServiceUnavailableException(
                        manageResources.string(R.string.service_unavailable)
                    )

                    else -> Exception()
                }
            }

            else -> {
                IOException(manageResources.string(R.string.check_internet_exception))
            }
        }
    }
}