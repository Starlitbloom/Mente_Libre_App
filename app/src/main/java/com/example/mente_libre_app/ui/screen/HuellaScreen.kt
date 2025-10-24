package com.example.mente_libre_app.ui.screen

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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

@Composable
fun HuellaScreen(
    activity: FragmentActivity, // Activity segura
    onVerificado: () -> Unit
) {
    val context = LocalContext.current
    val serifBold = FontFamily(Font(R.font.source_serif_pro_bold))
    val serifRegular = FontFamily(Font(R.font.source_serif_pro_regular))

    var verificado by remember { mutableStateOf(false) }
    var mensajeError by remember { mutableStateOf<String?>(null) }
    var tieneHuella by remember { mutableStateOf(false) }

    // Ejecutar autenticación al iniciar la pantalla
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
            .background(Color(0xFFFFEAF4))
            .padding(30.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                text = "Huellas Dactilares",
                color = Color(0xFF842C46),
                fontFamily = serifBold,
                fontSize = 28.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Escanea tus huellas dactilares para que el ingreso a tu aplicación sea más seguro.",
                color = Color(0xFF842C46),
                fontFamily = serifRegular,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(40.dp))

            Icon(
                painter = painterResource(id = R.drawable.huella_dactilar),
                contentDescription = "Huella digital",
                tint = Color.White,
                modifier = Modifier.size(150.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            mensajeError?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    fontFamily = serifRegular,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            if (verificado) {
                Button(
                    onClick = onVerificado,
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD94775)),
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(60.dp)
                ) {
                    Text(
                        text = "Siguiente",
                        fontFamily = serifBold,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }
            } else {
                if (tieneHuella) {
                    Text(
                        text = "Esperando autenticación...",
                        color = Color(0xFF842C46),
                        fontFamily = serifRegular,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            autenticarHuella(
                                activity,
                                onAutenticado = { verificado = true },
                                onError = { mensajeError = it }
                            )
                        },
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD94775)),
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .height(60.dp)
                    ) {
                        Text(
                            text = "Intentar de nuevo",
                            fontFamily = serifBold,
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    }
                } else {
                    Button(onClick = {
                        val intent = Intent(Settings.ACTION_FINGERPRINT_ENROLL)
                        context.startActivity(intent)
                    }) {
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

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                onError("No se reconoció la huella.")
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
