package com.hieu10.vendoza.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hieu10.vendoza.R
import com.hieu10.vendoza.ui.theme.VendozaTheme

data class BannerItem(
    val imageRes: Int,
    val tagline: String,
    val buttonText: Int = R.string.btn_shop_now
)

@Composable
fun HeroBanner(
    items: List<BannerItem> = defaultBannerItems,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { items.size })

    Box(modifier = modifier.fillMaxWidth()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            BannerSlide(item = items[page])
        }

        // Page indicators
        Row(
            modifier = Modifier.padding(bottom = 12.dp).align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(items.size) { index ->
                val color = if (pagerState.currentPage == index) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                }

                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(width = 16.dp, height = 4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(color)
                )
            }
        }
    }
}

@Composable
fun BannerSlide(item: BannerItem) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        // Placeholder image
        Image(
            painter = painterResource(id = item.imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Overlay with gradient
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.5f)),
                        startY = 0.3f
                    )
                )
        )

        // Text and button
        Column(
            modifier = Modifier.align(Alignment.BottomStart).padding(16.dp)
        ) {
            Text(
                text = item.tagline,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(text = stringResource(id = item.buttonText))
            }
        }
    }
}

// Dummy data for preview
private val defaultBannerItems = listOf(
    BannerItem(R.drawable.hero_banner_1, "Summer Sale – Up to 50% off"),
    BannerItem(R.drawable.hero_banner_2, "New Arrivals: Electronics"),
    BannerItem(R.drawable.hero_banner_3, "Free Shipping on Orders $50+")
)

@Preview(showBackground = true)
@Composable
private fun PreviewHeroBannerLight() {
    VendozaTheme(darkTheme = false) {
        HeroBanner()
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewHeroBannerDark() {
    VendozaTheme(darkTheme = true) {
        HeroBanner()
    }
}