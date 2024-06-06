package com.chris.marvel.ui.components

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chris.marvel.ui.components.ImagePosition.LEFT
import com.chris.marvel.ui.components.ImagePosition.RIGHT

@Composable
fun DirectionalButton(
    info: DirectionalButtonInfo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = AbsoluteCutCornerShape(0.dp),
    colors: ButtonColors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.surface),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.TextButtonContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) = TextButton(
    onClick,
    modifier,
    enabled,
    shape,
    colors,
    elevation,
    border,
    contentPadding,
    interactionSource
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        when (info.imagePosition) {
            LEFT -> {
                Icon(
                    imageVector = info.imageVector,
                    contentDescription = info.contentDescription
                )
                Text(text = info.text, fontWeight = FontWeight.ExtraBold)
            }

            RIGHT -> {
                Text(text = info.text, fontWeight = FontWeight.ExtraBold)
                Icon(
                    imageVector = info.imageVector,
                    contentDescription = info.contentDescription
                )
            }
        }
    }
}

@Composable
@Preview
@VisibleForTesting
fun LeftActionButton() {
    DirectionalButton(
        enabled = false,
        contentPadding = PaddingValues(0.dp),
        info = DirectionalButtonInfo(
            LEFT,
            Icons.Filled.KeyboardArrowLeft,
            "PREVIOUS"
        ),
        onClick = {
            // Not necessary for preview.
        }
    )
}

@Composable
@Preview
@VisibleForTesting
fun RightActionButton() {
    DirectionalButton(
        enabled = true,
        contentPadding = PaddingValues(0.dp),
        info = DirectionalButtonInfo(
            RIGHT,
            Icons.Filled.KeyboardArrowRight,
            "NEXT"
        ),
        onClick = {
            // Not necessary for preview.
        }
    )
}

data class DirectionalButtonInfo(
    val imagePosition: ImagePosition,
    val imageVector: ImageVector,
    val text: String,
    val contentDescription: String? = text
)

enum class ImagePosition {
    LEFT,
    RIGHT
}