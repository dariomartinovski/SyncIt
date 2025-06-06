package mk.ukim.finki.syncit.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import mk.ukim.finki.syncit.data.model.enums.Category

object CategoryUtils {
    fun getCategoryIcon(category: Category): ImageVector {
        return when (category) {
            Category.CONCERT -> Icons.Default.MusicNote
            Category.CONFERENCE -> Icons.Default.Mic
            Category.SPORTS -> Icons.Default.SportsSoccer
            Category.THEATRE -> Icons.Default.Theaters
            Category.EDUCATION -> Icons.Default.School
            Category.FUN -> Icons.Default.EmojiEmotions
            Category.GAMING -> Icons.Default.SportsEsports
            Category.PARTY -> Icons.Default.Celebration
            Category.RETRO_HITS -> Icons.Default.MusicNote
            Category.FESTIVAL -> Icons.Default.Festival
            Category.SUMMER -> Icons.Default.WbSunny
            Category.WINTER -> Icons.Default.AcUnit
            Category.NIGHT -> Icons.Default.NightsStay
            Category.OTHERS -> Icons.Default.Category
            else -> Icons.Default.Category
        }
    }

    fun getCategoryImage(category: Category): String {
        return when (category) {
            Category.FUN -> "https://images.unsplash.com/photo-1496843916299-590492c751f4?q=80&w=600&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
            Category.GAMING -> "https://images.unsplash.com/photo-1633545505446-586bf83717f0?q=80&w=2072&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
            Category.PARTY -> "https://images.unsplash.com/photo-1533174072545-7a4b6ad7a6c3?q=80&w=600&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
            Category.RETRO_HITS -> "https://images.unsplash.com/photo-1559424452-eeb3a13ffe2b?q=80&w=600&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
            Category.FESTIVAL -> "https://images.unsplash.com/photo-1533174072545-7a4b6ad7a6c3?q=80&w=600&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
            Category.SUMMER -> "https://images.unsplash.com/photo-1560359614-870d1a7ea91d?q=80&w=600&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
            Category.WINTER -> "https://images.unsplash.com/photo-1625165871192-e7c1064ed54d?q=80&w=610&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
            Category.OTHERS -> "https://images.unsplash.com/photo-1504805572947-34fad45aed93?q=80&w=600&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
            Category.NIGHT -> "https://images.unsplash.com/photo-1514525253161-7a46d19cd819?q=80&w=590&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
            Category.CONCERT -> "https://images.unsplash.com/photo-1470229722913-7c0e2dbbafd3?q=80&w=600&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
            Category.CONFERENCE -> "https://images.unsplash.com/photo-1540575467063-178a50c2df87?q=80&w=600&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
            Category.SPORTS -> "https://images.unsplash.com/photo-1517649763962-0c623066013b?q=80&w=600&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
            Category.THEATRE -> "https://images.unsplash.com/photo-1516307365426-bea591f05011?q=80&w=600&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
            Category.EDUCATION -> "https://images.unsplash.com/photo-1522202176988-66273c2fd55f?q=80&w=600&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
        }
    }
}
