package com.example.mente_libre_app.ui.screen

import androidx.compose.ui.graphics.toArgb
import android.content.Context
import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mente_libre_app.R
import com.example.mente_libre_app.data.local.mood.MoodDao
import com.example.mente_libre_app.data.local.mood.MoodDatabase
import com.example.mente_libre_app.data.local.mood.MoodEntry
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.ceil
import kotlin.math.roundToInt

/* ========================= PALETA ========================= */
private val Fondo = Color(0xFFFFEDF5)
private val Texto = Color(0xFF842C46)
private val ChipBg = Color(0xFFFFFFFF)
private val ChipStroke = Texto.copy(0.25f)
private val CurveColor = Color(0xFF734B3A)
private val MoodGreen = Color(0xFFA6E7A6)

/* =================== ENUM DE ESTADOS ===================== */
enum class Mood(
    val label: String,
    val imageRes: Int,
    val color: Color,
    val height: Float,   // altura relativa para el gráfico (0..1)
    val desc: String
) {
    Feliz     ("Feliz",     R.drawable.cara_feliz,     Color(0xFFA6E7A6), 0.80f, "Ánimo alto, energía y motivación."),
    Tranquilo ("Tranquilo", R.drawable.cara_tranquilo, Color(0xFFFFF1A6), 0.70f, "Calma, equilibrio y serenidad."),
    Sereno    ("Sereno",    R.drawable.cara_sereno,    Color(0xFFAEDCFF), 0.60f, "Ligero bienestar, sin estrés notable."),
    Neutral   ("Neutral",   R.drawable.cara_neutral,   Color(0xFFD9D9D9), 0.50f, "Sin emociones fuertes, estable."),
    Enojado   ("Enojado",   R.drawable.cara_enojado,   Color(0xFFFFB3B3), 0.40f, "Irritación, molestia o frustración."),
    Triste    ("Triste",    R.drawable.cara_triste,    Color(0xFFCFB8FF), 0.35f, "Bajo ánimo, decaimiento o nostalgia."),
    Deprimido ("Deprimido", R.drawable.cara_deprimido, Color(0xFFB3C7FF), 0.25f, "Ánimo muy bajo; busca apoyo si persiste.")
}

/* =================== FECHAS ===================== */
@Suppress("SimpleDateFormat")
private val ISO = SimpleDateFormat("yyyy-MM-dd", Locale("es", "ES"))
@Suppress("SimpleDateFormat")
private val DAY_SHORT = SimpleDateFormat("EEE", Locale("es", "ES"))
@Suppress("SimpleDateFormat")
private val MONTH_FULL = SimpleDateFormat("MMMM", Locale("es", "ES"))
@Suppress("SimpleDateFormat")
private val MONTH_ABBR = SimpleDateFormat("MMM", Locale("es", "ES"))  // abreviatura mes
@Suppress("SimpleDateFormat")
private val HUMAN = SimpleDateFormat("EEE d 'de' MMM", Locale("es","ES"))

private fun Date.toIso(): String = ISO.format(this)
private fun isoToDate(s: String): Date = ISO.parse(s)!!
private fun todayIso(): String = ISO.format(Date())

private fun calOf(date: Date): Calendar = Calendar.getInstance().apply {
    time = date
    set(Calendar.HOUR_OF_DAY, 0); set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0)
}
private fun addDays(date: Date, days: Int): Date = calOf(date).apply { add(Calendar.DAY_OF_YEAR, days) }.time
private fun daysBetweenInclusive(start: Date, end: Date): List<Date> {
    val out = mutableListOf<Date>(); var d = start
    while (!d.after(end)) { out += d; d = addDays(d, 1) }
    return out
}
private fun startOfWeekMonday(date: Date): Date {
    val c = calOf(date); val dow = c.get(Calendar.DAY_OF_WEEK)
    val mondayOffset = when (dow) { Calendar.MONDAY->0; Calendar.SUNDAY->-6; else-> Calendar.MONDAY - dow }
    c.add(Calendar.DAY_OF_YEAR, mondayOffset); return c.time
}
private fun endOfWeekMonday(date: Date): Date = addDays(startOfWeekMonday(date), 6)
private fun startOfMonth(date: Date): Date = calOf(date).apply { set(Calendar.DAY_OF_MONTH, 1) }.time
private fun endOfMonth(date: Date): Date = calOf(date).apply { set(Calendar.DAY_OF_MONTH, getActualMaximum(Calendar.DAY_OF_MONTH)) }.time
private fun startOfYear(date: Date): Date = calOf(date).apply { set(Calendar.DAY_OF_YEAR, 1) }.time
private fun endOfYear(date: Date): Date = calOf(date).apply { set(Calendar.MONTH, Calendar.DECEMBER); set(Calendar.DAY_OF_MONTH, 31) }.time
private fun daysAgo(days: Int): Date = addDays(Date(), -days)

/** Pair<startIso, endIso> para el período seleccionado */
private fun periodRangeIso(periodIndex: Int, anchor: Date): Pair<String, String> {
    val (start, end) = when (periodIndex) {
        0 -> anchor to anchor
        1 -> startOfWeekMonday(anchor) to endOfWeekMonday(anchor)
        2 -> startOfMonth(anchor) to endOfMonth(anchor)
        else -> startOfYear(anchor) to endOfYear(anchor)
    }
    return start.toIso() to end.toIso()
}

/* Estado de mes para calendario / tiras */
private data class MonthState(var year: Int, var monthZeroBased: Int) {
    fun toCalendarFirstDay(): Calendar = Calendar.getInstance().apply {
        set(Calendar.YEAR, year); set(Calendar.MONTH, monthZeroBased); set(Calendar.DAY_OF_MONTH, 1)
        set(Calendar.HOUR_OF_DAY, 0); set(Calendar.MINUTE, 0); set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0)
    }
    fun title(): String {
        val c = toCalendarFirstDay()
        return MONTH_FULL.format(c.time).replaceFirstChar { it.titlecase(Locale("es", "ES")) } + " ${c.get(Calendar.YEAR)}"
    }
    fun daysInMonth(): Int = toCalendarFirstDay().getActualMaximum(Calendar.DAY_OF_MONTH)
    fun leadingEmptySlotsMonday(): Int {
        val dow = toCalendarFirstDay().get(Calendar.DAY_OF_WEEK)
        return when (dow) { Calendar.MONDAY->0; Calendar.SUNDAY->6; else-> dow - Calendar.MONDAY }
    }
    fun atDay(day: Int): Date = toCalendarFirstDay().apply { set(Calendar.DAY_OF_MONTH, day) }.time
    fun prev() = MonthState(if (monthZeroBased==0) year-1 else year, if (monthZeroBased==0) 11 else monthZeroBased-1)
    fun next() = MonthState(if (monthZeroBased==11) year+1 else year, if (monthZeroBased==11) 0 else monthZeroBased+1)
}

/* =================== MODELO P/ GRÁFICO ===================== */
data class MoodPoint(val dateIso: String, val mood: Mood?)

/* =================== MAPEO GUARDADO → ENUM ===================== */
private fun moodFromLabel(label: String?): Mood? =
    enumValues<Mood>().firstOrNull { it.label == label }

/* =================== PANTALLA ===================== */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimoScreen(onBack: (() -> Unit)? = null) {

    var selectedPeriod by remember { mutableStateOf(0) }        // 0 día, 1 semana, 2 mes, 3 año
    var showHelp by remember { mutableStateOf(false) }
    var selectedMood by remember { mutableStateOf<Mood?>(null) }

    // Ancla temporal y mes visible
    var anchorDate by remember { mutableStateOf(Date()) }
    val nowCal = remember { Calendar.getInstance() }
    var currentMonth by remember {
        mutableStateOf(MonthState(nowCal.get(Calendar.YEAR), nowCal.get(Calendar.MONTH)))
    }

    // Nombres / menús para Mes/Año
    val monthNames = remember {
        (0..11).map { m ->
            Calendar.getInstance().apply { set(Calendar.MONTH, m); set(Calendar.DAY_OF_MONTH, 1) }.time.let {
                MONTH_FULL.format(it).replaceFirstChar { c -> c.titlecase(Locale("es","ES")) }
            }
        }
    }
    var monthMenuExpanded by remember { mutableStateOf(false) }
    var yearMenuExpanded by remember { mutableStateOf(false) }
    val yearList = remember { (nowCal.get(Calendar.YEAR) downTo nowCal.get(Calendar.YEAR) - 10).toList() }

    // BD (Room)
    val context = LocalContext.current
    val dao: MoodDao = remember { MoodDatabase.getInstance(context).moodDao() }
    val scope = rememberCoroutineScope()

    var periodEntries by remember { mutableStateOf<List<MoodEntry>>(emptyList()) }
    var monthEntries by remember { mutableStateOf<List<MoodEntry>>(emptyList()) }
    var recentEntries by remember { mutableStateOf<List<MoodEntry>>(emptyList()) }

    // Sincroniza anchorDate cuando cambian pestaña o currentMonth
    LaunchedEffect(selectedPeriod, currentMonth) {
        val c = Calendar.getInstance().apply {
            set(Calendar.YEAR, currentMonth.year)
            set(Calendar.MONTH, currentMonth.monthZeroBased)
            when (selectedPeriod) {
                0, 1 -> time = Date()
                2 -> set(Calendar.DAY_OF_MONTH, 15)
                3 -> { set(Calendar.MONTH, Calendar.JANUARY); set(Calendar.DAY_OF_MONTH, 1) }
            }
            set(Calendar.HOUR_OF_DAY, 0); set(Calendar.MINUTE, 0); set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0)
        }
        anchorDate = c.time
    }

    // Cargar datos del período del gráfico
    LaunchedEffect(selectedPeriod, anchorDate) {
        val (startIso, endIso) = periodRangeIso(selectedPeriod, anchorDate)
        periodEntries = dao.entriesBetween(startIso, endIso)
    }

    // Cargar datos del mes para la tira
    LaunchedEffect(currentMonth) {
        val startIso = currentMonth.atDay(1).toIso()
        val endIso = currentMonth.atDay(currentMonth.daysInMonth()).toIso()
        monthEntries = dao.entriesBetween(startIso, endIso)
    }

    // Historial reciente (últimos 90 días)
    LaunchedEffect(Unit) {
        val startIso = daysAgo(90).toIso()
        val endIso = todayIso()
        recentEntries = dao.entriesBetween(startIso, endIso)
    }

    // Puntos del gráfico
    val pointsForChart = remember(periodEntries, selectedPeriod, anchorDate) {
        val (startIso, endIso) = periodRangeIso(selectedPeriod, anchorDate)
        val days = daysBetweenInclusive(isoToDate(startIso), isoToDate(endIso))
        days.map { day ->
            val dayIso = day.toIso()
            val entry = periodEntries.firstOrNull { it.date == dayIso }
            MoodPoint(dayIso, moodFromLabel(entry?.moodType))
        }
    }

    // ---------- Lanzadores para crear documento PDF ----------
    var pendingPdfData by remember {
        mutableStateOf<Triple<String, Pair<List<MoodPoint>, List<MoodEntry>>, Boolean>?>(null)
    }
    val createPdfLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/pdf")
    ) { uri ->
        val triple = pendingPdfData
        if (uri != null && triple != null) {
            val (title, pair, showMonthSeparators) = triple
            val (points, entries) = pair
            exportMoodPdf(
                context = context,
                uri = uri,
                title = title,
                subtitle = "Resumen generado automáticamente",
                points = points,
                entries = entries,
                showMonthSeparators = showMonthSeparators
            )
        }
        pendingPdfData = null
    }

    Scaffold(
        containerColor = Fondo,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Estados de ánimo", color = Texto, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    onBack?.let {
                        IconButton(onClick = it) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Volver", tint = Texto)
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Fondo)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showHelp = true },
                containerColor = Texto,
                shape = RoundedCornerShape(20.dp)
            ) { Text("? ", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold) }
        }
    ) { padding ->

        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text("Ve tu estado de ánimo a lo largo de tu día.", color = Texto.copy(.75f), fontSize = 14.sp)
            Spacer(Modifier.height(12.dp))

            MoodPeriodTabs(
                options = listOf("Días", "Semanas", "Meses", "Años"),
                selectedIndex = selectedPeriod,
                onSelected = { selectedPeriod = it }
            )

            // Selectores para Mes/Año
            if (selectedPeriod == 2) {
                Spacer(Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color.White)
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text("Mes", color = Texto, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
                    TextButton(onClick = { yearMenuExpanded = true }) {
                        Text("${currentMonth.year}", color = Texto); Icon(Icons.Filled.ArrowDropDown, null, tint = Texto)
                    }
                    DropdownMenu(expanded = yearMenuExpanded, onDismissRequest = { yearMenuExpanded = false }) {
                        yearList.forEach { y ->
                            DropdownMenuItem(text = { Text(y.toString()) }, onClick = {
                                yearMenuExpanded = false
                                currentMonth = MonthState(y, currentMonth.monthZeroBased)
                            })
                        }
                    }
                    Spacer(Modifier.width(6.dp))
                    TextButton(onClick = { monthMenuExpanded = true }) {
                        Text(monthNames[currentMonth.monthZeroBased], color = Texto); Icon(Icons.Filled.ArrowDropDown, null, tint = Texto)
                    }
                    DropdownMenu(expanded = monthMenuExpanded, onDismissRequest = { monthMenuExpanded = false }) {
                        monthNames.forEachIndexed { idx, name ->
                            DropdownMenuItem(text = { Text(name) }, onClick = {
                                monthMenuExpanded = false
                                currentMonth = MonthState(currentMonth.year, idx)
                            })
                        }
                    }
                }
            } else if (selectedPeriod == 3) {
                Spacer(Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color.White)
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text("Año", color = Texto, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
                    TextButton(onClick = { yearMenuExpanded = true }) {
                        Text("${currentMonth.year}", color = Texto); Icon(Icons.Filled.ArrowDropDown, null, tint = Texto)
                    }
                    DropdownMenu(expanded = yearMenuExpanded, onDismissRequest = { yearMenuExpanded = false }) {
                        yearList.forEach { y ->
                            DropdownMenuItem(text = { Text(y.toString()) }, onClick = {
                                yearMenuExpanded = false
                                currentMonth = MonthState(y, currentMonth.monthZeroBased)
                            })
                        }
                    }
                }
            }

            Spacer(Modifier.height(14.dp))

            // -------- Tarjeta con gráfico --------
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

                    if (pointsForChart.all { it.mood == null }) {
                        Box(Modifier.fillMaxWidth().height(220.dp), contentAlignment = Alignment.Center) {
                            Text("Sin datos en este período", color = Texto.copy(.55f))
                        }
                    } else {
                        // ← deslizable en Mes/Año o si hay muchos puntos
                        val needsScroll = selectedPeriod >= 2 || pointsForChart.size > 12
                        val showSeparators = selectedPeriod == 3
                        MoodCurveScrollable(
                            points = pointsForChart,
                            scroll = needsScroll,
                            showMonthSeparators = showSeparators
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // -------- Registro rápido de hoy --------
            Text("Registra tu estado de ánimo", color = Texto, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(10.dp))

            MoodPickerRow(selected = selectedMood, onSelect = { selectedMood = it })

            Spacer(Modifier.height(10.dp))
            Button(
                onClick = {
                    selectedMood?.let { m ->
                        scope.launch {
                            val today = todayIso()
                            val existing = dao.entryOn(today)
                            if (existing == null) {
                                dao.insert(MoodEntry(moodType = m.label, date = today))
                            } else {
                                dao.insert(existing.copy(moodType = m.label))
                            }
                            // refrescos
                            val (startIso, endIso) = periodRangeIso(selectedPeriod, anchorDate)
                            periodEntries = dao.entriesBetween(startIso, endIso)
                            monthEntries = dao.entriesBetween(
                                currentMonth.atDay(1).toIso(),
                                currentMonth.atDay(currentMonth.daysInMonth()).toIso()
                            )
                            val recentStart = daysAgo(90).toIso()
                            recentEntries = dao.entriesBetween(recentStart, todayIso())
                            selectedMood = null
                        }
                    }
                },
                enabled = selectedMood != null,
                colors = ButtonDefaults.buttonColors(containerColor = Texto),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) { Text("Guardar estado", color = Color.White) }

            Spacer(Modifier.height(18.dp))

            // -------- Exportar PDF (Mes / Año) --------
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF1493)), // rosado brilloso
                    onClick = {
                        scope.launch {
                            // Datos del mes actual seleccionado
                            val startIso = currentMonth.atDay(1).toIso()
                            val endIso = currentMonth.atDay(currentMonth.daysInMonth()).toIso()
                            val entries = dao.entriesBetween(startIso, endIso)
                            val days = daysBetweenInclusive(isoToDate(startIso), isoToDate(endIso))
                            val points = days.map { d ->
                                val e = entries.firstOrNull { it.date == d.toIso() }
                                MoodPoint(d.toIso(), moodFromLabel(e?.moodType))
                            }
                            val fileName = "MenteLibre_${MONTH_FULL.format(currentMonth.atDay(1))}_${currentMonth.year}.pdf"
                            pendingPdfData = Triple(
                                "Registro de Ánimo - ${MONTH_FULL.format(currentMonth.atDay(1)).replaceFirstChar { it.titlecase(Locale("es","ES")) }} ${currentMonth.year}",
                                Pair(points, entries),
                                false // separadores no necesarios para 1 mes
                            )
                            createPdfLauncher.launch(fileName)
                        }
                    }
                ) { Text("Exportar PDF (Mes)") }

                Button(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF1493)),
                    onClick = {
                        scope.launch {
                            // Datos del año actual seleccionado en currentMonth.year
                            val c = Calendar.getInstance().apply {
                                set(Calendar.YEAR, currentMonth.year)
                                set(Calendar.MONTH, Calendar.JANUARY)
                                set(Calendar.DAY_OF_MONTH, 1)
                            }
                            val start = startOfYear(c.time)
                            val end = endOfYear(c.time)
                            val startIso = start.toIso()
                            val endIso = end.toIso()
                            val entries = dao.entriesBetween(startIso, endIso)
                            val days = daysBetweenInclusive(start, end)
                            val points = days.map { d ->
                                val e = entries.firstOrNull { it.date == d.toIso() }
                                MoodPoint(d.toIso(), moodFromLabel(e?.moodType))
                            }
                            val fileName = "MenteLibre_Anio_${currentMonth.year}.pdf"
                            pendingPdfData = Triple(
                                "Registro de Ánimo - Año ${currentMonth.year}",
                                Pair(points, entries),
                                true // separadores de inicio de mes
                            )
                            createPdfLauncher.launch(fileName)
                        }
                    }
                ) { Text("Exportar PDF (Año)") }
            }

            Spacer(Modifier.height(18.dp))

            // -------- Tira de Historial mensual --------
            MonthHistoryStrip(
                monthState = currentMonth,
                entries = monthEntries,
                onSelectMonth = { idx -> currentMonth = MonthState(currentMonth.year, idx) },
                onSelectYear  = { y   -> currentMonth = MonthState(y, currentMonth.monthZeroBased) }
            )

            Spacer(Modifier.height(16.dp))

            // Historial reciente
            RecentHistory(entries = recentEntries, title = "Historial reciente")
        }
    }

    // -------- Hoja de ayuda --------
    if (showHelp) {
        ModalBottomSheet(
            onDismissRequest = { showHelp = false },
            containerColor = Color.White,
            dragHandle = { BottomSheetDefaults.DragHandle(color = Texto.copy(alpha = .35f)) }
        ) {
            Column(Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
                Text("¿Qué significa cada carita?", color = Texto, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                ) {
                    items(enumValues<Mood>().toList()) { m ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(m.color.copy(alpha = 0.12f))
                                .padding(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(CircleShape)
                                    .background(m.color.copy(alpha = 0.28f))
                                    .border(2.dp, m.color.copy(alpha = 0.55f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(m.imageRes),
                                    contentDescription = m.label,
                                    modifier = Modifier
                                        .size(34.dp)
                                        .clip(CircleShape)
                                )
                            }
                            Spacer(Modifier.width(12.dp))
                            Column(Modifier.weight(1f)) {
                                Text(m.label, color = Texto, fontWeight = FontWeight.SemiBold)
                                Text(m.desc, color = Texto.copy(.8f), fontSize = 13.sp)
                            }
                        }
                    }
                }
                Button(
                    onClick = { showHelp = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Texto),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Entendido", color = Color.White) }
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}

/* ===================== COMPONENTES UI ===================== */

@Composable
private fun MoodPeriodTabs(
    options: List<String>,
    selectedIndex: Int,
    onSelected: (Int) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .border(1.dp, ChipStroke, RoundedCornerShape(20.dp))
            .background(ChipBg)
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        options.forEachIndexed { i, label ->
            val isSel = i == selectedIndex
            Surface(
                color = if (isSel) Texto else Color.Transparent,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(34.dp)
                    .padding(horizontal = 2.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { onSelected(i) }
            ) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
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

@Composable
private fun LegendDot(color: Color) {
    Box(Modifier.size(10.dp).clip(CircleShape).background(color))
}

/* === Picker de caritas en LazyRow (scrollable) === */
@Composable
private fun MoodPickerRow(
    selected: Mood?,
    onSelect: (Mood) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(enumValues<Mood>()) { m ->
            val sel = m == selected
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(88.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(if (sel) 72.dp else 64.dp)
                        .clip(CircleShape)
                        .background(m.color.copy(alpha = if (sel) 0.20f else 0.12f))
                        .border(width = if (sel) 3.dp else 2.dp, color = m.color, shape = CircleShape)
                        .clickable { onSelect(m) },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = m.imageRes),
                        contentDescription = m.label,
                        modifier = Modifier
                            .size(if (sel) 46.dp else 40.dp)
                            .clip(CircleShape)
                    )
                }
                Spacer(Modifier.height(8.dp))
                Text(
                    m.label,
                    color = if (sel) Texto else Texto.copy(alpha = 0.70f),
                    fontSize = 14.sp,
                    fontWeight = if (sel) FontWeight.SemiBold else FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

/* Gráfico Bezier + puntos; salta días sin dato
   ➜ AHORA: separadores y etiquetas de mes en rosado brilloso cuando showMonthSeparators = true */
@Composable
private fun MoodCurve(
    points: List<MoodPoint>,
    modifier: Modifier = Modifier,
    showMonthSeparators: Boolean = false
) {
    Canvas(modifier) {
        val left = 16f
        val right = size.width - 16f
        val top = 14f
        val bottom = size.height - 34f
        val step = if (points.size > 1) (right - left) / (points.size - 1) else 0f

        val coords = points.mapIndexed { i, mp ->
            val x = left + i * step
            val mood = mp.mood
            val y = if (mood != null) bottom - (mood.height * (bottom - top)) else Float.NaN
            Offset(x, y)
        }

        val valid = coords.withIndex().filter { !it.value.y.isNaN() }
        if (valid.size >= 2) {
            val path = Path().apply {
                moveTo(valid.first().value.x, valid.first().value.y)
                for (i in 1 until valid.size) {
                    val p0 = valid[i - 1].value
                    val p1 = valid[i].value
                    val midX = (p0.x + p1.x) / 2f
                    cubicTo(midX, p0.y, midX, p1.y, p1.x, p1.y)
                }
            }
            drawPath(path, CurveColor, style = Stroke(width = 5f, cap = StrokeCap.Round))
        }

        valid.forEach { v ->
            val mood = points[v.index].mood!!
            drawCircle(color = mood.color.copy(alpha = 0.95f), radius = 9f, center = v.value)
        }

        // Etiquetas inferior (días cortos)
        val paint = android.graphics.Paint().apply {
            isAntiAlias = true; textSize = 26f
            color = android.graphics.Color.parseColor("#842C46")
            textAlign = android.graphics.Paint.Align.CENTER
        }
        val stepX = if (points.size > 1) (size.width - 32f) / (points.size - 1) else 0f
        points.forEachIndexed { i, mp ->
            val d = isoToDate(mp.dateIso)
            val label = DAY_SHORT.format(d).replace(".", "").replaceFirstChar { it.titlecase(Locale("es", "ES")) }
            drawContext.canvas.nativeCanvas.drawText(label, 16f + i * stepX, size.height - 6f, paint)
        }

        // ---------- Separadores + etiquetas de mes (ROSADO BRILLOSO) ----------
        if (showMonthSeparators && points.isNotEmpty()) {
            val deepPink = "#FF1493"
            val linePaint = android.graphics.Paint().apply {
                isAntiAlias = true
                strokeWidth = 3f
                color = android.graphics.Color.parseColor(deepPink)
                setShadowLayer(6f, 0f, 0f, android.graphics.Color.parseColor("#FFFF80C0")) // halo rosado
            }
            val monthTextPaint = android.graphics.Paint().apply {
                isAntiAlias = true
                textSize = 22f
                color = android.graphics.Color.parseColor(deepPink)
                textAlign = android.graphics.Paint.Align.CENTER
                isFakeBoldText = true
            }
            val yLabel = (top - 6f).coerceAtLeast(10f)

            points.forEachIndexed { i, mp ->
                val date = isoToDate(mp.dateIso)
                val cal = calOf(date)
                if (cal.get(Calendar.DAY_OF_MONTH) == 1) {
                    val x = left + i * step
                    drawContext.canvas.nativeCanvas.drawLine(x, top, x, bottom + 22f, linePaint)
                    val label = MONTH_ABBR.format(date).replace(".", "")
                        .replaceFirstChar { it.titlecase(Locale("es","ES")) }
                    drawContext.canvas.nativeCanvas.drawText(label, x, yLabel, monthTextPaint)
                }
            }
        }
    }
}

/* === Envoltura scrollable para el gráfico ===
   ➜ pasa showMonthSeparators hacia adentro */
@Composable
private fun MoodCurveScrollable(
    points: List<MoodPoint>,
    scroll: Boolean,
    showMonthSeparators: Boolean = false
) {
    val height = 220.dp
    if (scroll) {
        val ticks = (points.size - 1).coerceAtLeast(1)
        val totalWidth = ((48f * ticks) + 32f).dp  // ~48dp por tick + márgenes laterales
        Box(Modifier.horizontalScroll(rememberScrollState())) {
            MoodCurve(
                points = points,
                showMonthSeparators = showMonthSeparators,
                modifier = Modifier
                    .width(totalWidth.coerceAtLeast(320.dp))
                    .height(height)
            )
        }
    } else {
        MoodCurve(
            points = points,
            showMonthSeparators = showMonthSeparators,
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
        )
    }
}

/* ======= Calendario mensual (opcional) ======= */
@Composable
private fun CalendarMonth(
    monthState: MonthState,
    entries: List<MoodEntry>,
    onPrev: () -> Unit,
    onNext: () -> Unit
) {
    val byDate = remember(entries) { entries.associate { isoToDate(it.date) to moodFromLabel(it.moodType) } }
    val daysInMonth = monthState.daysInMonth()
    val leading = monthState.leadingEmptySlotsMonday()

    Surface(
        color = Color.White,
        shape = RoundedCornerShape(18.dp),
        shadowElevation = 8.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(14.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Text(
                    monthState.title(), color = Texto, fontSize = 18.sp, fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f)
                )
                TextButton(onClick = onPrev) { Text("◀") }
                TextButton(onClick = onNext) { Text("▶") }
            }
            Spacer(Modifier.height(6.dp))

            val days = listOf("Lun","Mar","Mié","Jue","Vie","Sáb","Dom")
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                days.forEach { Text(it, color = Texto.copy(.7f), fontSize = 12.sp, modifier = Modifier.width(36.dp), textAlign = TextAlign.Center) }
            }
            Spacer(Modifier.height(8.dp))

            val totalCells = leading + daysInMonth
            val rows = ceil(totalCells / 7.0).roundToInt().coerceAtLeast(1)

            var dayCounter = 1
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                repeat(rows) { rowIndex ->
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        repeat(7) { cell ->
                            val showDay = (rowIndex * 7 + cell) >= leading && dayCounter <= daysInMonth
                            if (showDay) {
                                val date = monthState.atDay(dayCounter++)
                                val mood = byDate[date]

                                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(36.dp)) {
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clip(CircleShape)
                                            .background((mood?.color ?: Color.Transparent).copy(alpha = if (mood != null) 0.35f else 0.10f))
                                            .border(1.dp, (mood?.color ?: ChipStroke), CircleShape)
                                    )
                                    Spacer(Modifier.height(4.dp))
                                    Text("${calOf(date).get(Calendar.DAY_OF_MONTH)}", color = Texto, fontSize = 12.sp)
                                }
                            } else {
                                Spacer(Modifier.width(36.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

/* ======= Tira mensual estilo mockup (NÚMERO ARRIBA, DÍA ABAJO) ======= */
@Composable
private fun MonthHistoryStrip(
    monthState: MonthState,
    entries: List<MoodEntry>,
    onSelectMonth: (Int) -> Unit,
    onSelectYear: (Int) -> Unit
) {
    val byDate = remember(entries) {
        entries.associate { isoToDate(it.date) to moodFromLabel(it.moodType) }
    }
    val monthNames = remember {
        (0..11).map { m ->
            Calendar.getInstance().apply { set(Calendar.MONTH, m); set(Calendar.DAY_OF_MONTH, 1) }.time.let {
                MONTH_FULL.format(it).replaceFirstChar { c -> c.titlecase(Locale("es","ES")) }
            }
        }
    }
    val currentYear = remember { Calendar.getInstance().get(Calendar.YEAR) }
    val years = remember { (currentYear + 1 downTo currentYear - 10).toList() }

    var monthMenuOpen by remember { mutableStateOf(false) }
    var yearMenuOpen by remember { mutableStateOf(false) }

    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Text("Historial de humor", color = Texto, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.weight(1f))

        OutlinedButton(
            onClick = { yearMenuOpen = true },
            shape = RoundedCornerShape(20.dp),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text("${monthState.year}", color = Texto)
            Icon(Icons.Filled.ArrowDropDown, contentDescription = null, tint = Texto)
        }
        DropdownMenu(expanded = yearMenuOpen, onDismissRequest = { yearMenuOpen = false }) {
            years.forEach { y ->
                DropdownMenuItem(text = { Text(y.toString()) }, onClick = {
                    yearMenuOpen = false
                    onSelectYear(y)
                })
            }
        }

        Spacer(Modifier.width(8.dp))

        OutlinedButton(
            onClick = { monthMenuOpen = true },
            shape = RoundedCornerShape(20.dp),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(monthNames[monthState.monthZeroBased], color = Texto)
            Icon(Icons.Filled.ArrowDropDown, contentDescription = null, tint = Texto)
        }
        DropdownMenu(expanded = monthMenuOpen, onDismissRequest = { monthMenuOpen = false }) {
            monthNames.forEachIndexed { idx, name ->
                DropdownMenuItem(text = { Text(name) }, onClick = {
                    monthMenuOpen = false
                    onSelectMonth(idx)
                })
            }
        }
    }

    Spacer(Modifier.height(10.dp))

    val days = monthState.daysInMonth()
    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        items(days) { i ->
            val day = i + 1
            val date = monthState.atDay(day)
            val mood = byDate[date]
            val weekday = DAY_SHORT.format(date).replace(".", "")
                .replaceFirstChar { it.titlecase(Locale("es","ES")) }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    day.toString(),
                    color = Texto.copy(.85f),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background((mood?.color ?: ChipStroke).copy(alpha = 0.18f))
                        .border(2.dp, mood?.color ?: ChipStroke, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    if (mood != null) {
                        Image(
                            painter = painterResource(mood.imageRes),
                            contentDescription = mood.label,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
                Spacer(Modifier.height(6.dp))
                Text(
                    weekday,
                    color = Texto.copy(.8f),
                    fontSize = 12.sp
                )
            }
        }
    }
}

/* ======= Historial reciente (últimos 90 días) ======= */
@Composable
private fun RecentHistory(
    entries: List<MoodEntry>,
    title: String = "Historial reciente"
) {
    val parsed = remember(entries) {
        entries
            .mapNotNull { e -> runCatching { isoToDate(e.date) to moodFromLabel(e.moodType) }.getOrNull() }
            .sortedByDescending { it.first }
            .take(10)
    }

    if (parsed.isEmpty()) return

    Text(title, color = Texto, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
    Spacer(Modifier.height(8.dp))

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        parsed.forEach { (date, mood) ->
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(12.dp),
                shadowElevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background((mood?.color ?: ChipStroke).copy(alpha = 0.20f))
                            .border(2.dp, mood?.color ?: ChipStroke, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        if (mood != null) {
                            Image(
                                painter = painterResource(id = mood.imageRes),
                                contentDescription = mood.label,
                                modifier = Modifier.size(24.dp).clip(CircleShape)
                            )
                        }
                    }
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text(text = mood?.label ?: "Sin registro", color = Texto, fontWeight = FontWeight.SemiBold)
                        Text(text = HUMAN.format(date), color = Texto.copy(alpha = 0.70f), fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

/* ===================== PDF: Exportación ===================== */

private fun exportMoodPdf(
    context: Context,
    uri: Uri,
    title: String,
    subtitle: String,
    points: List<MoodPoint>,
    entries: List<MoodEntry>,
    showMonthSeparators: Boolean
) {
    val pageWidth = 595     // A4 en puntos (72dpi): 595x842
    val pageHeight = 842
    val doc = PdfDocument()
    val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
    val page = doc.startPage(pageInfo)
    val canvas = page.canvas

    // Fondo suave
    val bgPaint = Paint().apply { color = android.graphics.Color.parseColor("#FFF7FB") }
    canvas.drawRect(0f, 0f, pageWidth.toFloat(), pageHeight.toFloat(), bgPaint)

    // Encabezado
    val headerPaint = Paint().apply {
        isAntiAlias = true
        color = android.graphics.Color.parseColor("#FF1493") // rosado brilloso
        textSize = 18f
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    }
    val textPaint = Paint().apply {
        isAntiAlias = true
        color = android.graphics.Color.parseColor("#842C46")
        textSize = 14f
    }

    // Logo
    try {
        val logoBmp = BitmapFactory.decodeResource(context.resources, R.drawable.logo_mente_libre)
        if (logoBmp != null) {
            val logo = Bitmap.createScaledBitmap(logoBmp, 48, 48, true)
            canvas.drawBitmap(logo, 32f, 24f, null)
            canvas.drawText(title, 32f + 48f + 16f, 44f, headerPaint)
            canvas.drawText(subtitle, 32f + 48f + 16f, 64f, textPaint)
        } else {
            // fallback si no hay logo
            canvas.drawText(title, 32f, 44f, headerPaint)
            canvas.drawText(subtitle, 32f, 64f, textPaint)
        }
    } catch (_: Exception) {
        canvas.drawText(title, 32f, 44f, headerPaint)
        canvas.drawText(subtitle, 32f, 64f, textPaint)
    }

    // Caja para el gráfico
    val chartLeft = 36f
    val chartTop = 90f
    val chartRight = pageWidth - 36f
    val chartBottom = 300f

    val border = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 1.5f
        color = android.graphics.Color.parseColor("#DDCCCC")
    }
    canvas.drawRoundRect(chartLeft, chartTop, chartRight, chartBottom, 10f, 10f, border)

    // Gráfico
    drawChartOnPdfCanvas(canvas, chartLeft, chartTop, chartRight, chartBottom, points, showMonthSeparators)

    // Leyenda de caritas (significado)
    var legendX = chartLeft
    val legendY = chartBottom + 22f
    enumValues<Mood>().forEach { m ->
        val bmp = BitmapFactory.decodeResource(context.resources, m.imageRes)
        if (bmp != null) {
            val icon = Bitmap.createScaledBitmap(bmp, 18, 18, true)
            canvas.drawBitmap(icon, legendX, legendY - 14f, null)
        }
        canvas.drawText(" ${m.label}", legendX + 20f, legendY, textPaint)
        legendX += 84f
    }

    // Título de tabla
    val tableTitlePaint = Paint().apply {
        isAntiAlias = true
        color = android.graphics.Color.parseColor("#842C46")
        textSize = 16f
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    }
    canvas.drawText("Registro de estados", chartLeft, legendY + 32f, tableTitlePaint)

    // Cabeceras
    val rowStartY = legendY + 50f
    val colDateX = chartLeft
    val colMoodX = pageWidth / 2f
    canvas.drawText("Fecha", colDateX, rowStartY, tableTitlePaint)
    canvas.drawText("Estado", colMoodX, rowStartY, tableTitlePaint)

    // Filas (simple)
    val rowPaint = Paint().apply {
        color = android.graphics.Color.parseColor("#444444")
        textSize = 12f
        isAntiAlias = true
    }
    var y = rowStartY + 18f
    entries.sortedBy { it.date }.forEach { e ->
        val d = HUMAN.format(isoToDate(e.date))
        val mood = moodFromLabel(e.moodType)?.label ?: "Sin registro"
        canvas.drawText(d, colDateX, y, rowPaint)
        canvas.drawText(mood, colMoodX, y, rowPaint)
        y += 16f
        if (y > pageHeight - 36) return@forEach
    }

    // Pie
    val footPaint = Paint().apply {
        color = android.graphics.Color.parseColor("#999999")
        textSize = 10f
    }
    canvas.drawText(
        "Mente Libre • Generado ${SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())}",
        chartLeft, pageHeight - 20f, footPaint
    )

    doc.finishPage(page)
    context.contentResolver.openOutputStream(uri)?.use { out -> doc.writeTo(out) }
    doc.close()
}

/* Dibuja un gráfico simple (línea + puntos) en el canvas del PDF */
private fun drawChartOnPdfCanvas(
    canvas: android.graphics.Canvas,
    left: Float,
    top: Float,
    right: Float,
    bottom: Float,
    points: List<MoodPoint>,
    showMonthSeparators: Boolean
) {
    if (points.isEmpty()) return
    val width = right - left
    val height = bottom - top
    val step = if (points.size > 1) width / (points.size - 1) else 0f

    // Mapea puntos
    val coords = points.mapIndexed { i, mp ->
        val x = left + i * step
        val y = mp.mood?.let { bottom - (it.height * height) } ?: Float.NaN
        PointF(x, y)
    }
    val valid = coords.withIndex().filter { !it.value.y.isNaN() }

    // Línea
    if (valid.size >= 2) {
        val path = android.graphics.Path().apply {
            moveTo(valid.first().value.x, valid.first().value.y)
            for (i in 1 until valid.size) {
                val p0 = valid[i - 1].value
                val p1 = valid[i].value
                val midX = (p0.x + p1.x) / 2f
                cubicTo(midX, p0.y, midX, p1.y, p1.x, p1.y)
            }
        }
        val linePaint = Paint().apply {
            isAntiAlias = true
            color = android.graphics.Color.parseColor("#734B3A")
            strokeWidth = 4.5f
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
        }
        canvas.drawPath(path, linePaint)
    }

    // Puntos
    valid.forEach { v ->
        val mood = points[v.index].mood!!
        val dotPaint = Paint().apply {
            isAntiAlias = true
            color = mood.color.toArgb()
            style = Paint.Style.FILL
        }
        canvas.drawCircle(v.value.x, v.value.y, 5.5f, dotPaint)
    }

    // Separadores de mes (rosado brilloso)
    if (showMonthSeparators) {
        val deepPink = android.graphics.Color.parseColor("#FF1493")
        val linePaint = Paint().apply {
            isAntiAlias = true
            strokeWidth = 2.5f
            color = deepPink
            setShadowLayer(6f, 0f, 0f, android.graphics.Color.parseColor("#FFFF80C0"))
        }
        val monthText = Paint().apply {
            isAntiAlias = true
            textSize = 11.5f
            color = deepPink
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            textAlign = Paint.Align.CENTER
        }
        val labelY = (top - 4f).coerceAtLeast(10f)
        points.forEachIndexed { i, mp ->
            val d = isoToDate(mp.dateIso)
            val cal = calOf(d)
            if (cal.get(Calendar.DAY_OF_MONTH) == 1) {
                val x = left + i * step
                canvas.drawLine(x, top, x, bottom, linePaint)
                val label = MONTH_ABBR.format(d).replace(".", "")
                    .replaceFirstChar { it.titlecase(Locale("es","ES")) }
                canvas.drawText(label, x, labelY, monthText)
            }
        }
    }
}
