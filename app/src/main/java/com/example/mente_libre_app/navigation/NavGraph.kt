package com.example.mente_libre_app.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

import com.example.mente_libre_app.ui.screen.*

@Composable
fun AppNavGraph(navController: NavHostController) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Lambdas de navegación
    val goCargando   = { navController.navigate(Route.Cargando.path) }
    val goFrase   = { navController.navigate(Route.Frase.path) }
    val goBienvenida1 = { navController.navigate(Route.Bienvenida1.path) }
    val goBienvenida2 = { navController.navigate(Route.Bienvenida2.path) }
    val goBienvenida3 = { navController.navigate(Route.Bienvenida3.path) }
    val goBienvenida4 = { navController.navigate(Route.Bienvenida4.path) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { /* vacío */ }
    ) {
        Scaffold { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Route.Portada.path,
                modifier = Modifier.padding(innerPadding)
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
                    // Última pantalla de onboarding
                }
            }
        }
    }
}