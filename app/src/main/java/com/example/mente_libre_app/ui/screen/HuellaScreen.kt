package com.example.mente_libre_app.ui.screen

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.mente_libre_app.R
import com.example.mente_libre_app.ui.theme.LocalExtraColors

@Composable
fun HuellaScreen(
    activity: FragmentActivity,
    onVerificado: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val colorScheme = MaterialTheme.colorScheme
    val extra = LocalExtraColors.current

    val context = LocalContext.current
    val serifBold = FontFamily(Font(R.font.source_serif_pro_bold))
    val serifRegular = FontFamily(Font(R.font.source_serif_pro_regular))

    var verificado by remember { mutableStateOf(false) }
    var mensajeError by remember { mutableStateOf<String?>(null) }
    var tieneHuella by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        tieneHuella = puedeUsarBiometria(context)
        if (tieneHuella) {
            autenticarHuella(
                activity,
                onAutenticado = { verificado = true },
                onError = { mensajeError = it }
            )
        } else {
            mensajeError = "No hay huellas registradas. Ve a Ajustes para agregar una."
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)   // FONDO DINÁMICO
            .padding(30.dp),
        contentAlignment = Alignment.Center
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            // TÍTULO DINÁMICO
            Text(
                text = "Huella Dactilar",
                color = extra.title,
                fontFamily = serifBold,
                fontSize = 35.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // DESCRIPCIÓN DINÁMICA
            Text(
                text = "Escanea tu huella dactilar para que el ingreso a tu aplicación sea más seguro. Puedes desactivar esta opción luego en ajustes.",
                color = colorScheme.onBackground,
                fontFamily = serifRegular,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                lineHeight = 25.sp
            )

            Spacer(modifier = Modifier.height(40.dp))

            // ICONO HUELLAS DINÁMICO
            Icon(
                painter = painterResource(id = R.drawable.huella_dactilar),
                contentDescription = "Huella digital",
                tint = extra.title,
                modifier = Modifier.size(150.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            mensajeError?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    fontFamily = serifRegular,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(30.dp))

            if (verificado) {
                // BOTÓN SIGUIENTE (dinámico)
                Button(
                    onClick = { onVerificado() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = extra.buttonAlt,
                        contentColor = colorScheme.onPrimary
                    ),
                    shape = RoundedCornerShape(50.dp),
                    interactionSource = interactionSource,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(65.dp)
                ) {
                    Text(
                        text = "Siguiente",
                        color = colorScheme.onPrimary,
                        fontFamily = serifRegular,
                        fontSize = 30.sp
                    )
                }

            } else {
                if (tieneHuella) {

                    Text(
                        text = "Esperando autenticación...",
                        color = extra.title,
                        fontFamily = serifRegular,
                        fontSize = 15.sp
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    // BOTÓN REINTENTAR (dinámico)
                    Button(
                        onClick = {
                            autenticarHuella(
                                activity,
                                onAutenticado = { verificado = true },
                                onError = { mensajeError = it }
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = extra.buttonAlt,
                            contentColor = colorScheme.onPrimary
                        ),
                        shape = RoundedCornerShape(50.dp),
                        interactionSource = interactionSource,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(65.dp)
                    ) {
                        Text(
                            text = "Intentar de nuevo",
                            color = colorScheme.onPrimary,
                            fontFamily = serifRegular,
                            fontSize = 23.sp
                        )
                    }

                } else {
                    // Botón para registrar huella (tema neutro)
                    Button(
                        onClick = {
                            val intent = Intent(Settings.ACTION_FINGERPRINT_ENROLL)
                            context.startActivity(intent)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = extra.buttonAlt
                        )
                    ) {
                        Text("Registrar huella en ajustes")
                    }
                }
            }
        }
    }
}

// Verifica si el dispositivo tiene huellas registradas
fun puedeUsarBiometria(context: Context): Boolean {
    val biometricManager = BiometricManager.from(context)
    val authenticators = BiometricManager.Authenticators.BIOMETRIC_STRONG or
            BiometricManager.Authenticators.DEVICE_CREDENTIAL
    return biometricManager.canAuthenticate(authenticators) == BiometricManager.BIOMETRIC_SUCCESS
}

// Función de autenticación biométrica
fun autenticarHuella(
    activity: FragmentActivity,
    onAutenticado: () -> Unit,
    onError: (String) -> Unit
) {
    val executor = ContextCompat.getMainExecutor(activity)
    val biometricPrompt = BiometricPrompt(
        activity,
        executor,
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                onAutenticado()
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    onError("Autenticación cancelada.")
                } else {
                    onError(errString.toString())
                }
            }
        }
    )

    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Verificación de identidad")
        .setSubtitle("Coloca tu dedo en el sensor o usa tu PIN para continuar")
        .setAllowedAuthenticators(
            BiometricManager.Authenticators.BIOMETRIC_STRONG or
                    BiometricManager.Authenticators.DEVICE_CREDENTIAL
        )
        .build()

    biometricPrompt.authenticate(promptInfo)
}
