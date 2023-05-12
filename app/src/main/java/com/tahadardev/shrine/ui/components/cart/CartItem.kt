package com.tahadardev.shrine.ui.components.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tahadardev.shrine.data.ItemData
import com.tahadardev.shrine.data.SampleItems
import com.tahadardev.shrine.ui.theme.ShrineTheme

@Composable
fun CartItem(cartItem: ItemData, onRemove: (item: ItemData) -> Unit = {}) {

    Row(
        Modifier
            .fillMaxWidth()
            .padding(end = 16.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onRemove(cartItem) }) {
            Icon(
                imageVector = Icons.Default.RemoveCircleOutline,
                contentDescription = "Remove icon"
            )
        }
        Column {
            Divider()
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = cartItem.photoResId),
                    contentDescription = "Item image",
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Column() {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "${cartItem.vendor}")
                        Text(text = "$ ${cartItem.price}")
                    }
                    Text(text = cartItem.title)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CarItemPrev() {
    ShrineTheme {
        CartItem(SampleItems[0])
    }
}