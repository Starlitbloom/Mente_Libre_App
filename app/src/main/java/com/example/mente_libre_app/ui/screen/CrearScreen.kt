package com.example.mente_libre_app.ui.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mente_libre_app.R
import com.example.mente_libre_app.ui.components.GradientLoader
import com.example.mente_libre_app.ui.theme.ButtonMagenta
import com.example.mente_libre_app.ui.theme.Gray
import com.example.mente_libre_app.ui.theme.MainColor
import com.example.mente_libre_app.ui.viewmodel.AuthViewModel

// --------------------------------------
// VM Wrapper
// --------------------------------------
@Composable
fun CrearScreenVm(
    authViewModel: AuthViewModel,
    onComenzarClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    val state by authViewModel.register.collectAsStateWithLifecycle()
    var showSuccessDialog by remember { mutableStateOf(false) }

    LaunchedEffect(state.success) {
        if (state.success) {
            showSuccessDialog = true
            authViewModel.clearRegisterResult()
        }
    }

    CrearScreen(
        nombre = state.name,
        telefono = state.phone,
        correo = state.email,
        contrasena = state.pass,
        confirmarContrasena = state.confirm,
        nombreError = state.nameError,
        telefonoError = state.phoneError,
        correoError = state.emailError,
        contrasenaError = state.passError,
        confirmarContrasenaError = state.confirmError,
        globalError = state.errorMsg,
        isSubmitting = state.isSubmitting,
        canSubmit = state.canSubmit,
        onNombreChange = authViewModel::onNameChange,
        onTelefonoChange = authViewModel::onPhoneChange,
        onCorreoChange = authViewModel::onEmailChange,
        onContrasenaChange = authViewModel::onPassChange,
        onConfirmarContrasenaChange = authViewModel::onConfirmChange,
        onSubmit = authViewModel::submitRegister,
        onLoginClick = onLoginClick
    )
    // 🔹 Mostramos el mensaje de éxito con blur
    if (showSuccessDialog) {
        SuccessDialog(
            onDismiss = {
                showSuccessDialog = false
                onComenzarClick()
            }
        )
    }
}


// --------------------------------------
// Pantalla Crear
// --------------------------------------
@Composable
fun CrearScreen(
    nombre: String,
    telefono: String,
    correo: String,
    contrasena: String,
    confirmarContrasena: String,
    nombreError: String? = null,
    telefonoError: String? = null,
    correoError: String? = null,
    contrasenaError: String? = null,
    confirmarContrasenaError: String? = null,
    globalError: String? = null,
    isSubmitting: Boolean = false,
    canSubmit: Boolean = true,
    onNombreChange: (String) -> Unit,
    onTelefonoChange: (String) -> Unit,
    onCorreoChange: (String) -> Unit,
    onContrasenaChange: (String) -> Unit,
    onConfirmarContrasenaChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onLoginClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val serifBold = FontFamily(Font(R.font.source_serif_pro_bold))
    val serifRegular = FontFamily(Font(R.font.source_serif_pro_regular))

    // FocusRequesters para cada campo
    val nombreFocus = remember { FocusRequester() }
    val telefonoFocus = remember { FocusRequester() }
    val correoFocus = remember { FocusRequester() }
    val contrasenaFocus = remember { FocusRequester() }
    val confirmarFocus = remember { FocusRequester() }

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

            Text(
                text = "Bienvenid@!",
                color = Color(0xFF842C46),
                fontSize = 34.sp,
                fontFamily = serifBold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(25.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                CustomOutlinedTextField(
                    label = "Nombre",
                    value = nombre,
                    onValueChange = onNombreChange,
                    placeholder = "Apodo",
                    error = nombreError,
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(nombreFocus),
                    nextFocusRequester = telefonoFocus
                )

                CustomOutlinedTextField(
                    label = "Teléfono",
                    value = telefono,
                    onValueChange = onTelefonoChange,
                    placeholder = "Teléfono",
                    error = telefonoError,
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(telefonoFocus),
                    nextFocusRequester = correoFocus
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            CustomOutlinedTextField(
                label = "Correo Electrónico",
                value = correo,
                onValueChange = onCorreoChange,
                placeholder = "Ingresa tu correo electrónico",
                error = correoError,
                modifier = Modifier.focusRequester(correoFocus),
                nextFocusRequester = contrasenaFocus
            )

            Spacer(modifier = Modifier.height(10.dp))

            CustomOutlinedTextField(
                label = "Contraseña",
                value = contrasena,
                onValueChange = onContrasenaChange,
                placeholder = "Ingresa tu contraseña",
                isPassword = true,
                error = contrasenaError,
                modifier = Modifier.focusRequester(contrasenaFocus),
                nextFocusRequester = confirmarFocus
            )

            Spacer(modifier = Modifier.height(10.dp))

            CustomOutlinedTextField(
                label = "Confirmar contraseña",
                value = confirmarContrasena,
                onValueChange = onConfirmarContrasenaChange,
                placeholder = "Confirma tu contraseña",
                isPassword = true,
                error = confirmarContrasenaError,
                modifier = Modifier.focusRequester(confirmarFocus),
                onDoneAction = { if (canSubmit && !isSubmitting) onSubmit() }
            )

            // Mensaje de error global
            globalError?.let { error ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = error,
                    color = Color.Red,
                    fontFamily = serifRegular,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Botón
            val buttonColor by animateColorAsState(
                targetValue = when {
                    !canSubmit -> Gray
                    isPressed -> MainColor
                    else -> ButtonMagenta
                }
            )

            Button(
                onClick = { if (canSubmit && !isSubmitting) onSubmit() },
                enabled = true,
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                shape = RoundedCornerShape(50.dp),
                interactionSource = interactionSource,
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(65.dp)
            ) {
                if (isSubmitting) {
                    CircularProgressIndicator(
                        color = Color.White,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Comenzar",
                            color = Color.White,
                            fontWeight = FontWeight.Normal,
                            fontFamily = serifRegular,
                            fontSize = 34.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Rounded.ArrowForward,
                            contentDescription = "Ir adelante",
                            tint = Color.White,
                            modifier = Modifier.size(29.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(1.dp))

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
                TextButton(onClick = onLoginClick) {
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
    isPassword: Boolean = false,
    error: String? = null,
    nextFocusRequester: FocusRequester? = null,
    onDoneAction: (() -> Unit)? = null
) {
    val serifBold = FontFamily(Font(R.font.source_serif_pro_bold))
    val serifRegular = FontFamily(Font(R.font.source_serif_pro_regular))
    var isFocused by remember { mutableStateOf(false) }

    // 👁️ control del "ver contraseña"
    var passwordVisible by remember { mutableStateOf(false) }

    // 🎨 Animaciones de color según el foco
    val borderColor by animateColorAsState(
        targetValue = if (isFocused) Color(0xFFF95C1E) else Color(0xFF8688A8)
    )
    val borderWidth by animateDpAsState(
        targetValue = if (isFocused) 2.dp else 1.dp
    )
    val textColor by animateColorAsState(
        targetValue = if (isFocused) Color.Black else Color(0xFF8688A8)
    )

    // 👁️ color dinámico para el icono según foco
    val iconColor by animateColorAsState(
        targetValue = if (isFocused) Color(0xFFF95C1E) else Color(0xFF8688A8)
    )

    val cursorColor = Color(0xFFF95C1E)

    Column(modifier = modifier) {
        Text(
            text = label,
            color = Color(0xFF842C46),
            fontWeight = FontWeight.Bold,
            fontFamily = serifBold,
            fontSize = 15.sp
        )
        Spacer(modifier = Modifier.height(4.dp))

        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                if (!isFocused && value.isEmpty()) {
                    Text(placeholder, color = Color(0xFF8688A8), fontFamily = serifRegular)
                }
            },
            visualTransformation = if (isPassword && !passwordVisible)
                PasswordVisualTransformation() else VisualTransformation.None,

            // 👁️ Solo mostrar ícono si el campo NO está vacío
            trailingIcon = {
                if (isPassword && value.isNotEmpty()) {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible)
                                Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = if (passwordVisible)
                                "Ocultar contraseña" else "Ver contraseña",
                            tint = iconColor // 👈 cambia según foco (naranjo o gris)
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
                .onFocusChanged { focusState -> isFocused = focusState.isFocused },
            keyboardOptions = KeyboardOptions(
                imeAction = if (nextFocusRequester != null) ImeAction.Next else ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onNext = { nextFocusRequester?.requestFocus() },
                onDone = { onDoneAction?.invoke() }
            )
        )

        // 🔴 Mensaje de error (si existe)
        Box(
            modifier = Modifier
                .height(19.dp)
                .fillMaxWidth()
        ) {
            if (!error.isNullOrEmpty()) {
                Text(
                    text = error,
                    color = Color.Red,
                    fontSize = 12.sp,
                    fontFamily = serifRegular,
                    maxLines = 1
                )
            }
        }
    }
}
@Composable
fun SuccessDialog(onDismiss: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // 🔹 Fondo semitransparente + desenfoque
        Box(
            modifier = Modifier
                .matchParentSize()
                .blur(13.dp) // desenfoca solo el fondo
                .background(Color.Black.copy(alpha = 0.5f))
        )

        // 🔹 Contenido nítido
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEAF4)),
            modifier = Modifier
                .width(260.dp)
                .height(280.dp)
                .border(
                    width = 1.dp,
                    color = Color(0xFFF95C1E),
                    shape = RoundedCornerShape(24.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "¡Cuenta creada\ncorrectamente!",
                    color = Color(0xFF842C46),
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.source_serif_pro_bold)),
                    fontSize = 22.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                GradientLoader(modifier = Modifier.size(90.dp))

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Cargando...",
                    color = Color(0xFF842C46),
                    fontFamily = FontFamily(Font(R.font.source_serif_pro_regular)),
                    fontSize = 16.sp
                )
            }
        }

        // 🔹 Cierra automáticamente tras 2 segundos
        LaunchedEffect(Unit) {
            kotlinx.coroutines.delay(3000)
            onDismiss()
        }
    }
}

