package io.lb.presentation.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun IntSelector(
    intState: MutableIntState,
    minValue: Int,
    maxValue: Int,
    textSize: Int = 72,
    spaceBetween: Int = 24,
    onChangeAmount: (Int) -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        MemoryGameWhiteButton(
            modifier = Modifier,
            text = "-"
        ) {
            if (intState.intValue > minValue) {
                intState.intValue--
            }
            onChangeAmount(intState.intValue)
        }
        Spacer(modifier = Modifier.width(spaceBetween.dp))
        Text(
            text = intState.intValue.toString(),
            fontSize = textSize.sp,
            color = Color.Black,
        )
        Spacer(modifier = Modifier.width(spaceBetween.dp))
        MemoryGameWhiteButton(
            modifier = Modifier,
            text = "+"
        ) {
            if (intState.intValue < maxValue) {
                intState.intValue++
            }
            onChangeAmount(intState.intValue)
        }
    }
}