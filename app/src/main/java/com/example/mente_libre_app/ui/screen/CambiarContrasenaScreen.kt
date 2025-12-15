package com.example.mente_libre_app.ui.screen

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mente_libre_app.R
import com.example.mente_libre_app.ui.components.BackArrowCustom
import com.example.mente_libre_app.ui.theme.LocalExtraColors
import com.example.mente_libre_app.ui.viewmodel.AuthViewModel

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
    val scheme = MaterialTheme.colorScheme
    val extra = LocalExtraColors.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(scheme.background)
            .padding(24.dp)
    ) {
        BackArrowCustom(
            navController = navController,
            color = extra.title,
            size = 62,
            stroke = 15f,
            modifier = Modifier.clickable { navController.popBackStack() }
        )

        Spacer(Modifier.height(20.dp))

        Text(
            text = "Cambiar Contraseña",
            fontSize = 30.sp,
            color = extra.title,
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


        val context = LocalContext.current
        Button(
            onClick = {
                errorActual = ""
                errorNueva = ""
                errorConfirmar = ""

                if (actual.isBlank()) errorActual = "Ingresa tu contraseña actual"
                if (nueva.isBlank()) errorNueva = "Ingresa la nueva contraseña"
                if (confirmar.isBlank()) errorConfirmar = "Confirma la nueva contraseña"

                if (nueva.length < 6) {
                    errorNueva = "Debe tener al menos 6 caracteres"
                }

                if (nueva != confirmar) {
                    errorConfirmar = "Las contraseñas no coinciden"
                }

                if (listOf(errorActual, errorNueva, errorConfirmar).any { it.isNotEmpty() }) return@Button

                authViewModel.changePassword(actual, nueva, confirmar) { result ->
                    if (result.success) {
                        Toast.makeText(context, "Contraseña cambiada con éxito", Toast.LENGTH_LONG).show()
                        navController.popBackStack()
                    } else {
                        // típico: contraseña actual incorrecta
                        errorActual = result.errorMsg ?: "Error al cambiar contraseña"
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(extra.buttonAlt)
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

    val scheme = MaterialTheme.colorScheme
    val extra = LocalExtraColors.current

    val borderColor by animateColorAsState(if (isFocused) extra.arrowBorder else scheme.onSurface)
    val borderWidth by animateDpAsState(if (isFocused) 2.dp else 1.dp)
    val textColor by animateColorAsState(if (isFocused) Color.Black else scheme.onSurface)
    val iconColor by animateColorAsState(if (isFocused) extra.arrowBorder else scheme.onSurface)
    val cursorColor = extra.arrowBorder

    Column {
        Text(
            text = label,
            color = extra.title,
            fontFamily = serifBold,
            fontSize = 15.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                if (!isFocused && value.isEmpty()) {
                    Text("Ingresa tu contraseña", color = scheme.onSurface)
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
                focusedContainerColor = extra.arrowBackground,
                unfocusedContainerColor = extra.arrowBackground,
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

