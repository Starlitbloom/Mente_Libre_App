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
import com.example.mente_libre_app.data.remote.dto.userprofile.UpdateUserProfileRequestDto
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
    onBackClick: () -> Unit,
    onEditarFotoClick: () -> Unit
) {
    val serifBold = FontFamily(Font(R.font.source_serif_pro_bold))
    val serifRegular = FontFamily(Font(R.font.source_serif_pro_regular))

    // ------- DATOS DESDE AUTH -------
    val authUser = authViewModel.usuario.collectAsState().value
    val nombre = authUser?.username ?: ""
    val email = authUser?.email ?: ""
    val telefono = authUser?.phone ?: ""

    // ------- DATOS DESDE USER PROFILE -------
    val perfil by usuarioViewModel.perfil.collectAsState()
    val direccion = perfil?.direccion ?: ""
    val cumpleanos = perfil?.fechaNacimiento ?: ""
    val genero = perfil?.genero?.nombre ?: ""
    val fotoPerfil = perfil?.fotoPerfil

    // Estado para mapa
    var mostrarMapa by remember { mutableStateOf(false) }
    var ubicacionSeleccionada by remember { mutableStateOf(direccion) }

    // Scroll
    val scrollState = rememberScrollState()

    // Cargar perfil desde backend
    LaunchedEffect(Unit) {
        usuarioViewModel.cargarMiPerfil()
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
                modifier = Modifier.align(Alignment.Start)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color(0xFF842C46)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // FOTO PERFIL
            Box(contentAlignment = Alignment.BottomCenter) {
                Image(
                    painter = if (fotoPerfil != null)
                        rememberAsyncImagePainter(fotoPerfil)
                    else painterResource(id = R.drawable.ic_perfil_demo),
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
                        .clip(CircleShape)
                        .background(Color(0xFFFFD3B1))
                        .border(2.dp, Color(0xFFF95C1E), CircleShape)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_edit),
                        contentDescription = "Editar foto",
                        tint = Color(0xFFF95C1E)
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // CAMPOS
            PerfilCampo("Apodo", nombre, R.drawable.ic_usuario, editable = true) {
                authViewModel.setUsername(it)
            }

            PerfilCampo("Correo Electrónico", email, R.drawable.ic_correo, editable = true) {
                authViewModel.setEmail(it)
            }

            PerfilCampo("Teléfono", telefono, R.drawable.ic_telefono, editable = true) {
                authViewModel.setPhone(it)
            }

            FechaPickerCampo("Cumpleaños", cumpleanos, R.drawable.ic_calendario) {
                usuarioViewModel.setCumpleanos(it)
            }

            GeneroCampo(genero, usuarioViewModel)

            UbicacionCampo(
                valor = ubicacionSeleccionada,
                onClick = { mostrarMapa = true }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // GUARDAR CAMBIOS
            Button(
                onClick = {
                    val dto = UpdateUserProfileRequestDto(
                        direccion = ubicacionSeleccionada,
                        fechaNacimiento = cumpleanos,
                        generoId = usuarioViewModel.generoId.value ?: perfil?.genero?.id ?: 3,
                        fotoPerfil = fotoPerfil
                    )

                    usuarioViewModel.actualizarPerfil(dto) {}
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF842C46))
            ) {
                Text("Guardar cambios", color = Color.White, fontFamily = serifBold)
            }
        }

        // MAPA PARA SELECCIONAR UBICACIÓN
        if (mostrarMapa) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xAA000000))
            ) {
                MapaScreen(
                    initialLocation = LatLng(-33.45, -70.65),
                    onLocationSelected = { latLng ->
                        ubicacionSeleccionada = "${latLng.latitude}, ${latLng.longitude}"
                        usuarioViewModel.setDireccion(ubicacionSeleccionada)
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
