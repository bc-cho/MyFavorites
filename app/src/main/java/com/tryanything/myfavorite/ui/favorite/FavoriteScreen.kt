package com.tryanything.myfavorite.ui.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.tryanything.myfavorite.R
import com.tryanything.myfavorite.model.Place
import com.tryanything.myfavorite.ui.theme.MyFavoritesTheme
import com.tryanything.myfavorite.ui.widget.PlaceCard

@Composable
fun FavoriteScreen(viewModel: FavoriteViewModel) {
    val places: List<Place> by viewModel.favorites.collectAsStateWithLifecycle()
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
private fun FavoriteListView(places: List<Place>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(places, key = { item -> item.id }) { item ->
            FavoriteListItem(
                item = item,
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
    item: Place,
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
        PlaceCard(item, onFavoriteClick)
    }
}

@Preview
@Composable
fun PreviewFavoriteList() {
    MyFavoritesTheme {
        FavoriteListView(
            listOf(
                Place(
                    0,
                    "東京都庁",
                    "〒163-8001 東京都新宿区西新宿２丁目８−１",
                    0.0,
                    0.0,
                    null
                ),
                Place(
                    1,
                    "御茶ノ水駅",
                    "〒101-0062 東京都千代田区神田駿河台２丁目",
                    0.0,
                    0.0,
                    null
                ),
                Place(
                    2,
                    "東京駅",
                    "〒100-0005 東京都千代田区丸の内１丁目",
                    0.0,
                    0.0,
                    null
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
