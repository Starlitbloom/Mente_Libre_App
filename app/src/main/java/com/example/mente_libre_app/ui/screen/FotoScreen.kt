package com.example.mente_libre_app.ui.screen

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import android.content.Intent
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import coil.compose.rememberAsyncImagePainter
import com.example.mente_libre_app.R
import com.example.mente_libre_app.ui.viewmodel.UsuarioViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun FotoScreen(
    usuarioViewModel: UsuarioViewModel,
    onNext: () -> Unit
) {
    val serifBold = FontFamily(Font(R.font.source_serif_pro_bold))
    val serifRegular = FontFamily(Font(R.font.source_serif_pro_regular))
    val serifSemiBold = FontFamily(Font(R.font.source_serif_pro_semibold))
    val context = LocalContext.current
    var mostrarDialog by remember { mutableStateOf(false) }
    // URI temporal para la foto de cámara
    var uriFotoTemporal by remember { mutableStateOf<Uri?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val avatares = listOf(
        R.drawable.avatar_1,
        R.drawable.avatar_2,
        R.drawable.avatar_3,
        R.drawable.avatar_4,
        R.drawable.avatar_5,
        R.drawable.avatar_6,
        R.drawable.avatar_7,
        R.drawable.avatar_8,
        R.drawable.avatar_9,
        R.drawable.avatar_10,
    )

    var avatarSeleccionado by remember { mutableStateOf<Int?>(0) }

    // Estado que guarda la foto seleccionada (URI de la galería)
    var fotoPersonalizada by remember { mutableStateOf<Uri?>(null) }

    // Launcher para abrir la galería
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        fotoPersonalizada = uri
    }

    // Launcher para seleccionar foto desde galería
    // Launcher galería
    val galeriaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            fotoPersonalizada = it
            avatarSeleccionado = null // Desmarcamos cualquier avatar
            coroutineScope.launch { usuarioViewModel.guardarFoto(context, it.toString()) }
        }
    }

    // Launcher cámara
    val camaraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            uriFotoTemporal?.let {
                fotoPersonalizada = it
                avatarSeleccionado = null // Desmarcamos cualquier avatar
                coroutineScope.launch { usuarioViewModel.guardarFoto(context, it.toString()) }
            }
        }
    }

    // Launcher para pedir permiso de cámara
    val permisoCamaraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) abrirCamara(context, camaraLauncher) { uriFotoTemporal = it }
    }

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color(0xFFFFEAF4))
            .padding(horizontal = 24.dp, vertical = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top // mejor top si hay scroll
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            // 🔹 Título superior
            Text(
                text = "Ajustes de Perfil",
                color = Color(0xFF842C46),
                fontFamily = serifBold,
                fontSize = 29.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(18.dp))

            // 🔹 Decorativo (gota)
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF95C1E))
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 🔹 Imagen circular (avatar)
            Image(
                painter = if (fotoPersonalizada != null)
                    rememberAsyncImagePainter(fotoPersonalizada)
                else
                    painterResource(id = avatarSeleccionado?.let { avatares[it] } ?: avatares[0]),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(170.dp)
                    .clip(CircleShape)
                    .border(4.dp, Color(0xFF842C46), CircleShape),
                contentScale = androidx.compose.ui.layout.ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(18.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(avatares.size) { index ->
                    val avatar = avatares[index]
                    Image(
                        painter = painterResource(id = avatar),
                        contentDescription = "Avatar $index",
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                            .border(
                                width = if (avatarSeleccionado == index) 4.dp else 2.dp,
                                color = if (avatarSeleccionado == index) Color(0xFF842C46) else Color.Gray,
                                shape = CircleShape
                            )
                            .clickable {
                                avatarSeleccionado = index
                                fotoPersonalizada = null
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            // 🔹 Decorativo inferior
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF95C1E))
            )

            Spacer(modifier = Modifier.height(25.dp))

            // 🔹 Subtítulo
            Text(
                text = "Selecciona tu avatar",
                color = Color(0xFF842C46),
                fontFamily = serifSemiBold,
                fontSize = 22.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(9.dp))

            // 🔹 Texto descriptivo
            Text(
                text = "Tenemos un conjunto personalizable de avatar. O puedes cargar tu propia imagen.",
                color = Color(0xFF842C46),
                fontFamily = serifRegular,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(18.dp))

            // 🔹 Botón de agregar (+)
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .border(3.dp, Color(0xFFF95C1E), CircleShape)
                    .background(Color(0xFFFFD3B1))
                    .clickable {
                        mostrarDialog = true // 👉 Abre la galería para elegir una imagen
                    }
            ) {
                Text(
                    text = "+",
                    color = Color(0xFFF95C1E),
                    fontFamily = serifBold,
                    fontSize = 40.sp
                )
            }

            if (mostrarDialog) {
                AlertDialog(
                    onDismissRequest = { mostrarDialog = false },
                    title = { Text("Selecciona opción") },
                    text = { Text("¿Quieres tomar una foto o elegir de la galería?") },
                    confirmButton = {
                        TextButton(onClick = {
                            // Abrir galería
                            galeriaLauncher.launch("image/*")
                            mostrarDialog = false
                        }) { Text("Galería") }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            // Pedir permiso de cámara antes de abrir
                            permisoCamaraLauncher.launch(android.Manifest.permission.CAMERA)
                            mostrarDialog = false
                        }) { Text("Cámara") }
                    }
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            // 🔹 Botón “Siguiente”
            val baseColor = Color(0xFFD94775)
            val pressedColor = Color(0xFF842C46)
            val animatedColor by animateColorAsState(
                targetValue = if (isPressed) pressedColor else baseColor,
                label = "buttonPressAnimation"
            )

            Button(
                onClick = { onNext() },
                colors = ButtonDefaults.buttonColors(containerColor = animatedColor),
                shape = RoundedCornerShape(50.dp),
                interactionSource = interactionSource,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(65.dp)
            ) {
                Text(
                    text = "Siguiente",
                    color = Color.White,
                    fontFamily = serifRegular,
                    fontSize = 30.sp
                )
            }

            Spacer(modifier = Modifier.height(26.dp))

            // 🔹 Indicador de progreso
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color(0xFFFFFFFF))
                    .padding(horizontal = 20.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "3 de 5",
                    color = Color(0xFFC5A3B3),
                    fontFamily = serifRegular,
                    fontSize = 16.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(35.dp)) // Ajusta según necesites
    }
}
// Función que abre la cámara con URI válido
private fun abrirCamara(
    context: Context,
    camaraLauncher: androidx.activity.result.ActivityResultLauncher<Uri>,
    setUriTemporal: (Uri) -> Unit
) {
    val archivo = createTempImageFile(context)
    val uri = getImageUriFile(context, archivo)
    setUriTemporal(uri)

    // Conceder permisos temporales al URI
    context.grantUriPermission(
        context.packageName,
        uri,
        Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
    )

    camaraLauncher.launch(uri)
}

fun createTempImageFile(context: Context): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val storageDir = File(context.cacheDir, "images").apply { if (!exists()) mkdirs() }
    return File(storageDir, "IMG_$timeStamp.jpg")
}

fun getImageUriFile(context: Context, file: File): Uri {
    val authority = "${context.packageName}.fileprovider"
    return FileProvider.getUriForFile(context, authority, file)
}