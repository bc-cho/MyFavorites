package com.tryanything.myfavorite.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tryanything.myfavorite.model.Place
import com.tryanything.myfavorites.repository.FavoriteRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class FavoriteViewModel(
    private val repository: FavoriteRepository
) : ViewModel() {

    val favorites: StateFlow<List<Place>> = repository
        .observeAllFavorites()
        .map { list -> list.map { Place(it) } }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            emptyList()
        )
}
