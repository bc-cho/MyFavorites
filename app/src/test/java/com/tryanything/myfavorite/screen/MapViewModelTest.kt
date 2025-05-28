@file:OptIn(ExperimentalCoroutinesApi::class)

package com.tryanything.myfavorite.screen

import com.tryanything.myfavorite.MainDispatcherRule
import com.tryanything.myfavorite.ui.screen.MapViewModel
import com.tryanything.myfavorites.model.dto.FavoriteDto
import com.tryanything.myfavorites.model.dto.PlaceDto
import com.tryanything.myfavorites.repository.FavoriteRepository
import com.tryanything.myfavorites.repository.PlaceRepository
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MapViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    lateinit var placeRepository: PlaceRepository

    @MockK
    lateinit var favoriteRepository: FavoriteRepository

    private lateinit var mapViewModel: MapViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        coEvery { placeRepository.searchByText("Tokyo Station") } returns mockPlaceResult
        coEvery { favoriteRepository.observeAllFavorites() } returns flow { mockFavoriteResult }
        mapViewModel = MapViewModel(placeRepository, favoriteRepository, Dispatchers.Main)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun testIfReceiveSearchResultsForEachDelay() = runTest {
        coEvery { placeRepository.searchByText("Tokyo Station") } returns mockPlaceResult
        coEvery { favoriteRepository.observeAllFavorites() } returns flow { mockFavoriteResult }
        mapViewModel.searchByText("Tokyo Station")
        mapViewModel.searchResult.first()
        advanceTimeBy(480L)
        assert(mapViewModel.searchResult.value.isEmpty())
        advanceTimeBy(500L)
        assert(mapViewModel.searchResult.value.size == 1)
    }

    private val mockPlaceResult = listOf<PlaceDto>(
        PlaceDto(
            id = 0,
            name = "Tokyo Station",
            address = "東京都千代田区丸の内1-9-1",
            imageName = null,
            imageUrl = null,
            latitude = 35.681382,
            longitude = 139.766084
        )
    )

    private val mockFavoriteResult = listOf<FavoriteDto>()
}
