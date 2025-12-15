package com.example.mente_libre_app.ui.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
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
import com.example.mente_libre_app.R
import com.example.mente_libre_app.ui.components.GradientLoader
import com.example.mente_libre_app.ui.theme.ButtonMagenta
import com.example.mente_libre_app.ui.theme.Gray
import com.example.mente_libre_app.ui.theme.MainColor
import com.example.mente_libre_app.ui.viewmodel.AuthViewModel
import kotlinx.coroutines.delay

// --------------------------------------
// VM Wrapper para Login
// --------------------------------------
@Composable
fun IniciarScreenVm(
    authViewModel: AuthViewModel,
    onLoginSuccess: () -> Unit,
    onAdminLogin: () -> Unit,
    onRegisterClick: () -> Unit
) {
    val state by authViewModel.login.collectAsStateWithLifecycle()
    var showSuccessDialog by remember { mutableStateOf(false) }

    LaunchedEffect(state.success) {
        if (state.success) {
            showSuccessDialog = true
            authViewModel.clearLoginResult()
        }
    }

    IniciarScreen(
        correo = state.email,
        contrasena = state.pass,
        correoError = state.emailError,
        contrasenaError = state.passError,
        globalError = state.errorMsg,
        isSubmitting = state.isSubmitting,
        canSubmit = state.canSubmit,
        onCorreoChange = authViewModel::onLoginEmailChange,
        onContrasenaChange = authViewModel::onLoginPassChange,
        onSubmit = authViewModel::submitLogin,
        onRegisterClick = onRegisterClick
    )

    if (showSuccessDialog) {
        SuccessDialog2(
            onDismiss = {
                showSuccessDialog = false
                val rol = authViewModel.userRole.value ?: ""


                if (rol.uppercase().contains("ADMIN")) {
                    onAdminLogin()
                } else {
                    onLoginSuccess()
                }
            }
        )
    }
}

// --------------------------------------
// Pantalla Login
// --------------------------------------
@Composable
fun IniciarScreen(
    correo: String,
    contrasena: String,
    correoError: String? = null,
    contrasenaError: String? = null,
    globalError: String? = null,
    isSubmitting: Boolean = false,
    canSubmit: Boolean = true,
    onCorreoChange: (String) -> Unit,
    onContrasenaChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onRegisterClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val serifBold = FontFamily(Font(R.font.source_serif_pro_bold))
    val serifRegular = FontFamily(Font(R.font.source_serif_pro_regular))

    // FocusRequesters
    val correoFocus = remember { FocusRequester() }
    val contrasenaFocus = remember { FocusRequester() }
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFEAF4)),
        contentAlignment = Alignment.Center
    ) {

        // 🔹 Degradado que toca los bordes
        Box(
            modifier = Modifier
                .fillMaxWidth() // ocupa todo el ancho
                .height(160.dp)
                .clip(RoundedCornerShape(bottomStart = 120.dp, bottomEnd = 120.dp))
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFFD94775), Color(0xFFFCB5A7))
                    ))
                .align(Alignment.TopCenter) // fijarlo arriba
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(180.dp))

            Text(
                text = "Un gusto verte\nde nuevo!",
                color = Color(0xFF842C46),
                fontSize = 34.sp,
                lineHeight = 40.sp,
                fontFamily = serifBold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(25.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                CustomOutlinedTextField2(
                    label = "Correo Electrónico",
                    value = correo,
                    onValueChange = onCorreoChange,
                    placeholder = "Ingresa tu correo electrónico",
                    error = correoError,
                    modifier = Modifier.focusRequester(correoFocus),
                    nextFocusRequester = contrasenaFocus
                )

                CustomOutlinedTextField2(
                    label = "Contraseña",
                    value = contrasena,
                    onValueChange = onContrasenaChange,
                    placeholder = "Ingresa tu contraseña",
                    isPassword = true,
                    error = contrasenaError,
                    modifier = Modifier.focusRequester(contrasenaFocus),
                    onDoneAction = { if (canSubmit && !isSubmitting) onSubmit() }
                )

                Box(
                    modifier = Modifier
                        .height(19.dp)   // espacio fijo
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = globalError ?: "",
                        color = Color.Red,
                        fontFamily = serifRegular,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(5.dp))

                // Botón
                val buttonColor by animateColorAsState(
                    targetValue = when {
                        !canSubmit -> Gray
                        isPressed -> MainColor
                        else -> ButtonMagenta
                    }
                )
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = { if (canSubmit && !isSubmitting) onSubmit() },
                        enabled = true,
                        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                        shape = RoundedCornerShape(50.dp),
                        interactionSource = interactionSource,
                        modifier = Modifier
                            .width(230.dp) // o el ancho que quieras
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
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(1.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "¿No tienes una cuenta?",
                    color = Color(0xFF842C46),
                    fontSize = 16.sp,
                    fontFamily = serifRegular
                )
                TextButton(onClick = onRegisterClick) {
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
fun CustomOutlinedTextField2(
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

    // control del "ver contraseña"
    var passwordVisible by remember { mutableStateOf(false) }

    // Animaciones de color según el foco
    val borderColor by animateColorAsState(
        targetValue = if (isFocused) Color(0xFFF95C1E) else Color(0xFF8688A8)
    )
    val borderWidth by animateDpAsState(
        targetValue = if (isFocused) 2.dp else 1.dp
    )
    val textColor by animateColorAsState(
        targetValue = if (isFocused) Color.Black else Color(0xFF8688A8)
    )

    // color dinámico para el icono según foco
    val iconColor by animateColorAsState(
        targetValue = if (isFocused) Color(0xFFF95C1E) else Color(0xFF8688A8)
    )

    val cursorColor = Color(0xFFF95C1E)

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

        trailingIcon = {
            if (isPassword && value.isNotEmpty()) {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible)
                            Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = if (passwordVisible)
                            "Ocultar contraseña" else "Ver contraseña",
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
        modifier = modifier
            .fillMaxWidth()
            .height(55.dp)
            .border(borderWidth, borderColor, RoundedCornerShape(20.dp))
            .onFocusChanged { focusState -> isFocused = focusState.isFocused },
        keyboardOptions = KeyboardOptions(
            imeAction = if (nextFocusRequester != null) ImeAction.Next else ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onNext = { nextFocusRequester?.requestFocus() }, // 👈 va al siguiente
            onDone = { onDoneAction?.invoke() }              // 👈 si es el último, ejecuta acción
        )
    )

    // Mensaje de error (si existe)
    Box(
        modifier = Modifier
            .height(19.dp)   // espacio fijo aunque no haya error
            .fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
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


@Composable
fun SuccessDialog2(onDismiss: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .blur(13.dp)
                .background(Color.Black.copy(alpha = 0.5f))
        )

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
                    text = "¡Inicio de sesión\ncorrecto!",
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

        LaunchedEffect(Unit) {
            delay(3000)
            onDismiss()
        }
    }
}
