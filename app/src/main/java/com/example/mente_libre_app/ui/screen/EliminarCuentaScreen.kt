package com.example.mente_libre_app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mente_libre_app.R
import com.example.mente_libre_app.ui.components.BackArrowCustom
import com.example.mente_libre_app.ui.components.GradientLoader
import com.example.mente_libre_app.ui.theme.LocalExtraColors
import com.example.mente_libre_app.ui.viewmodel.UsuarioViewModel
import com.example.mente_libre_app.ui.viewmodel.AuthViewModel

@Composable
fun EliminarCuentaScreen(
    navController: NavHostController,
    usuarioViewModel: UsuarioViewModel,
    authViewModel: AuthViewModel
) {
    val scheme = MaterialTheme.colorScheme
    val extra = LocalExtraColors.current

    val serifBold = FontFamily(Font(R.font.source_serif_pro_bold))
    val serifRegular = FontFamily(Font(R.font.source_serif_pro_regular))

    var motivo by remember { mutableStateOf("No la uso lo suficiente") }
    var expanded by remember { mutableStateOf(false) }

    // ESTADOS --- deben estar AFUERA del Column
    var mostrandoLoading by remember { mutableStateOf(false) }
    var navegarDespues by remember { mutableStateOf(false) }

    val motivos = listOf(
        "No la uso lo suficiente",
        "Problemas técnicos",
        "Preocupación por privacidad",
        "No encontré lo que buscaba",
        "Otro"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(scheme.background)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {

            // Flecha
            BackArrowCustom(
                navController = navController,
                color = extra.title,
                size = 68,
                stroke = 18f,
                modifier = Modifier.clickable { navController.popBackStack() }
            )

            Spacer(Modifier.height(10.dp))

            Text(
                text = "Eliminar cuenta",
                fontFamily = serifBold,
                fontSize = 28.sp,
                color = extra.title
            )

            Spacer(Modifier.height(10.dp))

            Text(
                text = "Lamentamos que quieras irte. Antes de proceder, ten en cuenta que al eliminar tu cuenta, todos tus datos y configuraciones serán permanentemente eliminados...",
                color = extra.title,
                fontFamily = serifRegular,
                fontSize = 14.sp
            )

            Spacer(Modifier.height(20.dp))

            Text(
                text = "Por favor, cuéntanos el motivo...",
                fontFamily = serifRegular,
                fontSize = 14.sp,
                color = extra.title
            )

            Spacer(Modifier.height(20.dp))

            // DROPDOWN
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .background(scheme.onPrimary, RoundedCornerShape(20.dp))
                    .border(2.dp, extra.title, RoundedCornerShape(20.dp))
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { expanded = true }
                ) {
                    Text(
                        text = motivo,
                        fontFamily = serifRegular,
                        fontSize = 16.sp,
                        color = extra.title
                    )

                    Spacer(Modifier.weight(1f))

                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        tint = extra.title
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    motivos.forEach { m ->
                        DropdownMenuItem(
                            text = { Text(m) },
                            onClick = {
                                motivo = m
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(30.dp))

            // BOTÓN ELIMINAR
            Button(
                onClick = {
                    mostrandoLoading = true

                    usuarioViewModel.eliminarPerfil { ok ->
                        if (ok) {
                            authViewModel.logout()
                            navegarDespues = true  // activar navegación retrasada
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(extra.buttonAlt),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    text = "Eliminar",
                    fontFamily = serifRegular,
                    fontSize = 20.sp,
                    color = Color.White
                )
            }

            Spacer(Modifier.height(30.dp))
        }

        // Loading sobre toda la pantalla
        if (mostrandoLoading) {
            EliminarCuentaDialogLoading()
        }

        // EFECTO DE NAVEGACIÓN DIFERIDA (3 segundos)
        if (navegarDespues) {
            LaunchedEffect(Unit) {
                kotlinx.coroutines.delay(3000)
                navController.navigate("inicio") {
                    popUpTo(0) { inclusive = true }
                }
            }
        }
    }
}

@Composable
fun EliminarCuentaDialogLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .blur(13.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEAF4)),
            modifier = Modifier
                .width(260.dp)
                .height(280.dp)
                .border(1.dp, Color(0xFFF95C1E), RoundedCornerShape(24.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Gracias por haber sido\nparte de nuestra\ncomunidad.\nSi decides volver,\nsiempre serás\nbienvenid@.",
                    color = Color(0xFF842C46),
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.source_serif_pro_bold)),
                    fontSize = 18.sp
                )

                Spacer(Modifier.height(20.dp))

                GradientLoader(modifier = Modifier.size(90.dp))

                Spacer(Modifier.height(10.dp))

                Text(
                    text = "Cargando...",
                    color = Color(0xFF842C46),
                    fontFamily = FontFamily(Font(R.font.source_serif_pro_regular)),
                    fontSize = 16.sp
                )
            }
        }
    }
}
