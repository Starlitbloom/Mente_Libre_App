package com.example.mente_libre_app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mente_libre_app.R
import com.example.mente_libre_app.ui.components.CurvedHeader
import com.example.mente_libre_app.ui.theme.LocalExtraColors

@Composable
fun OrganizarseScreen(navController: NavController) {

    val serifBold = FontFamily(Font(R.font.source_serif_pro_bold))
    val serifRegular = FontFamily(Font(R.font.source_serif_pro_regular))
    val scroll = rememberScrollState()
    val scheme = MaterialTheme.colorScheme
    val extra = LocalExtraColors.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(scheme.background) // fondo uniforme
            .verticalScroll(scroll)           // SOLO UN SCROLL
    ) {

        CurvedHeader(navController)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Cómo organizarse mejor",
                fontFamily = serifBold,
                fontSize = 32.sp,
                color = extra.title,
                lineHeight = 34.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()   // SE CENTRA
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Prioriza tus tareas",
                color = extra.arrowColor,
                fontFamily = serifBold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextSection(
                title = "La Regla de las Tres Tareas",
                text = "Cada mañana, selecciona tres tareas principales que debes completar ese día. Estas deben ser tus prioridades y ayudarte a enfocarte en lo más importante.",
                fontRegular = serifRegular
            )

            TextSection(
                title = "Divide y Vence",
                text = "Desglosa las tareas grandes en pasos más pequeños y manejables. Completar pequeñas partes de una tarea grande puede hacer que todo el proceso sea menos abrumador.",
                serifRegular
            )

            TextSection(
                title = "El Método Eisenhower",
                text = "Clasifica tus tareas en una matriz de cuatro cuadrantes según su urgencia e importancia:\n\n" +
                        "Urgente e importante (hazlo ahora)\n" +
                        "Importante pero no urgente (planifícalo)\n" +
                        "Urgente pero no importante (delegalo)\n" +
                        "Ni urgente ni importante (elimínalo).",
                serifRegular
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Planifica con Anticipación",
                color = extra.arrowColor,
                fontFamily = serifBold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextSection(
                title = "Planificación Semanal",
                text = "Dedica tiempo al final de cada semana para planificar la próxima. Esto incluye preparar materiales para las clases, programar reuniones y establecer metas semanales.",
                serifRegular
            )

            TextSection(
                title = "Listas de Verificación",
                text = "Utiliza listas de verificación para tareas recurrentes, como la preparación de clases o la corrección de exámenes, para asegurarte de no olvidar nada.",
                serifRegular
            )

            TextSection(
                title = "Calendarios Digitales",
                text = "Utiliza calendarios en tu teléfono o computadora para programar recordatorios y plazos importantes. Las notificaciones automáticas pueden ser un gran aliado para mantenerte organizado.",
                serifRegular
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Establece Rutinas",
                color = extra.arrowColor,
                fontFamily = serifBold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextSection(
                title = "Rutinas de Clase",
                text = "Establece rutinas diarias en la sala de clases que ayuden a los estudiantes a saber qué esperar, reduciendo tu carga de gestión de tiempo.",
                serifRegular
            )

            TextSection(
                title = "Rutina de Final de Día",
                text = "Termina cada día organizando tu escritorio y haciendo una lista de tareas pendientes para el día siguiente. Esto te ayuda a empezar cada día de manera clara y organizada.",
                serifRegular
            )

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

// Pequeño componente para texto + título de párrafo
@Composable
fun TextSection(title: String, text: String, fontRegular: FontFamily) {
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        val scheme = MaterialTheme.colorScheme
        val extra = LocalExtraColors.current

        Text(
            text = "• $title:",
            color = extra.title,              // DINÁMICO
            fontWeight = FontWeight.Bold,
            fontFamily = fontRegular,
            fontSize = 16.sp
        )

        Text(
            text = text,
            color = scheme.onBackground,      // DINÁMICO
            fontFamily = fontRegular,
            fontSize = 15.sp,
            lineHeight = 20.sp
        )
    }
}

