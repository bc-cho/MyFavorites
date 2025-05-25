package com.tryanything.myfavorite.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tryanything.myfavorites.model.dto.FavoriteDto
import com.tryanything.myfavorites.repository.FavoriteRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class FavoriteViewModel(private val repository: FavoriteRepository) : ViewModel() {

    val favorites: StateFlow<List<FavoriteDto>> = repository
        .observeAllFavorites()
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            emptyList()
        )
}
