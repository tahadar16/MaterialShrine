package com.tahadardev.shrine.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tahadardev.shrine.R
import com.tahadardev.shrine.data.Category
import com.tahadardev.shrine.data.ItemData
import com.tahadardev.shrine.data.SampleItems
import com.tahadardev.shrine.ui.components.home.ShrineAppBar
import com.tahadardev.shrine.ui.components.home.catalogue.Catalog
import com.tahadardev.shrine.ui.theme.ShrineTheme
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun HomeScreen(
    onBackDropReveal: (Boolean) -> Unit = {},
    onItemAddedToCartFromCatalog: (item: ItemData) -> Unit = {}
) {

    val backdropState = rememberBackdropScaffoldState(initialValue = BackdropValue.Concealed)
    val coroutineScope = rememberCoroutineScope()
    var isBackDropRevealed by rememberSaveable { mutableStateOf(backdropState.isRevealed) }
    var searchText by remember { mutableStateOf("") }
    var activeCategory by rememberSaveable {
        mutableStateOf(
            Category.All
        )
    }

    BackdropScaffold(scaffoldState = backdropState, appBar = {
        ShrineAppBar(
            isBackDropRevealed,
            onBackDropRevealed = {
                onBackDropReveal(it)
                isBackDropRevealed = !isBackDropRevealed
                if (backdropState.isRevealed) {
                    coroutineScope.launch { backdropState.conceal() }
                } else {
                    coroutineScope.launch { backdropState.reveal() }
                }
            },
            SearchField = {
                MenuSearchField(
                    searchText = searchText,
                    onTextChanged = { searchText = it }
                )
            },
        )
    }, frontLayerShape = MaterialTheme.shapes.large, frontLayerContent = {
        Catalog(items = SampleItems.filter { itemData ->
            if (activeCategory != Category.All)
                itemData.category == activeCategory
            else
                true
        }, onAddItemToCart = { onItemAddedToCartFromCatalog(it) })
    }, backLayerContent = {
        NavigationMenu(
            activeCategory,
            revealed = isBackDropRevealed,
            onMenuSelected = {
                activeCategory = it
            })
    })
}

@Composable
fun MenuSearchField(searchText: String, onTextChanged: (searchText: String) -> Unit = {}) {
    TextField(
        value = searchText,
        onValueChange = {
            onTextChanged(it)
        },
        singleLine = true,
        textStyle = MaterialTheme.typography.subtitle1.copy(fontSize = 17.sp),
        placeholder = {
            Text(
                text = "Search Shrine".uppercase(),
                style = MaterialTheme.typography.subtitle1.copy(fontSize = 17.sp)
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent
        ),
    )
}

@ExperimentalAnimationApi
@Composable
private fun NavigationMenu(
    activeCategory: Category = Category.All,
    revealed: Boolean = true,
    onMenuSelected: (category: Category) -> Unit = {}
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {

        AnimatedVisibility(
            visible = revealed,
            enter = EnterTransition.None,
            exit = ExitTransition.None,
            label = "NavMenu Visibility"
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val categories = Category.values()
                categories.forEachIndexed { index, category ->
                    MenuItem(index, modifier = Modifier.clickable { onMenuSelected(category) }) {
                        MenuLabel(category.toString(), category == activeCategory)
                    }
                }
                MenuItem(index = categories.size) {
                    Divider(
                        Modifier
                            .padding(vertical = 4.dp)
                            .width(60.dp),
                        MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
                    )
                }
                MenuItem(index = categories.size + 1, modifier = Modifier.padding(0.dp)) {
                    MenuLabel("Logout")
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun AnimatedVisibilityScope.MenuItem(
    index: Int, modifier: Modifier = Modifier, content: @Composable () -> Unit = @Composable {}
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.then(
            Modifier
                .fillMaxWidth(.5f)
                .animateEnterExit(
                    enter = fadeIn(
                        animationSpec = tween(
                            durationMillis = 240,
                            delayMillis = index * 15 + 60,
                            easing = LinearEasing
                        )
                    ),
                    exit = fadeOut(
                        animationSpec = tween(
                            durationMillis = 90,
                            easing = LinearEasing
                        )
                    ),
                    label = "Menu item $index"
                )
        )
    ) {
        content()
    }
}

@Composable
fun MenuLabel(title: String, isActive: Boolean = false) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.height(44.dp)) {
        if (isActive) {
            Image(
                painter = painterResource(id = R.drawable.ic_tab_indicator),
                contentDescription = null
            )
        }
        Text(
            title.uppercase(),
            style = MaterialTheme.typography.subtitle1
        )
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    ShrineTheme {
        Column {
            ShrineAppBar()
            Spacer(modifier = Modifier.height(20.dp))
            ShrineAppBar(
                isBackDropRevealed = true,
                SearchField = { MenuSearchField(searchText = "") },
            )
        }
    }
}

@Preview(showBackground = true)
@ExperimentalAnimationApi
@Composable
fun NavigationMenuPrev() {
    ShrineTheme {
        NavigationMenu(Category.All, true) {}
    }
}

@Preview
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun HomeScreenPreview() {
    ShrineTheme {
        HomeScreen()
    }
}