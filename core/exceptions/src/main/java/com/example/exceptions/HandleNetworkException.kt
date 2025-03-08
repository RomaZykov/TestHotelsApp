package com.example.exceptions

import com.example.common.ManageResources
import com.example.exceptions.TestHotelsAppException.Companion.NO_DATA_FOUND
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

                    NO_DATA_FOUND -> TestHotelsAppException.NoDataFoundException(
                        manageResources.string(R.string.no_data_found)
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