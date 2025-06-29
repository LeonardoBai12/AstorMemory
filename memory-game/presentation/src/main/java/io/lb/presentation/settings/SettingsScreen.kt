package io.lb.presentation.settings

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.lb.common.data.model.AstorCard
import io.lb.presentation.R
import io.lb.presentation.game.model.GameCard
import io.lb.presentation.ui.components.IntSelector
import io.lb.presentation.ui.components.MemoryGameBackButton
import io.lb.presentation.ui.components.MemoryGameCard

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
            modifier = Modifier.padding(padding)
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    stringResource(R.string.dark_mode),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.size(12.dp))

                Switch(
                    checked = darkMode.value,
                    onCheckedChange = {
                        darkMode.value = it
                        onChangeDarkMode(it)
                    }
                )
            }

            Text(
                stringResource(R.string.game_screen_layout),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.size(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        stringResource(R.string.cards_per_line),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onBackground
                    )
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
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        stringResource(R.string.cards_per_column),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onBackground
                    )
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

            Text(
                stringResource(R.string.preview),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.size(12.dp))

            LazyVerticalGrid(
                modifier = Modifier
                    .padding(horizontal = 12.dp),
                columns = GridCells.Fixed(selectedCardsPerLine.intValue),
                userScrollEnabled = true,
            ) {
                items(16) {
                    MemoryGameCard(
                        GameCard(
                            astorCard = AstorCard(
                                id = "",
                                astorId = 0,
                                name = "",
                                imageData = ByteArray(0),
                                imageUrl = ""
                            ),
                            isFlipped = false,
                            isMatched = false
                        ),
                        cardsPerLine = selectedCardsPerLine.intValue,
                        cardsPerColumn = selectedCardsPerColumn.intValue
                    ) {

                    }
                }
            }
        }
    }
}
