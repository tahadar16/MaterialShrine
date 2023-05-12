package com.tahadardev.shrine.ui.components.cart

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tahadardev.shrine.data.SampleItems
import com.tahadardev.shrine.ui.theme.ShrineTheme

@Composable
fun CartHeader(
    onCollapse: () -> Unit = {}
) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { onCollapse() }) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Arrow down"
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = "Cart".uppercase())
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = "${SampleItems.size} Items")
    }
}

@Preview(showBackground = true)
@Composable
fun CartHeaderPrev() {
    ShrineTheme {
        CartHeader()
    }
}