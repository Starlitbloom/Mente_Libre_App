package com.example.mente_libre_app.ui.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mente_libre_app.R
import com.example.mente_libre_app.ui.components.BottomNavigationBar

@Composable
fun AjustesScreen(
    selectedItem: String = "ajustes",
    onItemSelected: (String) -> Unit,
    onPerfilClick: () -> Unit,
    onSeguridadClick: () -> Unit,
    onNotificacionesClick: () -> Unit,
    onDispositivosClick: () -> Unit,
    onEmergenciaClick: () -> Unit,
    onSoporteClick: () -> Unit,
    onSugerenciasClick: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    val serifBold = FontFamily(Font(R.font.source_serif_pro_bold))
    val serifRegular = FontFamily(Font(R.font.source_serif_pro_regular))

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedItem = selectedItem,
                onItemSelected = onItemSelected
            )
        },
        containerColor = Color(0xFFFFEAF4)
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding) // ✅ Esto evita que el contenido quede detrás de la barra
                .fillMaxSize()
                .background(Color(0xFFFFEAF4))
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(25.dp))
            // 🔹 Título
            Text(
                text = "Ajustes",
                color = Color(0xFF842C46),
                fontFamily = serifBold,
                fontSize = 29.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(30.dp))

            // 🔸 Primera sección
            AjusteCard(
                items = listOf(
                    AjusteItem("Mi Perfil", R.drawable.ic_perfil, onPerfilClick),
                    AjusteItem("Seguridad", R.drawable.ic_seguridad, onSeguridadClick),
                    AjusteItem("Notificaciones", R.drawable.ic_notificacion, onNotificacionesClick),
                    AjusteItem("Disp. Conectados", R.drawable.ic_dispositivos, onDispositivosClick),
                    AjusteItem("Contacto de Emergencia", R.drawable.ic_emergencia, onEmergenciaClick)
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 🔸 Segunda sección
            AjusteCard(
                items = listOf(
                    AjusteItem("Soporte", R.drawable.ic_soporte, onSoporteClick),
                    AjusteItem("Sugerencias", R.drawable.ic_sugerencia, onSugerenciasClick)
                )
            )

            Spacer(modifier = Modifier.height(35.dp))

            // 🔹 Botón cerrar sesión
            Button(
                onClick = onCerrarSesion,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8688A8)),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(70.dp)
            ) {
                Text(
                    text = "Cerrar Sesión",
                    fontFamily = serifRegular,
                    fontSize = 29.sp,
                    color = Color.White
                )
            }
        }
    }
}

// 🔸 Card con opciones
@Composable
fun AjusteCard(items: List<AjusteItem>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            items.forEachIndexed { index, item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { item.onClick() }
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.text,
                        tint = Color(0xFFC5A3B3),
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Text(
                        text = item.text,
                        fontSize = 18.sp,
                        color = Color(0xFF842C46),
                        fontFamily = FontFamily(Font(R.font.source_serif_pro_regular))
                    )
                }
                if (index != items.lastIndex) {
                    Divider(
                        color = Color(0xFF842C46),
                        thickness = 1.dp,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                }
            }
        }
    }
}

// 🔸 Modelo de cada ítem
data class AjusteItem(
    val text: String,
    val icon: Int,
    val onClick: () -> Unit
)