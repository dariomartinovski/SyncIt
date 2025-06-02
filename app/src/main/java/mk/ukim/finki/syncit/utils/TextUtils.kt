package mk.ukim.finki.syncit.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
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
}