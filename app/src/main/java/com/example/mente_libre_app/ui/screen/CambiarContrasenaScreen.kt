package com.example.mente_libre_app.ui.screen

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mente_libre_app.R
import com.example.mente_libre_app.ui.components.BackArrowCustom
import com.example.mente_libre_app.ui.viewmodel.AuthViewModel
import com.example.mente_libre_app.ui.theme.LocalExtraColors
import kotlinx.coroutines.launch

@Composable
fun CambiarContrasenaScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    val serifRegular = FontFamily(Font(R.font.source_serif_pro_regular))
    var actual by remember { mutableStateOf("") }
    var nueva by remember { mutableStateOf("") }
    var confirmar by remember { mutableStateOf("") }

    var errorActual by remember { mutableStateOf("") }
    var errorNueva by remember { mutableStateOf("") }
    var errorConfirmar by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFEAF4))
            .padding(24.dp)
    ) {
        BackArrowCustom(
            navController = navController,
            color = Color(0xFF842C46),
            size = 62,
            stroke = 15f,
            modifier = Modifier.clickable { navController.popBackStack() }
        )

        Spacer(Modifier.height(20.dp))

        Text(
            text = "Cambiar Contraseña",
            fontSize = 30.sp,
            color = Color(0xFF842C46),
            fontFamily = serifRegular
        )

        Spacer(Modifier.height(20.dp))

        PasswordField(
            label = "Contraseña actual",
            value = actual,
            onValueChange = { actual = it },
            error = errorActual
        )

        Spacer(Modifier.height(14.dp))

        PasswordField(
            label = "Nueva contraseña",
            value = nueva,
            onValueChange = { nueva = it },
            error = errorNueva
        )

        Spacer(Modifier.height(14.dp))

        PasswordField(
            label = "Confirmar contraseña",
            value = confirmar,
            onValueChange = { confirmar = it },
            error = errorConfirmar
        )

        Spacer(Modifier.height(30.dp))

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFDF5078))
        ) {
            Text("Confirmar", color = Color.White, fontSize = 22.sp)
        }
    }
}

@Composable
fun PasswordField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    error: String? = null
) {
    val serifBold = FontFamily(Font(R.font.source_serif_pro_bold))
    val serifRegular = FontFamily(Font(R.font.source_serif_pro_regular))

    var isFocused by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

    val borderColor by animateColorAsState(if (isFocused) Color(0xFFF95C1E) else Color(0xFF8688A8))
    val borderWidth by animateDpAsState(if (isFocused) 2.dp else 1.dp)
    val textColor by animateColorAsState(if (isFocused) Color.Black else Color(0xFF8688A8))
    val iconColor by animateColorAsState(if (isFocused) Color(0xFFF95C1E) else Color(0xFF8688A8))
    val cursorColor = Color(0xFFF95C1E)

    Column {
        Text(
            text = label,
            color = Color(0xFF842C46),
            fontFamily = serifBold,
            fontSize = 15.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                if (!isFocused && value.isEmpty()) {
                    Text("Ingresa tu contraseña", color = Color(0xFF8688A8))
                }
            },
            visualTransformation = if (!passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = {
                if (value.isNotEmpty()) {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible)
                                Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = null,
                            tint = iconColor
                        )
                    }
                }
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFFFD3B1),
                unfocusedContainerColor = Color(0xFFFFD3B1),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = cursorColor
            ),
            textStyle = androidx.compose.ui.text.TextStyle(
                color = textColor,
                fontFamily = serifRegular,
                fontSize = 16.sp
            ),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .border(borderWidth, borderColor, RoundedCornerShape(20.dp))
                .onFocusChanged { isFocused = it.isFocused }
        )

        if (!error.isNullOrEmpty()) {
            Text(error, color = Color.Red, fontSize = 12.sp)
        }
    }
}

