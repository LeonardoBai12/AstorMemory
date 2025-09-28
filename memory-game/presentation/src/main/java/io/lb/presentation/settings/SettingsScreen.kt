package io.lb.presentation.settings

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.lb.common.data.model.AstorCard
import io.lb.presentation.R
import io.lb.presentation.game.model.GameCard
import io.lb.presentation.ui.components.IntSelector
import io.lb.presentation.ui.components.MemoryGameBackButton
import io.lb.presentation.ui.components.MemoryGameCard
import io.lb.presentation.ui.theme.AstorMemoryChallengeTheme
import io.lb.presentation.ui.theme.Dimens

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun SettingsScreen(
    navController: NavController,
    cardsPerLine: Int,
    cardsPerColumn: Int,
    isDarkMode: Boolean,
    onChangeDarkMode: (Boolean) -> Unit,
    onChangeCardsPerLine: (Int) -> Unit,
    onChangeCardsPerColumn: (Int) -> Unit
) {
    val configuration = LocalConfiguration.current
    val selectedCardsPerLine = remember { mutableIntStateOf(cardsPerLine) }
    val selectedCardsPerColumn = remember { mutableIntStateOf(cardsPerColumn) }
    val darkMode = remember { mutableStateOf(isDarkMode) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
        ) {
            BackButton(navController)
            Spacer(modifier = Modifier.height(Dimens.largePadding))
            DarkModeSwitch(darkMode, onChangeDarkMode)
            Spacer(modifier = Modifier.height(32.dp))
            GameScreenLayoutText()
            Spacer(modifier = Modifier.height(Dimens.largePadding))

            val isSmallScreen = configuration.screenWidthDp < 400
            if (isSmallScreen) {
                SmallScreenContent(
                    selectedCardsPerLine = selectedCardsPerLine,
                    darkMode = darkMode,
                    onChangeCardsPerLine = onChangeCardsPerLine,
                    selectedCardsPerColumn = selectedCardsPerColumn,
                    onChangeCardsPerColumn = onChangeCardsPerColumn
                )
            } else {
                LargeScreenContent(
                    selectedCardsPerLine = selectedCardsPerLine,
                    darkMode = darkMode,
                    onChangeCardsPerLine = onChangeCardsPerLine,
                    selectedCardsPerColumn = selectedCardsPerColumn,
                    onChangeCardsPerColumn = onChangeCardsPerColumn
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = stringResource(R.string.preview),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.padding),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(Dimens.padding))
            CardsPreview(configuration, selectedCardsPerLine, selectedCardsPerColumn)
            Spacer(modifier = Modifier.height(Dimens.largePadding))
        }
    }
}

@Composable
private fun GameScreenLayoutText() {
    Text(
        text = stringResource(R.string.game_screen_layout),
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.padding),
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
private fun LargeScreenContent(
    selectedCardsPerLine: MutableIntState,
    darkMode: MutableState<Boolean>,
    onChangeCardsPerLine: (Int) -> Unit,
    selectedCardsPerColumn: MutableIntState,
    onChangeCardsPerColumn: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.padding),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CardsPerLineContent(selectedCardsPerLine, darkMode, onChangeCardsPerLine)
        }
        Spacer(modifier = Modifier.width(Dimens.padding))
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CardsPerColumnContent(
                selectedCardsPerColumn,
                darkMode,
                onChangeCardsPerColumn
            )
        }
    }
}

@Composable
private fun SmallScreenContent(
    selectedCardsPerLine: MutableIntState,
    darkMode: MutableState<Boolean>,
    onChangeCardsPerLine: (Int) -> Unit,
    selectedCardsPerColumn: MutableIntState,
    onChangeCardsPerColumn: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.padding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CardsPerLineContent(selectedCardsPerLine, darkMode, onChangeCardsPerLine)
        }
        Spacer(modifier = Modifier.height(Dimens.largePadding))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CardsPerColumnContent(
                selectedCardsPerColumn,
                darkMode,
                onChangeCardsPerColumn
            )
        }
    }
}

@Composable
private fun DarkModeSwitch(
    darkMode: MutableState<Boolean>,
    onChangeDarkMode: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.padding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.dark_mode),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Switch(
            checked = darkMode.value,
            onCheckedChange = {
                darkMode.value = it
                onChangeDarkMode(it)
            }
        )
    }
}

@Composable
private fun BackButton(navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MemoryGameBackButton(
            modifier = Modifier.padding(
                top = Dimens.padding,
                start = Dimens.padding
            ),
        ) {
            navController.navigateUp()
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
private fun CardsPreview(
    configuration: Configuration,
    selectedCardsPerLine: MutableIntState,
    selectedCardsPerColumn: MutableIntState
) {
    val screenHeight = configuration.screenHeightDp
    val maxPreviewItems = minOf(16, selectedCardsPerLine.intValue * 4)

    LazyVerticalGrid(
        modifier = Modifier
            .padding(horizontal = Dimens.padding)
            .heightIn(max = (screenHeight * 0.6f).dp),
        columns = GridCells.Fixed(selectedCardsPerLine.intValue),
        userScrollEnabled = false,
    ) {
        items(maxPreviewItems) { index ->
            MemoryGameCard(
                card = GameCard(
                    astorCard = AstorCard(
                        id = index.toString(),
                        astorId = index,
                        name = "Preview $index",
                        imageData = ByteArray(0),
                        imageUrl = ""
                    ),
                    isFlipped = false,
                    isMatched = false
                ),
                cardsPerLine = selectedCardsPerLine.intValue,
                cardsPerColumn = selectedCardsPerColumn.intValue
            ) {
                // No action in preview
            }
        }
    }
}

@Composable
private fun CardsPerColumnContent(
    selectedCardsPerColumn: MutableIntState,
    darkMode: MutableState<Boolean>,
    onChangeCardsPerColumn: (Int) -> Unit
) {
    Text(
        text = stringResource(R.string.cards_per_column),
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onBackground
    )
    Spacer(modifier = Modifier.height(8.dp))
    IntSelector(
        intState = selectedCardsPerColumn,
        minValue = 5,
        maxValue = 9,
        spaceBetween = 12,
        textSize = 48,
        isDarkMode = darkMode.value,
        onChangeAmount = {
            onChangeCardsPerColumn(it)
        }
    )
}

@Composable
private fun CardsPerLineContent(
    selectedCardsPerLine: MutableIntState,
    darkMode: MutableState<Boolean>,
    onChangeCardsPerLine: (Int) -> Unit
) {
    Text(
        text = stringResource(R.string.cards_per_line),
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onBackground
    )
    Spacer(modifier = Modifier.height(8.dp))
    IntSelector(
        intState = selectedCardsPerLine,
        minValue = 3,
        maxValue = 6,
        spaceBetween = 12,
        textSize = 48,
        isDarkMode = darkMode.value,
        onChangeAmount = {
            onChangeCardsPerLine(it)
        }
    )
}

@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@Composable
@Suppress("LongMethod", "MagicNumber")
internal fun SettingsScreenPreviewWrapper(
    cardsPerLine: Int = 4,
    cardsPerColumn: Int = 6,
    isDarkMode: Boolean = false
) {
    val configuration = LocalConfiguration.current
    val selectedCardsPerLine = remember { mutableIntStateOf(cardsPerLine) }
    val selectedCardsPerColumn = remember { mutableIntStateOf(cardsPerColumn) }
    val darkMode = remember { mutableStateOf(isDarkMode) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                MemoryGameBackButton(
                    modifier = Modifier.padding(
                        top = Dimens.padding,
                        start = Dimens.padding
                    ),
                ) { }
            }

            Spacer(modifier = Modifier.height(Dimens.largePadding))
            DarkModeSwitch(darkMode)
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = stringResource(R.string.game_screen_layout_config),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.padding),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(Dimens.largePadding))

            val isSmallScreen = configuration.screenWidthDp < 400
            if (isSmallScreen) {
                ContentForSmallScreen(selectedCardsPerLine, darkMode, selectedCardsPerColumn)
            } else {
                ContentForLargeScreen(selectedCardsPerLine, darkMode, selectedCardsPerColumn)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Preview",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.padding),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(Dimens.padding))

            val screenHeight = configuration.screenHeightDp
            val maxPreviewItems = minOf(16, selectedCardsPerLine.intValue * 4)

            LazyVerticalGrid(
                modifier = Modifier
                    .padding(horizontal = Dimens.padding)
                    .heightIn(max = (screenHeight * 0.4f).dp),
                columns = GridCells.Fixed(selectedCardsPerLine.intValue),
                userScrollEnabled = false,
            ) {
                items(maxPreviewItems) { index ->
                    MemoryGameCard(
                        card = GameCard(
                            astorCard = AstorCard(
                                id = index.toString(),
                                astorId = index,
                                name = "Preview $index",
                                imageData = ByteArray(0),
                                imageUrl = ""
                            ),
                            isFlipped = false,
                            isMatched = false
                        ),
                        cardsPerLine = selectedCardsPerLine.intValue,
                        cardsPerColumn = selectedCardsPerColumn.intValue
                    ) { }
                }
            }

            Spacer(modifier = Modifier.height(Dimens.largePadding))
        }
    }
}

@Composable
private fun ContentForLargeScreen(
    selectedCardsPerLine: MutableIntState,
    darkMode: MutableState<Boolean>,
    selectedCardsPerColumn: MutableIntState
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.padding),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Cards Per Line",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            IntSelector(
                intState = selectedCardsPerLine,
                minValue = 3,
                maxValue = 6,
                spaceBetween = 12,
                textSize = 48,
                isDarkMode = darkMode.value,
                onChangeAmount = { }
            )
        }

        Spacer(modifier = Modifier.width(Dimens.padding))

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Cards Per Column",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            IntSelector(
                intState = selectedCardsPerColumn,
                minValue = 5,
                maxValue = 9,
                spaceBetween = 12,
                textSize = 48,
                isDarkMode = darkMode.value,
                onChangeAmount = { }
            )
        }
    }
}

@Composable
private fun ContentForSmallScreen(
    selectedCardsPerLine: MutableIntState,
    darkMode: MutableState<Boolean>,
    selectedCardsPerColumn: MutableIntState
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.padding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Cards Per Line",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            IntSelector(
                intState = selectedCardsPerLine,
                minValue = 3,
                maxValue = 6,
                spaceBetween = 12,
                textSize = 48,
                isDarkMode = darkMode.value,
                onChangeAmount = { }
            )
        }

        Spacer(modifier = Modifier.height(Dimens.largePadding))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Cards Per Column",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            IntSelector(
                intState = selectedCardsPerColumn,
                minValue = 5,
                maxValue = 9,
                spaceBetween = 12,
                textSize = 48,
                isDarkMode = darkMode.value,
                onChangeAmount = { }
            )
        }
    }
}

@Composable
private fun DarkModeSwitch(darkMode: MutableState<Boolean>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.padding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Dark Mode",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Switch(
            checked = darkMode.value,
            onCheckedChange = {
                darkMode.value = it
            }
        )
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Preview(name = "Settings - Light Mode", showBackground = true)
@Composable
internal fun SettingsScreenPreview() {
    AstorMemoryChallengeTheme(darkTheme = false) {
        SettingsScreenPreviewWrapper(
            cardsPerLine = 4,
            cardsPerColumn = 6,
            isDarkMode = false
        )
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Preview(name = "Settings - Dark Mode", showBackground = true)
@Composable
internal fun SettingsScreenDarkPreview() {
    AstorMemoryChallengeTheme(darkTheme = true) {
        SettingsScreenPreviewWrapper(
            cardsPerLine = 4,
            cardsPerColumn = 6,
            isDarkMode = true
        )
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Preview(name = "Settings - 3x5 Layout", showBackground = true)
@Composable
internal fun SettingsScreen3x5Preview() {
    AstorMemoryChallengeTheme(darkTheme = false) {
        SettingsScreenPreviewWrapper(
            cardsPerLine = 3,
            cardsPerColumn = 5,
            isDarkMode = false
        )
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Preview(name = "Settings - 6x9 Layout", showBackground = true)
@Composable
internal fun SettingsScreen6x9Preview() {
    AstorMemoryChallengeTheme(darkTheme = false) {
        SettingsScreenPreviewWrapper(
            cardsPerLine = 6,
            cardsPerColumn = 9,
            isDarkMode = false
        )
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Preview(name = "Settings - Large Font", showBackground = true, fontScale = 1.5f)
@Composable
internal fun SettingsScreenLargeFontPreview() {
    AstorMemoryChallengeTheme(darkTheme = false) {
        SettingsScreenPreviewWrapper(
            cardsPerLine = 4,
            cardsPerColumn = 6,
            isDarkMode = false
        )
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Preview(name = "Settings - Small Screen", showBackground = true, widthDp = 320, heightDp = 480)
@Composable
internal fun SettingsScreenSmallPreview() {
    AstorMemoryChallengeTheme(darkTheme = false) {
        SettingsScreenPreviewWrapper(
            cardsPerLine = 4,
            cardsPerColumn = 6,
            isDarkMode = false
        )
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Preview(
    name = "Settings - Small Screen Large Font",
    showBackground = true,
    widthDp = 320,
    heightDp = 480,
    fontScale = 1.3f
)
@Composable
internal fun SettingsScreenSmallLargeFontPreview() {
    AstorMemoryChallengeTheme(darkTheme = false) {
        SettingsScreenPreviewWrapper(
            cardsPerLine = 3,
            cardsPerColumn = 5,
            isDarkMode = false
        )
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Preview(name = "Settings - Tablet", showBackground = true, widthDp = 800, heightDp = 1200)
@Composable
internal fun SettingsScreenTabletPreview() {
    AstorMemoryChallengeTheme(darkTheme = false) {
        SettingsScreenPreviewWrapper(
            cardsPerLine = 5,
            cardsPerColumn = 7,
            isDarkMode = false
        )
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Preview(name = "Settings - Edge Cases", showBackground = true)
@Composable
internal fun SettingsScreenEdgeCasesPreview() {
    AstorMemoryChallengeTheme(darkTheme = true) {
        SettingsScreenPreviewWrapper(
            cardsPerLine = 6,
            cardsPerColumn = 5,
            isDarkMode = true
        )
    }
}
