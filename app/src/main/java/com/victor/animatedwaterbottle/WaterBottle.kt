package com.victor.animatedwaterbottle

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WaterBottle(
    modifier: Modifier = Modifier,
    waterAmount: Int,
    totalAmount: Int,
    unit: String,
    waterColor: Color = Color(0xff279eff),
    capColor: Color = Color(0xff0065b9),
    bottleColor: Color = Color.White,
) {
    val waterPercentage = animateFloatAsState(
        targetValue = (waterAmount.toFloat() / totalAmount),
        label = "Animate water percentage",
        animationSpec = tween(durationMillis = 500)
    ).value

    val animatedWaterAmount = animateIntAsState(
        targetValue = waterAmount,
        label = "Animate water amount",
        animationSpec = tween(durationMillis = 500)
    ).value

    val animatedTextColor = animateColorAsState(
        targetValue = if (waterPercentage > 0.5f) Color.White else capColor,
        label = "Animate text color",
        animationSpec = tween(500)
    ).value

    Box(modifier = modifier
        .width(200.dp)
        .height(600.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            val capWidth = width * 0.55f
            val capHeight = height * 0.1f

            val bottleBodyPath = drawBottlePath(width, height)

            clipPath(bottleBodyPath) {
                drawRect(color = bottleColor, size = size)

                val waterWavesYPosition = (1 - waterPercentage) * size.height
                val waterPath = Path().apply {
                    moveTo(x = 0f, y = waterWavesYPosition)
                    lineTo(x = width, y = waterWavesYPosition)
                    lineTo(x = width, y = height)
                    lineTo(x = 0f, y = height)
                    close()
                }
                drawPath(path = waterPath, color = waterColor)
            }

            drawRoundRect(
                color = capColor,
                size = Size(capWidth, capHeight),
                topLeft = Offset(x = (width / 2) - (capWidth / 2f), y = 0f),
                cornerRadius = CornerRadius(45f, 45f)
            )
        }
        val text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(color = animatedTextColor, fontSize = 44.sp)
            ) {
                append(animatedWaterAmount.toString())
            }
            withStyle(
                style = SpanStyle(color = animatedTextColor, fontSize = 22.sp)
            ) {
                append(" ")
                append(unit)
            }
        }

        Text(text = text)
    }
}

private fun drawBottlePath(width: Float, height: Float): Path {
    return Path().apply {
        moveTo(x = width * 0.3f, y = height * 0.1f)

        lineTo(x = width * 0.3f, y = height * 0.2f)

        quadraticBezierTo(
            x1 = 0f, y1 = height * 0.3f,
            x2 = 0f, y2 = height * 0.4f
        )

        lineTo(x = 0f, y = height * 0.95f)

        quadraticBezierTo(
            x1 = 0f, y1 = height,
            x2 = width * 0.1f, y2 = height
        )

        lineTo(x = width * 0.9f, y = height)

        quadraticBezierTo(
            x1 = width, y1 = height,
            x2 = width, y2 = height * 0.95f
        )

        lineTo(x = width, y = height * 0.4f)

        quadraticBezierTo(
            x1 = width, y1 = height * 0.3f,
            x2 = width * 0.7f, y2 = height * 0.2f
        )

        lineTo(x = width * 0.7f, y = height * 0.1f)

        close()
    }
}