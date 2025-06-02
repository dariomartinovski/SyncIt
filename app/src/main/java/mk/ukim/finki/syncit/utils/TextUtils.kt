package mk.ukim.finki.syncit.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object TextUtils {
    @Composable
    fun LargeTitle(text: String,
                   fontWeight: FontWeight = FontWeight.Bold,
                   fontSize: TextUnit = 24.sp,
                   fontColor: Color = MaterialTheme.colorScheme.primary,
                   modifier: Modifier = Modifier
    ){
        return Text(
            text = text,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = fontWeight,
                fontSize = fontSize,
                color = fontColor
            ),
            modifier = modifier
        )
    }

    @Composable
    fun CenteredMessage(message: String, modifier: Modifier = Modifier) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp),
                textAlign = TextAlign.Center
            )
        }
    }
}