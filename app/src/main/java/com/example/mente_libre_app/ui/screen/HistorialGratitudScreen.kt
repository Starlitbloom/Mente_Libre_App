package com.example.mente_libre_app.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mente_libre_app.data.local.mood.Mood
import com.example.mente_libre_app.data.remote.dto.evaluation.GratitudeEntryResponseDto
import com.example.mente_libre_app.data.viewmodel.EvaluationViewModel
import com.example.mente_libre_app.data.viewmodel.EvaluationViewModelFactory
import com.example.mente_libre_app.ui.viewmodel.AuthViewModel


@Composable
fun HistorialGratitudScreen(
    onBack: () -> Unit,
    authViewModel: AuthViewModel = viewModel()
) {
    val context = LocalContext.current
    val evalVm: EvaluationViewModel = viewModel(factory = EvaluationViewModelFactory(context))

    val userId = authViewModel.currentUserId.collectAsState().value

    val gratitudeList = evalVm.gratitudeList.collectAsState().value
    val isLoading = evalVm.isLoading
    val error = evalVm.errorMessage

    // Cargar historial al abrir la pantalla
    LaunchedEffect(userId) {
        userId?.let { evalVm.loadGratitudeEntries(it) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFEDF5))
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {

        // Header
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "volver",
                tint = Color(0xFF7A2C54),
                modifier = Modifier
                    .size(28.dp)
                    .clickable { onBack() }
            )
            Spacer(Modifier.width(12.dp))
            Text(
                "Historial de Gratitud",
                fontSize = 26.sp,
                color = Color(0xFF7A2C54)
            )
        }

        Spacer(Modifier.height(20.dp))

        if (isLoading) {
            Text("Cargando entradas...", color = Color(0xFF7A2C54))
            return@Column
        }

        if (error != null) {
            Text("Error: $error", color = Color.Red)
            return@Column
        }

        if (gratitudeList.isEmpty()) {
            Text(
                "Aún no tienes entradas registradas 💗",
                fontSize = 16.sp,
                color = Color(0xFF7A2C54)
            )
            return@Column
        }

        // Mostrar lista de entradas
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            gratitudeList.forEach { entry ->
                GratitudeHistoryCard(entry)
            }
        }
    }
}

@Composable
fun GratitudeHistoryCard(entry: GratitudeEntryResponseDto) {

    val mood = entry.moodLabel?.let { Mood.valueOf(it) }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = mood?.color?.copy(alpha = 0.25f) ?: Color(0xFFEADCF0)
        ),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp)
    ) {

        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Icono de emoción
            if (mood != null) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.95f)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(mood.icon),
                        contentDescription = mood.label,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(Modifier.width(12.dp))
            }

            Column(modifier = Modifier.weight(1f)) {

                Text(
                    text = entry.date,
                    fontSize = 13.sp,
                    color = Color(0xFF7A2C54).copy(alpha = 0.8f)
                )

                Text(
                    text = entry.title ?: mood?.label ?: "(Sin título)",
                    fontSize = 17.sp,
                    color = Color(0xFF7A2C54)
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = entry.text,
                    fontSize = 14.sp,
                    color = Color(0xFF7A2C54)
                )
            }
        }
    }
}