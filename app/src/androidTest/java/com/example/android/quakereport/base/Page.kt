package com.example.android.quakereport.base

abstract class Page {
    abstract fun waitForLoading()

    init {
        waitForLoading()
    }
}