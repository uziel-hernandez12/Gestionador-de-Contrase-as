package com.example.gestionadordecontraseas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState

/**
 * BottomBar
 */
@Composable
fun BottomBar(navController: androidx.navigation.NavHostController) {
    // Obtener la entrada del back stack actual del NavController
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    // Argumentos de la entrada del back stack anterior, si existe
    val previousArgs = navBackStackEntry?.arguments

    // Barra inferior con el color de fondo y de contenido del tema
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,  // Color de fondo de la barra
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,  // Color del contenido de la barra
        modifier = Modifier.fillMaxWidth()  // Modificador para que la barra ocupe todo el ancho disponible
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),  // Modificador para que la fila ocupe todo el ancho disponible
            horizontalArrangement = Arrangement.SpaceBetween,  // Espaciado entre los elementos de la fila
            verticalAlignment = Alignment.CenterVertically  // Alineación vertical de los elementos en la fila
        ) {
            // Icono y acción para navegar a la pantalla de favoritos
            IconButton(onClick = { navController.navigate("favorites") }) {
                Icon(
                    imageVector = Icons.Default.Favorite,  // Icono de favoritos
                    contentDescription = stringResource(id = R.string.favorite),  // Descripción del contenido
                    modifier = Modifier.size(36.dp)  // Tamaño del icono
                )
            }
            // Icono y acción para navegar a la pantalla de inicio
            IconButton(onClick = { navController.navigate("home") }) {
                Icon(
                    imageVector = Icons.Default.Home,  // Icono de inicio
                    contentDescription = stringResource(id = R.string.home),  // Descripción del contenido
                    modifier = Modifier.size(36.dp)  // Tamaño del icono
                )
            }
            IconButton(onClick = {navController.navigate("acercaDe") }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_account_circle_24),  // Icono de salir
                    contentDescription = stringResource(id = R.string.acercaDe),  // Descripción del contenido
                    modifier = Modifier.size(36.dp)  // Tamaño del icono
                )
            }
            IconButton(onClick = {
                navController.navigate("login") {
                    popUpTo("start") { inclusive = true }
                }
            }) {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = stringResource(id = R.string.exit),
                    modifier = Modifier.size(36.dp)
                )
            }


        }
    }
}