package io.lb.presentation.scores

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.lb.presentation.R
import io.lb.presentation.ui.components.LoadingIndicator
import io.lb.presentation.ui.components.MemoryGameLogo
import io.lb.presentation.ui.components.MemoryGameWhiteButton
import io.lb.presentation.ui.components.PokeBall
import io.lb.presentation.ui.theme.DarkerRed

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
    val screenHeight = configuration.screenWidthDp.dp

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            MemoryGameLogo(
                isDarkMode,
                Modifier.fillMaxWidth(0.6f)
            )

            Spacer(modifier = Modifier.height(12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = stringResource(R.string.filter),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
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

            if (state.isLoading) {
                LoadingIndicator(
                    modifier = Modifier.fillMaxSize(0.5f),
                    screenHeight = screenHeight
                )
            } else if (state.message.isNullOrEmpty().not()) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = state.message.orEmpty(),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            } else {
                Spacer(modifier = Modifier.height(12.dp))
                ScoresColumn(state)
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                MemoryGameWhiteButton(
                    text = "BACK",
                    isDarkMode = isDarkMode,
                    onClick = {
                        navController.navigateUp()
                    }
                )
                Spacer(modifier = Modifier.height(24.dp))
                PokeBall()
            }
        }
    }
}

@Composable
private fun ScoresColumn(
    state: ScoreState,
) {
    LazyColumn(
        userScrollEnabled = true
    ) {
        items(state.scores.size) { index ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "${index + 1}.",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = state.scores[index].score.toString(),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "(${state.scores[index].amount} ${stringResource(R.string.cards)})",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(24.dp))
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
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkerRed,
                contentColor = Color.White
            ),
            onClick = { dropDownMenuExpanded.value = true },
        ) {
            Text(
                text = if (selectedFilter.intValue == 0)
                    "All"
                else
                    "${selectedFilter.intValue} cards",
                fontSize = 16.sp,
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
                            text = if (filter == 0) stringResource(R.string.all) else "$filter ${stringResource(R.string.cards)}",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 16.sp,
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
