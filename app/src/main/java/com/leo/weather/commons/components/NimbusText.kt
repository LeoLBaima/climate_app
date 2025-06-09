package com.leo.weather.commons.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.leo.weather.ui.theme.WhiteD1

@Composable
fun NimbusText(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 14.sp,
    textAlign: TextAlign? = null,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
) {
    Text(
        text = text,
        modifier = modifier,
        fontSize = fontSize,
        fontStyle = fontStyle,
        color = WhiteD1,
        textAlign = textAlign,
        fontWeight = fontWeight,
    )
}