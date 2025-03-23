package mk.ukim.finki.syncit.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SegmentedToggle(
    options: List<String>,
    selectedIndex: Int,
    onOptionSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier
//            .fillMaxWidth()
            .width(250.dp)
            .height(45.dp)
            .clip(RoundedCornerShape(25.dp))
            .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(25.dp)),
        horizontalArrangement = Arrangement.Center
    ) {
        options.forEachIndexed { index, text ->
            val isSelected = selectedIndex == index

            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                TextButton(
                    onClick = { onOptionSelected(index) },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(text, fontSize = 14.sp)
                }
            }
        }
    }
}
