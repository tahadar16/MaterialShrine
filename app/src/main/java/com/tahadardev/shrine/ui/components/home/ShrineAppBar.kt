package com.tahadardev.shrine.ui.components.home

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tahadardev.shrine.R
import com.tahadardev.shrine.ui.screens.MenuSearchField
import com.tahadardev.shrine.ui.theme.ShrineTheme

@OptIn(ExperimentalAnimationApi::class)
@ExperimentalMaterialApi
@Composable
fun ShrineAppBar(
    isBackDropRevealed: Boolean = false,
    onBackDropRevealed: (Boolean) -> Unit = {},
    SearchField: @Composable () -> Unit = {},
) {
    val density = LocalDensity.current
    TopAppBar(
        elevation = 0.dp,
        title = {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(46.dp)
                    .toggleable(
                        value = isBackDropRevealed,
                        onValueChange = { onBackDropRevealed(it) },
                        interactionSource = MutableInteractionSource(),
                        indication = rememberRipple(false, 50.dp)
                    ),
                contentAlignment = Alignment.CenterStart
            ) {
                AnimatedVisibility(
                    visible = !isBackDropRevealed,
                    label = "MenuBtn Visibility",
                    enter = fadeIn(
                        animationSpec = tween(
                            durationMillis = 180,
                            delayMillis = 90,
                            easing = LinearEasing
                        )
                    )
                            + slideInHorizontally(
                        initialOffsetX = { with(density) { (-20).dp.roundToPx() } },
                        animationSpec = tween(durationMillis = 270, easing = LinearEasing)
                    ),
                    exit = fadeOut(
                        animationSpec = tween(
                            durationMillis = 120,
                            easing = LinearEasing
                        )
                    )
                            + slideOutHorizontally(
                        targetOffsetX = { with(density) { (-20).dp.roundToPx() } },
                        animationSpec = tween(durationMillis = 120, easing = LinearEasing)
                    ),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_menu_cut_24px),
                        contentDescription = "Menu icon"
                    )
                }

                val logoTransition = updateTransition(
                    targetState = isBackDropRevealed,
                    label = "logoTransition"
                )
                val logoOffset by logoTransition.animateDp(
                    transitionSpec = {
                        if (targetState) {
                            tween(durationMillis = 120, easing = LinearEasing)
                        } else {
                            tween(durationMillis = 270, easing = LinearEasing)
                        }
                    },
                    label = "logoOffset"
                ) { revealed ->
                    if (!revealed) 20.dp else 0.dp
                }

                Icon(
                    painter = painterResource(id = R.drawable.ic_shrine_logo),
                    contentDescription = "Shrine logo",
                    modifier = Modifier.offset(x = logoOffset)
                )
            }

            AnimatedContent(
                targetState = isBackDropRevealed,
                transitionSpec = {
                    if (targetState) {
                        // Conceal to reveal
                        fadeIn(
                            animationSpec = tween(
                                durationMillis = 240,
                                delayMillis = 120,
                                easing = LinearEasing
                            )
                        ) +
                                slideInHorizontally(
                                    initialOffsetX = { with(density) { 30.dp.roundToPx() } },
                                    animationSpec = tween(
                                        durationMillis = 270,
                                        easing = LinearEasing
                                    )
                                ) with
                                fadeOut(
                                    animationSpec = tween(
                                        durationMillis = 120,
                                        easing = LinearEasing
                                    )
                                ) +
                                slideOutHorizontally(
                                    targetOffsetX = { with(density) { (-30).dp.roundToPx() } },
                                    animationSpec = tween(
                                        durationMillis = 120,
                                        easing = LinearEasing
                                    )
                                )
                    } else {
                        // Reveal to conceal
                        fadeIn(
                            animationSpec = tween(
                                durationMillis = 180,
                                delayMillis = 90,
                                easing = LinearEasing
                            )
                        ) +
                                slideInHorizontally(
                                    initialOffsetX = { with(density) { (-30).dp.roundToPx() } },
                                    animationSpec = tween(
                                        durationMillis = 270,
                                        easing = LinearEasing
                                    )
                                ) with
                                fadeOut(
                                    animationSpec = tween(
                                        durationMillis = 90,
                                        easing = LinearEasing
                                    )
                                ) +
                                slideOutHorizontally(
                                    targetOffsetX = { with(density) { 30.dp.roundToPx() } },
                                    animationSpec = tween(
                                        durationMillis = 90,
                                        easing = LinearEasing
                                    )
                                )
                    }.using(SizeTransform(clip = false))
                },
                contentAlignment = Alignment.CenterStart
            ) { revealed ->
                if (revealed) {
                    SearchField()
                } else {
                    Text(
                        text = "Shrine".uppercase(),
                        style = MaterialTheme.typography.subtitle1,
                        fontSize = 17.sp
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search icon",
                    tint = LocalContentColor.current.copy(alpha = ContentAlpha.high),
                    modifier = Modifier
                        .padding(end = 12.dp)
                )
            }
        }
    )
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    ShrineTheme {
        Column {
            ShrineAppBar(
                isBackDropRevealed = false,
            )
            Spacer(modifier = Modifier.height(20.dp))
            ShrineAppBar(
                isBackDropRevealed = true,
                SearchField = {
                    MenuSearchField(searchText = "")
                },
            )
            Spacer(modifier = Modifier.height(20.dp))
            ShrineAppBar(
                isBackDropRevealed = true,
                SearchField = { BasicTextField(value = "", onValueChange = {}) },
            )
        }
    }
}