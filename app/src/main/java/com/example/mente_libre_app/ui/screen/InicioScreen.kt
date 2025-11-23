package com.example.mente_libre_app.ui.screen

import com.example.mente_libre_app.R
import androidx.compose.runtime.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt
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
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mente_libre_app.data.local.mood.MoodDao
import com.example.mente_libre_app.data.local.mood.MoodDatabase
import com.example.mente_libre_app.data.local.mood.MoodEntry
import com.example.mente_libre_app.navigation.Route
import com.example.mente_libre_app.ui.components.BottomNavigationBar
import kotlinx.coroutines.launch
import com.example.mente_libre_app.data.local.mood.Mood


/* ============================================================
   InicioScreen — Tips + Consejos Semanales
   ============================================================ */

private val Fondo = Color(0xFFFFEDF5)
private val Texto = Color(0xFF842C46)
private val Acento = Color(0xFFFF6D9B)
private val CardRosa = Color(0xFFFFDCE3)
private val CardVerde = Color(0xFF9DB25D)

private val TipBg = Color.White
private val TipOvalBg = Color(0xFFF6EEF1)
private val TipTitle = Texto

private val serifBold = FontFamily(Font(R.font.source_serif_pro_bold))
private val serifRegular = FontFamily(Font(R.font.source_serif_pro_regular))

data class Tip(val titulo: String, val imagen: Int)

/* ====== Helpers de fecha y puntaje (igual que en PuntajeScreen) ====== */

@Suppress("SimpleDateFormat")
private val ISO = SimpleDateFormat("yyyy-MM-dd", Locale("es", "ES"))

private fun Date.toIso(): String = ISO.format(this)

private fun daysAgo(d: Int): Date =
    Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -d) }.time

private fun moodFromLabel(label: String?): Mood? =
    enumValues<Mood>().firstOrNull { it.label == label }

private fun moodScore(m: Mood): Int = when (m) {
    Mood.Feliz     -> 100
    Mood.Tranquilo -> 85
    Mood.Sereno    -> 75
    Mood.Neutral   -> 60
    Mood.Enojado   -> 40
    Mood.Triste    -> 35
    Mood.Deprimido -> 25
}

/* ======================= PANTALLA INICIO ======================= */

@Composable
fun InicioScreen(
    onNavChange: (Int) -> Unit = {},
    onGoAnimo: () -> Unit = {},
    onGoPuntaje: () -> Unit = {},
    navController: NavHostController
) {
    // --- Cargar puntaje real desde Room (últimos 30 días) ---
    val context = LocalContext.current
    val dao: MoodDao = remember { MoodDatabase.getInstance(context).moodDao() }
    val scope = rememberCoroutineScope()

    var overallScore by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        scope.launch {
            val startIso = daysAgo(30).toIso()
            val endIso = Date().toIso()
            val entries: List<MoodEntry> = dao.entriesBetween(startIso, endIso)

            val scores = entries.mapNotNull { e ->
                moodFromLabel(e.moodType)?.let { moodScore(it) }
            }

            overallScore = if (scores.isEmpty()) 0 else scores.average().roundToInt()
        }
    }

    Scaffold(
        containerColor = Fondo,
        bottomBar = {
            BottomNavigationBar(
                selectedItem = "inicio",
                onItemSelected = { route ->
                    when (route) {
                        "inicio"   -> onNavChange(0)
                        "conexion" -> onNavChange(1)
                        "texto"    -> onNavChange(2)
                        "ajustes"  -> onNavChange(3)
                    }
                }
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
                fontFamily = serifBold
            )

            Spacer(Modifier.height(12.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // 👉 ahora usa el puntaje calculado
                CardScore(
                    score = overallScore,
                    onClick = onGoPuntaje,
                    modifier = Modifier
                        .weight(1f)
                        .height(156.dp)
                )
                CardMood(
                    mood = "Feliz", // luego esto saldrá del microservicio
                    values = listOf(2f, 5f, 3.5f, 6f, 4.5f, 7f),
                    onClick = onGoAnimo,
                    modifier = Modifier
                        .weight(1f)
                        .height(156.dp)
                )
            }

            Spacer(Modifier.height(26.dp))

            Text(
                text = "Tips",
                color = Texto,
                fontSize = 20.sp,
                fontFamily = serifBold,
                modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
            )
            TipsSection(
                tips = listOf(
                    Tip("Cómo Organizarse Mejor", R.drawable.organizarse),
                    Tip("¿Qué hacer en una crisis emocional?", R.drawable.crisis_emocional),
                    Tip("Estrategias para el Autocuidado", R.drawable.autocuidado)
                ),
                navController = navController
            )

            Spacer(Modifier.height(22.dp))

            Text(
                text = "Consejos Semanales",
                color = Texto,
                fontSize = 20.sp,
                fontFamily = serifBold,
                modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
            )
            WeeklyAdviceCard(
                titulo = "Salud Física",
                imagen = R.drawable.salud_fisica,
                onClick = {}
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
                imageVector = Icons.Filled.FavoriteBorder,
                contentDescription = "Foto de perfil",
                tint = Texto.copy(0.35f),
                modifier = Modifier.size(size * 0.45f)
            )
        }
    }
}

@Composable
private fun TipsSection(tips: List<Tip>, navController: NavHostController) {
    val scroll = rememberScrollState()
    Row(
        Modifier
            .fillMaxWidth()
            .horizontalScroll(scroll)
            .padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        tips.forEach { tip ->
            val onClick: () -> Unit = when (tip.titulo) {
                "Cómo Organizarse Mejor" ->
                { { navController.navigate(Route.Organizarse.path) } }

                "¿Qué hacer en una crisis emocional?" ->
                { { navController.navigate(Route.Crisis.path) } }

                "Estrategias para el Autocuidado" ->
                { { navController.navigate(Route.Estrategias.path) } }

                else -> { {} }
            }
            TipCardHorizontal(tip, onClick)
        }
    }
}

@Composable
private fun TipCardHorizontal(tip: Tip, onClick: () -> Unit) {
    Surface(
        color = TipBg,
        shape = RoundedCornerShape(18.dp),
        shadowElevation = 10.dp,
        modifier = Modifier
            .width(300.dp)
            .height(120.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
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

            Text(
                text = tip.titulo,
                color = TipTitle,
                fontSize = 16.sp,
                fontFamily = serifRegular,
                lineHeight = 18.sp,
                maxLines = 3,
                overflow = TextOverflow.Clip,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun WeeklyAdviceCard(titulo: String, imagen: Int, onClick: () -> Unit) {
    Surface(
        color = TipBg,
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
                fontFamily = serifRegular,
                textAlign = TextAlign.Center,
                maxLines = 2
            )
        }
    }
}

/* =================== Métricas =================== */

@Composable
private fun CardScore(score: Int, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Surface(
        color = CardVerde,
        shape = RoundedCornerShape(20.dp),
        modifier = modifier.clickable { onClick() }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Puntaje", color = Color.White, fontFamily = serifBold)
                Icon(Icons.Filled.FavoriteBorder, contentDescription = null, tint = Color.White)
            }
            Spacer(Modifier.height(2.dp))
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                CircularScore(value = score, stroke = 8f, color = Color.White, size = 72.dp)
            }
            Text(
                "Salud",
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontFamily = serifBold
            )
        }
    }
}

@Composable
private fun CardMood(
    mood: String,
    values: List<Float>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = CardRosa,
        shape = RoundedCornerShape(20.dp),
        modifier = modifier.clickable { onClick() }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Ánimo", color = Texto, fontFamily = serifBold)
                    Text(mood, color = Texto, fontSize = 14.sp, fontFamily = serifRegular)
                }
                Text("ⓘ", color = Texto.copy(alpha = 0.7f), fontFamily = serifBold)
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
                startAngle = 135f,
                sweepAngle = 270f,
                useCenter = false,
                style = Stroke(width = stroke, cap = StrokeCap.Round)
            )
            val sweep = (value.coerceIn(0, 100) / 100f) * 270f
            drawArc(
                color,
                startAngle = 135f,
                sweepAngle = sweep,
                useCenter = false,
                style = Stroke(width = stroke, cap = StrokeCap.Round)
            )
        }
        Text("$value", color = Color.White, fontSize = 20.sp, fontFamily = serifBold)
    }
}

@Composable
private fun MiniBarChart(values: List<Float>, barWidth: Dp, color: Color) {
    val max = (values.maxOrNull() ?: 1f)
    Canvas(
        Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
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

@Preview(showBackground = true, backgroundColor = 0xFFF4F4F4)
@Composable
private fun PreviewInicioScreen() {
    val navController = rememberNavController()
    MaterialTheme {
        InicioScreen(navController = navController)
    }
}
