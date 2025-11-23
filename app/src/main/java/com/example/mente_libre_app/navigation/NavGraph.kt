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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.mente_libre_app.data.local.database.AppDatabase
import com.example.mente_libre_app.data.repository.UserRepository
import com.example.mente_libre_app.ui.screen.*
import com.example.mente_libre_app.ui.viewmodel.AuthViewModel
import com.example.mente_libre_app.ui.viewmodel.AuthViewModelFactory
import com.example.mente_libre_app.ui.viewmodel.UsuarioViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavGraph(
    navController: NavHostController,
    activity: FragmentActivity
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Lambdas de navegación
    val goCargando       = { navController.navigate(Route.Cargando.path) }
    val goFrase          = { navController.navigate(Route.Frase.path) }
    val goBienvenida1    = { navController.navigate(Route.Bienvenida1.path) }
    val goBienvenida2    = { navController.navigate(Route.Bienvenida2.path) }
    val goBienvenida3    = { navController.navigate(Route.Bienvenida3.path) }
    val goBienvenida4    = { navController.navigate(Route.Bienvenida4.path) }
    val goBienvenida     = { navController.navigate(Route.Bienvenida.path) }
    val goCrear          = { navController.navigate(Route.Crear.path) }
    val goObjetivo       = { navController.navigate(Route.Objetivo.path) }
    val goGenero         = { navController.navigate(Route.Genero.path) }
    val goFoto           = { navController.navigate(Route.Foto.path) }
    val goHuella         = { navController.navigate(Route.Huella.path) }
    val goMascota        = { navController.navigate(Route.Mascota.path) }
    val goSelector       = { navController.navigate(Route.Selector.path) }
    val goNombrarMascota = { navController.navigate(Route.NombrarMascota.path) }
    val goIniciar        = { navController.navigate(Route.Iniciar.path) }
    val goInicio         = { navController.navigate(Route.Inicio.path) }
    val goAjustes        = { navController.navigate(Route.Ajustes.path) }
    val goPerfil         = { navController.navigate(Route.Perfil.path) }
    val goAnimo          = { navController.navigate(Route.Animo.path) }
    val goPuntaje        = { navController.navigate(Route.Puntaje.path) }
    val goBitacora       = { navController.navigate(Route.Bitacora.path) }
    val goDiarioGratitud = { navController.navigate(Route.DiarioGratitud.path) }

    // Rutas simples extra
    val goLineasDeAyuda     = { navController.navigate("lineas_ayuda") }
    val goDesafiosSemanales = { navController.navigate("desafios_semanales") }

    // Inicialización de DB, DAO y repositorio
    val context = LocalContext.current
    val dao = AppDatabase.getInstance(context).userDao()
    val repository = UserRepository(dao)
    val factory = AuthViewModelFactory(repository, context)
    val authViewModel: AuthViewModel = viewModel(factory = factory)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { }
    ) {
        Scaffold { innerPadding ->
            AnimatedNavHost(
                navController = navController,
                startDestination = Route.Portada.path,
                modifier = Modifier.padding(innerPadding),
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { it },
                        animationSpec = tween(400)
                    ) + fadeIn(animationSpec = tween(400))
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { -it },
                        animationSpec = tween(400)
                    ) + fadeOut(animationSpec = tween(400))
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { -it },
                        animationSpec = tween(400)
                    ) + fadeIn(animationSpec = tween(400))
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { it },
                        animationSpec = tween(400)
                    ) + fadeOut(animationSpec = tween(400))
                }
            ) {

                // -------------------- PORTADA + FLUJO INICIAL --------------------
                composable(Route.Portada.path) {
                    val isLoggedIn by authViewModel.isLoggedIn.collectAsState(initial = false)
                    PortadaScreen(onNext = goCargando)

                    LaunchedEffect(isLoggedIn) {
                        if (isLoggedIn) {
                            navController.navigate(Route.Inicio.path) {
                                popUpTo(Route.Portada.path) { inclusive = true }
                            }
                        }
                    }
                }

                composable(Route.Cargando.path) { CargandoScreen(onNext = goFrase) }
                composable(Route.Frase.path) { FraseScreen(onNext = goBienvenida1) }
                composable(Route.Bienvenida1.path) { Bienvenida1Screen(onNext = goBienvenida2) }
                composable(Route.Bienvenida2.path) { Bienvenida2Screen(onNext = goBienvenida3) }
                composable(Route.Bienvenida3.path) { Bienvenida3Screen(onNext = goBienvenida4) }
                composable(Route.Bienvenida4.path) { Bienvenida4Screen(onNext = goBienvenida) }

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

                composable(Route.Objetivo.path) { ObjetivoScreen(onNext = goGenero) }

                composable(Route.Genero.path) {
                    GeneroScreen(onNext = { navController.navigate("FotoScreen") })
                }

                composable(
                    route = "FotoScreen?isEditingProfile={isEditingProfile}",
                    arguments = listOf(
                        navArgument("isEditingProfile") {
                            type = NavType.BoolType
                            defaultValue = false
                        }
                    )
                ) { backStackEntry ->
                    val usuarioViewModel: UsuarioViewModel = viewModel()
                    val isEditing = backStackEntry.arguments?.getBoolean("isEditingProfile") ?: false

                    FotoScreen(
                        usuarioViewModel = usuarioViewModel,
                        isEditingProfile = isEditing,
                        onNext = {
                            if (isEditing) {
                                navController.popBackStack()
                            } else {
                                navController.navigate(Route.Huella.path)
                            }
                        }
                    )
                }

                composable(Route.Huella.path) {
                    HuellaScreen(
                        activity = activity,
                        onVerificado = goMascota
                    )
                }

                composable(Route.Mascota.path) {
                    MascotaScreen(onNext = goSelector)
                }

                composable(Route.Selector.path) {
                    SelectorScreen { mascota ->
                        val encoded = Uri.encode(mascota)
                        navController.navigate("nombrar_mascota/$encoded")
                    }
                }

                composable(
                    route = "nombrar_mascota/{mascota}",
                    arguments = listOf(navArgument("mascota") { type = NavType.StringType })
                ) { backStackEntry ->
                    val mascota = backStackEntry.arguments?.getString("mascota") ?: ""
                    NombrarMascotaScreen(
                        mascota = mascota,
                        onGuardarNombre = {
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

                // -------------------- HOME --------------------
                composable(Route.Inicio.path) {
                    InicioScreen(
                        onNavChange = { index ->
                            when (index) {
                                0 -> navController.navigate(Route.Inicio.path)
                                1 -> { /* futuro */ }
                                2 -> navController.navigate(Route.Bitacora.path)
                                3 -> navController.navigate(Route.Ajustes.path)
                            }
                        },
                        onGoAnimo = goAnimo,
                        onGoPuntaje = goPuntaje,
                        navController = navController
                    )
                }

                // -------------------- BITÁCORA --------------------
                composable(Route.Bitacora.path) {
                    BitacoraScreen(
                        onBack = { navController.popBackStack() },
                        onOpenDiarioGratitud = goDiarioGratitud,
                        onOpenLineasDeAyuda = goLineasDeAyuda,
                        onOpenDesafiosSemanales = goDesafiosSemanales
                    )
                }

                // -------------------- LÍNEAS DE AYUDA --------------------
                composable("lineas_ayuda") {
                    LineasDeAyudaScreen(
                        onBack = { navController.popBackStack() }
                    )
                }

                // -------------------- DESAFÍOS SEMANALES --------------------
                composable("desafios_semanales") {
                    DesafiosSemanalesScreen(
                        onBack = { navController.popBackStack() }
                    )
                }

                // -------------------- DIARIO DE GRATITUD --------------------
                composable(Route.DiarioGratitud.path) {
                    DiarioGratitudScreen(
                        onBack = { navController.popBackStack() }
                    )
                }

                composable(Route.Animo.path) {
                    AnimoScreen(onBack = { navController.popBackStack() })
                }

                composable(Route.Puntaje.path) {
                    PuntajeScreen(onBack = { navController.popBackStack() })
                }

                composable(Route.Organizarse.path) { OrganizarseScreen(navController) }
                composable(Route.Estrategias.path) { EstrategiasScreen(navController) }
                composable(Route.Crisis.path)      { CrisisScreen(navController) }
                composable(Route.Salud.path)       { SaludScreen(navController) }

                // -------------------- AJUSTES --------------------
                composable(Route.Ajustes.path) {
                    AjustesScreen(
                        onItemSelected = { selectedItem ->
                            when (selectedItem) {
                                "inicio" -> navController.navigate(Route.Inicio.path)
                                "perfil" -> navController.navigate(Route.Perfil.path)
                            }
                        },
                        onPerfilClick = { navController.navigate(Route.Perfil.path) },
                        onSeguridadClick = { /* TODO: navegar a seguridad si la creas */ },
                        onNotificacionesClick = { /* TODO */ },
                        onDispositivosClick = { /* TODO */ },
                        onEmergenciaClick = { /* TODO */ },
                        onSoporteClick = { /* TODO */ },
                        onSugerenciasClick = { /* TODO */ },
                        onCerrarSesion = { /* TODO: acá luego haces logout */ }
                    )
                }


                // -------------------- PERFIL --------------------
                composable(Route.Perfil.path) {
                    val usuarioViewModel: UsuarioViewModel = viewModel()
                    PerfilScreen(
                        usuarioViewModel = usuarioViewModel,
                        authViewModel = authViewModel,
                        onBackClick = { navController.popBackStack() },
                        onEditarFotoClick = {
                            navController.navigate("FotoScreen?isEditingProfile=true")
                        }
                    )
                }
            }
        }
    }
}
