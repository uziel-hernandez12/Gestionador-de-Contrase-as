package com.example.gestionadordecontraseas

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.gestionadordecontraseas.data.ComponentViewModel
import com.example.gestionadordecontraseas.data.Componente

/**
 * Pantalla que solo muestra las contraseñas favoritas
 */
@Composable
fun FavoriteScreen(navController: NavHostController, componentViewModel: ComponentViewModel) {
    // Observa el flujo de componentes del ViewModel y actualiza la UI cuando cambia
    val componentes by componentViewModel.componentes.collectAsState()

    // Estado para controlar la búsqueda
    var isSearching by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }

    // Función para alternar el estado de favorito de un componente
    fun toggleFavorite(componente: Componente) {
        componentViewModel.setComponentes(componentes.map {
            if (it == componente) it.copy(esFavorita = !it.esFavorita) else it
        })
    }

    // Filtrar componentes favoritos según el texto de búsqueda
    val filteredComponentes = if (searchText.isEmpty()) {
        componentes.filter { it.esFavorita }
    } else {
        componentes.filter { it.esFavorita && it.nombre.contains(searchText, ignoreCase = true) }
    }

    // Función para eliminar un componente
    fun deleteComponente(componente: Componente) {
        componentViewModel.setComponentes(componentes.filter { it != componente })
    }

    // Diseño de la pantalla con Scaffold
    Scaffold(
        // Barra superior con la funcionalidad de búsqueda
        topBar = {
            TopBar(
                isSearching = isSearching,
                searchText = searchText,
                onSearchTextChange = { searchText = it },
                onSearchClick = { isSearching = true },
                onCancelClick = {
                    isSearching = false
                    searchText = ""
                },
                R.string.contraseñasFavoritas
            )
        },
        // Barra inferior con botones de navegación
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->
        // Columna vertical con lista de componentes favoritos
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier.fillMaxSize()
        ) {
            // Mostrar cada componente favorito en la lista
            items(filteredComponentes) { componente ->
                // Renderizar tarjeta de componente
                ComponenteCard(
                    componente,
                    onFavoriteClick = { toggleFavorite(it) },
                    onDeleteClick = { deleteComponente(it) },
                    onEditClick = { navController.navigate("updatePassword/${componente.nombre}") }
                )
            }
        }
    }
}

