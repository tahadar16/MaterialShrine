package com.tahadardev.shrine.ui.components.home.catalogue

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tahadardev.shrine.data.ItemData
import com.tahadardev.shrine.data.SampleItems
import com.tahadardev.shrine.data.getVendorResId
import com.tahadardev.shrine.ui.theme.ShrineTheme

@Composable
fun CatalogCard(modifier: Modifier, item: ItemData, onAddToCart: (item: ItemData) -> Unit = {}) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Box(Modifier.weight(1f)) {
            Image(
                painter = painterResource(id = item.photoResId),
                contentDescription = item.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
            IconButton(onClick = { onAddToCart(item) }) {
                Icon(
                    imageVector = Icons.Default.AddShoppingCart,
                    contentDescription = "Add to Cart",
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(12.dp)
                )
            }
            Image(
                painter = painterResource(id = getVendorResId(item.vendor)),
                contentDescription = "Vendor logo",
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = 17.dp)
                    .size(34.dp)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = item.title, style = MaterialTheme.typography.subtitle2)
        Text(
            text = "$ ${item.price}",
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CatalogCardPrev() {
    ShrineTheme {
        CatalogCard(Modifier.height(380.dp), SampleItems[0])
    }
}