package com.example.mente_libre_app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mente_libre_app.R
import com.example.mente_libre_app.ui.theme.LocalExtraColors
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.mente_libre_app.ui.components.BackArrowCustom
import com.example.mente_libre_app.ui.viewmodel.AuthViewModel
import com.example.mente_libre_app.ui.viewmodel.UsuarioViewModel

@Composable
fun SeguridadScreen(
    navController: NavHostController,
    usuarioViewModel: UsuarioViewModel,
    authViewModel: AuthViewModel
) {
    val extra = LocalExtraColors.current
    var biometriaActiva by remember { mutableStateOf(true) }
    var pinActivo by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFFFEAF4))
            .padding(24.dp)
    ) {

        // 🔙 Flecha volver
        BackArrowCustom(
            navController = navController,
            color = extra.title,
            size = 62,
            stroke = 15f,
            modifier = Modifier.clickable { navController.popBackStack() }
        )

        Spacer(Modifier.height(20.dp))

        Text(
            text = "Seguridad",
            color = extra.title,
            fontSize = 28.sp
        )

        Spacer(Modifier.height(26.dp))

        // --------------------------
        // BLOQUE: BIOMETRÍA
        // --------------------------
        SeguridadCard(title = "Métodos de acceso") {

            // 🔘 Acceso con huella
            SeguridadSwitchItem(
                icon = R.drawable.huella_dactilar,
                text = "Usar huella para entrar",
                checked = biometriaActiva,
                onToggle = { biometriaActiva = it }
            )

            SeguridadSwitchItem(
                icon = R.drawable.pin,
                text = "Usar PIN para entrar",
                checked = pinActivo,
                onToggle = { pinActivo = it }
            )
        }

        Spacer(Modifier.height(20.dp))

        // --------------------------
        // BLOQUE: CAMBIAR CONTRASEÑA
        // --------------------------
        SeguridadCard(title = "Cuenta") {

            SeguridadButtonItem(
                text = "Cambiar contraseña",
                icon = R.drawable.ic_seguridad,
                onClick = {
                    navController.navigate("cambiar_contrasena")
                }
            )

            SeguridadButtonItem(
                text = "Cerrar sesión en todos los dispositivos",
                icon = R.drawable.cierre_sesion,
                onClick = {
                    // authViewModel.logoutAll()
                }
            )
        }
    }
}

@Composable
fun SeguridadCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Text(text = title, fontSize = 18.sp, color = Color(0xFF842C46))

            Spacer(Modifier.height(10.dp))

            content()
        }
    }
}

@Composable
fun SeguridadSwitchItem(
    icon: Int,
    text: String,
    checked: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            painter = painterResource(id = icon),
            contentDescription = text,
            tint = Color(0xFFC5A3B3),
            modifier = Modifier.size(32.dp)
        )

        Spacer(Modifier.width(16.dp))

        Text(text, fontSize = 18.sp, color = Color(0xFF842C46))

        Spacer(Modifier.weight(1f))

        Switch(
            checked = checked,
            onCheckedChange = onToggle
        )
    }
}

@Composable
fun SeguridadButtonItem(
    icon: Int,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            painter = painterResource(id = icon),
            contentDescription = text,
            tint = Color(0xFFC5A3B3),
            modifier = Modifier.size(32.dp)
        )

        Spacer(Modifier.width(18.dp))

        Text(text, fontSize = 18.sp, color = Color(0xFF842C46))
    }
}
