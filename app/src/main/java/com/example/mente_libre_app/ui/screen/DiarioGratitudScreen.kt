package com.example.mente_libre_app.ui.screen

import androidx.lifecycle.viewmodel.compose.viewModel
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mente_libre_app.data.local.database.AppDatabase
import com.example.mente_libre_app.data.local.database.gratitud.GratitudEntry
import com.example.mente_libre_app.data.local.mood.Mood
import com.example.mente_libre_app.data.local.mood.MoodDao
import com.example.mente_libre_app.data.local.mood.MoodDatabase
import com.example.mente_libre_app.data.local.mood.MoodEntry
import com.example.mente_libre_app.data.viewmodel.EvaluationViewModel
import com.example.mente_libre_app.data.viewmodel.EvaluationViewModelFactory
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/* ===== Helpers ===== */

@Suppress("SimpleDateFormat")
private val ISO = SimpleDateFormat("yyyy-MM-dd", Locale("es", "ES"))

@Suppress("SimpleDateFormat")
private val HUMAN = SimpleDateFormat("dd 'de' MMM", Locale("es", "ES"))

private fun Date.toIso(): String = ISO.format(this)
private fun Date.toHuman(): String = HUMAN.format(this)

/* ===== Modelo UI ===== */

data class GratitudeUiEntry(
    val date: String,
    val mood: Mood?,
    val title: String?,
    val text: String
)

/* ============================================================
   DIARIO DE GRATITUD — PANTALLA COMPLETA
   ============================================================ */

@Composable
fun DiarioGratitudScreen(
    onBack: () -> Unit
) {
    val context = LocalContext.current

    // 🔗 ViewModel que llama al microservicio evaluation-service
    val evaluationViewModel: EvaluationViewModel = viewModel(
        factory = EvaluationViewModelFactory(context)
    )

    val moodDao: MoodDao = remember { MoodDatabase.getInstance(context).moodDao() }
    val appDb = remember { AppDatabase.getInstance(context) }
    val gratitudDao = remember { appDb.gratitudDao() }

    val scope = rememberCoroutineScope()

    var selectedMood by remember { mutableStateOf<Mood?>(null) }
    var title by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }
    var entries by remember { mutableStateOf<List<GratitudeUiEntry>>(emptyList()) }

    LaunchedEffect(Unit) {
        val stored = gratitudDao.getAll()
        entries = stored.map { it.toUi() }
    }

    val scroll = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFEDF5))
            .verticalScroll(scroll)
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {

        /* HEADER */
        Row(verticalAlignment = Alignment.CenterVertically) {
            androidx.compose.material3.Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Volver",
                tint = Color(0xFF7A2C54),
                modifier = Modifier
                    .size(28.dp)
                    .clickable { onBack() }
            )
            Spacer(Modifier.width(12.dp))
            Text(
                text = "Diario de Gratitud",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF7A2C54)
            )
        }

        Spacer(Modifier.height(20.dp))

        Text(
            text = "Recuerda: no es sólo un diario para agradecer.\n" +
                    "También te ayuda a llevar un historial de tus emociones y momentos importantes 💗",
            color = Color(0xFF7A2C54),
            fontSize = 16.sp
        )

        Spacer(Modifier.height(20.dp))

        /* SELECTOR DE EMOCIONES */
        EmotionSelector(
            selectedMood = selectedMood,
            onSelect = { selectedMood = it }
        )

        Spacer(Modifier.height(16.dp))

        /* INFO DE EMOCIÓN SELECCIONADA */
        if (selectedMood != null) {
            val mood = selectedMood!!
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = mood.color.copy(alpha = 0.20f),
                        shape = RoundedCornerShape(22.dp)
                    )
                    .padding(14.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.9f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(mood.icon),
                            contentDescription = mood.label,
                            modifier = Modifier.size(34.dp)
                        )
                    }
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(
                            text = mood.label,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color(0xFF7A2C54)
                        )
                        Text(
                            text = mood.description,
                            fontSize = 14.sp,
                            color = Color(0xFF7A2C54)
                        )
                    }
                }
            }

            Spacer(Modifier.height(18.dp))
        }

        /* ---------- Entrada de hoy ---------- */
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(24.dp))
                .border(1.dp, Color(0xFFE7CFE0), RoundedCornerShape(24.dp))
                .padding(horizontal = 18.dp, vertical = 16.dp)
        ) {
            Column {

                Text(
                    text = "Entrada de hoy",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF7A2C54)
                )

                Spacer(Modifier.height(14.dp))

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    placeholder = { Text("Título (opcional)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 52.dp),
                    singleLine = true
                )

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    placeholder = { Text("Hoy agradezco por...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp),
                    maxLines = 6
                )

                Spacer(Modifier.height(16.dp))

                val canSave = text.isNotBlank() || title.isNotBlank()

                Button(
                    onClick = {
                        val now = Date()
                        val iso = now.toIso()
                        val mood = selectedMood
                        val cleanTitle = title.trim().ifBlank { null }
                        val cleanText = text.trim()

                        scope.launch {

                            // ---- 1) ENVIAR AL MICRO SERVICIO -----------------
                            evaluationViewModel.saveGratitude(
                                date = iso,
                                moodLabel = mood?.label,
                                title = cleanTitle,
                                text = cleanText
                            )

                            // ---- 2) GUARDAR EN LOCAL (Room) -------------------
                            if (mood != null) {
                                val existing = moodDao.entryOn(iso)
                                if (existing == null) {
                                    moodDao.insert(
                                        MoodEntry(date = iso, moodType = mood.label)
                                    )
                                } else {
                                    moodDao.insert(existing.copy(moodType = mood.label))
                                }
                            }

                            gratitudDao.insert(
                                GratitudEntry(
                                    date = iso,
                                    moodLabel = mood?.label,
                                    title = cleanTitle,
                                    text = cleanText
                                )
                            )

                            val stored = gratitudDao.getAll()
                            entries = stored.map { it.toUi() }
                        }

                        title = ""
                        text = ""
                    },
                    enabled = canSave,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEBB3CC)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = "Guardar entrada",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        /* LISTA DE ENTRADAS RECIENTES */
        Text(
            text = "Entradas recientes",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF7A2C54)
        )

        Spacer(Modifier.height(12.dp))

        if (entries.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(18.dp))
                    .padding(20.dp)
            ) {
                Text(
                    text = "Aún no has escrito en tu diario.\nComienza con una pequeña cosa que agradezcas hoy 💗",
                    color = Color(0xFF7A2C54),
                    fontSize = 15.sp
                )
            }
        } else {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                entries.forEach { entry ->
                    GratitudeCard(entry = entry)
                }
            }
        }

        Spacer(Modifier.height(16.dp))
    }
}

/* ============================================================
   SELECTOR DE EMOCIONES
   ============================================================ */

@Composable
fun EmotionSelector(
    selectedMood: Mood?,
    onSelect: (Mood) -> Unit
) {
    Text(
        text = "¿Cómo te sientes?",
        color = Color(0xFF7A2C54),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )

    Spacer(Modifier.height(12.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(26.dp)
    ) {
        Mood.values().forEach { mood ->
            val isSelected = selectedMood == mood

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { onSelect(mood) }
            ) {

                val circleSize = if (isSelected) 68.dp else 62.dp

                Box(
                    modifier = Modifier
                        .size(circleSize)
                        .clip(CircleShape)
                        .background(
                            if (isSelected)
                                mood.color.copy(alpha = 0.38f)
                            else
                                Color(0xFFF2DDEA)
                        )
                        .border(
                            width = if (isSelected) 3.dp else 2.dp,
                            color = if (isSelected) mood.color else Color(0xFFE5C8D3),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(mood.icon),
                        contentDescription = mood.label,
                        modifier = Modifier.size(
                            if (isSelected) 58.dp else 52.dp
                        )
                    )
                }

                Spacer(Modifier.height(8.dp))

                Text(
                    text = mood.label,
                    color = if (isSelected) Color(0xFF7A2C54) else Color(0xFF7A2C54).copy(alpha = 0.8f),
                    fontSize = 14.sp,
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                )
            }
        }
    }
}

/* ============================================================
   TARJETA DE ENTRADAS GUARDADAS
   ============================================================ */

@Composable
fun GratitudeCard(entry: GratitudeUiEntry) {
    val baseColor = entry.mood?.color ?: Color(0xFFEADCF0)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(baseColor.copy(alpha = 0.45f), RoundedCornerShape(18.dp))
            .padding(14.dp)
    ) {
        if (entry.mood != null) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.95f)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(entry.mood.icon),
                    contentDescription = entry.mood.label,
                    modifier = Modifier.size(34.dp)
                )
            }
            Spacer(Modifier.width(12.dp))
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = entry.date,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF7A2C54).copy(alpha = 0.8f)
            )

            if (!entry.title.isNullOrBlank()) {
                Text(
                    text = entry.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF7A2C54)
                )
            } else if (entry.mood != null) {
                Text(
                    text = entry.mood.label,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF7A2C54)
                )
            }

            Spacer(Modifier.height(4.dp))

            Text(
                text = entry.text,
                fontSize = 14.sp,
                color = Color(0xFF7A2C54)
            )
        }
    }
}

/* ============================================================
   MAPPER: Entidad BD → Modelo UI
   ============================================================ */

private fun GratitudEntry.toUi(): GratitudeUiEntry {
    val mood = Mood.values().firstOrNull { it.label == this.moodLabel }
    val parsedDate = try { ISO.parse(this.date) } catch (_: Exception) { null }
    val human = parsedDate?.toHuman() ?: this.date

    return GratitudeUiEntry(
        date = human,
        mood = mood,
        title = this.title,
        text = this.text
    )
}
