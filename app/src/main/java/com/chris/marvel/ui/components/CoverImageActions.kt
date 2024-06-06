package com.chris.marvel.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.chris.marvel.R

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

@Preview(Devices.PIXEL_7_PRO, showSystemUi = true)
@Composable
fun CoverImageActionsPixel7ProPreview() {
    CoverImageActionsTest()
}

@Preview(Devices.NEXUS_5, showSystemUi = true)
@Composable
fun CoverImageActionsPhonePreview() {
    CoverImageActionsTest()
}

@Composable
private fun CoverImageActionsTest() {
    CoverImageActions(coverImageUrl = "") {
        Button(
            onClick = { /**No-op**/ },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, fill = true),
            shape = AbsoluteCutCornerShape(0.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = stringResource(R.string.read_now),
                fontWeight = FontWeight.ExtraBold,
                style = MaterialTheme.typography.titleLarge
            )
        }
        ActionButton(
            info = ActionButtonInfo(
                Icons.Filled.CheckCircle,
                stringResource(R.string.mark_as_read)
            ),
            onClick = { /**No-op**/ },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
            modifier = Modifier.fillMaxWidth()
        )
        ActionButton(
            info = ActionButtonInfo(
                Icons.Filled.AddCircle,
                stringResource(R.string.add_to_library)
            ),
            onClick = { /**No-op**/ },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
            modifier = Modifier.fillMaxWidth()
        )
        ActionButton(
            info = ActionButtonInfo(
                Icons.Filled.Download,
                stringResource(R.string.read_offline)
            ),
            onClick = { /**No-op**/ },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
            modifier = Modifier.fillMaxWidth()
        )
    }
}