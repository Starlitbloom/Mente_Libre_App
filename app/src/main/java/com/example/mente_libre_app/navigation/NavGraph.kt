package com.example.mente_libre_app.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.mente_libre_app.ui.screen.*
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.rememberCoroutineScope

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavGraph(navController: NavHostController = rememberAnimatedNavController()) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Lambdas de navegación
    val goCargando   = { navController.navigate(Route.Cargando.path) }
    val goFrase      = { navController.navigate(Route.Frase.path) }
    val goBienvenida1 = { navController.navigate(Route.Bienvenida1.path) }
    val goBienvenida2 = { navController.navigate(Route.Bienvenida2.path) }
    val goBienvenida3 = { navController.navigate(Route.Bienvenida3.path) }
    val goBienvenida4 = { navController.navigate(Route.Bienvenida4.path) }
    val goBienvenida = { navController.navigate(Route.Bienvenida.path) }
    val goCrear = { navController.navigate(Route.Crear.path) }
    val goIniciar = { navController.navigate(Route.Iniciar.path)}

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { /* vacío */ }
    ) {
        Scaffold { innerPadding ->
            AnimatedNavHost(
                navController = navController,
                startDestination = Route.Portada.path,
                modifier = Modifier.padding(innerPadding),
                enterTransition = { slideInHorizontally(
                    initialOffsetX = { it }, animationSpec = tween(400)
                ) + fadeIn(animationSpec = tween(400)) },
                exitTransition = { slideOutHorizontally(
                    targetOffsetX = { -it }, animationSpec = tween(400)
                ) + fadeOut(animationSpec = tween(400)) },
                popEnterTransition = { slideInHorizontally(
                    initialOffsetX = { -it }, animationSpec = tween(400)
                ) + fadeIn(animationSpec = tween(400)) },
                popExitTransition = { slideOutHorizontally(
                    targetOffsetX = { it }, animationSpec = tween(400)
                ) + fadeOut(animationSpec = tween(400)) }
            ) {
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
                    CrearScreen(
                        onComenzarClick = { /* acción: registrar usuario o siguiente */ },
                        onLoginClick = goIniciar
                    )
                }
            }
        }
    }
}
