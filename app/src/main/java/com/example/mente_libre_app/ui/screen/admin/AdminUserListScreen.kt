package com.example.mente_libre_app.ui.screen.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mente_libre_app.data.remote.dto.admin.UserAdminDto
import com.example.mente_libre_app.ui.viewmodel.AdminViewModel

@Composable
fun AdminUserListScreen(
    onBack: () -> Unit,
    viewModel: AdminViewModel
) {
    val usuarios by viewModel.usuarios.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.cargarUsuarios()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFE8EE)) // rosado suave pero más formal
            .padding(20.dp)
    ) {

        // HEADER
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Volver",
                tint = Color(0xFF70263D),
                modifier = Modifier
                    .size(28.dp)
                    .clickable { onBack() }
            )

            Spacer(Modifier.width(12.dp))

            Text(
                text = "Panel Administrativo",
                fontSize = 26.sp,
                color = Color(0xFF70263D),
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(14.dp))

        Text(
            text = "Gestión de Usuarios",
            fontSize = 20.sp,
            color = Color(0xFF9F4061),
            fontWeight = FontWeight.SemiBold
        )

        Spacer(Modifier.height(20.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(usuarios) { user ->
                UserCard(
                    user = user,
                    onBlockToggle = {
                        if (user.bloqueado)
                            viewModel.desbloquearUsuario(user.id)
                        else
                            viewModel.bloquearUsuario(user.id)
                    }
                )
            }
        }
    }
}

@Composable
fun UserCard(
    user: UserAdminDto,
    onBlockToggle: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Text(
                text = user.username,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF70263D)
            )

            Text(
                text = user.email,
                fontSize = 15.sp,
                color = Color(0xFF9F4061)
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Rol: ${user.rol?.nombre ?: "Sin rol"}",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF7A2C54)
            )

            Spacer(Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {

                // Estado visual
                if (user.bloqueado) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Block,
                            contentDescription = null,
                            tint = Color.Red
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            "Bloqueado",
                            color = Color.Red,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                } else {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Color(0xFF47A358)
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            "Activo",
                            color = Color(0xFF47A358),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                // Botón bloquear / desbloquear
                Button(
                    onClick = onBlockToggle,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (user.bloqueado) Color(0xFF6FB569) else Color(0xFFE56B6F)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {

                    Icon(
                        imageVector = if (user.bloqueado) Icons.Default.LockOpen else Icons.Default.Block,
                        contentDescription = null,
                        tint = Color.White
                    )

                    Spacer(Modifier.width(6.dp))

                    Text(
                        text = if (user.bloqueado) "Desbloquear" else "Bloquear",
                        color = Color.White
                    )
                }
            }
        }
    }
}
