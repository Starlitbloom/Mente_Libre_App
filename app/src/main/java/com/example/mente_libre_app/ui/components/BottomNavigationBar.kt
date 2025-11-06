package com.example.mente_libre_app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mente_libre_app.R

@Composable
fun BottomNavigationBar(
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    Surface(
        color = Color.White,
        shadowElevation = 16.dp,
        shape = RoundedCornerShape(topStart = 26.dp, topEnd = 26.dp)
    ) {
        NavigationBar(
            containerColor = Color.White,
            tonalElevation = 0.dp
        ) {
            val items = listOf(
                Triple("inicio", R.drawable.ic_inicio, "Inicio"),
                Triple("conexion", R.drawable.ic_chat, "Conexión"),
                Triple("texto", R.drawable.ic_diario, "Texto"),
                Triple("ajustes", R.drawable.ic_ajustes, "Ajustes")
            )

            items.forEach { (route, icon, description) ->
                val isSelected = selectedItem == route

                NavigationBarItem(
                    selected = false,
                    onClick = {},
                    icon = {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isSelected) Color(0xFFFFEAF4) else Color.Transparent
                                )
                                .clickable {
                                    onItemSelected(route)
                                }
                        ) {
                            Icon(
                                painter = painterResource(icon),
                                contentDescription = description,
                                tint = if (isSelected) Color(0xFF842C46) else Color(0xFFC5A3B3),
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    },
                    colors = androidx.compose.material3.NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}
