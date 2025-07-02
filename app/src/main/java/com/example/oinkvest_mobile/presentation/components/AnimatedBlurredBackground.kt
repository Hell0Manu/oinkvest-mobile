package com.example.oinkvest_mobile.presentation.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.oinkvest_mobile.R

@Composable
fun AnimatedBlurredBackground(
    content: @Composable () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        // Box para sobrepor a imagem e o conteúdo
        Box(modifier = Modifier.fillMaxSize()) {

            val infiniteTransition = rememberInfiniteTransition(label = "background_animation")

            // Animação para o eixo X (vai de -100.dp a 100.dp e volta)
            val offsetX by infiniteTransition.animateFloat(
                initialValue = -100f,
                targetValue = 100f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 20000, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                ), label = "offsetX"
            )

            // Animação para o eixo Y
            val offsetY by infiniteTransition.animateFloat(
                initialValue = -100f,
                targetValue = 100f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 22000, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                ), label = "offsetY"
            )

            // Animação de rotação
            val rotation by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 38000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                ), label = "rotation"
            )

            // 3. A imagem de fundo com os modificadores de animação
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alpha = 0.5f,
                modifier = Modifier
                    .fillMaxSize()
                    .scale(1.8f)
                    .offset(x = offsetX.dp, y = offsetY.dp)
                    .rotate(rotation)
            )

            content()
        }
    }
}