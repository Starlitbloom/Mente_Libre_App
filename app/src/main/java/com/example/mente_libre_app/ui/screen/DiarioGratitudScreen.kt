package com.example.mente_libre_app.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mente_libre_app.R
import com.example.mente_libre_app.data.local.mood.Mood
import com.example.mente_libre_app.data.local.mood.MoodDatabase
import com.example.mente_libre_app.data.local.mood.MoodEntry
import com.example.mente_libre_app.data.remote.dto.evaluation.GratitudeEntryRequestDto
import com.example.mente_libre_app.data.viewmodel.EvaluationViewModel
import com.example.mente_libre_app.data.viewmodel.EvaluationViewModelFactory
import com.example.mente_libre_app.ui.components.RewardPopup
import com.example.mente_libre_app.ui.theme.LocalExtraColors
import com.example.mente_libre_app.ui.viewmodel.AuthViewModel
import com.example.mente_libre_app.ui.viewmodel.PetViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/* ================================
   Helpers de fecha
   ================================ */
@Suppress("SimpleDateFormat")
private val ISO = SimpleDateFormat("yyyy-MM-dd", Locale("es", "ES"))
private fun Date.toIso(): String = ISO.format(this)

/* ============================================================
   DIARIO DE GRATITUD — PANTALLA COMPLETA
   ============================================================ */

@Composable
fun DiarioGratitudScreen(
    onBack: () -> Unit,
    authViewModel: AuthViewModel,
    petViewModel: PetViewModel
) {
    val context = LocalContext.current
    val evaluationViewModel: EvaluationViewModel = viewModel(
        factory = EvaluationViewModelFactory(context)
    )

    val saveResult by evaluationViewModel.saveResult.collectAsState(initial = null)

    val serifBold = FontFamily(Font(R.font.source_serif_pro_bold))
    val serifRegular = FontFamily(Font(R.font.source_serif_pro_regular))

    var selectedMood by remember { mutableStateOf<Mood?>(null) }
    var title by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }

    val scroll = rememberScrollState()


    val extra = LocalExtraColors.current
    val scheme = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(scheme.background)
            .verticalScroll(scroll)
            .padding(20.dp)
    ) {


        // HEADER
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Volver",
                tint = extra.title,
                modifier = Modifier
                    .size(28.dp)
                    .clickable { onBack() }
            )
            Spacer(Modifier.width(12.dp))
            Text(
                text = "Diario de Gratitud",
                fontSize = 30.sp,
                fontFamily = serifBold,
                color = extra.title
            )
        }

        Spacer(Modifier.height(18.dp))

        // SUBTEXTO
        Text(
            text = "Escribe algo que agradeces hoy 💗\nRegistrar tus emociones te ayuda a sanar.",
            fontSize = 17.sp,
            fontFamily = serifRegular,
            color = extra.title
        )

        Spacer(Modifier.height(26.dp))

        // SELECTOR DE EMOCIONES
        EmotionSelector(selectedMood, onSelect = { selectedMood = it })

        Spacer(Modifier.height(26.dp))

        // TARJETA
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(32.dp),
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {

                Text(
                    text = "Entrada de hoy",
                    fontSize = 22.sp,
                    fontFamily = serifBold,
                    color = extra.title
                )

                Spacer(Modifier.height(16.dp))

                // TÍTULO
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    textStyle = LocalTextStyle.current.copy(fontFamily = serifRegular),
                    placeholder = {
                        Text("Título (opcional)", fontFamily = serifRegular)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp)),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color(0xFFE5C8D3),
                        focusedBorderColor = Color(0xFFBF8FAE)
                    )
                )

                Spacer(Modifier.height(16.dp))

                // TEXTO
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    placeholder = {
                        Text("Hoy agradezco por...", fontFamily = serifRegular)
                    },
                    textStyle = LocalTextStyle.current.copy(fontFamily = serifRegular),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(24.dp)),
                    maxLines = 6,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color(0xFFE5C8D3),
                        focusedBorderColor = Color(0xFFBF8FAE)
                    )
                )

                Spacer(Modifier.height(26.dp))

                // BOTÓN
                val enabled = text.isNotBlank()

                Button(
                    onClick = {
                        val userId = authViewModel.currentUserId.value ?: return@Button
                        val today = Date().toIso()

                        // Guardar gratitud en backend
                        evaluationViewModel.saveGratitude(
                            userId,
                            GratitudeEntryRequestDto(
                                date = today,
                                moodLabel = selectedMood?.label ?: "Gratitud",
                                title = title.ifBlank { null },
                                text = text
                            )
                        )

                        // ️ Guardar para puntaje en ROOM
                        val dao = MoodDatabase.getInstance(context).moodDao()
                        CoroutineScope(Dispatchers.IO).launch {
                            dao.insert(
                                MoodEntry(
                                    moodType = selectedMood?.label ?: "Feliz",
                                    date = today
                                )
                            )
                        }

                        // Limpiar campos
                        title = ""
                        text = ""
                        selectedMood = null
                    },
                    enabled = text.isNotBlank(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(32.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEBB3CC),
                        disabledContainerColor = Color(0xFFE6D6DE)
                    )
                ) {
                    Text(
                        text = "Guardar entrada",
                        fontFamily = serifBold,
                        fontSize = 18.sp
                    )
                }

            }
        }

        Spacer(Modifier.height(20.dp))

        // MENSAJE
        when (saveResult) {
            "OK" -> Text(
                text = "Entrada guardada 💗",
                fontSize = 17.sp,
                fontFamily = serifBold,
                color = extra.title
            )
        }
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
    val extra = LocalExtraColors.current
    Text(
        text = "¿Cómo te sientes?",
        color = extra.title,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )

    Spacer(Modifier.height(12.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        Mood.values().forEach { mood ->
            val isSelected = selectedMood == mood

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { onSelect(mood) }
            ) {

                Box(
                    modifier = Modifier
                        .size(if (isSelected) 70.dp else 60.dp)
                        .clip(CircleShape)
                        .background(
                            if (isSelected) mood.color.copy(alpha = 0.4f)
                            else Color(0xFFF2DDEA)
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
                        modifier = Modifier.size(if (isSelected) 55.dp else 48.dp)
                    )
                }

                Spacer(Modifier.height(8.dp))

                Text(
                    text = mood.label,
                    color = extra.title,
                    fontSize = 14.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}
