package com.example.mente_libre_app.ui.screen

import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mente_libre_app.data.local.mood.Mood
import com.example.mente_libre_app.data.local.mood.MoodDao
import com.example.mente_libre_app.data.local.mood.MoodDatabase
import com.example.mente_libre_app.data.local.mood.MoodEntry
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

/* ====== PALETA (colores de tu app) ====== */
private val Fondo = Color(0xFFFFEDF5)
private val Texto = Color(0xFF842C46)
private val PuntajeGreen = Color(0xFF9DB25D) // Verde pastel igual al de Inicio
private val CardBg = Color.White
private val TrendPastel = Color(0xFFF8EAF0)
private val TrendBar = Color(0xFFFFB6C1)
private val DividerPink = Color(0xFFFF1493)

/* ====== FORMATO DE FECHAS ====== */
@Suppress("SimpleDateFormat")
private val ISO = SimpleDateFormat("yyyy-MM-dd", Locale("es", "ES"))
@Suppress("SimpleDateFormat")
private val HUMAN_DAY = SimpleDateFormat("EEE d 'de' MMM", Locale("es", "ES"))
private fun Date.toIso(): String = ISO.format(this)
private fun daysAgo(d: Int): Date =
    Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -d) }.time

/* ====== ENUM MOOD (de tu proyecto) ====== */
private fun moodFromLabel(label: String?): Mood? =
    enumValues<Mood>().firstOrNull { it.label == label }

private fun moodScore(m: Mood): Int = when (m) {
    Mood.Feliz -> 100
    Mood.Tranquilo -> 85
    Mood.Sereno -> 75
    Mood.Neutral -> 60
    Mood.Enojado -> 40
    Mood.Triste -> 35
    Mood.Deprimido -> 25
}

private fun mensajeSalud(percentage: Int): String = when {
    percentage >= 90 -> "¡Excelente! Tu bienestar mental está en su punto más alto ✨"
    percentage >= 75 -> "Muy bien 😊 Estás mentalmente saludable"
    percentage >= 60 -> "Bien 👍 Mantén el equilibrio y cuida de ti"
    percentage >= 40 -> "Atención ⚠️ podrías estar algo estresado, tómate un respiro"
    else -> "Cuidado 💭 sería recomendable hablar con un especialista"
}

/* =========================================================================
   PANTALLA DE PUNTAJE
   ========================================================================= */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PuntajeScreen(
    onBack: () -> Unit,
    diasAnalisis: Int = 30
) {
    val context = LocalContext.current
    val dao: MoodDao = remember { MoodDatabase.getInstance(context).moodDao() }
    val scope = rememberCoroutineScope()

    var entries by remember { mutableStateOf<List<MoodEntry>>(emptyList()) }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(diasAnalisis) {
        scope.launch {
            val startIso = daysAgo(diasAnalisis).toIso()
            val endIso = Date().toIso()
            entries = dao.entriesBetween(startIso, endIso)
        }
    }

    val scores = remember(entries) {
        entries.mapNotNull { e -> moodFromLabel(e.moodType)?.let { moodScore(it) } }
    }
    val overall = remember(scores) { if (scores.isEmpty()) 0 else scores.average().roundToInt() }
    val mensaje = remember(overall) { mensajeSalud(overall) }

    Scaffold(
        containerColor = Fondo,
        topBar = {
            TopAppBar(
                title = { Text("Puntaje", color = Texto, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver", tint = Texto)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Fondo)
            )
        }
    ) { padding ->

        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            // ======== HEADER (verde pastel) ========
            Surface(
                shape = RoundedCornerShape(22.dp),
                color = PuntajeGreen,
                shadowElevation = 10.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 18.dp, vertical = 18.dp)
                ) {
                    Text("Puntaje", color = Color.White.copy(alpha = .95f), fontSize = 18.sp)
                    Text(
                        "$overall%",
                        color = Color.White,
                        fontSize = 64.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Text(
                        "Salud mental (últimos $diasAnalisis días)",
                        color = Color.White.copy(alpha = .92f),
                        fontSize = 15.sp
                    )
                    Spacer(Modifier.height(10.dp))
                    Surface(
                        shape = CircleShape,
                        color = Color.White, // círculo blanco
                        shadowElevation = 8.dp,
                        modifier = Modifier.clickable { showDialog = true }
                    ) {
                        Text(
                            "?",
                            color = Texto,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                }
            }

            // ======== MENSAJE ========
            Text(
                mensaje,
                color = Texto,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            // ======== TENDENCIA RECIENTE ========
            Surface(
                color = CardBg,
                shape = RoundedCornerShape(18.dp),
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Tendencia reciente", color = Texto, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(12.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(TrendPastel),
                        contentAlignment = Alignment.Center
                    ) {
                        BarsFromEntries(entries = entries, barCount = 7, barColor = TrendBar)
                    }
                }
            }

            // ======== HISTORIAL ========
            Text(
                "Historial de puntuación",
                color = Texto,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            if (entries.isEmpty()) {
                Surface(
                    color = CardBg, shape = RoundedCornerShape(14.dp), shadowElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Aún no hay datos suficientes. Registra tus estados en la pantalla de Ánimo.",
                        color = Texto.copy(.7f), fontSize = 14.sp, modifier = Modifier.padding(14.dp)
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(entries.sortedByDescending { it.date }) { e ->
                        val mood = moodFromLabel(e.moodType)
                        val s = mood?.let { moodScore(it) } ?: 0
                        HistoryRow(
                            leftLabel = HUMAN_DAY.format(ISO.parse(e.date)!!),
                            title = mood?.label ?: "Sin registro",
                            score = s,
                            chipColor = (mood?.color ?: Texto.copy(.3f))
                        )
                    }
                }
            }

            // ======== DIALOGO DE INFORMACIÓN ========
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    confirmButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text("Entendido", color = Texto)
                        }
                    },
                    title = {
                        Text(
                            "¿Qué es este puntaje?",
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    },
                    text = {
                        Text(
                            "El puntaje es un promedio de tus registros de ánimo de los últimos 30 días.\n\n" +
                                    "• Estados positivos (Feliz, Tranquilo, Sereno) suman más.\n" +
                                    "• Estados neutros o negativos restan.\n\n" +
                                    "La sección “Tendencia reciente” muestra cómo han variado tus últimos registros.",
                            color = Color.Black
                        )
                    },
                    containerColor = Color(0xFFFFD9E3), // Rosado pastel más notorio
                    shape = RoundedCornerShape(24.dp)
                )
            }
        }
    }
}

/* ======================= COMPONENTES ======================= */

// Barras (últimas N entradas)
@Composable
private fun BarsFromEntries(
    entries: List<MoodEntry>,
    barCount: Int,
    barColor: Color
) {
    val recent = remember(entries, barCount) { entries.sortedBy { it.date }.takeLast(barCount) }
    val values = remember(recent) {
        recent.mapNotNull { moodFromLabel(it.moodType)?.let(::moodScore) }
    }
    val max = (values.maxOrNull() ?: 100).coerceAtLeast(10)

    Row(
        Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        values.forEach { v ->
            val h = (v / max.toFloat()).coerceIn(0f, 1f)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(10.dp))
                    .background(barColor.copy(alpha = 0.18f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(h)
                        .align(Alignment.BottomCenter)
                        .clip(RoundedCornerShape(10.dp))
                        .background(barColor)
                )
            }
        }
    }
}

@Composable
private fun HistoryRow(
    leftLabel: String,
    title: String,
    score: Int,
    chipColor: Color
) {
    Surface(
        color = CardBg,
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 6.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp)
        ) {
            // Fecha compacta
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.width(92.dp)
            ) {
                val parts = leftLabel.split(' ')
                Text(parts.firstOrNull() ?: "", color = Texto.copy(.7f), fontSize = 13.sp)
                Text(
                    parts.drop(1).joinToString(" "),
                    color = Texto,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp
                )
            }

            Column(Modifier.weight(1f)) {
                Text(title, color = Texto, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(4.dp))
                LinearProgressIndicator(
                    progress = score / 100f,
                    trackColor = chipColor.copy(.20f),
                    color = chipColor,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }

            Spacer(Modifier.width(12.dp))

            RingProgress(
                percentage = score,
                size = 50.dp,
                stroke = 6.dp,
                color = chipColor
            )
        }
    }
}

@Composable
private fun RingProgress(
    percentage: Int,
    size: Dp,
    stroke: Dp,
    color: Color
) {
    Box(
        modifier = Modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Canvas(Modifier.size(size)) {
            // track
            drawArc(
                color = color.copy(.2f),
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = stroke.toPx(), cap = StrokeCap.Round),
                size = Size(size.toPx(), size.toPx())
            )
            // progress
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = (percentage.coerceIn(0, 100) / 100f) * 360f,
                useCenter = false,
                style = Stroke(width = stroke.toPx(), cap = StrokeCap.Round),
                size = Size(size.toPx(), size.toPx())
            )
        }
        Text("$percentage", color = Texto, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
    }
}
