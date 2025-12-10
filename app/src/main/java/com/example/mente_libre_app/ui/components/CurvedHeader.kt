package com.example.mente_libre_app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mente_libre_app.ui.theme.LocalExtraColors

@Composable
fun CurvedHeader(navController: NavController) {

    val scheme = MaterialTheme.colorScheme
    val extra = LocalExtraColors.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
    ) {

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        listOf(extra.gradientTop, extra.gradientBottom)
                    )
                )
        )

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        ) {
            withTransform({
                scale(scaleX = 1f, scaleY = -1f)
            }) {

                val path = Path().apply {
                    moveTo(0f, size.height * 0.15f)
                    quadraticTo(size.width * 0.25f, size.height * 0.45f, size.width * 0.50f, size.height * 0.30f)
                    quadraticTo(size.width * 0.80f, size.height * 0.15f, size.width, size.height * 0.25f)
                    lineTo(size.width, 0f)
                    lineTo(0f, 0f)
                    close()
                }

                drawPath(path, scheme.background)
            }
        }

        // FLECHA SIEMPRE BLANCA
        Box(
            modifier = Modifier
                .padding(start = 20.dp, top = 45.dp)
                .size(68.dp)
                .clickable { navController.popBackStack() }
        ) {
            BackArrowCustom(
                navController = navController,
                color = Color.White, // SEGUIRÁ SIENDO BLANCA
                stroke = 18f,
                size = 68
            )
        }
    }
}

