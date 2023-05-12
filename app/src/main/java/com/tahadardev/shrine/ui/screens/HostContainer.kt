package com.tahadardev.shrine.ui.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tahadardev.shrine.data.ItemData
import com.tahadardev.shrine.ui.theme.ShrineTheme

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun HostContainer() {

    var cartSheetState by remember { mutableStateOf(CartState.Collapsed) }
    val cartItems = remember { mutableStateListOf<ItemData>() }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        HomeScreen(
            onBackDropReveal = { revealed ->
                cartSheetState = if (revealed) CartState.Hidden else CartState.Collapsed
            },
            onItemAddedToCartFromCatalog = { cartItems.add(it) }
        )

        CartScreen(
            modifier = Modifier.align(Alignment.BottomEnd),
            items = cartItems,
            cartState = cartSheetState,
            maxHeight = maxHeight,
            maxWidth = maxWidth,
            onCartStateChanged = { cartSheetState = it },
            onItemRemovedFromCart = { cartItems.remove(it)}
        )
    }
}

@Preview
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun HostContainerPrev() {
    ShrineTheme {
        HostContainer()
    }
}