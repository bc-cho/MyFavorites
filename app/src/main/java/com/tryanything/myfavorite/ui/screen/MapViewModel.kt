package com.tryanything.myfavorite.ui.screen

import androidx.lifecycle.ViewModel
import com.tryanything.myfavorites.repository.PlaceRepository
import androidx.lifecycle.viewModelScope
import com.tryanything.myfavorite.model.Place
import com.tryanything.myfavorites.model.dto.FavoriteDto
import com.tryanything.myfavorites.repository.FavoriteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MapViewModel(
    val placeRepository: PlaceRepository,
    val favoriteRepository: FavoriteRepository
) : ViewModel() {

    private val _query = MutableStateFlow("")
    private val _selectedPlace = MutableStateFlow<Place?>(null)

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val searchResult: StateFlow<List<Place>> = _query
        .debounce(500L)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            if (query.length < 2) {
                flowOf(emptyList())
            } else {
                flowOf(placeRepository.searchByText(query).map { Place(it) })
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    val selectedPlace: StateFlow<Place?> =
        // TODO: 指定されたIDでObserveする
        _selectedPlace.combine(favoriteRepository.observeAllFavorites()) { place, favorites ->
            place?.copy(isFavorite = favorites.any { it.id == place.id })
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = null
        )

    fun searchByText(text: String) {
        _query.update { text }
    }

    fun selectPlace(id: Place) {
        _selectedPlace.update { id }
    }

    fun addToFavorite(item: Place) {
        viewModelScope.launch {
            favoriteRepository.addFavorite(
                FavoriteDto(
                    id = item.id,
                    name = item.name,
                    address = item.address,
                    imageName = item.imageName,
                    lat = item.latitude,
                    lon = item.longitude
                )
            )
        }
    }
}
