package com.tryanything.myfavorite.di

import com.tryanything.myfavorite.ui.favorite.FavoriteViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModels = module {
    viewModelOf(::FavoriteViewModel)
}
