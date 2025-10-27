package com.example.mente_libre_app.ui.screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import java.util.Calendar


@Composable
fun PerfilScreen(
    usuarioViewModel: UsuarioViewModel,
    authViewModel: AuthViewModel,
    onBackClick: () -> Unit,
    onEditarFotoClick: () -> Unit
) {
    val serifBold = FontFamily(Font(R.font.source_serif_pro_bold))
    val serifRegular = FontFamily(Font(R.font.source_serif_pro_regular))

    val registerState by authViewModel.register.collectAsState()
    val context = LocalContext.current
    val fotoUri by usuarioViewModel.fotoUsuarioFlow(context).collectAsState(initial = null)
    val genero by usuarioViewModel.obtenerGenero(context).collectAsState(initial = "No especificado")
    val nombre by usuarioViewModel.obtenerNombre(context).collectAsState(initial = registerState.name)
    val email by usuarioViewModel.obtenerEmail(context).collectAsState(initial = registerState.email)
    val telefono by usuarioViewModel.obtenerTelefono(context).collectAsState(initial = registerState.phone)
    val cumpleanos by usuarioViewModel.obtenerCumpleanos(context).collectAsState(initial = null)

    var mostrarMapa by remember { mutableStateOf(false) }
    var ubicacionSeleccionada by remember { mutableStateOf("No especificado") }

    val scrollState = rememberScrollState()
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
                    painter = if (fotoUri != null)
                        rememberAsyncImagePainter(fotoUri)
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

            PerfilCampo(
                titulo = "Apodo",
                valor = nombre ?: "",
                icono = R.drawable.ic_usuario,
                editable = true,
                onValueChange = { usuarioViewModel.guardarNombre(context, it) }
            )
            PerfilCampo(
                titulo = "Correo Electrónico",
                valor = email ?: "",
                icono = R.drawable.ic_correo,
                editable = true,
                onValueChange = { usuarioViewModel.guardarEmail(context, it) }
            )
            PerfilCampo(
                titulo = "Teléfono",
                valor = telefono ?: "",
                icono = R.drawable.ic_telefono,
                editable = true,
                onValueChange = { usuarioViewModel.guardarTelefono(context, it) }
            )
            FechaPickerCampo(
                titulo = "Cumpleaños",
                fecha = cumpleanos,
                icono = R.drawable.ic_calendario
            ) { nuevaFecha ->
                usuarioViewModel.guardarCumpleanos(context, nuevaFecha)
            }
            GeneroCampo(
                generoActual = genero ?: "No especificado",
                usuarioViewModel = usuarioViewModel
            )
            UbicacionCampo(valor = ubicacionSeleccionada) {
                mostrarMapa = true
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        // Mostrar mapa como overlay
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
                        usuarioViewModel.guardarUbicacion(context, latLng.latitude, latLng.longitude)
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
    desplegable: Boolean = false,
    onValueChange: ((String) -> Unit)? = null
) {
    val serifBold = FontFamily(Font(R.font.source_serif_pro_bold))
    val serifRegular = FontFamily(Font(R.font.source_serif_pro_regular))
    var text by remember { mutableStateOf(valor) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = titulo,
            color = Color(0xFF842C46),
            fontFamily = serifBold,
            fontSize = 15.sp,
            modifier = Modifier.padding(start = 4.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color(0xFFFFD3B1), RoundedCornerShape(14.dp))
                .border(1.dp, Color(0xFF842C46), RoundedCornerShape(14.dp))
                .padding(horizontal = 14.dp, vertical = 6.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            if (editable) {
                TextField(
                    value = text,
                    onValueChange = {
                        text = it
                        onValueChange?.invoke(it)
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFFFD3B1),
                        unfocusedContainerColor = Color(0xFFFFD3B1),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = icono),
                            contentDescription = null,
                            tint = Color(0xFF842C46),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = valor,
                            color = Color(0xFF2D2D2D),
                            fontSize = 16.sp,
                            fontFamily = serifRegular
                        )
                    }

                    if (editable) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_edit),
                            contentDescription = "Editar",
                            tint = Color(0xFF842C46),
                            modifier = Modifier.size(18.dp)
                        )
                    } else if (desplegable) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_dropdown),
                            contentDescription = "Desplegar",
                            tint = Color(0xFF842C46),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FechaPickerCampo(
    titulo: String,
    fecha: String?,
    icono: Int,
    onFechaSeleccionada: (String) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    var textoFecha by remember { mutableStateOf(fecha ?: "No especificado") }
    val serifBold = FontFamily(Font(R.font.source_serif_pro_bold))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = titulo,
            color = Color(0xFF842C46),
            fontFamily = serifBold,
            fontSize = 15.sp,
            modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Color(0xFFFFD3B1), RoundedCornerShape(14.dp))
                .border(1.dp, Color(0xFF842C46), RoundedCornerShape(14.dp))
                .padding(horizontal = 14.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = icono),
                        contentDescription = null,
                        tint = Color(0xFF842C46),
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = textoFecha,
                        color = Color(0xFF2D2D2D),
                        fontSize = 16.sp
                    )
                }

                IconButton(onClick = {
                    val dpd = android.app.DatePickerDialog(
                        context,
                        { _, year, month, day ->
                            val fechaSeleccionada = "${day}/${month + 1}/$year"
                            textoFecha = fechaSeleccionada
                            onFechaSeleccionada(fechaSeleccionada)
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    )
                    dpd.show()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_dropdown),
                        contentDescription = "Seleccionar fecha",
                        tint = Color(0xFF842C46),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun GeneroCampo(
    generoActual: String,
    usuarioViewModel: UsuarioViewModel
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    val opciones = listOf("Masculino", "Femenino", "Otro")
    var genero by remember { mutableStateOf(generoActual) }
    val serifBold = FontFamily(Font(R.font.source_serif_pro_bold))
    val serifRegular = FontFamily(Font(R.font.source_serif_pro_regular))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = "Género",
            color = Color(0xFF842C46),
            fontFamily = serifBold,
            fontSize = 15.sp,
            modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Color(0xFFFFD3B1), RoundedCornerShape(14.dp))
                .border(1.dp, Color(0xFF842C46), RoundedCornerShape(14.dp))
                .padding(horizontal = 14.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = genero,
                    fontFamily = serifRegular,
                    fontSize = 16.sp,
                    color = Color(0xFF2D2D2D)
                )

                IconButton(onClick = { expanded = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_dropdown),
                        contentDescription = "Seleccionar género",
                        tint = Color(0xFF842C46),
                        modifier = Modifier.size(18.dp)
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    opciones.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                genero = opcion
                                expanded = false
                                usuarioViewModel.guardarGenero(context, opcion)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun UbicacionCampo(
    valor: String = "No especificado",
    onClick: () -> Unit
) {
    val serifBold = FontFamily(Font(R.font.source_serif_pro_bold))
    val serifRegular = FontFamily(Font(R.font.source_serif_pro_regular))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = "Ubicación",
            color = Color(0xFF842C46),
            fontFamily = serifBold,
            fontSize = 15.sp,
            modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Color(0xFFFFD3B1), RoundedCornerShape(14.dp))
                .border(1.dp, Color(0xFF842C46), RoundedCornerShape(14.dp))
                .padding(horizontal = 14.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = valor,
                    fontFamily = serifRegular,
                    fontSize = 16.sp,
                    color = Color(0xFF2D2D2D)
                )

                IconButton(onClick = onClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_direccion),
                        contentDescription = "Abrir mapa",
                        tint = Color(0xFF842C46),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun MapaScreen(
    initialLocation: LatLng = LatLng(-33.45, -70.65),
    onLocationSelected: (LatLng) -> Unit
) {
    var selectedLocation by remember { mutableStateOf(initialLocation) }

    // CameraPositionState inicial
    val cameraPositionState = rememberCameraPositionState {
        position = com.google.android.gms.maps.model.CameraPosition.builder()
            .target(initialLocation)
            .zoom(12f)
            .build()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapClick = { latLng ->
                selectedLocation = latLng
            }
        ) {
            Marker(
                state = MarkerState(position = selectedLocation),
                title = "Mi ubicación"
            )
        }

        Button(
            onClick = { onLocationSelected(selectedLocation) },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text("Seleccionar ubicación")
        }
    }
}