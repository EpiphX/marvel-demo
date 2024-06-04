package com.chris.marvel.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Opt in for experimental material3 api in this case is necessary in order to use the
// LocalMinimumInteractiveComponentEnforcement provides false in order to remove the implicit padding on button's that is not correct to the design.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionButton(
    info: ActionButtonInfo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = AbsoluteCutCornerShape(0.dp),
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(8.dp),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    // This is necessary to override because Android adds its own implicit top padding to the top of the button to make it
    // accessible, but that goes against the design specs for Marvel to have random padding.
    CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
        Button(
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
                modifier = modifier.height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = info.imageVector,
                    contentDescription = info.contentDescription
                )
                VerticalDivider()
                Text(text = info.text, fontWeight = FontWeight.ExtraBold)
            }
        }
    }

}

@Preview
@Composable
fun ActionButtonTest() {
    ActionButton(
        info = ActionButtonInfo(
            Icons.Filled.AddCircle,
            "ADD TO LIBRARY"
        ), onClick = { /* Not needed for preview */ }, modifier = Modifier.fillMaxWidth()
    )
}

data class ActionButtonInfo(
    val imageVector: ImageVector,
    val text: String,
    val contentDescription: String? = text
)