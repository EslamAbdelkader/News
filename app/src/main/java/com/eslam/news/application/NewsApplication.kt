package com.eslam.news.application

import android.app.Application
import com.eslam.news.di.Injection

class NewsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Injection.application = this
    }
}