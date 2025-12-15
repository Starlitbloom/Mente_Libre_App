package com.example.mente_libre_app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/* ============================================================
   PANTALLA PRINCIPAL BITÁCORA
   ============================================================ */

data class ReminderItem(
    val title: String,
    val time: String,
    val enabled: Boolean = true
)

@Composable
fun BitacoraScreen(
    onBack: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    // Recordatorios personalizados
    var customReminders by remember { mutableStateOf(listOf<ReminderItem>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF1F7))
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {

        // ---------- HEADER ----------
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "< Volver",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF7A2C54),
                modifier = Modifier.clickable { onBack() }
            )
            Spacer(Modifier.width(12.dp))
            Text(
                text = "Bitácora — Recordatorios",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF7A2C54)
            )
        }

        Spacer(Modifier.height(20.dp))

        Text(
            text = "Pequeños hábitos para sentirte mejor cada día 💗",
            fontSize = 16.sp,
            color = Color(0xFF7A2C54)
        )

        Spacer(Modifier.height(25.dp))

        // ----------- RECOMENDADOS -------------
        Text(
            text = "Recomendados para ti",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF7A2C54)
        )
        Spacer(Modifier.height(12.dp))

        ReminderCardToggle("Beber agua 💧", "Cada 2 horas")
        Spacer(Modifier.height(10.dp))

        ReminderCardToggle("Respiración consciente 🌿", "20:00")
        Spacer(Modifier.height(10.dp))

        ReminderCardToggle("Registrar emoción 😊", "21:00")
        Spacer(Modifier.height(10.dp))

        ReminderCardToggle("Momento de gratitud ✨", "22:00")

        Spacer(Modifier.height(30.dp))

        // ----------- PERSONALIZADOS -------------
        Text(
            text = "Tus recordatorios",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF7A2C54)
        )
        Spacer(Modifier.height(12.dp))

        if (customReminders.isEmpty()) {
            Text(
                text = "Aún no creas recordatorios.\nPresiona + para comenzar 💗",
                color = Color(0xFF7A2C54),
                fontSize = 15.sp
            )
        } else {
            customReminders.forEach { reminder ->
                ReminderCardToggle(
                    title = reminder.title,
                    subtitle = reminder.time,
                    initial = reminder.enabled
                )
                Spacer(Modifier.height(10.dp))
            }
        }

        Spacer(Modifier.height(20.dp))

        // ---------- BOTÓN AGREGAR ----------
        Button(
            onClick = { showDialog = true },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC8DB)),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(
                text = "+ Crear recordatorio",
                fontSize = 18.sp,
                color = Color(0xFF7A2C54)
            )
        }
    }

    // ---------- DIALOG PARA CREAR ----------
    if (showDialog) {
        CrearRecordatorioDialog(
            onDismiss = { showDialog = false },
            onSave = { title, time ->
                customReminders = customReminders + ReminderItem(title, time)
                showDialog = false
            }
        )
    }

}
@Composable
fun ReminderCardToggle(
    title: String,
    subtitle: String,
    initial: Boolean = true
) {
    var isActive by remember { mutableStateOf(initial) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFCE7EF), RoundedCornerShape(20.dp))
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {

            Column {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF7A2C54)
                )
                Text(
                    text = subtitle,
                    fontSize = 14.sp,
                    color = Color(0xFF7A2C54)
                )
            }

            Switch(
                checked = isActive,
                onCheckedChange = { isActive = it }
            )
        }
    }
}
@Composable
fun CrearRecordatorioDialog(
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Crear recordatorio",
                fontWeight = FontWeight.Bold,
                color = Color(0xFF7A2C54)
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Título") }
                )
                Spacer(Modifier.height(10.dp))
                OutlinedTextField(
                    value = time,
                    onValueChange = { time = it },
                    label = { Text("Hora (ej: 20:00)") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(title, time) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEFB6CB)
                )
            ) {
                Text("Guardar", color = Color(0xFF7A2C54))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar", color = Color(0xFF7A2C54))
            }
        }
    )
}
