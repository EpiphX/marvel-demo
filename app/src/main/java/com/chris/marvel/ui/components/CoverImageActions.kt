package com.chris.marvel.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun CoverImageActions(
    coverImageUrl: String?,
    actions: @Composable ColumnScope.() -> Unit = {},
) {
    // Wrap the given actions in a column.
    val actionsRow = @Composable {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            content = actions
        )
    }

    Box(
        modifier = Modifier
            .aspectRatio(1.77f)
    ) {
        AsyncImage(
            model = coverImageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .blur(radius = 8.dp),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImage(
                model = coverImageUrl,
                contentDescription = "",
                // Comic Book Covers: 1.4:1 aspect ratio or 1.2:1 aspect ratio.
                Modifier.aspectRatio(0.714f)
            )
            actionsRow()
        }
    }
}