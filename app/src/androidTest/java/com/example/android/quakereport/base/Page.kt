package com.example.android.quakereport.base

abstract class Page {
    abstract fun waitForLoadingOf()

    init {
        waitForLoadingOf()
    }
}