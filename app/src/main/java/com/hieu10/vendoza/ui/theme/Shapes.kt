package com.hieu10.vendoza.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),  // Small chips, badges
    small = RoundedCornerShape(8.dp),       // Text fields, buttons
    medium = RoundedCornerShape(12.dp),     // Cards, dialogs
    large = RoundedCornerShape(16.dp),      // Bottom sheets, modals
    extraLarge = RoundedCornerShape(28.dp)  // FAB, search bars
)