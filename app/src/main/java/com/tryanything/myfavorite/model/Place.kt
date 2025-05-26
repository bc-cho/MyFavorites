package com.tryanything.myfavorite.model

import com.tryanything.myfavorites.model.dto.FavoriteDto
import com.tryanything.myfavorites.model.dto.PlaceDto

data class Place(
    val id: Long,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val imageName: String? = null,
    val imageUrl: String? = null,
    val isFavorite: Boolean = false
) {
    constructor(placeDto: PlaceDto) : this(
        id = placeDto.id,
        name = placeDto.name,
        address = placeDto.address,
        latitude = placeDto.latitude,
        longitude = placeDto.longitude,
        imageName = placeDto.imageName,
        imageUrl = placeDto.imageUrl,
        isFavorite = placeDto.isFavorite
    )

    constructor(favoriteDto: FavoriteDto) : this(
        id = favoriteDto.id,
        name = favoriteDto.name,
        address = favoriteDto.address,
        latitude = favoriteDto.lat,
        longitude = favoriteDto.lon,
        imageName = favoriteDto.imageName,
        imageUrl = favoriteDto.imageUrl
    )
}
