package com.hieu10.vendoza.ui.components.bg

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hieu10.vendoza.ui.theme.VendozaTheme

@Composable
fun AppBG(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        colors.background,
                        colors.surfaceVariant.copy(alpha = 0.8f)
                    )
                )
            )
    ) {
        // Blob 1
        Box(
            modifier = Modifier
                .size(420.dp)
                .offset(x = 220.dp, y = (-120).dp)
                .background(
                    colors.primary.copy(alpha = 0.15f),
                    shape = CircleShape
                )
                .blur(140.dp)
        )

        // Blob 2
        Box(
            modifier = Modifier
                .size(360.dp)
                .offset(x = (-120).dp, y = 500.dp)
                .background(
                    colors.secondary.copy(alpha = 0.14f),
                    shape = CircleShape
                )
                .blur(120.dp)
        )

        // Blob 3
        Box(
            modifier = Modifier
                .size(300.dp)
                .offset(x = 120.dp, y = 600.dp)
                .background(
                    colors.tertiary.copy(alpha = 0.12f),
                    shape = CircleShape
                )
                .blur(110.dp)
        )

        // Subtle grid decoration
        Canvas(
            modifier = Modifier.fillMaxSize().alpha(0.05f)
        ) {
            val step = 80.dp.toPx()

            for (x in 0..size.width.toInt() step step.toInt()) {
                drawLine(
                    color = colors.onBackground,
                    start = Offset(x.toFloat(), 0f),
                    end = Offset(x.toFloat(), size.height),
                    strokeWidth = 1f
                )
            }

            for (y in 0..size.height.toInt() step step.toInt()) {
                drawLine(
                    color = colors.onBackground,
                    start = Offset(0f, y.toFloat()),
                    end = Offset(size.width, y.toFloat()),
                    strokeWidth = 1f
                )
            }
        }

        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewBGLight() {
    VendozaTheme(darkTheme = false) {
        AppBG { }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewBGDark() {
    VendozaTheme(darkTheme = true) {
        AppBG { }
    }
}