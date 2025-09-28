package io.lb.presentation.menu

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import io.lb.presentation.R
import io.lb.presentation.ui.components.IntSelector
import io.lb.presentation.ui.components.MemoryGameBlueButton
import io.lb.presentation.ui.components.MemoryGameLogo
import io.lb.presentation.ui.components.MemoryGameRedButton
import io.lb.presentation.ui.components.MemoryGameWhiteButton
import io.lb.presentation.ui.components.Narcisus
import io.lb.presentation.ui.navigation.MemoryGameScreens
import io.lb.presentation.ui.theme.AstorMemoryChallengeTheme
import io.lb.presentation.ui.theme.Dimens

@Composable
internal fun MenuScreen(
    navController: NavController,
    isDarkMode: Boolean,
    initialAmount: Int,
    isMuted: Boolean,
    onChangeMuted: (Boolean) -> Unit,
    onClickQuit: () -> Unit,
    onChangeAmount: (Int) -> Unit
) {
    val muted = remember {
        mutableStateOf(isMuted)
    }
    val amount = remember {
        mutableIntStateOf(initialAmount)
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
        ) {
            MenuTopIcons(onChangeMuted, muted, navController)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.padding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(Dimens.largePadding))
                MemoryGameLogo(
                    Modifier
                        .fillMaxWidth(0.9f)
                        .heightIn(min = 80.dp, max = 160.dp)
                )
                PairsAmountSelector(amount, isDarkMode, onChangeAmount)
                Text(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    text = stringResource(R.string.the_more_cards_you_play_with),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(32.dp))
                ButtonsColumn(
                    navController = navController,
                    isDarkMode = isDarkMode,
                    amount = amount,
                    onClickQuit = onClickQuit
                )
                Spacer(modifier = Modifier.height(Dimens.largePadding))
            }
        }
    }
}

@Composable
private fun PairsAmountSelector(
    amount: MutableIntState,
    isDarkMode: Boolean,
    onChangeAmount: (Int) -> Unit
) {
    Spacer(modifier = Modifier.height(32.dp))
    Text(
        text = stringResource(R.string.amount_of_card_pairs),
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onBackground
    )
    Spacer(modifier = Modifier.height(Dimens.padding))
    IntSelector(
        intState = amount,
        minValue = 1,
        maxValue = 30,
        isDarkMode = isDarkMode,
        onChangeAmount = onChangeAmount
    )
    Spacer(modifier = Modifier.height(Dimens.padding))
}

@Composable
private fun MenuTopIcons(
    onChangeMuted: (Boolean) -> Unit,
    muted: MutableState<Boolean>,
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.padding)
            .padding(top = Dimens.padding),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        val configuration = LocalConfiguration.current
        val screenHeight = configuration.screenHeightDp
        val buttonSize = (screenHeight.dp / 12).coerceIn(56.dp, 72.dp)
        val iconSize = (buttonSize * 0.6f).coerceIn(28.dp, 40.dp)

        IconButton(
            onClick = {
                onChangeMuted(muted.value.not())
                muted.value = muted.value.not()
            },
            modifier = Modifier.size(buttonSize)
        ) {
            Icon(
                painter = if (muted.value) {
                    painterResource(R.drawable.music_off)
                } else {
                    painterResource(R.drawable.music_on)
                },
                contentDescription = "Muted or not",
                tint = Color.Gray,
                modifier = Modifier.size(iconSize)
            )
        }
        IconButton(
            onClick = {
                navController.navigate(MemoryGameScreens.Settings.name)
            },
            modifier = Modifier.size(buttonSize)
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = stringResource(R.string.settings),
                tint = Color.Gray,
                modifier = Modifier.size(iconSize)
            )
        }
    }
}

@Composable
private fun ButtonsColumn(
    navController: NavController,
    isDarkMode: Boolean,
    amount: MutableIntState,
    onClickQuit: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Dimens.padding)
    ) {
        MemoryGameRedButton(
            text = stringResource(R.string.start_game),
            onClick = {
                navController.navigate(MemoryGameScreens.Game.name + "/${amount.intValue}")
            }
        )

        MemoryGameBlueButton(
            text = stringResource(R.string.highscores),
            onClick = {
                navController.navigate(MemoryGameScreens.HighScores.name)
            }
        )

        MemoryGameWhiteButton(
            isDarkMode = isDarkMode,
            text = stringResource(R.string.quit),
            onClick = {
                onClickQuit()
            }
        )

        Spacer(modifier = Modifier.height(8.dp))
        Narcisus()
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
internal fun MenuScreenPreview() {
    AstorMemoryChallengeTheme(
        darkTheme = false
    ) {
        MenuScreen(
            navController = rememberNavController(),
            isDarkMode = false,
            initialAmount = 5,
            isMuted = false,
            onChangeMuted = { },
            onClickQuit = { },
            onChangeAmount = { }
        )
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
internal fun MenuScreenDarkPreview() {
    AstorMemoryChallengeTheme(
        darkTheme = true
    ) {
        MenuScreen(
            navController = rememberNavController(),
            isDarkMode = true,
            initialAmount = 10,
            isMuted = true,
            onChangeMuted = { },
            onClickQuit = { },
            onChangeAmount = { }
        )
    }
}

@Preview(name = "Large Font", showBackground = true, fontScale = 1.5f)
@Composable
internal fun MenuScreenLargeFontPreview() {
    AstorMemoryChallengeTheme(
        darkTheme = false
    ) {
        MenuScreen(
            navController = rememberNavController(),
            isDarkMode = false,
            initialAmount = 15,
            isMuted = false,
            onChangeMuted = { },
            onClickQuit = { },
            onChangeAmount = { }
        )
    }
}

@Preview(name = "Small Screen", showBackground = true, widthDp = 320, heightDp = 480)
@Composable
internal fun MenuScreenSmallPreview() {
    AstorMemoryChallengeTheme(
        darkTheme = false
    ) {
        MenuScreen(
            navController = rememberNavController(),
            isDarkMode = false,
            initialAmount = 8,
            isMuted = false,
            onChangeMuted = { },
            onClickQuit = { },
            onChangeAmount = { }
        )
    }
}
