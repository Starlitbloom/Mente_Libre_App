package com.example.mente_libre_app.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mente_libre_app.ui.viewmodel.UsuarioViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun SeleccionarUbicacionScreen(
    navController: NavHostController,
    usuarioViewModel: UsuarioViewModel
) {
    val santiago = LatLng(-33.4489, -70.6693)

    var selectedPosition by remember { mutableStateOf<LatLng?>(null) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(santiago, 12f)
    }
    val context = LocalContext.current

    Box(Modifier.fillMaxSize()) {

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapClick = { latLng ->
                selectedPosition = latLng
            }
        ) {
            selectedPosition?.let { pos ->
                Marker(
                    state = MarkerState(position = pos),
                    title = "Ubicación seleccionada"
                )
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {

            selectedPosition?.let {
                Text("Lat: %.5f, Lng: %.5f".format(it.latitude, it.longitude))
                Spacer(Modifier.height(8.dp))
            }

            Button(
                onClick = {
                    selectedPosition?.let { pos ->

                        val direccionBonita =
                            usuarioViewModel.obtenerDireccionBonita(
                                context,
                                pos.latitude,
                                pos.longitude
                            )

                        // Guardar en el ViewModel
                        usuarioViewModel.setDireccion(direccionBonita)

                        // Pasar la dirección a la pantalla anterior (PerfilScreen)
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set("direccionElegida", direccionBonita)

                        // Volver a PerfilScreen
                        navController.popBackStack()
                    }
                },
                enabled = selectedPosition != null,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar ubicación")
            }

        }
    }
}
