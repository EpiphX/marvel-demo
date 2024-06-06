package com.chris.marvel

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.chris.marvel.ui.comics.ComicDetailScreen
import com.chris.marvel.ui.theme.MarvelTheme
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isSystemInDarkMode = isSystemInDarkTheme()
            // Default will be based on the system settings.
            val isDarkTheme = rememberSaveable {
                mutableStateOf(isSystemInDarkMode)
            }

            val showComicDetailScreen = rememberSaveable {
                mutableStateOf(false)
            }
            val comicId = rememberSaveable {
                mutableStateOf("47800")
            }

            LaunchedEffect(key1 = isDarkTheme.value) {
                enableEdgeToEdge(
                    statusBarStyle = if (isDarkTheme.value) {
                        SystemBarStyle.dark(Color.TRANSPARENT)
                    } else {
                        SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
                    }
                )
            }
            MarvelTheme(dynamicColor = false, darkTheme = isDarkTheme.value) {
                Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        title = {
                            // No-op
                        },
                        actions = {
                            IconButton(onClick = { isDarkTheme.value = !isDarkTheme.value }) {
                                Icon(
                                    imageVector = if (isDarkTheme.value) Icons.Default.LightMode else Icons.Default.DarkMode,
                                    contentDescription = stringResource(R.string.change_theme),
                                    tint = MaterialTheme.colorScheme.surface
                                )
                            }
                            if (showComicDetailScreen.value) {
                                IconButton(onClick = { showComicDetailScreen.value = false }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = stringResource(R.string.close),
                                        tint = MaterialTheme.colorScheme.surface
                                    )
                                }
                            }
                        }
                    )
                }) { innerPadding ->
                    Box(
                        Modifier.padding(innerPadding)
                    ) {
                        // This transition could be improved by bringing in jetpack navigation and setting up routes.
                        if (showComicDetailScreen.value) {
                            Column(verticalArrangement = Arrangement.Top) {
                                ComicDetailScreen(comicId = comicId.value)
                            }
                        } else {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                TextField(label = {
                                    Text(stringResource(R.string.comic_id_label))
                                }, value = comicId.value, onValueChange = {
                                    comicId.value = it
                                })
                                Button(
                                    onClick = { showComicDetailScreen.value = true },
                                    enabled = comicId.value.isNotEmpty()
                                ) {
                                    Text(stringResource(R.string.submit))
                                }

                            }
                        }

                    }
                }
            }
        }
    }
}