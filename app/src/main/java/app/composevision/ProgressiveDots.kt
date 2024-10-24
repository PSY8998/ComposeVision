package app.composevision

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Preview
@Composable
fun ProgressiveDots(){
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.Red
    ) {
        ProgressiveDotsAnimation()
    }
}

@Composable
fun ProgressiveDotsAnimation(){
    val dots = listOf(
        remember {Animatable(0f)},
        remember {Animatable(0f)},
        remember {Animatable(0f)},
    )

    dots.forEachIndexed{ index, animatable ->
        LaunchedEffect(animatable) {
            delay(index*100L)
            animatable.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 2000
                        0.0F at 0 using LinearOutSlowInEasing
                        1.0F at 200 using LinearOutSlowInEasing
                        0.0F at 400 using LinearOutSlowInEasing
                        0.0F at 2000
                    },
                    repeatMode = RepeatMode.Restart
                )
            )
        }
    }

    val dys = dots.map { it.value }

    val travelDistance = with(LocalDensity.current){15.dp.toPx()}

    Row(
        modifier = Modifier
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        dys.forEachIndexed{index, dy ->
            Box(
                modifier =Modifier
                    .size(25.dp)
                    .graphicsLayer {
                        translationY = -dy*travelDistance
                    },
            ){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.White, shape = CircleShape)
                )
            }
        }
    }
}