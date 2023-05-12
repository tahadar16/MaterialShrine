 package com.tahadardev.shrine.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

 private val DarkColorPalette = darkColors(
    primary = ShrinePink900,
    primaryVariant = ShrinePink500,
    secondary = ShrinePink300
)

private val LightColorPalette = ShrineLightColorPalette

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */

@Composable
fun ShrineTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        ShrineLightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

 @Preview(showBackground = true)
 @Composable
 fun ShrineThemeTest() {
     Column(Modifier.padding(16.dp)) {
         ShrineTheme() {
             Button(onClick = { /*TODO*/ }) {
                 Text(text = "This is a shrine button")
             }
         }

         Spacer(modifier = Modifier.height(10.dp))

         MaterialTheme() {
             Button(onClick = { /*TODO*/ }) {
                 Text(text = "This is a material baseline button")
             }
         }
     }
 }