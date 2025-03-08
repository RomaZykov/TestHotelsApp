package com.example.exceptions

interface HandleError {
    fun handle(error: Exception) : Throwable
}