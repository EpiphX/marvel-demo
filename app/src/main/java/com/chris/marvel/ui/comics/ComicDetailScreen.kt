package com.chris.marvel.ui.comics

import android.content.res.Configuration
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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

    ComicDetailContent(
        comicId = comicId,
        comicDetailUiState = uiState,
        readNowAction = { },
        markAsReadAction = { },
        addToLibraryAction = { },
        downloadAction = { },
        onPreviousAction = { },
        onNextAction = { },
        reloadData = detailViewModel::loadData
    )
}

@Composable
fun ComicDetailContent(
    comicId: String,
    comicDetailUiState: ComicDetailUiState,
    readNowAction: (String) -> Unit,
    markAsReadAction: (String) -> Unit,
    addToLibraryAction: (String) -> Unit,
    downloadAction: (String) -> Unit,
    onPreviousAction: (String) -> Unit,
    onNextAction: (String) -> Unit,
    reloadData: (String) -> Unit
) {
    when (comicDetailUiState) {
        is ComicDetailUiState.ComicInfoRetrieved -> {
            ComicInfoDisplay(
                comicDetailUiState,
                readNowAction,
                comicId,
                markAsReadAction,
                addToLibraryAction,
                downloadAction,
                onPreviousAction,
                onNextAction
            )
        }

        ComicDetailUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier.testTag(stringResource(R.string.loader)))
            }
        }

        ComicDetailUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = stringResource(R.string.failure_retry_message))
                    Button(onClick = { reloadData(comicId) }) {
                        Text(text = stringResource(R.string.retry))
                    }
                }

            }
        }
    }
}

@Composable
private fun ComicInfoDisplay(
    comicDetailUiState: ComicDetailUiState.ComicInfoRetrieved,
    readNowAction: (String) -> Unit,
    comicId: String,
    markAsReadAction: (String) -> Unit,
    readOfflineAction: (String) -> Unit,
    downloadAction: (String) -> Unit,
    onPreviousAction: (String) -> Unit,
    onNextAction: (String) -> Unit
) {
    val comicInfo = comicDetailUiState.comicInfo
    val portrait = LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT
    if (portrait) {
        ComicInfoDisplayPortrait(
            comicInfo,
            readNowAction,
            comicId,
            markAsReadAction,
            readOfflineAction,
            downloadAction,
            onPreviousAction,
            onNextAction
        )
    } else {
        ComicInfoDisplayLandscape(
            comicInfo,
            readNowAction,
            comicId,
            markAsReadAction,
            readOfflineAction,
            downloadAction,
            onPreviousAction,
            onNextAction
        )
    }
}

@Composable
private fun ComicInfoDisplayLandscape(
    comicInfo: ComicInfo,
    readNowAction: (String) -> Unit,
    comicId: String,
    markAsReadAction: (String) -> Unit,
    readOfflineAction: (String) -> Unit,
    downloadAction: (String) -> Unit,
    onPreviousAction: (String) -> Unit,
    onNextAction: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxHeight(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CoverImageActions(comicInfo.comicBookImageUrl) {
            Button(
                onClick = { readNowAction(comicId) },
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
                onClick = { markAsReadAction(comicId) },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                modifier = Modifier.fillMaxWidth()
            )
            ActionButton(
                info = ActionButtonInfo(
                    Icons.Filled.AddCircle,
                    stringResource(R.string.add_to_library)
                ),
                onClick = { readOfflineAction(comicId) },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                modifier = Modifier.fillMaxWidth()
            )
            ActionButton(
                info = ActionButtonInfo(
                    Icons.Filled.Download,
                    stringResource(R.string.read_offline)
                ),
                onClick = { downloadAction(comicId) },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                modifier = Modifier.fillMaxWidth()
            )
        }

        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 8.dp, vertical = 16.dp).weight(1f),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = comicInfo.comicTitle,
                    style = MaterialTheme.typography.titleLarge
                )
                Divider(modifier = Modifier.padding(vertical = 16.dp))
                Text(
                    text = stringResource(R.string.the_story),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = comicInfo.description,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.marvel_attribution_legal),
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
                        stringResource(R.string.previous)
                    ),
                    onClick = {
                        onPreviousAction(comicId)
                    }
                )
                DirectionalButton(
                    enabled = true,
                    contentPadding = PaddingValues(0.dp),
                    info = DirectionalButtonInfo(
                        ImagePosition.RIGHT,
                        Icons.Filled.KeyboardArrowRight,
                        stringResource(R.string.next)
                    ),
                    onClick = {
                        onNextAction(comicId)
                    }
                )
            }
        }
    }
}

@Composable
private fun ComicInfoDisplayPortrait(
    comicInfo: ComicInfo,
    readNowAction: (String) -> Unit,
    comicId: String,
    markAsReadAction: (String) -> Unit,
    readOfflineAction: (String) -> Unit,
    downloadAction: (String) -> Unit,
    onPreviousAction: (String) -> Unit,
    onNextAction: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        CoverImageActions(comicInfo.comicBookImageUrl) {
            Button(
                onClick = { readNowAction(comicId) },
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
                onClick = { markAsReadAction(comicId) },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                modifier = Modifier.fillMaxWidth()
            )
            ActionButton(
                info = ActionButtonInfo(
                    Icons.Filled.AddCircle,
                    stringResource(R.string.add_to_library)
                ),
                onClick = { readOfflineAction(comicId) },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                modifier = Modifier.fillMaxWidth()
            )
            ActionButton(
                info = ActionButtonInfo(
                    Icons.Filled.Download,
                    stringResource(R.string.read_offline)
                ),
                onClick = { downloadAction(comicId) },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                modifier = Modifier.fillMaxWidth()
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .weight(1f)
                .padding(horizontal = 8.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = comicInfo.comicTitle,
                style = MaterialTheme.typography.titleLarge
            )
            Divider(modifier = Modifier.padding(vertical = 16.dp))
            Text(
                text = stringResource(R.string.the_story),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = comicInfo.description,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.marvel_attribution_legal),
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
                    stringResource(R.string.previous)
                ),
                onClick = {
                    onPreviousAction(comicId)
                }
            )
            DirectionalButton(
                enabled = true,
                contentPadding = PaddingValues(0.dp),
                info = DirectionalButtonInfo(
                    ImagePosition.RIGHT,
                    Icons.Filled.KeyboardArrowRight,
                    stringResource(R.string.next)
                ),
                onClick = {
                    onNextAction(comicId)
                }
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ComicInfoDisplayTest() {
    ComicInfoDisplay(
        comicDetailUiState = ComicDetailUiState.ComicInfoRetrieved(
            ComicInfo(
                "",
                "Deadpool",
                "Wade Wilson..."
            )
        ),
        readNowAction = {},
        comicId = "48700",
        markAsReadAction = {},
        readOfflineAction = {},
        downloadAction = {},
        onPreviousAction = {}
    ) {

    }
}