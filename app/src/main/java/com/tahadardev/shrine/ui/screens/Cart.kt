package com.tahadardev.shrine.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tahadardev.shrine.data.ItemData
import com.tahadardev.shrine.data.SampleItems
import com.tahadardev.shrine.ui.components.cart.CartHeader
import com.tahadardev.shrine.ui.components.cart.CartItem
import com.tahadardev.shrine.ui.theme.ShrineTheme
import kotlin.math.min

enum class CartState {
    Expanded,
    Collapsed,
    Hidden
}

@ExperimentalAnimationApi
@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    maxHeight: Dp,
    maxWidth: Dp,
    items: List<ItemData> = SampleItems.take(14),
    cartState: CartState,
    onCartStateChanged: (cartState: CartState) -> Unit = {},
    onItemRemovedFromCart: (item: ItemData) -> Unit = {}
) {

    val cartScreenTransition = updateTransition(targetState = cartState, label = "Cart Transition")

    val cartHeight by cartScreenTransition.animateDp(
        label = "Cart Height Transition",
        transitionSpec = {
            when {
                CartState.Expanded isTransitioningTo CartState.Collapsed ->
                    tween(durationMillis = 283)
                else ->
                    tween(durationMillis = 500)
            }
        }) {
        if (it == CartState.Expanded) maxHeight else 56.dp
    }

    val cartXOffset by cartScreenTransition.animateDp(
        label = "Cart Offset Transition",
        transitionSpec = {
            when {
                CartState.Collapsed isTransitioningTo CartState.Expanded ->
                    tween(durationMillis = 150)
                CartState.Expanded isTransitioningTo CartState.Collapsed ->
                    tween(durationMillis = 433, delayMillis = 67)
                else ->
                    tween(durationMillis = 450)
            }
        }) {
        when (it) {
            CartState.Expanded -> 0.dp
            CartState.Hidden -> maxWidth
            CartState.Collapsed -> {
                val size = min(3, items.size)
                var width = 24 + 40 * (size + 1) + 16 * size + 16
                if (items.size > 3) width += 32 + 16
                maxWidth - width.dp
            }
        }
    }

    val cornerSize by cartScreenTransition.animateDp(
        label = "Corner size Transition",
        transitionSpec = {
            when {
                CartState.Expanded isTransitioningTo CartState.Collapsed ->
                    tween(durationMillis = 433, delayMillis = 67)
                else ->
                    tween(durationMillis = 150)
            }
        }) {
        if (it == CartState.Expanded) 0.dp else 24.dp
    }

    Box(
        modifier = modifier.then(
            Modifier
                .width(maxWidth)
                .height(cartHeight)
                .offset(cartXOffset)
                .clip(shape = CutCornerShape(topStart = cornerSize))
        )
    ) {
        cartScreenTransition.AnimatedContent(
            transitionSpec = {
                when {
                    CartState.Collapsed isTransitioningTo CartState.Expanded ->
                        fadeIn(
                            animationSpec = tween(
                                durationMillis = 150,
                                delayMillis = 150,
                                easing = LinearEasing
                            )
                        ) with fadeOut(
                            animationSpec = tween(
                                durationMillis = 150,
                                easing = LinearEasing
                            )
                        )
                    CartState.Expanded isTransitioningTo CartState.Collapsed ->
                        fadeIn(
                            animationSpec = tween(
                                durationMillis = 117,
                                delayMillis = 117,
                                easing = LinearEasing
                            )
                        ) with fadeOut(
                            animationSpec = tween(
                                durationMillis = 117,
                                easing = LinearEasing
                            )
                        )
                    else -> {
                        EnterTransition.None with ExitTransition.None
                    }
                }
            }) { cartState ->
            if (cartState == CartState.Expanded) {
                ExpandedCart(
                    cartItems = items,
                    onCartStateChanged = {onCartStateChanged(CartState.Collapsed) },
                    onItemRemoved = { onItemRemovedFromCart(it) })
            } else {
                CollapsedCart(cartItems = items) {
                    onCartStateChanged(CartState.Expanded)
                }
            }
        }

        cartScreenTransition.AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp, vertical = 4.dp),
            visible = { it == CartState.Expanded },
            enter = fadeIn(
                animationSpec = tween(
                    durationMillis = 150,
                    delayMillis = 150,
                    easing = LinearEasing
                )
            ) +
                    scaleIn(
                        animationSpec = tween(
                            durationMillis = 250,
                            delayMillis = 250,
                            easing = LinearOutSlowInEasing
                        ), initialScale = 0.8f
                    ),
            exit = fadeOut(animationSpec = tween(durationMillis = 117, easing = LinearEasing)) +
                    scaleOut(
                        animationSpec = tween(
                            durationMillis = 100,
                            easing = FastOutLinearInEasing
                        ), targetScale = 0.8f
                    )
        ) {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                Icon(
                    imageVector = Icons.Outlined.ShoppingCart,
                    contentDescription = "Shopping cart icon"
                )
                Spacer(Modifier.width(16.dp))
                Text("Proceed to checkout".uppercase())
            }
        }
    }
}

@Composable
fun CollapsedCart(
    cartItems: List<ItemData> = SampleItems.take(6),
    onTap: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .padding(24.dp, 8.dp, 16.dp, 8.dp)
            .clickable { onTap() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Box(
            Modifier.size(40.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Shopping cart icon",
            )
        }
        cartItems.take(3).forEach { item ->
            Image(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(12.dp))
                    .size(40.dp),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = item.photoResId),
                contentDescription = "Item Image"
            )
        }
        if (cartItems.size > 3) {
            Box(
                Modifier.size(width = 32.dp, height = 40.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "+${cartItems.size - 3}",
                    style = MaterialTheme.typography.subtitle2,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CollapsedCartPrev() {
    ShrineTheme {
        CollapsedCart()
    }
}

@Composable
fun ExpandedCart(
    cartItems: List<ItemData> = listOf(),
    onCartStateChanged: () -> Unit,
    onItemRemoved: (item: ItemData) -> Unit
) {
    Surface(color = MaterialTheme.colors.surface) {
        Column(
            Modifier
                .fillMaxSize()
        ) {
            CartHeader(onCollapse = onCartStateChanged)

            cartItems.forEach { item ->
                CartItem(item, onRemove = {onItemRemoved(it)})
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartHeaderPrev() {
    ShrineTheme {
        CartHeader()
    }
}

@Preview(showBackground = true)
@Composable
fun CarItemPrev() {
    ShrineTheme {
        CartItem(SampleItems[0])
    }
}

@Preview(showBackground = true)
@ExperimentalAnimationApi
@Composable
fun CartScreenPreview() {
    ShrineTheme {
        BoxWithConstraints(Modifier.fillMaxSize()) {
            CartScreen(cartState = CartState.Expanded, maxHeight = maxHeight, maxWidth = maxWidth)
        }
    }
}