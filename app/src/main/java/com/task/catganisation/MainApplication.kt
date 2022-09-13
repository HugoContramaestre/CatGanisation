package com.task.catganisation

import android.app.Application
import com.task.catganisation.di.initDI
import timber.log.Timber

class MainApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        initDI()

        Timber.plant(Timber.DebugTree())
    }
}