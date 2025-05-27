package com.tryanything.myfavorite.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.tryanything.myfavorite.model.Place
import com.tryanything.myfavorite.ui.theme.MyFavoritesTheme
import com.tryanything.myfavorite.ui.widget.MapSearchBar
import com.tryanything.myfavorite.ui.widget.PlaceCard

@Composable
fun MapScreen(viewModel: MapViewModel) {
    val searchResults by viewModel.searchResult.collectAsStateWithLifecycle()
    val selectedPlace by viewModel.selectedPlace.collectAsStateWithLifecycle()
    MapView(
        searchResults,
        selectedPlace,
        viewModel::selectPlace,
        viewModel::searchByText,
        viewModel::addToFavorite
    )
}

@Composable
private fun MapView(
    searchResults: List<Place>,
    selectedPlace: Place?,
    onPlaceSelected: (Place) -> Unit,
    searchByText: (String) -> Unit,
    addToFavorite: (Place) -> Unit
) {
    // FIXME: 現在位置取得後を指定する
    val tokyo = LatLng(35.6895, 139.69171)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(tokyo, 10f)
    }
    val marker = rememberMarkerState(position = tokyo)
    val title = remember { mutableStateOf("") }
    Box(Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.matchParentSize(),
            cameraPositionState = cameraPositionState
        ) {
            selectedPlace?.also { place ->
                Marker(
                    state = marker,
                    title = title.value,
                )
            }
        }

        Column(
            Modifier
                .wrapContentSize()
                .align(alignment = Alignment.TopCenter)
                .windowInsetsPadding(WindowInsets.statusBars)
        ) {
            Spacer(Modifier.height(10.dp))
            MapSearchBar(searchResults, searchByText) { selectedItem ->
                marker.position = selectedItem.latLng
                onPlaceSelected.invoke(selectedItem)
                title.value = selectedItem.name
                cameraPositionState.position =
                    CameraPosition.fromLatLngZoom(selectedItem.latLng, 10f)
            }
        }

        selectedPlace?.also { selectedPlace ->
            SelectedPlace(
                Modifier
                    .padding(horizontal = 15.dp)
                    .align(Alignment.BottomCenter),
                selectedPlace,
                addToFavorite
            )
        }
    }
}

@Composable
private fun SelectedPlace(
    modifier: Modifier,
    selectedPlace: Place,
    onFavoriteClick: (Place) -> Unit
) {
    Card(
        modifier = modifier
            .wrapContentSize()
            .windowInsetsPadding(WindowInsets.navigationBars),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        PlaceCard(selectedPlace, onFavoriteClick)
    }
}


@Preview
@Composable
fun PreviewMapScreen() {
    MyFavoritesTheme {
        MapView(emptyList(), null, {}, {}, {})
    }
}

@Preview
@Composable
fun PreviewSelectedPlace() {
    MyFavoritesTheme {
        SelectedPlace(Modifier, Place(0, "Place1", "Tokyo", 0.0, 0.0, null, null, false), {})
    }
}
