package com.tryanything.myfavorite.ui.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.ArrowBack
import androidx.compose.material.icons.automirrored.sharp.ManageSearch
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tryanything.myfavorite.R
import com.tryanything.myfavorite.model.Place
import com.tryanything.myfavorite.ui.theme.MyFavoritesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapSearchBar(
    searchResults: List<Place>,
    onQueryChanged: (String) -> Unit,
    onSearchItemSelected: (Place) -> Unit
) {
    var query by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val onExpandedChange: (Boolean) -> Unit = {
        expanded = it
    }
    DockedSearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = query,
                onQueryChange = {
                    query = it
                    onQueryChanged.invoke(it)
                },
                onSearch = { expanded = false },
                expanded = expanded,
                onExpandedChange = { expanded = true },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = stringResource(R.string.map_searchbar_placeholder)) },
                leadingIcon = {
                    if (expanded) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Sharp.ArrowBack,
                            modifier = Modifier
                                .padding(12.dp)
                                .size(28.dp)
                                .clickable {
                                    expanded = false
                                    query = ""
                                },
                            contentDescription = "",
                        )
                    } else {
                        Icon(
                            imageVector = Icons.AutoMirrored.Sharp.ManageSearch,
                            modifier = Modifier
                                .padding(12.dp)
                                .size(28.dp),
                            contentDescription = ""
                        )
                    }
                }
            )
        },
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        modifier = Modifier,
        content = {
            if (searchResults.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    items(items = searchResults, key = { it.id }) { place ->
                        ListItem(
                            modifier = Modifier.clickable {
                                onSearchItemSelected.invoke(place)
                                query = ""
                                expanded = false
                            },
                            headlineContent = { Text(place.name) },
                            supportingContent = { Text(place.address) },
                        )
                    }
                }
            } else {
                Text(
                    text = stringResource(id = R.string.map_searchbar_no_result),
                    modifier = Modifier.padding(16.dp),
                )
            }
        },
    )
}

@Preview
@Composable
fun PreViewPlaceSearchBar() {
    MyFavoritesTheme {
        MapSearchBar(onQueryChanged = {}, searchResults = listOf(), onSearchItemSelected = {})
    }
}
