package com.example.mente_libre_app.ui.screen

import com.example.mente_libre_app.R
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/* ============================================================
   InicioScreen — Tips + Consejos Semanales (tarjetas blancas,
   tip en formato horizontal para ver el título completo)
   ============================================================ */

private val Fondo = Color(0xFFFFEDF5)
private val Texto = Color(0xFF842C46)
private val Acento = Color(0xFFFF6D9B)
private val CardRosa = Color(0xFFFFDCE3)
private val CardVerde = Color(0xFF9DB25D)

// Tarjetas de Tips / Consejos
private val TipBg = Color.White            // ← ahora blanco
private val TipOvalBg = Color(0xFFF6EEF1)  // fondo claro del óvalo
private val TipTitle = Texto               // mismo color que títulos

private data class BottomItem(val icon: ImageVector, val label: String)

@Composable
fun InicioScreen(
    onNavChange: (Int) -> Unit = {},
    // 🔹 NUEVO: callback para ir a la pantalla de Ánimo
    onGoAnimo: () -> Unit = {}
) {
    Scaffold(
        containerColor = Fondo,
        bottomBar = {
            BottomBar(
                items = listOf(
                    BottomItem(Icons.Filled.Home, "Inicio"),
                    BottomItem(Icons.Filled.FavoriteBorder, "Hábitos"),
                    BottomItem(Icons.Filled.Person, "Perfil"),
                    BottomItem(Icons.Filled.Settings, "Ajustes")
                ),
                selected = 0,
                onSelected = onNavChange,
                modifier = Modifier.navigationBarsPadding()
            )
        }
    ) { padding ->
        val scroll = rememberScrollState()
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .statusBarsPadding()
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .verticalScroll(scroll)
        ) {
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                AvatarPlaceholder(size = 160.dp)
            }

            Spacer(Modifier.height(18.dp))

            Text(
                text = "Métricas de salud",
                color = Texto,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(12.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CardScore(
                    score = 90,
                    onClick = { /* TODO: ir a detalle de puntaje si quieres */ },
                    modifier = Modifier.weight(1f).height(156.dp)
                )
                // 🔹 AQUÍ el cambio: al tocar Ánimo navega al gráfico
                CardMood(
                    mood = "Feliz",
                    values = listOf(2f, 5f, 3.5f, 6f, 4.5f, 7f),
                    onClick = onGoAnimo,
                    modifier = Modifier.weight(1f).height(156.dp)
                )
            }

            Spacer(Modifier.height(26.dp))

            // Tips
            Text(
                text = "Tips",
                color = Texto,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
            )
            TipsSection(
                tips = listOf(
                    Tip("Cómo Organizarse Mejor", R.drawable.organizarse),
                    Tip("¿Qué hacer en una crisis emocional?", R.drawable.crisis_emocional),
                    Tip("Extrategias para el Autocuidado", R.drawable.autocuidado)
                )
            )

            Spacer(Modifier.height(22.dp))

            // Consejos Semanales
            Text(
                text = "Consejos Semanales",
                color = Texto,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
            )
            WeeklyAdviceCard(
                titulo = "Salud Física",
                imagen = R.drawable.salud_fisica,
                onClick = { /* TODO */ }
            )

            Spacer(Modifier.height(40.dp))
        }
    }
}

/* ======================= Componentes UI ======================= */

@Composable
private fun AvatarPlaceholder(size: Dp) {
    Box(Modifier.size(size * 1.25f), contentAlignment = Alignment.Center) {
        Canvas(Modifier.matchParentSize()) {
            drawCircle(
                brush = Brush.radialGradient(listOf(Acento.copy(0.45f), Color.Transparent)),
                radius = size.toPx(), center = center
            )
        }
        Box(
            Modifier
                .size(size)
                .clip(CircleShape)
                .border(3.dp, Acento, CircleShape)
                .background(Color(0xFFFFF6FA)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "Foto de perfil",
                tint = Texto.copy(0.35f),
                modifier = Modifier.size(size * 0.45f)
            )
        }
    }
}

/* ====================== SECCIÓN: TIPS ====================== */

data class Tip(val titulo: String, val imagen: Int)

@Composable
private fun TipsSection(tips: List<Tip>) {
    val scroll = rememberScrollState()
    Row(
        Modifier
            .fillMaxWidth()
            .horizontalScroll(scroll)
            .padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        tips.forEach { tip ->
            TipCardHorizontal(tip)
        }
    }
}

/** Tarjeta horizontal: óvalo con imagen a la izquierda + título a la derecha (multilínea) */
@Composable
private fun TipCardHorizontal(tip: Tip) {
    Surface(
        color = TipBg,
        shape = RoundedCornerShape(18.dp),
        shadowElevation = 10.dp,
        modifier = Modifier
            .width(300.dp)         // más larga para textos largos
            .height(120.dp)
            .clickable { /* TODO */ }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Óvalo con imagen
            Box(
                modifier = Modifier
                    .width(128.dp)
                    .height(88.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clip(RoundedCornerShape(50))
                        .background(TipOvalBg)
                )
                Image(
                    painter = painterResource(tip.imagen),
                    contentDescription = tip.titulo,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth(0.86f)
                        .fillMaxHeight(0.86f)
                        .clip(RoundedCornerShape(50))
                )
            }

            Spacer(Modifier.width(14.dp))

            // Texto a la derecha (varias líneas)
            Text(
                text = tip.titulo,
                color = TipTitle,
                fontSize = 16.sp,
                lineHeight = 18.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 3,
                overflow = TextOverflow.Clip,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

/* =============== SECCIÓN: CONSEJOS SEMANALES =============== */

@Composable
private fun WeeklyAdviceCard(
    titulo: String,
    imagen: Int,
    onClick: () -> Unit
) {
    Surface(
        color = TipBg, // blanco
        shape = RoundedCornerShape(18.dp),
        shadowElevation = 10.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(92.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.94f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(50))
                        .background(TipOvalBg)
                )
                Image(
                    painter = painterResource(imagen),
                    contentDescription = titulo,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth(0.86f)
                        .fillMaxHeight(0.86f)
                        .clip(RoundedCornerShape(50))
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = titulo,
                color = TipTitle,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                maxLines = 2
            )
        }
    }
}

/* =================== Métricas existentes =================== */

@Composable
private fun CardScore(score: Int, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Surface(
        color = CardVerde,
        shape = RoundedCornerShape(20.dp),
        modifier = modifier.clickable { onClick() }
    ) {
        Column(
            Modifier.fillMaxSize().padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Puntaje", color = Color.White, fontWeight = FontWeight.SemiBold)
                Icon(Icons.Filled.FavoriteBorder, contentDescription = null, tint = Color.White)
            }
            Spacer(Modifier.height(2.dp))
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                CircularScore(value = score, stroke = 8f, color = Color.White, size = 72.dp)
            }
            Text("Salud", color = Color.White, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
private fun CardMood(mood: String, values: List<Float>, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Surface(
        color = CardRosa,
        shape = RoundedCornerShape(20.dp),
        modifier = modifier.clickable { onClick() }
    ) {
        Column(
            Modifier.fillMaxSize().padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Ánimo", color = Texto, fontWeight = FontWeight.SemiBold)
                    Text(mood, color = Texto, fontSize = 14.sp)
                }
                Text("ⓘ", color = Texto.copy(alpha = 0.7f))
            }
            MiniBarChart(values = values, barWidth = 9.dp, color = Texto.copy(alpha = 0.85f))
        }
    }
}

@Composable
private fun CircularScore(value: Int, stroke: Float, color: Color, size: Dp) {
    Box(Modifier.size(size), contentAlignment = Alignment.Center) {
        Canvas(Modifier.fillMaxSize()) {
            drawArc(
                color.copy(alpha = 0.25f),
                startAngle = 135f, sweepAngle = 270f, useCenter = false,
                style = Stroke(width = stroke, cap = StrokeCap.Round)
            )
            val sweep = (value.coerceIn(0, 100) / 100f) * 270f
            drawArc(
                color,
                startAngle = 135f, sweepAngle = sweep, useCenter = false,
                style = Stroke(width = stroke, cap = StrokeCap.Round)
            )
        }
        Text("$value", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun MiniBarChart(values: List<Float>, barWidth: Dp, color: Color) {
    val max = (values.maxOrNull() ?: 1f)
    Canvas(Modifier.fillMaxWidth().height(60.dp)) {
        val bw = barWidth.toPx()
        val space = (size.width - (values.size * bw)) / (values.size + 1)
        var x = space
        values.forEach { v ->
            val h = (v / max) * size.height
            drawRoundRect(
                color = color,
                topLeft = Offset(x, size.height - h),
                size = androidx.compose.ui.geometry.Size(bw, h),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(8f, 8f)
            )
            x += bw + space
        }
    }
}

/** 🔹 Barra inferior solo redondeada arriba **/
@Composable
private fun BottomBar(
    items: List<BottomItem>,
    selected: Int,
    onSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = Color.White.copy(alpha = 0.98f),
        shadowElevation = 12.dp,
        shape = RoundedCornerShape(topStart = 26.dp, topEnd = 26.dp),
        modifier = modifier
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { i, item ->
                val tint = if (i == selected) Acento else Texto.copy(0.55f)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clip(RoundedCornerShape(18.dp))
                        .clickable { onSelected(i) }
                        .padding(vertical = 6.dp, horizontal = 10.dp)
                ) {
                    Icon(item.icon, contentDescription = item.label, tint = tint)
                    Text(item.label, fontSize = 11.sp, color = tint)
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF4F4F4)
@Composable
private fun PreviewInicioScreen() {
    MaterialTheme { InicioScreen() }
}
