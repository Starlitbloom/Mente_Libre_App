package com.example.mente_libre_app.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mente_libre_app.R
import kotlin.math.max
import kotlin.math.min

/* ====== Paleta usada ====== */
private val Fondo = Color(0xFFFFEDF5)
private val Texto = Color(0xFF842C46)
private val ChipBg = Color(0xFFFFFFFF)
private val ChipStroke = Texto.copy(0.25f)
private val CurveColor = Color(0xFF734B3A)
/* leyenda “positivo” con verde pastel */
private val MoodGreen = Color(0xFFA6E7A6)
private val MoodGray = Color(0xFFD9D9D9)

/* ====== Modelo de estado de ánimo (COLORES PASTEL) ====== */
enum class Mood(val label: String, val imageRes: Int, val color: Color, val height: Float) {
    Feliz     ("Feliz",      R.drawable.cara_feliz,     Color(0xFFA6E7A6), 0.80f), // verde pastel
    Tranquilo ("Tranquilo",  R.drawable.cara_tranquilo, Color(0xFFFFF1A6), 0.70f), // amarillo pastel
    Sereno    ("Sereno",     R.drawable.cara_sereno,    Color(0xFFAEDCFF), 0.60f), // celeste pastel
    Neutral   ("Neutral",    R.drawable.cara_neutral,   Color(0xFFD9D9D9), 0.50f), // gris suave
    Enojado   ("Enojado",    R.drawable.cara_enojado,   Color(0xFFFFB3B3), 0.40f), // rojo pastel
    Triste    ("Triste",     R.drawable.cara_triste,    Color(0xFFCFB8FF), 0.35f), // lila pastel
    Deprimido ("Deprimido",  R.drawable.cara_deprimido, Color(0xFFB3C7FF), 0.25f)  // azul pastel
}

data class MoodPoint(val day: String, val mood: Mood)

/* ====== Pantalla principal ====== */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimoScreen(onBack: (() -> Unit)? = null) {
    var selectedPeriod by remember { mutableStateOf(1) }

    val weekData = remember {
        listOf(
            MoodPoint("Lun", Mood.Feliz),
            MoodPoint("Mar", Mood.Neutral),
            MoodPoint("Mié", Mood.Deprimido),
            MoodPoint("Jue", Mood.Feliz),
            MoodPoint("Vie", Mood.Neutral),
            MoodPoint("Sáb", Mood.Feliz),
            MoodPoint("Dom", Mood.Neutral)
        )
    }

    Scaffold(
        containerColor = Fondo,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Estados de ánimo", color = Texto, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    if (onBack != null) {
                        IconButton(onClick = onBack) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Volver", tint = Texto)
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Fondo)
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* TODO */ }, containerColor = Texto) {
                Text("? ", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text("Ve tu estado de ánimo a lo largo de tu día.", color = Texto.copy(.75f), fontSize = 14.sp)
            Spacer(Modifier.height(12.dp))

            SegmentedTabs(
                items = listOf("Días", "Semanas", "Meses", "Años"),
                selected = selectedPeriod,
                onChange = { selectedPeriod = it }
            )

            Spacer(Modifier.height(14.dp))

            Surface(
                color = Color.White,
                shape = RoundedCornerShape(18.dp),
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        LegendDot(MoodGreen); Text(" Positivo", color = Texto, fontSize = 12.sp)
                        Spacer(Modifier.width(12.dp))
                        LegendDot(Color(0xFFE57373)); Text(" Negativo", color = Texto, fontSize = 12.sp)
                    }
                    Spacer(Modifier.height(10.dp))
                    MoodCurve(points = weekData, modifier = Modifier.fillMaxWidth().height(220.dp))
                }
            }

            Spacer(Modifier.height(16.dp))

            Text("Historial de humor", color = Texto, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(10.dp))
            EmojiHistoryRow(points = weekData)
        }
    }
}

/* ====== UI Components ====== */
@Composable
private fun LegendDot(color: Color) {
    Box(
        Modifier.size(10.dp).clip(CircleShape).background(color)
    )
}

/* Clickable sin ripple */
private fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier =
    composed {
        this.then(
            Modifier.clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick() }
        )
    }

@Composable
private fun SegmentedTabs(items: List<String>, selected: Int, onChange: (Int) -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .border(1.dp, ChipStroke, RoundedCornerShape(20.dp))
            .background(ChipBg)
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        items.forEachIndexed { i, label ->
            val isSel = i == selected
            Surface(
                color = if (isSel) Texto else Color.Transparent,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(34.dp)
                    .padding(horizontal = 2.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp)
                        .noRippleClickable { onChange(i) }
                ) {
                    Text(
                        text = label,
                        color = if (isSel) Color.White else Texto,
                        fontSize = 13.sp,
                        fontWeight = if (isSel) FontWeight.SemiBold else FontWeight.Medium
                    )
                }
            }
        }
    }
}

/* ====== Curva con Bezier ====== */
@Composable
private fun MoodCurve(points: List<MoodPoint>, modifier: Modifier = Modifier) {
    Canvas(modifier) {
        val left = 16f
        val right = size.width - 16f
        val top = 14f
        val bottom = size.height - 34f
        val step = (right - left) / (points.size - 1)

        val base = points.mapIndexed { i, mp ->
            val x = left + i * step
            val y = bottom - (mp.mood.height * (bottom - top))
            Offset(x, y)
        }

        val path = Path().apply {
            moveTo(base.first().x, base.first().y)
            for (i in 1 until base.size) {
                val midX = (base[i - 1].x + base[i].x) / 2f
                cubicTo(midX, base[i - 1].y, midX, base[i].y, base[i].x, base[i].y)
            }
        }

        drawPath(path, CurveColor, style = Stroke(width = 5f, cap = StrokeCap.Round))

        // puntos con el mismo color pastel
        base.forEachIndexed { i, pt ->
            drawCircle(color = points[i].mood.color.copy(alpha = 0.95f), radius = 9f, center = pt)
        }

        val textPaint = android.graphics.Paint().apply {
            isAntiAlias = true
            textSize = 26f
            color = android.graphics.Color.parseColor("#842C46")
            textAlign = android.graphics.Paint.Align.CENTER
        }
        points.forEachIndexed { i, mp ->
            val x = left + i * step
            drawContext.canvas.nativeCanvas.drawText(mp.day, x, size.height - 6f, textPaint)
        }
    }
}

/* ====== Historial con caritas circulares (sin cuadrado) ====== */
@Composable
private fun EmojiHistoryRow(points: List<MoodPoint>) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(18.dp),
        shadowElevation = 8.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(points) { p ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                        Box(
                            modifier = Modifier
                                .size(56.dp) // más grande
                                .clip(CircleShape)
                                .background(p.mood.color.copy(alpha = 0.28f)) // halo pastel
                                .border(
                                    width = 2.dp,
                                    color = p.mood.color.copy(alpha = 0.55f), // anillo pastel
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = p.mood.imageRes),
                                contentDescription = p.mood.label,
                                modifier = Modifier
                                    .size(40.dp)        // cara
                                    .clip(CircleShape)  // recorte circular (evita cuadrado)
                            )
                        }

                        Spacer(Modifier.height(8.dp))
                        Text(p.day, color = Texto.copy(.75f), fontSize = 13.sp)
                    }
                }
            }
            Spacer(Modifier.weight(1f))
            AssistChip(
                onClick = { /* TODO */ },
                label = { Text("Noviembre", color = Texto) },
                border = BorderStroke(1.dp, ChipStroke),
                colors = AssistChipDefaults.assistChipColors(containerColor = ChipBg)
            )
        }
    }
}
