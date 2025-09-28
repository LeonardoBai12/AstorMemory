package io.lb.presentation.settings

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
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.lb.common.data.model.AstorCard
import io.lb.presentation.R
import io.lb.presentation.game.model.GameCard
import io.lb.presentation.ui.components.IntSelector
import io.lb.presentation.ui.components.MemoryGameBackButton
import io.lb.presentation.ui.components.MemoryGameCard
import io.lb.presentation.ui.theme.AstorMemoryChallengeTheme

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
    val selectedCardsPerLine = remember {
        mutableIntStateOf(cardsPerLine)
    }
    val selectedCardsPerColumn = remember {
        mutableIntStateOf(cardsPerColumn)
    }
    val darkMode = remember {
        mutableStateOf(isDarkMode)
    }

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
                        top = 16.dp,
                        start = 16.dp
                    ),
                ) {
                    navController.navigateUp()
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
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

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = stringResource(R.string.game_screen_layout),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(24.dp))

            val isSmallScreen = configuration.screenWidthDp < 400

            if (isSmallScreen) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
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

                    Spacer(modifier = Modifier.height(24.dp))

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
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
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
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

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
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
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = stringResource(R.string.preview),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            val screenHeight = configuration.screenHeightDp
            val maxPreviewItems = minOf(16, selectedCardsPerLine.intValue * 4)

            LazyVerticalGrid(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
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

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@Composable
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
                        top = 16.dp,
                        start = 16.dp
                    ),
                ) { }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
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

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Game Screen Layout",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(24.dp))

            val isSmallScreen = configuration.screenWidthDp < 400

            if (isSmallScreen) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
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

                    Spacer(modifier = Modifier.height(24.dp))

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
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
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

                    Spacer(modifier = Modifier.width(16.dp))

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

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Preview",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            val screenHeight = configuration.screenHeightDp
            val maxPreviewItems = minOf(16, selectedCardsPerLine.intValue * 4)

            LazyVerticalGrid(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
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

            Spacer(modifier = Modifier.height(24.dp))
        }
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
