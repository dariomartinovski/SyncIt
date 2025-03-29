package mk.ukim.finki.syncit.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

object TopBarUtils {
    @Composable
    fun CustomTitle(title: String) {
        return Text(
            title,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = LightTextColor
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CustomBackground(): TopAppBarColors {
        return TopAppBarDefaults.topAppBarColors(
            containerColor = DeepBlueColor
        )
    }

    @Composable
    fun CustomBackAction(navController: NavController) {
        return IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = LightTextColor
            )
        }
    }

    @Composable
    fun CustomSuffixIconButton(text: String, icon: ImageVector, onClick: () -> Unit) {
        return IconButton(onClick = onClick) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = LightTextColor
            )
        }
    }

    @Composable
    fun CustomLoginLogoutIconButton(navController: NavController, isUserLoggedIn: Boolean = false) {
        CustomSuffixIconButton(
            text = if (isUserLoggedIn) "Logout" else "Login",
            icon = if (isUserLoggedIn) Icons.AutoMirrored.Filled.Logout else Icons.AutoMirrored.Filled.Login,
            onClick = {
                if (isUserLoggedIn) {
                    navController.navigate("logout")
                } else {
                    navController.navigate("login")
                }
            }
        )
    }
}