package com.example.mente_libre_app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.example.mente_libre_app.ui.theme.LocalExtraColors

@Composable
fun BottomNavigationBar(
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    val extra = LocalExtraColors.current
    val scheme = MaterialTheme.colorScheme

    Surface(
        color = scheme.onSecondary,
        shadowElevation = 16.dp,
        shape = RoundedCornerShape(topStart = 26.dp, topEnd = 26.dp)
    ) {
        NavigationBar(
            containerColor = Color.White,
            tonalElevation = 0.dp
        ) {
            val items = listOf(
                Triple("inicio", R.drawable.ic_inicio, "Inicio"),
                Triple("companera", R.drawable.ic_chat, "Companera"),
                Triple("diario", R.drawable.ic_diario, "Diario"),
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
                                    if (isSelected) scheme.background else Color.Transparent
                                )
                                .clickable {
                                    onItemSelected(route)
                                }
                        ) {
                            Icon(
                                painter = painterResource(icon),
                                contentDescription = description,
                                tint = if (isSelected) extra.title else extra.icon,
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
