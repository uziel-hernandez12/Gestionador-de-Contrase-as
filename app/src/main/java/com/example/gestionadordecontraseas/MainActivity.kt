package com.example.gestionadordecontraseas

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gestionadordecontraseas.data.ComponentViewModel
import com.example.gestionadordecontraseas.data.componentes
import com.example.gestionadordecontraseas.ui.theme.ui.theme.GestionadorDeContraseñasTheme
import java.util.Locale


class MainActivity : ComponentActivity() {
    // ViewModel para manejar la lógica de los componentes
    private val componentViewModel: ComponentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa la lista de componentes en el ViewModel
        componentViewModel.setComponentes(componentes)

        // Establece el diseño de la actividad utilizando Compose
        setContent {
            // Aplica el tema personalizado a la actividad
            GestionadorDeContraseñasTheme {
                // Obtiene el NavController para la navegación entre pantallas
                val navController = rememberNavController()

                // Configura el sistema de navegación con las pantallas disponibles
                NavHost(navController = navController, startDestination = "start") {
                    // Define la pantalla de inicio
                    composable("start") { StartScreen(navController) }
                    // Define la pantalla de inicio de sesión
                    composable("login") { LoginScreen(navController) }
                    // Define la pantalla principal, pasando el ViewModel como argumento
                    composable("home") { HomeScreen(navController, componentViewModel) }
                    // Define la pantalla de favoritos, pasando el ViewModel como argumento
                    composable("favorites") { FavoriteScreen(navController, componentViewModel) }
                    // Define la pantalla de actualización de contraseña, pasando el ViewModel y el ID del componente como argumentos
                    composable("updatePassword/{componenteId}") { backStackEntry ->
                        val componenteId = backStackEntry.arguments?.getString("componenteId") ?: ""
                        UpdatePasswordScreen(
                            componentViewModel = componentViewModel,
                            componenteId = componenteId,
                            onCloseClick = { navController.popBackStack() },
                            onSaveClick = { newPassword ->
                                // Actualiza la contraseña del componente y vuelve atrás en la pila de navegación
                                componentViewModel.updateComponentePassword(componenteId, newPassword)
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}

/**
 * Funcion para cambiar el idioma a la app
 */
fun setLocale(context: Context, languageCode: String) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)
    val config = Configuration()
    config.setLocale(locale)
    context.resources.updateConfiguration(config, context.resources.displayMetrics)

    // Reinicia la actividad para aplicar el cambio de idioma
    val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
    intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    context.startActivity(intent)
}



@Preview(showBackground = true)  // Anotación para mostrar una vista previa en el diseñador
@Composable
fun StartScreenPreview() {
    GestionadorDeContraseñasTheme {  // Aplicar el tema de la aplicación
        val navController = rememberNavController()  // Recordar el NavController para la navegación
        StartScreen(navController)  // Mostrar la pantalla de inicio en la vista previa
    }
}






