//package com.example.mente_libre_app
//
//import android.os.Bundle
//import androidx.fragment.app.FragmentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.runtime.collectAsState
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//import com.example.mente_libre_app.navigation.Route
//import com.example.mente_libre_app.ui.screen.*
//import com.example.mente_libre_app.ui.theme.Mente_Libre_AppTheme
//import com.example.mente_libre_app.ui.viewmodel.*
//import com.example.mente_libre_app.data.remote.core.RetrofitInstance
//import com.example.mente_libre_app.data.repository.EmotionRepository
//import com.example.mente_libre_app.data.repository.UserRepository
//import com.example.mente_libre_app.data.local.TokenDataStore
//
//class TestActivity : FragmentActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        setContent {
//
//            val navController = rememberNavController()
//
//            // ----------------------------------------
//            //            AUTH VIEWMODEL
//            // ----------------------------------------
//
//            val tokenStore = TokenDataStore(this)
//            val authApi = RetrofitInstance.createAuthApi(this)
//
//            val userRepository = UserRepository(authApi, tokenStore)
//
//            val authViewModel: AuthViewModel =
//                viewModel(factory = AuthViewModelFactory(userRepository))
//
//            // ----------------------------------------
//            //            EMOTION VIEWMODEL
//            // ----------------------------------------
//
//            val emotionApi = RetrofitInstance.createEmotionApi(this)
//            val emotionRepository = EmotionRepository(emotionApi)
//
//            val emotionViewModel: EmotionViewModel =
//                viewModel(factory = EmotionViewModelFactory(emotionRepository))
//
//            // ----------------------------------------
//            //            USUARIO VIEWMODEL
//            // ----------------------------------------
//            val usuarioViewModel: UsuarioViewModel =
//                viewModel(factory = UsuarioViewModelFactory(this))
//
//            val tema = usuarioViewModel.tema.collectAsState().value
//            val foto = usuarioViewModel.fotoPerfil.collectAsState().value
//
//            Mente_Libre_AppTheme(selectedTheme = tema) {
//
//                NavHost(
//                    navController = navController,
//                    startDestination = "inicio"
//                ) {
//
//                    composable("inicio") {
//                        InicioScreen(
//                            navController = navController,
//                            fotoPerfil = foto,
//                            nombreUsuario = "Usuario",
//                            onNavChange = { index ->
//                                when (index) {
//                                    0 -> navController.navigate("inicio")
//                                    1 -> {}
//                                    2 -> navController.navigate("perfil")
//                                    3 -> navController.navigate("ajustes")
//                                }
//                            },
//                            onGoAnimo = { navController.navigate(Route.Animo.path) }
//                        )
//                    }
//
//                    composable(Route.Animo.path) {
//                        AnimoScreen(
//                            authViewModel = authViewModel,
//                            emotionViewModel = emotionViewModel,
//                            onBack = { navController.popBackStack() }
//                        )
//                    }
//
//                    composable("ajustes") {
//                        AjustesScreen(
//                            selectedItem = "ajustes",
//                            onItemSelected = {},
//                            onPerfilClick = { navController.navigate("perfil") },
//                            onSeguridadClick = {},
//                            onNotificacionesClick = {},
//                            onTemaClick = {},
//                            onEmergenciaClick = {},
//                            onSoporteClick = {},
//                            onSugerenciasClick = {}
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
