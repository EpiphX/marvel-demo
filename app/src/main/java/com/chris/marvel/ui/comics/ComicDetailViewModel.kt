package com.chris.marvel.ui.comics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chris.marvel.data.ComicsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ComicDetailUiState {
    data object Loading : ComicDetailUiState()
    data class ComicInfoRetrieved(val comicInfo: ComicInfo) : ComicDetailUiState()
    data object Error : ComicDetailUiState()
}

data class ComicInfo(
    val comicBookImageUrl: String?,
    val comicTitle: String,
    val header: String,
    val description: String
)

@HiltViewModel
class ComicDetailViewModel @Inject constructor(
    private val comicsRepository: ComicsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<ComicDetailUiState>(ComicDetailUiState.Loading)
    val uiState: StateFlow<ComicDetailUiState> = _uiState.asStateFlow()

    fun loadData(comicId: String) {
        viewModelScope.launch {
            _uiState.value = ComicDetailUiState.Loading
            // Retrieve info from comic repository.
            comicsRepository.getComic(comicId)
                .onSuccess {
                    _uiState.value = ComicDetailUiState.ComicInfoRetrieved(
                        comicInfo = ComicInfo(
                            comicBookImageUrl = it.thumbnailUrl,
                            comicTitle = it.title,
                            header = "The Story",
                            description = it.description
                        )
                    )
                }
                .onFailure {
                    _uiState.value = ComicDetailUiState.Error
                }
        }
    }
}