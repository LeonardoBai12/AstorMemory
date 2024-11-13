package io.lb.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.lb.presentation.ui.theme.DarkerBlue
import io.lb.presentation.ui.theme.DarkerRed
import io.lb.presentation.ui.theme.PrimaryBlue
import io.lb.presentation.ui.theme.PrimaryRed

@Composable
internal fun MemoryGameRedButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryRed,
            contentColor = Color.White
        ),
        border = BorderStroke(4.dp, DarkerBlue),
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
internal fun MemoryGameRestartButton(
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryRed,
            contentColor = Color.White
        ),
        border = BorderStroke(4.dp, DarkerBlue),
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
internal fun MemoryGameBlueButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryBlue,
            contentColor = Color.White
        ),
        border = BorderStroke(4.dp, DarkerRed),
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
            containerColor = Color.White,
            contentColor = PrimaryRed
        ),
        border = BorderStroke(4.dp, DarkerBlue),
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
