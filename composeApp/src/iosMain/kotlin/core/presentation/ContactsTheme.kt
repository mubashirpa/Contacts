package core.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import ui.theme.DarkColorScheme
import ui.theme.LightColorScheme
import ui.theme.Shapes
import ui.theme.Typography

@Composable
actual fun ContactsTheme(
    darkTheme: Boolean,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean,
    content: @Composable () -> Unit
) {
    val colorScheme = if (!darkTheme) {
        LightColorScheme
    } else {
        DarkColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        shapes = Shapes,
        typography = Typography,
        content = content
    )
}