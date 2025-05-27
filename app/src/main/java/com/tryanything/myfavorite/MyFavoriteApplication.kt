package com.tryanything.myfavorite

import android.app.Application
import com.tryanything.myfavorite.di.dispatcher
import com.tryanything.myfavorite.di.viewModels
import com.tryanything.myfavorites.di.repositories
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyFavoriteApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyFavoriteApplication)
            modules(dispatcher)
            modules(repositories)
            modules(viewModels)
        }
    }
}
