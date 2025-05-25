package com.tryanything.myfavorite

import android.app.Application
import com.tryanything.myfavorite.di.viewModels
import com.tryanything.myfavorite.ui.favorite.FavoriteViewModel
import com.tryanything.myfavorites.di.repositories
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

class MyFavoriteApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyFavoriteApplication)
            modules(repositories)
            modules(viewModels)
        }
    }
}
