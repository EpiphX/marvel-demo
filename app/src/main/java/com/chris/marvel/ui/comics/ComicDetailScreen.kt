package com.chris.marvel.ui.comics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chris.marvel.R
import com.chris.marvel.ui.components.ActionButton
import com.chris.marvel.ui.components.ActionButtonInfo
import com.chris.marvel.ui.components.CoverImageActions
import com.chris.marvel.ui.components.DirectionalButton
import com.chris.marvel.ui.components.DirectionalButtonInfo
import com.chris.marvel.ui.components.ImagePosition

@Composable
fun ComicDetailScreen(
    comicId: String,
    detailViewModel: ComicDetailViewModel = viewModel()
) {
    // Initial state will be loading as the view model retrieves information.
    val uiState by detailViewModel.uiState.collectAsState()

    // Launched effect to pull data for the requested comic id.
    LaunchedEffect(Unit) {
        detailViewModel.loadData(comicId)
    }

    when (val detailScreenState = uiState) {
        is ComicDetailUiState.ComicInfoRetrieved -> {
            ComicDetailContent(comicInfo = detailScreenState.comicInfo)
        }

        ComicDetailUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        ComicDetailUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "A failure has seemed to occur. Please Retry")
                    Button(onClick = { detailViewModel.loadData(comicId) }) {
                        Text(text = stringResource(R.string.retry))
                    }
                }

            }
        }
    }
}

@Composable
fun ComicDetailContent(comicInfo: ComicInfo) {
    // TODO: Need to look into fixing landscape mode orientation for Comic book view.
    // TODO: Need to look into writing unit and UI test. (Mock Data implementation will be used instead, so the test does not halt).
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        CoverImageActions(comicInfo.comicBookImageUrl) {
            Button(
                onClick = {
                    /**
                     * TODO: Implement event for read on view model.
                     */
                },
                modifier = Modifier
                    .height(76.dp)
                    .fillMaxWidth(),
                shape = AbsoluteCutCornerShape(0.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = "READ NOW",
                    fontWeight = FontWeight.ExtraBold,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            ActionButton(
                info = ActionButtonInfo(
                    Icons.Filled.CheckCircle,
                    "MARK AS READ"
                ),
                onClick = { /* Not needed for preview */ },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                modifier = Modifier.fillMaxWidth()
            )
            ActionButton(
                info = ActionButtonInfo(
                    Icons.Filled.AddCircle,
                    "ADD TO LIBRARY"
                ),
                onClick = { /* Not needed for preview */ },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                modifier = Modifier.fillMaxWidth()
            )
            ActionButton(
                info = ActionButtonInfo(
                    Icons.Filled.Download,
                    "READ OFFLINE"
                ),
                onClick = { /* Not needed for preview */ },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                modifier = Modifier.fillMaxWidth()
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .weight(1f)
                .padding(horizontal = 8.dp, vertical = 16.dp), verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = comicInfo.comicTitle,
                style = MaterialTheme.typography.titleLarge
            )
            Divider(modifier = Modifier.padding(vertical = 16.dp))
            Text(text = comicInfo.header, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = comicInfo.description,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Data provided by Marvel. © 2014 Marvel",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            DirectionalButton(
                enabled = false,
                contentPadding = PaddingValues(0.dp),
                info = DirectionalButtonInfo(
                    ImagePosition.LEFT,
                    Icons.Filled.KeyboardArrowLeft,
                    "PREVIOUS"
                ),
                onClick = {
                    /**
                     * TODO: Implement event for onPrevious to view model.
                     */
                }
            )
            DirectionalButton(
                enabled = true,
                contentPadding = PaddingValues(0.dp),
                info = DirectionalButtonInfo(
                    ImagePosition.RIGHT,
                    Icons.Filled.KeyboardArrowRight,
                    "NEXT"
                ),
                onClick = {
                    /**
                     * TODO: Implement event for onNext to view model.
                     */
                }
            )
        }
    }
}