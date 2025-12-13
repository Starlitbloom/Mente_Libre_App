package com.example.mente_libre_app.navigation

import android.net.Uri
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.mente_libre_app.ui.screen.AnimoScreen
import com.example.mente_libre_app.ui.screen.AjustesScreen
import com.example.mente_libre_app.ui.screen.Bienvenida1Screen
import com.example.mente_libre_app.ui.screen.Bienvenida2Screen
import com.example.mente_libre_app.ui.screen.Bienvenida3Screen
import com.example.mente_libre_app.ui.screen.Bienvenida4Screen
import com.example.mente_libre_app.ui.screen.BienvenidaScreen
import com.example.mente_libre_app.ui.screen.CargandoScreen
import com.example.mente_libre_app.ui.screen.CrearScreenVm
import com.example.mente_libre_app.ui.screen.CrisisScreen
import com.example.mente_libre_app.ui.screen.EliminarCuentaScreen
import com.example.mente_libre_app.ui.screen.EstrategiasScreen
import com.example.mente_libre_app.ui.screen.FotoScreen
import com.example.mente_libre_app.ui.screen.FraseScreen
import com.example.mente_libre_app.ui.screen.GeneroScreen
import com.example.mente_libre_app.ui.screen.HuellaScreen
import com.example.mente_libre_app.ui.screen.IniciarScreenVm
import com.example.mente_libre_app.ui.screen.InicioScreen
import com.example.mente_libre_app.ui.screen.MascotaScreen
import com.example.mente_libre_app.ui.screen.NombrarMascotaScreen
import com.example.mente_libre_app.ui.screen.ObjetivoScreen
import com.example.mente_libre_app.ui.screen.OrganizarseScreen
import com.example.mente_libre_app.ui.screen.PerfilScreen
import com.example.mente_libre_app.ui.screen.PortadaScreen
import com.example.mente_libre_app.ui.screen.PuntajeScreen
import com.example.mente_libre_app.ui.screen.SaludScreen
import com.example.mente_libre_app.ui.screen.SeguridadScreen
import com.example.mente_libre_app.ui.screen.SeleccionarUbicacionScreen
import com.example.mente_libre_app.ui.screen.SelectorScreen
import com.example.mente_libre_app.ui.screen.TemaScreen
import com.example.mente_libre_app.ui.viewmodel.AuthViewModel
import com.example.mente_libre_app.ui.viewmodel.AuthViewModelFactory
import com.example.mente_libre_app.ui.viewmodel.UsuarioViewModel
import com.example.mente_libre_app.ui.viewmodel.UsuarioViewModelFactory
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavGraph(
    navController: NavHostController = rememberAnimatedNavController(),
    activity: FragmentActivity
) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // -------------------------
    // ViewModels Globales
    // -------------------------
    val context = LocalContext.current

    val authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(context)
    )

    val usuarioViewModel: UsuarioViewModel = viewModel(
        factory = UsuarioViewModelFactory(context)
    )

    // -------------------------
    // Navegadores
    // -------------------------
    val goCargando   = { navController.navigate(Route.Cargando.path) }
    val goFrase      = { navController.navigate(Route.Frase.path) }
    val goBienvenida1 = { navController.navigate(Route.Bienvenida1.path) }
    val goBienvenida2 = { navController.navigate(Route.Bienvenida2.path) }
    val goBienvenida3 = { navController.navigate(Route.Bienvenida3.path) }
    val goBienvenida4 = { navController.navigate(Route.Bienvenida4.path) }
    val goBienvenida  = { navController.navigate(Route.Bienvenida.path) }
    val goCrear       = { navController.navigate(Route.Crear.path) }
    val goObjetivo    = { navController.navigate(Route.Objetivo.path) }
    val goGenero      = { navController.navigate(Route.Genero.path) }
    val goFoto        = { navController.navigate(Route.Foto.path) }
    val goTema        = { navController.navigate(Route.Tema.path) }
    val goHuella      = { navController.navigate(Route.Huella.path) }
    val goMascota     = { navController.navigate(Route.Mascota.path) }
    val goSelector    = { navController.navigate(Route.Selector.path) }
    val goNombrarMascota = { navController.navigate(Route.NombrarMascota.path) }
    val goIniciar     = { navController.navigate(Route.Iniciar.path) }
    val goInicio      = { navController.navigate(Route.Inicio.path) }
    val goAjustes     = { navController.navigate(Route.Ajustes.path) }
    val goPerfil      = { navController.navigate(Route.Perfil.path) }
    val goAnimo       = { navController.navigate(Route.Animo.path) }


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { /* menú lateral */ }
    ) {
        Scaffold { innerPadding ->

            AnimatedNavHost(
                navController = navController,
                startDestination = Route.Portada.path,
                modifier = Modifier.padding(innerPadding),
                enterTransition = {
                    slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(400)) +
                            fadeIn(animationSpec = tween(400))
                },
                exitTransition = {
                    slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(400)) +
                            fadeOut(animationSpec = tween(400))
                },
                popEnterTransition = {
                    slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(400)) +
                            fadeIn(animationSpec = tween(400))
                },
                popExitTransition = {
                    slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(400)) +
                            fadeOut(animationSpec = tween(400))
                }
            ) {

                // -------------------------
                //  PANTALLAS DE FLUJO
                // -------------------------

                composable(Route.Portada.path) {
                    PortadaScreen(onNext = goCargando)
                }

                composable(Route.Cargando.path) {
                    CargandoScreen(onNext = goFrase)
                }

                composable(Route.Frase.path) {
                    FraseScreen(onNext = goBienvenida1)
                }

                composable(Route.Bienvenida1.path) {
                    Bienvenida1Screen(onNext = goBienvenida2)
                }

                composable(Route.Bienvenida2.path) {
                    Bienvenida2Screen(onNext = goBienvenida3)
                }

                composable(Route.Bienvenida3.path) {
                    Bienvenida3Screen(onNext = goBienvenida4)
                }

                composable(Route.Bienvenida4.path) {
                    Bienvenida4Screen(onNext = goBienvenida)
                }

                composable(Route.Bienvenida.path) {
                    BienvenidaScreen(
                        onComenzarClick = goCrear,
                        onLoginClick = goIniciar
                    )
                }

                composable(Route.Crear.path) {
                    CrearScreenVm(
                        authViewModel = authViewModel,
                        onComenzarClick = goObjetivo,
                        onLoginClick = goIniciar
                    )
                }

                composable(Route.Objetivo.path) {
                    ObjetivoScreen(
                        usuarioViewModel = usuarioViewModel,
                        onNext = goGenero
                    )
                }

                composable(Route.Genero.path) {
                    GeneroScreen(
                        usuarioViewModel = usuarioViewModel,
                        onNext = goFoto
                    )
                }

                // Ruta para edición: foto?edit=true
                composable(
                    route = "foto?edit={edit}",
                    arguments = listOf(
                        navArgument("edit") {
                            defaultValue = "false"
                        }
                    )
                ) { backStackEntry ->

                    val isEditing = backStackEntry.arguments?.getString("edit") == "true"

                    FotoScreen(
                        usuarioViewModel = usuarioViewModel,
                        authViewModel = authViewModel,
                        onNext = {
                            if (isEditing) {
                                navController.popBackStack()   // Vuelve a PerfilScreen
                            } else {
                                goTema()   // Flujo normal del registro
                            }
                        },
                        isEditingProfile = isEditing
                    )
                }

                composable(
                    route = "tema?edit={edit}",
                    arguments = listOf(navArgument("edit") { defaultValue = "false" })
                ) { backStackEntry ->

                    val isEditing = backStackEntry.arguments?.getString("edit") == "true"

                    TemaScreen(
                        usuarioViewModel = usuarioViewModel,
                        isEditing = isEditing,
                        onNext = {
                            if (isEditing) {
                                navController.popBackStack()   // vuelve a Ajustes
                            } else {
                                goHuella()                     // sigue flujo normal
                            }
                        }
                    )
                }

                composable(Route.Huella.path) {
                    HuellaScreen(
                        activity = activity,
                        onVerificado = {
                            val userId = authViewModel.usuario.value?.userId

                            if (userId != null) {
                                usuarioViewModel.crearPerfilFinal(userId) { success ->
                                    if (success) {
                                        navController.navigate(Route.Mascota.path) {
                                            launchSingleTop = true
                                        }
                                    }
                                }
                            }
                        }
                    )

                }

                composable(Route.Mascota.path) {
                    MascotaScreen(onNext = goSelector)
                }

                composable(Route.Selector.path) {
                    SelectorScreen(
                        onMascotaElegida = { mascota ->
                            val encoded = Uri.encode(mascota)
                            navController.navigate("nombrar_mascota/$encoded")
                        }
                    )
                }

                composable(
                    route = "nombrar_mascota/{mascota}",
                    arguments = listOf(navArgument("mascota") { type = NavType.StringType })
                ) {
                    val mascota = it.arguments?.getString("mascota") ?: ""
                    NombrarMascotaScreen(
                        mascota = mascota,
                        onGuardarNombre = { nombre ->
                            navController.navigate(Route.Inicio.path) {
                                popUpTo(Route.Portada.path) { inclusive = false }
                                launchSingleTop = true
                            }
                        }
                    )
                }

                composable(Route.Iniciar.path) {
                    IniciarScreenVm(
                        authViewModel = authViewModel,
                        onRegisterClick = goCrear,
                        onLoginSuccess = goInicio
                    )
                }

                composable(Route.Inicio.path) {

                    val fotoPerfil = usuarioViewModel.fotoPerfil.collectAsState().value
                    val nombre = authViewModel.usuario.collectAsState().value?.username

                    InicioScreen(
                        navController = navController,
                        fotoPerfil = fotoPerfil, // ESTA ES LA FOTO REAL O AVATAR
                        nombreUsuario = nombre,
                        onNavChange = { index ->
                            when (index) {
                                0 -> navController.navigate(Route.Inicio.path)
                                1 -> { }
                                2 -> navController.navigate(Route.Perfil.path)
                                3 -> navController.navigate(Route.Ajustes.path)
                            }
                        },
                        onGoAnimo = goAnimo,
                    )
                }

                composable(Route.Puntaje.path) {
                    PuntajeScreen(
                        onBack = { navController.popBackStack() }
                    )
                }

                composable(Route.Organizarse.path) {
                    OrganizarseScreen(navController)
                }

                composable(Route.Crisis.path) {
                    CrisisScreen(navController)
                }

                composable(Route.Estrategias.path) {
                    EstrategiasScreen(navController)
                }

                composable(Route.Salud.path) {
                    SaludScreen(navController)
                }

                composable(Route.Animo.path) {
                    AnimoScreen(
                        onBack = { navController.popBackStack() }
                    )
                }

                composable(Route.Ajustes.path) {
                    AjustesScreen(
                        onItemSelected = { selectedItem ->
                            when (selectedItem) {
                                "inicio" -> navController.navigate(Route.Inicio.path)
                                "perfil" -> navController.navigate(Route.Perfil.path)
                            }
                        },
                        onPerfilClick = { navController.navigate(Route.Perfil.path) },
                        onSeguridadClick = { navController.navigate(Route.Seguridad.path) },
                        onNotificacionesClick = { /* Navegar a Notificaciones */ },
                        onTemaClick = { navController.navigate("tema?edit=true") },
                        onEmergenciaClick = { /* Navegar a Emergencia */ },
                        onSoporteClick = { /* Navegar a Soporte */ },
                        onSugerenciasClick = { /* Navegar a Sugerencias */ },
                        onCerrarSesion = { /* Lógica para cerrar sesión */ }
                    )
                }

                composable(Route.Perfil.path) {
                    PerfilScreen(
                        navController = navController,
                        usuarioViewModel = usuarioViewModel,
                        authViewModel = authViewModel
                    )
                }

                composable("eliminar_cuenta") {
                    EliminarCuentaScreen(
                        navController = navController,
                        usuarioViewModel = usuarioViewModel,
                        authViewModel = authViewModel
                    )
                }

                composable("seleccionar_ubicacion") {
                    SeleccionarUbicacionScreen(
                        navController = navController,
                        usuarioViewModel = usuarioViewModel
                    )
                }

                composable("seguridad") {
                    SeguridadScreen(
                        navController = navController,
                        usuarioViewModel = usuarioViewModel,
                        authViewModel = authViewModel
                    )
                }


            }
        }
    }
}
