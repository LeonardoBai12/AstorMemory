package io.lb.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun MemoryGameRedButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        border = BorderStroke(4.dp, MaterialTheme.colorScheme.onPrimaryContainer),
        onClick = {
            onClick()
        }
    ) {
        Text(
            text = text,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
internal fun MemoryGameStopButton(
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        ),
        border = BorderStroke(4.dp, MaterialTheme.colorScheme.onSecondaryContainer),
        onClick = {
            onClick()
        }
    ) {
        Icon(
            Icons.Default.Close,
            modifier = Modifier.size(32.dp),
            contentDescription = "Restart Game"
        )
    }
}

@Composable
internal fun MemoryGameRestartButton(
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        border = BorderStroke(4.dp, MaterialTheme.colorScheme.onPrimaryContainer),
        onClick = {
            onClick()
        }
    ) {
        Icon(
            Icons.Default.Refresh,
            modifier = Modifier.size(32.dp),
            contentDescription = "Restart Game"
        )
    }
}

@Composable
internal fun MemoryGameIconButton(
    icon: @Composable () -> Unit,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .padding(top = 16.dp)
            .padding(horizontal = 8.dp)
            .fillMaxWidth(0.2f),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        border = BorderStroke(4.dp, MaterialTheme.colorScheme.onPrimaryContainer),
        onClick = {
            onClick()
        }
    ) {
        icon.invoke()
    }
}

@Composable
internal fun MemoryGameBlueButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        ),
        border = BorderStroke(4.dp, MaterialTheme.colorScheme.onSecondaryContainer),
        onClick = {
            onClick()
        }
    ) {
        Text(
            text = text,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
internal fun MemoryGameWhiteButton(
    modifier: Modifier = Modifier.fillMaxWidth(),
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.onTertiary
        ),
        border = BorderStroke(4.dp, MaterialTheme.colorScheme.onTertiaryContainer),
        onClick = {
            onClick()
        }
    ) {
        Text(
            text = text,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
