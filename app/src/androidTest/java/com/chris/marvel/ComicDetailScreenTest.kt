package com.chris.marvel

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.chris.marvel.ui.comics.ComicDetailContent
import com.chris.marvel.ui.comics.ComicDetailUiState
import com.chris.marvel.ui.comics.ComicInfo
import org.junit.Rule
import org.junit.Test

class ComicDetailScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private fun getString(@StringRes resId: Int): String =
        composeTestRule.activity.getString(resId)

    @Test
    fun testDetailRetrievedScreen_expected_all_content_rendered() {
        val comicTitle = "ComicTitle"
        val comicDescription = "ComicDescription"
        composeTestRule.setContent {
            ComicDetailContent(
                comicId = "48700",
                comicDetailUiState = ComicDetailUiState.ComicInfoRetrieved(
                    ComicInfo(
                        "",
                        comicTitle,
                        comicDescription
                    )
                ),
                reloadData = { /**No op **/ },
                readNowAction = { /**No op **/ },
                markAsReadAction = { /**No op **/ },
                addToLibraryAction = { /**No op **/ },
                downloadAction = { /**No op **/ },
                onPreviousAction = { /**No op **/ },
                onNextAction = { /**No op **/ }
            )
        }

        composeTestRule.onNodeWithText(comicTitle).assertIsDisplayed()
        composeTestRule.onNodeWithText(getString(R.string.the_story)).assertIsDisplayed()
        composeTestRule.onNodeWithText(comicDescription).assertIsDisplayed()

        composeTestRule.onNodeWithText(getString(R.string.read_now)).assertIsDisplayed()
            .assertHasClickAction()
        composeTestRule.onNodeWithText(getString(R.string.mark_as_read)).assertIsDisplayed()
            .assertHasClickAction()
        composeTestRule.onNodeWithText(getString(R.string.add_to_library)).assertIsDisplayed()
            .assertHasClickAction()
        composeTestRule.onNodeWithText(getString(R.string.read_offline)).assertIsDisplayed()
            .assertHasClickAction()
        composeTestRule.onNodeWithText(getString(R.string.previous)).assertIsDisplayed()
            .assertHasClickAction()
        composeTestRule.onNodeWithText(getString(R.string.next)).assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun testDetailLoadingScreen_expected_spinner_shown() {
        composeTestRule.setContent {
            // Composable under test.
            ComicDetailContent(
                comicId = "48700",
                // Set the state to error, so we can validate that the expected is shown.
                comicDetailUiState = ComicDetailUiState.Loading,
                reloadData = { /**No op **/ },
                readNowAction = { /**No op **/ },
                markAsReadAction = { /**No op **/ },
                addToLibraryAction = { /**No op **/ },
                downloadAction = { /**No op **/ },
                onPreviousAction = { /**No op **/ },
                onNextAction = { /**No op **/ }
            )
        }

        composeTestRule
            .onNodeWithTag(composeTestRule.activity.getString(R.string.loader))
            .assertIsDisplayed()
    }

    @Test
    fun testDetailErrorScreen_expected_error_messaging_with_retry_button() {
        val retryText = composeTestRule.activity.getString(R.string.retry)
        val errorMessageText = composeTestRule.activity.getString(R.string.failure_retry_message)
        composeTestRule.setContent {
            val isReloadClicked = remember {
                mutableStateOf(false)
            }

            // Composable under test.
            ComicDetailContent(
                comicId = "48700",
                // Set the state to error, so we can validate that the expected is shown.
                comicDetailUiState = ComicDetailUiState.Error,
                reloadData = {
                    // If reload is clicked, we'll use a separate com to confirm that it is executing this lambda properly.
                    isReloadClicked.value = true
                },
                readNowAction = { /**No op **/ },
                markAsReadAction = { /**No op **/ },
                addToLibraryAction = { /**No op **/ },
                downloadAction = { /**No op **/ },
                onPreviousAction = { /**No op **/ },
                onNextAction = { /**No op **/ }
            )
            if (isReloadClicked.value) {
                Text(text = "Reload Successfully Executed", Modifier.testTag("reloadResult"))
            }
        }
        val retryButtonNode = composeTestRule.onNodeWithText(retryText)
        retryButtonNode.assertIsDisplayed()
        retryButtonNode.assertHasClickAction()
        composeTestRule.onNodeWithText(errorMessageText).assertIsDisplayed()

        // Perform click on retry button, and confirm that reloadResult is shown.
        retryButtonNode.performClick()
        composeTestRule.onNodeWithTag("reloadResult").assertIsDisplayed()
    }
}