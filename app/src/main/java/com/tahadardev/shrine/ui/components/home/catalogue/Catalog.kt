package com.tahadardev.shrine.ui.components.home.catalogue

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tahadardev.shrine.data.ItemData
import com.tahadardev.shrine.data.SampleItems
import com.tahadardev.shrine.ui.theme.ShrineTheme

@Composable
fun Catalog(
    items: List<ItemData>, onAddItemToCart: (item: ItemData) -> Unit = {}
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp

    LazyRow(
        contentPadding = PaddingValues(top = 40.dp, bottom = 56.dp, start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(55.dp)
    ) {
        itemsIndexed(items = transformToWeavedList(items)) { idx, itemsList ->
            Column(
                Modifier
                    .fillMaxHeight()
                    .width((screenWidth * 0.7f).dp),
                verticalArrangement = Arrangement.Center
            ) {
                val even = idx % 2 == 0
                if (even) {
                    if (itemsList.size > 1) {
                        CatalogCard(
                            modifier = Modifier
                                .align(Alignment.Start)
                                .weight(1f)
                                .fillMaxWidth(.75f),
                            item = itemsList[0],
                            onAddToCart = { onAddItemToCart(it) }
                        )
                        Spacer(modifier = Modifier.height(40.dp))
                        CatalogCard(
                            modifier = Modifier
                                .align(Alignment.End)
                                .weight(1f)
                                .fillMaxWidth(.75f),
                            item = itemsList[1],
                            onAddToCart = { onAddItemToCart(it) }
                        )
                    } else {
                        CatalogCard(
                            modifier = Modifier.fillMaxHeight(0.6f),
                            item = itemsList[0],
                            onAddToCart = { onAddItemToCart(it) })
                    }
                } else {
                    CatalogCard(
                        modifier = Modifier
                            .padding(top = 150.dp)
                            .fillMaxHeight(0.85f),
                        item = itemsList[0],
                        onAddToCart = { onAddItemToCart(it) }
                    )
                }
            }
        }
    }
}

private fun <T> transformToWeavedList(items: List<T>): List<List<T>> {
    var i = 0
    val list = mutableListOf<List<T>>()
    while (i < items.size) {
        val even = i % 3 == 0
        val wList = mutableListOf<T>()
        wList.add(items[i])
        if (even && i + 1 < items.size) wList.add(items[i + 1])
        list.add(wList.toList())
        i += if (even) 2 else 1
    }
    return list.toList()
}

@Preview(showBackground = true)
@Composable
fun CatalogPrev() {
    ShrineTheme {
        Catalog(SampleItems)
    }
}