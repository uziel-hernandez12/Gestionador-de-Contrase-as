package com.example.gestionadordecontraseas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * TopBar
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    isSearching: Boolean,  // Indica si se está realizando una búsqueda
    searchText: String,  // Texto de la búsqueda
    onSearchTextChange: (String) -> Unit,  // Función para manejar cambios en el texto de búsqueda
    onSearchClick: () -> Unit,  // Función para iniciar la búsqueda
    onCancelClick: () -> Unit,  // Función para cancelar la búsqueda
    idTitulo: Int  // ID del recurso de cadena para el título cuando no se está buscando
) {
    // Barra superior con título o campo de búsqueda según el estado de búsqueda
    TopAppBar(
        title = {
            if (isSearching) {  // Si se está buscando, muestra un campo de texto para la búsqueda
                TextField(
                    value = searchText,  // Valor del texto de búsqueda
                    onValueChange = onSearchTextChange,  // Función para manejar cambios en el texto de búsqueda
                    placeholder = { Text(text = stringResource(id = R.string.search_placeholder)) },  // Marcador de posición del campo de búsqueda
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,  // Color de fondo del campo de búsqueda
                        focusedIndicatorColor = Color.Transparent,  // Color del indicador enfocado
                        unfocusedIndicatorColor = Color.Transparent,  // Color del indicador no enfocado
                        cursorColor = MaterialTheme.colorScheme.onPrimaryContainer,  // Color del cursor de texto
                    ),
                    shape = RoundedCornerShape(8.dp),  // Forma del campo de búsqueda (esquinas redondeadas)
                    modifier = Modifier
                        .fillMaxWidth()  // Modificador para que el campo de búsqueda ocupe todo el ancho disponible
                        .padding(horizontal = 8.dp)  // Padding horizontal para el campo de búsqueda
                )
            } else {  // Si no se está buscando, muestra el título
                Text(
                    text = stringResource(id = idTitulo),  // Título obtenido del recurso de cadena
                    fontWeight = FontWeight.Bold  // Estilo de fuente para el título
                )
            }
        },
        actions = {
            if (isSearching) {  // Si se está buscando, muestra un botón de cancelar búsqueda
                IconButton(onClick = onCancelClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.round_cancel_24),  // Icono de cancelar búsqueda
                        contentDescription = stringResource(id = R.string.cancel)  // Descripción del contenido del icono
                    )
                }
            } else {  // Si no se está buscando, muestra un botón de iniciar búsqueda
                IconButton(onClick = onSearchClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_search_24),  // Icono de iniciar búsqueda
                        contentDescription = stringResource(id = R.string.search)  // Descripción del contenido del icono
                    )
                }
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,  // Color de fondo de la barra superior
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,  // Color del contenido del título
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,  // Color del contenido del icono de navegación
            actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer  // Color del contenido del icono de acción
        )
    )
}
