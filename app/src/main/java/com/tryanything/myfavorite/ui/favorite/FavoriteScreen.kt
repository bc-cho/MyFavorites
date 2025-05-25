package com.tryanything.myfavorite.ui.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.BrokenImage
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.placeholder
import com.tryanything.myfavorite.R
import com.tryanything.myfavorite.ui.theme.MyFavoritesTheme
import com.tryanything.myfavorites.model.dto.FavoriteDto

@Composable
fun FavoriteScreen(viewModel: FavoriteViewModel) {
    val places: List<FavoriteDto> by viewModel.favorites.collectAsStateWithLifecycle()
    if (places.isEmpty()) {
        EmptyListView()
    } else {
        FavoriteListView(places)
    }
}

@Composable
private fun EmptyListView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            stringResource(R.string.favorite_no_item),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
private fun FavoriteListView(places: List<FavoriteDto>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(places, key = { item -> item.id }) { item ->
            FavoriteListItem(
                item = item,
                isFavorite = true,
                onFavoriteClick = { },
                onItemClick = {
                    // TODO: DetailViewに進む
                }
            )
        }
    }
}

@Composable
private fun FavoriteListItem(
    item: FavoriteDto,
    isFavorite: Boolean, // 削除も考慮する
    onFavoriteClick: () -> Unit,
    onItemClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onItemClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            item.imageUrl?.let { url ->
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data(data = url)
                            .apply(block = fun ImageRequest.Builder.() {
                                crossfade(true)
                                placeholder(R.drawable.ic_error)
                                error(R.drawable.ic_error)
                            }).build()
                    ),
                    contentDescription = item.name,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            } ?: Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Outlined.BrokenImage,
                    contentDescription = "No image",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.address,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = if (isFavorite)
                        stringResource(R.string.favorite_description_remove_item)
                    else stringResource(R.string.favorite_description_add_item),
                    tint = if (isFavorite) MaterialTheme.colorScheme.primary else Color.Gray
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewFavoriteList() {
    MyFavoritesTheme {
        FavoriteListView(
            listOf(
                FavoriteDto(
                    0,
                    "東京都庁",
                    "〒163-8001 東京都新宿区西新宿２丁目８−１",
                    null,
                    0.0,
                    0.0
                ),
                FavoriteDto(
                    1,
                    "御茶ノ水駅",
                    "〒101-0062 東京都千代田区神田駿河台２丁目",
                    null,
                    0.0,
                    0.0
                ),
                FavoriteDto(
                    2,
                    "東京駅",
                    "〒100-0005 東京都千代田区丸の内１丁目",
                    null,
                    0.0,
                    0.0
                ),
            )
        )
    }
}


@Preview
@Composable
fun PreviewFNoFavoriteList() {
    MyFavoritesTheme {
        EmptyListView()
    }
}
