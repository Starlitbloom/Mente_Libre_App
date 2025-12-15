package com.example.mente_libre_app.ui.screen.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mente_libre_app.ui.viewmodel.AdminViewModel

@Composable
fun AdminDashboardScreen(
    viewModel: AdminViewModel,
    goToUsers: () -> Unit,
    goToRoles: () -> Unit,
    goToReportes: () -> Unit,
) {

    val resumen by viewModel.resumen.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.cargarDashboard()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDE5EA)) // Fondo suave
            .padding(22.dp)
    ) {

        // ---------- HEADER ----------
        Text(
            text = "Panel Administrativo",
            color = Color(0xFF70263D),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(6.dp))

        Text(
            text = "Bienvenido, Administrador",
            color = Color(0xFF9F4061),
            fontSize = 18.sp
        )

        Spacer(Modifier.height(22.dp))

        // ---------- TARJETAS PRINCIPALES ----------
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DashboardCard(
                title = "Usuarios",
                value = resumen.totalUsuarios,
                icon = Icons.Default.Person,
                color = Color(0xFFFFD2D2),
                onClick = goToUsers
            )
            DashboardCard(
                title = "Bloqueados",
                value = resumen.usuariosBloqueados,
                icon = Icons.Default.Block,
                color = Color(0xFFFFE3BA),
                onClick = goToUsers
            )
        }

        Spacer(Modifier.height(16.dp))

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DashboardCard(
                title = "Evaluaciones",
                value = resumen.totalEvaluaciones,
                icon = Icons.Default.Assessment,
                color = Color(0xFFCFE8FF),
                onClick = goToReportes
            )
            DashboardCard(
                title = "Emociones",
                value = resumen.totalEmociones,
                icon = Icons.Default.Favorite,
                color = Color(0xFFEAC4FF),
                onClick = goToReportes
            )
        }

        Spacer(Modifier.height(16.dp))

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DashboardCard(
                title = "Metas",
                value = resumen.totalMetas,
                icon = Icons.Default.Flag,
                color = Color(0xFFFFF4A8),
                onClick = goToReportes
            )
            DashboardCard(
                title = "Logros",
                value = resumen.totalLogros,
                icon = Icons.Default.EmojiEvents,
                color = Color(0xFFDFFFB5),
                onClick = goToReportes
            )
        }

        Spacer(Modifier.height(30.dp))

        // ---------- BOTÓN GENERAR REPORTE ----------
        Button(
            onClick = { viewModel.generarReporte() },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF9F4061)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            shape = RoundedCornerShape(18.dp)
        ) {
            Text(
                text = "Generar Reporte General",
                color = Color.White,
                fontSize = 18.sp
            )
        }

        Spacer(Modifier.height(24.dp))

        // ---------- MENÚ DE NAVEGACIÓN ----------
        Text(
            text = "Opciones avanzadas",
            color = Color(0xFF70263D),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(Modifier.height(14.dp))

        AdminOptionItem("Gestión de Usuarios", Icons.Default.People, goToUsers)
        AdminOptionItem("Gestión de Roles", Icons.Default.Security, goToRoles)
        AdminOptionItem("Historial de Reportes", Icons.Default.Article, goToReportes)
    }
}

@Composable
fun DashboardCard(
    title: String,
    value: Int,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(165.dp)
            .height(120.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = color),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Icon(icon, contentDescription = null, tint = Color(0xFF70263D), modifier = Modifier.size(28.dp))

            Spacer(Modifier.height(6.dp))

            Text(text = title, color = Color(0xFF70263D), fontSize = 16.sp)
            Text(
                text = value.toString(),
                color = Color(0xFF70263D),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun AdminOptionItem(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = Color(0xFF9F4061), modifier = Modifier.size(26.dp))

        Spacer(Modifier.width(14.dp))

        Text(text = title, color = Color(0xFF70263D), fontSize = 18.sp)
    }
}
