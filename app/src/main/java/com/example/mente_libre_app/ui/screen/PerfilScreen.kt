package com.example.mente_libre_app.ui.screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.mente_libre_app.R
import com.example.mente_libre_app.ui.viewmodel.AuthViewModel
import com.example.mente_libre_app.ui.viewmodel.UsuarioViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun PerfilScreen(
    usuarioViewModel: UsuarioViewModel,
    authViewModel: AuthViewModel,
    userId: Long, // id del usuario actual
    onBackClick: () -> Unit,
    onEditarFotoClick: () -> Unit
) {
    val serifBold = FontFamily(Font(R.font.source_serif_pro_bold))
    val serifRegular = FontFamily(Font(R.font.source_serif_pro_regular))
    val context = LocalContext.current

    // Estados del ViewModel
    val nombre by usuarioViewModel.nombre.collectAsState()
    val email by usuarioViewModel.email.collectAsState()
    val telefono by usuarioViewModel.telefono.collectAsState()
    val direccion by usuarioViewModel.direccion.collectAsState()
    val cumpleanos by usuarioViewModel.cumpleanos.collectAsState()
    val genero by usuarioViewModel.generoNombre.collectAsState()
    val fotoPerfil by usuarioViewModel.fotoPerfil.collectAsState()
    val ubicacion by usuarioViewModel.ubicacion.collectAsState()


    // Estado de mapa
    var mostrarMapa by remember { mutableStateOf(false) }
    val ubicacionSeleccionada by usuarioViewModel.direccion.collectAsState()


    val scrollState = rememberScrollState()

    // Cargar perfil al iniciar la pantalla
    LaunchedEffect(userId) {
        usuarioViewModel.cargarPerfil(userId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFEAF4))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 20.dp)
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .align(Alignment.Start)
                    .size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color(0xFF842C46)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Box(contentAlignment = Alignment.BottomCenter) {
                Image(
                    painter = if (fotoPerfil != null)
                        rememberAsyncImagePainter(fotoPerfil)
                    else
                        painterResource(id = R.drawable.ic_perfil_demo),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(170.dp)
                        .clip(CircleShape)
                        .border(4.dp, Color(0xFF842C46), CircleShape),
                    contentScale = ContentScale.Crop
                )

                IconButton(
                    onClick = onEditarFotoClick,
                    modifier = Modifier
                        .offset(y = 10.dp)
                        .size(50.dp)
                        .background(Color(0xFFFFD3B1), CircleShape)
                        .border(2.dp, Color(0xFFF95C1E), CircleShape)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_edit),
                        contentDescription = "Editar foto",
                        tint = Color(0xFFF95C1E),
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Campos de perfil
            PerfilCampo(
                titulo = "Apodo",
                valor = nombre,
                icono = R.drawable.ic_usuario,
                editable = true,
                onValueChange = { usuarioViewModel.setUsername(it) }
            )
            PerfilCampo(
                titulo = "Correo Electrónico",
                valor = email,
                icono = R.drawable.ic_correo,
                editable = true,
                onValueChange = { usuarioViewModel.setEmail(it) }
            )
            PerfilCampo(
                titulo = "Teléfono",
                valor = telefono,
                icono = R.drawable.ic_telefono,
                editable = true,
                onValueChange = { usuarioViewModel.setPhone(it) }
            )
            FechaPickerCampo(
                titulo = "Cumpleaños",
                fecha = cumpleanos,
                icono = R.drawable.ic_calendario
            ) { nuevaFecha ->
                usuarioViewModel.setCumpleanos(nuevaFecha)
            }
            GeneroCampo(
                generoActual = genero,
                usuarioViewModel = usuarioViewModel
            )
            UbicacionCampo(valor = ubicacionSeleccionada) {
                mostrarMapa = true
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Botón para guardar cambios
            Button(
                onClick = {
                    usuarioViewModel.actualizarPerfil(
                        userId = userId,
                        direccion = direccion,
                        fechaNacimiento = cumpleanos,
                        generoId = usuarioViewModel.generoId.value,
                        fotoPerfil = fotoPerfil
                    ) { success ->
                        if (success) {
                            // Opcional: mostrar mensaje "Perfil actualizado"
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF842C46))
            ) {
                Text(text = "Guardar cambios", color = Color.White, fontFamily = serifBold)
            }
        }

        // Mapa para seleccionar ubicación
        if (mostrarMapa) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xAA000000))
            ) {
                MapaScreen(
                    initialLocation = ubicacion?.let { LatLng(it.first, it.second) }
                        ?: LatLng(-33.45, -70.65),
                    onLocationSelected = { latLng ->
                        usuarioViewModel.setUbicacion(latLng.latitude, latLng.longitude)
                        usuarioViewModel.setDireccion("${latLng.latitude}, ${latLng.longitude}")

                        mostrarMapa = false
                    }
                )
            }
        }
    }
}

@Composable
fun PerfilCampo(
    titulo: String,
    valor: String,
    icono: Int,
    editable: Boolean = false,
    onValueChange: (String) -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Text(text = titulo, fontSize = 14.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(4.dp))
        if (editable) {
            OutlinedTextField(
                value = valor,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        } else {
            Text(text = valor, fontSize = 16.sp)
        }
    }
}

@Composable
fun FechaPickerCampo(
    titulo: String,
    fecha: String,
    icono: Int,
    onFechaSelected: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Text(text = titulo, fontSize = 14.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = fecha,
            onValueChange = { },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = {
                    // Aquí podrías abrir un DatePickerDialog real si querés
                }) {
                    Icon(painter = painterResource(id = icono), contentDescription = "Seleccionar fecha")
                }
            }
        )
    }
}

@Composable
fun GeneroCampo(
    generoActual: String,
    usuarioViewModel: UsuarioViewModel
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Text(text = "Género", fontSize = 14.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(4.dp))
        // Para simplificar, usamos un Dropdown
        var expanded by remember { mutableStateOf(false) }
        val generos = listOf("No especificado", "Masculino", "Femenino", "Otro")
        Box {
            OutlinedTextField(
                value = generoActual,
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                }
            )
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                generos.forEachIndexed { index, g ->
                    DropdownMenuItem(text = { Text(g) }, onClick = {
                        usuarioViewModel.setGeneroId(index.toLong(), g)
                        expanded = false
                    })
                }
            }
        }
    }
}

@Composable
fun UbicacionCampo(
    valor: String,
    onClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Text(text = "Ubicación", fontSize = 14.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = valor,
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = onClick) {
                    Icon(painterResource(id = R.drawable.ic_direccion), contentDescription = "Seleccionar ubicación")
                }
            }
        )
    }
}
@Composable
fun MapaScreen(
    initialLocation: LatLng,
    onLocationSelected: (LatLng) -> Unit
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(initialLocation, 14f)
    }


    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        onMapClick = { latLng ->
            onLocationSelected(latLng)
        }
    ) {
        Marker(
            state = MarkerState(position = initialLocation),
            title = "Ubicación seleccionada"
        )
    }
}
