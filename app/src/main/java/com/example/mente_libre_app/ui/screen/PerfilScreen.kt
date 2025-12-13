package com.example.mente_libre_app.ui.screen

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.mente_libre_app.R
import com.example.mente_libre_app.data.remote.dto.userprofile.UpdateUserProfileRequestDto
import com.example.mente_libre_app.ui.components.BackArrowCustom
import com.example.mente_libre_app.ui.theme.LocalExtraColors
import com.example.mente_libre_app.ui.viewmodel.AuthViewModel
import com.example.mente_libre_app.ui.viewmodel.UsuarioViewModel

@Composable
fun PerfilScreen(
    navController: NavHostController,
    usuarioViewModel: UsuarioViewModel,
    authViewModel: AuthViewModel
) {
    val scheme = MaterialTheme.colorScheme
    val extra = LocalExtraColors.current
    val perfil by usuarioViewModel.perfil.collectAsState()
    val usuario by authViewModel.usuario.collectAsState()
    val direccion by usuarioViewModel.direccion.collectAsState()
    val cumpleanos by usuarioViewModel.cumpleanos.collectAsState()
    val serifBold = FontFamily(Font(R.font.source_serif_pro_bold))
    val serifRegular = FontFamily(Font(R.font.source_serif_pro_regular))
    val context = LocalContext.current
    var cambiosPendientes by remember { mutableStateOf(false) }
    var editingField by remember { mutableStateOf<String?>(null) }
    val error by usuarioViewModel.error.collectAsState()

    // Recibir dirección desde SeleccionarUbicacionScreen
    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle

// Convertimos StateFlow → Compose State
    val direccionElegida by savedStateHandle
        ?.getStateFlow("direccionElegida", "")
        ?.collectAsState()
        ?: remember { mutableStateOf("") }

    LaunchedEffect(direccionElegida) {
        if (direccionElegida.isNotBlank()) {
            usuarioViewModel.setDireccion(direccionElegida)
            cambiosPendientes = true
        }
    }

    // Carga inicial del perfil
    LaunchedEffect(perfil) {
        if (perfil == null) {
            usuarioViewModel.cargarMiPerfil()
        }
    }

    // Si la direccion cambia se muestra el boton guardar
    LaunchedEffect(direccion) {
        if (editingField == "direccion") {
            cambiosPendientes = true
        }
    }

    LaunchedEffect(error) {
        if (error != null) {
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }
    }

    LaunchedEffect(cumpleanos) {
        if (editingField == "fecha") {
            cambiosPendientes = true
        }
    }

    if (perfil == null || usuario == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(scheme.background)
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {

        // ---------- FLECHA + TÍTULO ----------
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
        ) {

            BackArrowCustom(
                navController = navController,
                color = extra.title,
                size = 68,
                stroke = 18f,
                modifier = Modifier
                    .clickable { navController.popBackStack() }
            )

        }

        // Construir URL completa de la foto
        val baseUrl = "http://10.0.2.2:8088" // StorageService
        val fotoUrlCompleta =
            if (perfil!!.fotoPerfil?.startsWith("/") == true)
                baseUrl + perfil!!.fotoPerfil
            else
                perfil!!.fotoPerfil

        // FOTO PERFIL
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = rememberAsyncImagePainter(fotoUrlCompleta ?: R.drawable.avatar_1),
                contentDescription = "Foto perfil",
                modifier = Modifier
                    .size(190.dp)
                    .clip(CircleShape)
                    .border(3.dp, color = extra.title, CircleShape)
                    .clickable { navController.navigate("foto?edit=true") },
                contentScale = ContentScale.Crop
            )
        }

        Spacer(Modifier.height(30.dp))

        // -------------------------
        // CAMPOS EDITABLES
        // -------------------------

        EditableCustomField(
            label = "Nombre / Apodo",
            value = usuario!!.username,
            icon = R.drawable.ic_usuario,
            isEditing = editingField == "username",
            onEdit = { editingField = "username" },
            onSave = {
                authViewModel.setUsername(it)
                editingField = null
            },
            onCancel = { editingField = null }
        )

        EditableCustomField(
            label = "Correo Electrónico",
            value = usuario!!.email,
            icon = R.drawable.ic_correo,
            isEditing = editingField == "email",
            onEdit = { editingField = "email" },
            onSave = {
                authViewModel.setEmail(it)
                cambiosPendientes = true   // Se modificó algo
                editingField = null
            },
            onCancel = { editingField = null }
        )


        EditableCustomField(
            label = "Teléfono",
            value = usuario!!.phone,
            icon = R.drawable.ic_telefono,
            isEditing = editingField == "phone",
            onEdit = { editingField = "phone" },
            onSave = {
                authViewModel.setPhone(it)
                editingField = null
            },
            onCancel = { editingField = null }
        )

        // GÉNERO (Dropdown)
        GeneroEditableCampo(
            generoActual = perfil!!.genero.nombre,
            isEditing = editingField == "genero",
            onEdit = { editingField = "genero" },
            onGeneroSelected = { id, nombre ->
                usuarioViewModel.setGeneroId(id, nombre)
                cambiosPendientes = true
                editingField = null
            },
            onCancel = { editingField = null }
        )

        // FECHA
        FechaEditableCampo(
            fechaActual = cumpleanos,
            isEditing = editingField == "fecha",
            onEdit = { editingField = "fecha" },
            onFechaSelected = {
                usuarioViewModel.setCumpleanos(it)
                cambiosPendientes = true
                editingField = null
            },
            onCancel = { editingField = null }
        )

        // UBICACIÓN
        UbicacionEditableCampo(
            navController = navController,
            direccionActual = direccion,
            isEditing = editingField == "direccion",
            onEdit = { editingField = "direccion" },
            onDireccionSelected = {
                usuarioViewModel.setDireccion(it)
                cambiosPendientes = true
                editingField = null
            },
            onCancel = { editingField = null }
        )

        Spacer(Modifier.height(30.dp))

        // BOTON GUARDAR
        if (cambiosPendientes) {
            Button(
                onClick = {
                    val dto = UpdateUserProfileRequestDto(
                        direccion = usuarioViewModel.direccion.value,
                        fechaNacimiento = usuarioViewModel.cumpleanos.value,
                        notificaciones = perfil!!.notificaciones,
                        generoId = usuarioViewModel.generoId.value ?: perfil!!.genero.id,
                        fotoPerfil = usuarioViewModel.fotoPerfil.value ?: perfil!!.fotoPerfil
                    )

                    usuarioViewModel.actualizarPerfil(dto) { ok ->
                        if (ok) {
                            usuarioViewModel.cargarMiPerfil()  // RECARGA
                            cambiosPendientes = false
                            editingField = null
                            println("DIRECCIÓN A ENVIAR = ${usuarioViewModel.direccion.value}")

                        }   // Después de guardar, volver a ocultar el botón
                    }
                },
                colors = ButtonDefaults.buttonColors(extra.buttonAlt),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Guardar Cambios",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontFamily = serifRegular
                )
            }
        }
    }
    // --- BOTÓN ELIMINAR CUENTA ---
    Spacer(Modifier.height(20.dp))

    Button(
        onClick = {
            navController.navigate("eliminar_cuenta")
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = extra.title,   // tu color principal oscuro (morado/magenta según tema)
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
    ) {
        Text(
            text = "Eliminar Cuenta",
            fontFamily = serifRegular,
            fontSize = 20.sp
        )
    }

}

@Composable
fun GeneroEditableCampo(
    generoActual: String,
    isEditing: Boolean,
    onEdit: () -> Unit,
    onGeneroSelected: (Long, String) -> Unit,
    onCancel: () -> Unit
) {
    val scheme = MaterialTheme.colorScheme
    val extra = LocalExtraColors.current
    val serifRegular = FontFamily(Font(R.font.source_serif_pro_regular))

    val generos = listOf(
        1L to "Mujer",
        2L to "Hombre",
        3L to "Prefiero no decirlo"
    )

    var expanded by remember { mutableStateOf(false) }

    // Animaciones igual que los otros campos
    val borderColor by animateColorAsState(
        if (isEditing) extra.arrowColor else scheme.onSurface
    )
    val borderWidth by animateDpAsState(
        if (isEditing) 2.dp else 1.dp
    )
    val textColor by animateColorAsState(
        if (isEditing) Color.Black else scheme.onSurface
    )
    val iconColor by animateColorAsState(
        if (isEditing) extra.arrowColor else scheme.onSurface
    )

    Column(Modifier.fillMaxWidth().padding(vertical = 6.dp)) {

        Text("Género", color = extra.title, fontFamily = serifRegular, fontSize = 14.sp)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(borderWidth, borderColor, RoundedCornerShape(20.dp))
                .background(scheme.onPrimary, RoundedCornerShape(20.dp))
                .padding(horizontal = 14.dp)
                .clickable {
                    if (!isEditing) onEdit() else expanded = true
                }
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_usuario),
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(22.dp)
                )

                Spacer(Modifier.width(10.dp))

                Text(
                    text = generoActual,
                    fontFamily = serifRegular,
                    fontSize = 17.sp,
                    color = textColor
                )

                Spacer(Modifier.weight(1f))

                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = "Seleccionar género",
                    tint = iconColor,
                    modifier = Modifier.size(22.dp)
                )
            }

            if (isEditing) {
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    generos.forEach { (id, nombre) ->
                        DropdownMenuItem(
                            text = { Text(nombre) },
                            onClick = {
                                onGeneroSelected(id, nombre)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        if (isEditing) {
            Row {
                TextButton(onClick = { onCancel() }) {
                    Text("Cancelar")
                }
            }
        }
    }
}

@Composable
fun FechaEditableCampo(
    fechaActual: String,
    isEditing: Boolean,
    onEdit: () -> Unit,
    onFechaSelected: (String) -> Unit,
    onCancel: () -> Unit
) {
    val serifRegular = FontFamily(Font(R.font.source_serif_pro_regular))
    val context = LocalContext.current

    val scheme = MaterialTheme.colorScheme
    val extra = LocalExtraColors.current

    // Animaciones igual que otros campos
    val borderColor by animateColorAsState(
        if (isEditing) extra.arrowColor else scheme.onSurface
    )
    val borderWidth by animateDpAsState(
        if (isEditing) 2.dp else 1.dp
    )
    val textColor by animateColorAsState(
        if (isEditing) Color.Black else scheme.onSurface
    )
    val iconColor by animateColorAsState(
        if (isEditing) extra.arrowColor else scheme.onSurface
    )

    Column(Modifier.fillMaxWidth().padding(vertical = 6.dp)) {

        Text("Cumpleaños", color = extra.title, fontFamily = serifRegular, fontSize = 14.sp)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(borderWidth, borderColor, RoundedCornerShape(20.dp))
                .background(scheme.onPrimary, RoundedCornerShape(20.dp))
                .padding(horizontal = 14.dp)
                .clickable {
                    if (!isEditing) onEdit()
                    else {
                        // Mostrar DatePicker
                        val hoy = java.util.Calendar.getInstance()
                        DatePickerDialog(
                            context,
                            { _, year, month, day ->
                                val mes = (month + 1).toString().padStart(2, '0')
                                val dia = day.toString().padStart(2, '0')
                                val fechaISO = "$year-$mes-$dia"
                                onFechaSelected(fechaISO)

                            },
                            hoy.get(java.util.Calendar.YEAR),
                            hoy.get(java.util.Calendar.MONTH),
                            hoy.get(java.util.Calendar.DAY_OF_MONTH)
                        ).show()
                    }
                }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_calendario),
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(22.dp)
                )

                Spacer(Modifier.width(10.dp))

                Text(
                    if (fechaActual.isEmpty()) "Sin especificar" else fechaActual,
                    fontFamily = serifRegular,
                    fontSize = 17.sp,
                    color = textColor
                )
            }
        }

        if (isEditing) {
            Row {
                TextButton(onClick = { onCancel() }) {
                    Text("Cancelar")
                }
            }
        }
    }
}

@Composable
fun UbicacionEditableCampo(
    navController: NavHostController,
    direccionActual: String,
    isEditing: Boolean,
    onEdit: () -> Unit,
    onDireccionSelected: (String) -> Unit,
    onCancel: () -> Unit
) {
    val scheme = MaterialTheme.colorScheme
    val extra = LocalExtraColors.current
    val serifRegular = FontFamily(Font(R.font.source_serif_pro_regular))

    val borderColor by animateColorAsState(
        if (isEditing) extra.arrowColor else scheme.onSurface
    )
    val borderWidth by animateDpAsState(
        if (isEditing) 2.dp else 1.dp
    )
    val textColor by animateColorAsState(
        if (isEditing) Color.Black else scheme.onSurface
    )
    val iconColor by animateColorAsState(
        if (isEditing) extra.arrowColor else scheme.onSurface
    )

    Column(Modifier.fillMaxWidth().padding(vertical = 6.dp)) {

        Text("Ubicación", color = extra.title, fontFamily = serifRegular, fontSize = 14.sp)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(borderWidth, borderColor, RoundedCornerShape(20.dp))
                .background(scheme.onPrimary, RoundedCornerShape(20.dp))
                .padding(horizontal = 14.dp)
                .clickable {
                    if (!isEditing) onEdit()
                }
        ) {

            if (!isEditing) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_direccion),
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(22.dp)
                    )

                    Spacer(Modifier.width(10.dp))

                    Text(
                        text = if (direccionActual.isBlank()) "Sin especificar" else direccionActual,
                        fontFamily = serifRegular,
                        fontSize = 17.sp,
                        color = textColor
                    )
                }
            } else {
                Button(
                    onClick = { navController.navigate("seleccionar_ubicacion") },
                    colors = ButtonDefaults.buttonColors(extra.buttonAlt),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Elegir ubicación en el mapa", color = Color.White)
                }
            }

        }
        if (isEditing) {
            Row {

                TextButton(onClick = onCancel) {
                    Text("Cancelar")
                }
            }
        }
    }
}

@Composable
fun EditableCustomField(
    label: String,
    value: String,
    icon: Int,
    isEditing: Boolean,
    onEdit: () -> Unit,
    onCancel: () -> Unit,
    onSave: (String) -> Unit
) {
    val serifRegular = FontFamily(Font(R.font.source_serif_pro_regular))
    var text by remember { mutableStateOf(value) }

    val scheme = MaterialTheme.colorScheme
    val extra = LocalExtraColors.current
    // Animaciones visuales siempre según isEditing
    val borderColor by animateColorAsState(
        if (isEditing) extra.arrowColor else scheme.onSurface
    )
    val borderWidth by animateDpAsState(
        if (isEditing) 2.dp else 1.dp
    )
    val iconColor by animateColorAsState(
        if (isEditing) extra.arrowColor else scheme.onSurface
    )
    val textColor by animateColorAsState(
        if (isEditing) Color.Black else scheme.onSurface
    )

    Column(Modifier.fillMaxWidth().padding(vertical = 6.dp)) {

        Text(label, color = extra.title, fontFamily = serifRegular, fontSize = 14.sp)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(borderWidth, borderColor, RoundedCornerShape(20.dp))
                .background(scheme.onPrimary, RoundedCornerShape(20.dp))
                .padding(start = 14.dp, end = 14.dp)
        ) {

            if (!isEditing) {
                // MODO VISUAL
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        painterResource(id = icon),
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(Modifier.width(10.dp))

                    Text(
                        text,
                        fontFamily = serifRegular,
                        fontSize = 17.sp,
                        color = textColor
                    )

                    Spacer(Modifier.weight(1f))

                    // ICONO DE EDITAR
                    Icon(
                        painter = painterResource(id = R.drawable.ic_edit),
                        contentDescription = "Editar",
                        tint = iconColor,
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { onEdit() }
                    )
                }

            } else {
                // MODO EDICIÓN (sin autoguardado)
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    singleLine = true,
                    modifier = Modifier.fillMaxSize(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        cursorColor = extra.arrowColor,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    textStyle = androidx.compose.ui.text.TextStyle(
                        fontFamily = serifRegular,
                        fontSize = 17.sp,
                        color = Color.Black
                    )
                )
            }
        }

        if (isEditing) {
            Row {
                TextButton(onClick = onCancel) {
                    Text("Cancelar")
                }
            }
        }
    }
}
