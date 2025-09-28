package io.lb.presentation.scores

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.lb.common.data.model.Score
import io.lb.presentation.R
import io.lb.presentation.ui.components.LoadingIndicator
import io.lb.presentation.ui.components.MemoryGameLogo
import io.lb.presentation.ui.components.MemoryGameWhiteButton
import io.lb.presentation.ui.components.Narcisus
import io.lb.presentation.ui.theme.AstorMemoryChallengeTheme
import io.lb.presentation.ui.theme.DarkerRed
import io.lb.presentation.ui.theme.Dimens

@Composable
internal fun ScoreScreen(
    navController: NavController,
    isDarkMode: Boolean,
    viewModel: ScoreViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    val selectedFilter = remember {
        mutableIntStateOf(0)
    }
    val dropDownMenuExpanded = remember {
        mutableStateOf(false)
    }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(Dimens.padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            MemoryGameLogo(
                Modifier
                    .fillMaxWidth(0.6f)
                    .heightIn(min = 60.dp, max = 120.dp)
            )

            Spacer(modifier = Modifier.height(Dimens.largePadding))
            Filter(dropDownMenuExpanded, selectedFilter, state, viewModel)
            Spacer(modifier = Modifier.height(Dimens.largePadding))
            ScoreContent(state, screenHeight)
            Spacer(modifier = Modifier.weight(1f))

            MemoryGameWhiteButton(
                text = stringResource(R.string.back),
                isDarkMode = isDarkMode,
                onClick = {
                    navController.navigateUp()
                }
            )

            Spacer(modifier = Modifier.height(Dimens.padding))
            Narcisus()
            Spacer(modifier = Modifier.height(Dimens.largePadding))
        }
    }
}

@Composable
private fun ScoreContent(
    state: ScoreState,
    screenHeight: Dp
) {
    if (state.isLoading) {
        LoadingIndicator(
            modifier = Modifier.size(120.dp),
            screenHeight = screenHeight
        )
    } else if (state.message.isNullOrEmpty().not()) {
        Text(
            text = state.message.orEmpty(),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = Dimens.padding)
        )
    } else {
        ScoresColumn(
            state = state,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun Filter(
    dropDownMenuExpanded: MutableState<Boolean>,
    selectedFilter: MutableIntState,
    state: ScoreState,
    viewModel: ScoreViewModel
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.filter),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(Dimens.padding))
        FilterMenu(dropDownMenuExpanded, selectedFilter, state) { filter ->
            dropDownMenuExpanded.value = false
            selectedFilter.intValue = filter
            viewModel.onEvent(
                ScoresEvent.OnRequestScores(
                    amount = filter
                )
            )
        }
    }
}

@Composable
private fun ScoresColumn(
    state: ScoreState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        state.scores.forEachIndexed { index, score ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "${index + 1}.",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.widthIn(min = 32.dp)
                )
                Spacer(modifier = Modifier.width(Dimens.padding))
                Text(
                    text = score.score.toString(),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "(${score.amount} ${stringResource(R.string.cards)})",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun FilterMenu(
    dropDownMenuExpanded: MutableState<Boolean>,
    selectedFilter: MutableIntState,
    state: ScoreState,
    onClickMenuItem: (Int) -> Unit
) {
    Box {
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkerRed,
                contentColor = Color.White
            ),
            onClick = { dropDownMenuExpanded.value = true },
        ) {
            Text(
                text = if (selectedFilter.intValue == 0) {
                    stringResource(R.string.all)
                } else {
                    "${selectedFilter.intValue} ${stringResource(R.string.cards)}"
                },
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
        }
        DropdownMenu(
            expanded = dropDownMenuExpanded.value,
            onDismissRequest = { dropDownMenuExpanded.value = false }
        ) {
            state.filters.forEach { filter ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = if (filter == 0) {
                                stringResource(R.string.all)
                            } else {
                                "$filter ${stringResource(R.string.cards)}"
                            },
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = if (selectedFilter.intValue == filter) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    onClick = {
                        onClickMenuItem(filter)
                    }
                )
            }
        }
    }
}

@Composable
@Suppress("LongMethod", "MagicNumber")
internal fun ScoreScreenPreviewWrapper(
    isDarkMode: Boolean = false,
    state: ScoreState
) {
    val selectedFilter = remember { mutableIntStateOf(0) }
    val dropDownMenuExpanded = remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(Dimens.padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            MemoryGameLogo(
                Modifier
                    .fillMaxWidth(0.6f)
                    .heightIn(min = 60.dp, max = 120.dp)
            )

            Spacer(modifier = Modifier.height(Dimens.largePadding))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Filter",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(Dimens.padding))
                FilterMenu(dropDownMenuExpanded, selectedFilter, state) { }
            }

            Spacer(modifier = Modifier.height(Dimens.largePadding))

            if (state.isLoading) {
                Box(
                    modifier = Modifier.size(120.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            } else if (state.message.isNullOrEmpty().not()) {
                Text(
                    text = state.message.orEmpty(),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = Dimens.padding)
                )
            } else {
                ScoresColumn(
                    state = state,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            MemoryGameWhiteButton(
                text = "Back",
                isDarkMode = isDarkMode,
                onClick = { }
            )

            Spacer(modifier = Modifier.height(Dimens.padding))
            Narcisus()
            Spacer(modifier = Modifier.height(Dimens.largePadding))
        }
    }
}

@Preview(name = "Light Mode - With Scores", showBackground = true)
@Composable
internal fun ScoreScreenPreview() {
    val mockScores = listOf(
        Score(score = 2450, amount = 12, timeMillis = 1702345200000L),
        Score(score = 1980, amount = 10, timeMillis = 1702258800000L),
        Score(score = 1750, amount = 8, timeMillis = 1702172400000L),
        Score(score = 1420, amount = 15, timeMillis = 1702086000000L),
        Score(score = 1200, amount = 6, timeMillis = 1701999600000L)
    )
    val mockState = ScoreState(
        scores = mockScores,
        filters = listOf(0, 6, 8, 10, 12, 15),
        isLoading = false,
        message = null
    )

    AstorMemoryChallengeTheme(darkTheme = false) {
        ScoreScreenPreviewWrapper(
            isDarkMode = false,
            state = mockState
        )
    }
}

@Preview(name = "Dark Mode - With Scores", showBackground = true)
@Composable
internal fun ScoreScreenDarkPreview() {
    val mockScores = listOf(
        Score(score = 3200, amount = 20, timeMillis = 1702431600000L),
        Score(score = 2890, amount = 18, timeMillis = 1702345200000L),
        Score(score = 2650, amount = 16, timeMillis = 1702258800000L)
    )
    val mockState = ScoreState(
        scores = mockScores,
        filters = listOf(0, 16, 18, 20),
        isLoading = false,
        message = null
    )

    AstorMemoryChallengeTheme(darkTheme = true) {
        ScoreScreenPreviewWrapper(
            isDarkMode = true,
            state = mockState
        )
    }
}

@Preview(name = "Loading State", showBackground = true)
@Composable
internal fun ScoreScreenLoadingPreview() {
    val mockState = ScoreState(
        scores = emptyList(),
        filters = listOf(0),
        isLoading = true,
        message = null
    )

    AstorMemoryChallengeTheme(darkTheme = false) {
        ScoreScreenPreviewWrapper(
            isDarkMode = false,
            state = mockState
        )
    }
}

@Preview(name = "Empty State", showBackground = true)
@Composable
internal fun ScoreScreenEmptyPreview() {
    val mockState = ScoreState(
        scores = emptyList(),
        filters = listOf(0, 5, 10, 15),
        isLoading = false,
        message = "No scores found for this filter"
    )

    AstorMemoryChallengeTheme(darkTheme = false) {
        ScoreScreenPreviewWrapper(
            isDarkMode = false,
            state = mockState
        )
    }
}

@Preview(name = "Many Scores", showBackground = true)
@Composable
internal fun ScoreScreenManyScoresPreview() {
    val mockScores = (1..15).map { index ->
        Score(
            score = 3000 - (index * 150),
            amount = (5..25).random(),
            timeMillis = System.currentTimeMillis() - (index * 86400000L) // Different days
        )
    }
    val mockState = ScoreState(
        scores = mockScores,
        filters = listOf(0, 5, 8, 10, 12, 15, 18, 20, 25),
        isLoading = false,
        message = null
    )

    AstorMemoryChallengeTheme(darkTheme = false) {
        ScoreScreenPreviewWrapper(
            isDarkMode = false,
            state = mockState
        )
    }
}

@Preview(name = "Large Font", showBackground = true, fontScale = 1.5f)
@Composable
internal fun ScoreScreenLargeFontPreview() {
    val mockScores = listOf(
        Score(score = 4500, amount = 25, timeMillis = 1702518000000L),
        Score(score = 3200, amount = 20, timeMillis = 1702431600000L),
        Score(score = 2800, amount = 18, timeMillis = 1702345200000L),
        Score(score = 2400, amount = 15, timeMillis = 1702258800000L)
    )
    val mockState = ScoreState(
        scores = mockScores,
        filters = listOf(0, 15, 18, 20, 25),
        isLoading = false,
        message = null
    )

    AstorMemoryChallengeTheme(darkTheme = false) {
        ScoreScreenPreviewWrapper(
            isDarkMode = false,
            state = mockState
        )
    }
}

@Preview(name = "Small Screen", showBackground = true, widthDp = 320, heightDp = 480)
@Composable
internal fun ScoreScreenSmallPreview() {
    val mockScores = listOf(
        Score(score = 1800, amount = 12, timeMillis = 1702345200000L),
        Score(score = 1650, amount = 10, timeMillis = 1702258800000L),
        Score(score = 1500, amount = 8, timeMillis = 1702172400000L),
        Score(score = 1200, amount = 6, timeMillis = 1702086000000L)
    )
    val mockState = ScoreState(
        scores = mockScores,
        filters = listOf(0, 6, 8, 10, 12),
        isLoading = false,
        message = null
    )

    AstorMemoryChallengeTheme(darkTheme = false) {
        ScoreScreenPreviewWrapper(
            isDarkMode = false,
            state = mockState
        )
    }
}

@Preview(name = "Error Message", showBackground = true)
@Composable
internal fun ScoreScreenErrorPreview() {
    val mockState = ScoreState(
        scores = emptyList(),
        filters = listOf(0),
        isLoading = false,
        message = "Failed to load scores. Please check your connection and try again."
    )

    AstorMemoryChallengeTheme(darkTheme = false) {
        ScoreScreenPreviewWrapper(
            isDarkMode = false,
            state = mockState
        )
    }
}
