package com.example.mente_libre_app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mente_libre_app.R
import com.example.mente_libre_app.ui.theme.ButtonMagenta
import com.example.mente_libre_app.ui.theme.MainColor

@Composable
fun CrearScreen(
    onComenzarClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    val serifBold = FontFamily(Font(R.font.source_serif_pro_bold))
    val serifRegular = FontFamily(Font(R.font.source_serif_pro_regular))

    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var confirmarContrasena by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFEAF4)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(30.dp))

            // 🔹 Título
            Text(
                text = "Bienvenid@!",
                color = Color(0xFF842C46),
                fontSize = 34.sp,
                fontFamily = serifBold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(30.dp))

            // 🔹 Campos de texto
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                CustomOutlinedTextField(
                    label = "Nombre",
                    value = nombre,
                    onValueChange = { nombre = it },
                    placeholder = "Apodo favorito",
                    modifier = Modifier.weight(1f)
                )

                CustomOutlinedTextField(
                    label = "Teléfono",
                    value = telefono,
                    onValueChange = { telefono = it },
                    placeholder = "Teléfono",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            CustomOutlinedTextField(
                label = "Correo Electrónico",
                value = correo,
                onValueChange = { correo = it },
                placeholder = "Ingresa tu correo electrónico"
            )

            Spacer(modifier = Modifier.height(14.dp))

            CustomOutlinedTextField(
                label = "Contraseña",
                value = contrasena,
                onValueChange = { contrasena = it },
                placeholder = "Ingresa tu contraseña",
                isPassword = true
            )

            Spacer(modifier = Modifier.height(14.dp))

            CustomOutlinedTextField(
                label = "Confirmar contraseña",
                value = confirmarContrasena,
                onValueChange = { confirmarContrasena = it },
                placeholder = "Ingresa tu contraseña",
                isPassword = true
            )

            Spacer(modifier = Modifier.height(25.dp))

            // 🔹 Íconos de redes sociales
            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SocialIcon(R.drawable.google_icon)
                SocialIcon(R.drawable.gmail_icon)
                SocialIcon(R.drawable.facebook_icon)
            }

            Spacer(modifier = Modifier.height(35.dp))

            // 🔹 Botón principal
            Button(
                onClick = { onComenzarClick() },
                colors = ButtonDefaults.buttonColors(containerColor = ButtonMagenta),
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(65.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Comenzar",
                        color = Color.White,
                        fontSize = 26.sp,
                        fontFamily = serifBold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Rounded.ArrowForward,
                        contentDescription = "Ir adelante",
                        tint = Color.White,
                        modifier = Modifier.size(26.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 🔹 Texto inferior
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "¿Ya tienes una cuenta?",
                    color = Color(0xFF842C46),
                    fontSize = 16.sp,
                    fontFamily = serifRegular
                )
                TextButton(onClick = { onLoginClick() }) {
                    Text(
                        text = "Ingresa aquí",
                        color = Color(0xFFF95C1E),
                        fontSize = 16.sp,
                        fontFamily = serifRegular
                    )
                }
            }
        }
    }
}

@Composable
fun CustomOutlinedTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false
) {
    val serifRegular = FontFamily(Font(R.font.source_serif_pro_regular))

    Column(modifier = modifier) {
        Text(
            text = label,
            color = Color(0xFF842C46),
            fontWeight = FontWeight.Bold,
            fontFamily = serifRegular,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = Color(0xFFB08686)) },
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFFFD2A6),
                unfocusedContainerColor = Color(0xFFFFD2A6),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        )
    }
}

@Composable
fun SocialIcon(iconRes: Int) {
    IconButton(
        onClick = { /* Acción social */ },
        modifier = Modifier
            .size(60.dp)
            .border(2.dp, Color(0xFF842C46), CircleShape)
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = Color(0xFF842C46),
            modifier = Modifier.size(30.dp)
        )
    }
}
