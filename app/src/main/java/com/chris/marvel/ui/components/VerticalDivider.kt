package com.chris.marvel.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Inspired by the horizontal divider from Jetpack Compose:
 * [Divider].
 *
 * Intended to be used as a divider between 2 separate elements such as:
 * element1 | element2
 *
 * @param modifier the [Modifier] to be applied to this divider line.
 * @param thickness thickness of this divider line. Using [Dp.Hairline] will produce a single pixel
 * divider regardless of screen density.
 * @param color color of this divider line.
 */
@Composable
fun VerticalDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = DividerDefaults.Thickness,
    color: Color = DividerDefaults.color,
) {
    val targetThickness = if (thickness == Dp.Hairline) {
        (1f / LocalDensity.current.density).dp
    } else {
        thickness
    }
    Box(
        modifier
            .fillMaxHeight()
            .padding(top = 1.dp, bottom = 1.dp)
            .width(targetThickness)
            .background(color = color)
    )
}

/**
 * The Intrinsic Height
 */
@Preview
@Composable
fun VerticalDividerTest() {
    Row(
        modifier = Modifier.height(IntrinsicSize.Min) //intrinsic measurements
    ) {
        Text(text = "start")
        VerticalDivider()
        Text(text = "end")
    }
}