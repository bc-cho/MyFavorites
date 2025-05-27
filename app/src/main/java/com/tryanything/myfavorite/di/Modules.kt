package com.tryanything.myfavorite.di

import com.tryanything.myfavorite.ui.favorite.FavoriteViewModel
import com.tryanything.myfavorite.ui.screen.MapViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val dispatcher = module {
    single<CoroutineDispatcher> { Dispatchers.IO }
}

val viewModels = module {
    viewModelOf(::FavoriteViewModel)
    viewModelOf(::MapViewModel)
}
