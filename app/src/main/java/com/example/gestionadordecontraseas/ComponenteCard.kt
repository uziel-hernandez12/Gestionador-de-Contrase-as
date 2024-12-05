package com.example.gestionadordecontraseas

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestionadordecontraseas.data.Componente
import kotlinx.coroutines.delay

/**
 * contiene el codigo para cada card de la lista
 */
@Composable
fun ComponenteCard(
    componente: Componente,
    onFavoriteClick: (Componente) -> Unit,
    onDeleteClick: (Componente) -> Unit,
    onEditClick: () -> Unit
) {
    // Variables para controlar la visibilidad de la contraseña y las notificaciones
    var showPassword by remember { mutableStateOf(false) }
    var showAddNotification by remember { mutableStateOf(false) }
    var showRemoveNotification by remember { mutableStateOf(false) }

    // Tarjeta que contiene la información del componente
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Imagen del componente
                Image(
                    painter = painterResource(id = componente.imageResourceId),
                    contentDescription = componente.nombre,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(shape = CircleShape)

                )
                Spacer(modifier = Modifier.width(8.dp))
                // Nombre del componente
                Text(
                    text = componente.nombre,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f)
                )
                // Botón para alternar la visibilidad de la contraseña
                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(
                        painter = painterResource(if (showPassword) R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24),
                        contentDescription = stringResource(R.string.visibility)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            // Contraseña del componente (visible o enmascarada)
            Text(
                text = if (showPassword) componente.contraseña else "••••••••",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Botones de acción en la parte inferior de la tarjeta
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Botón para editar el componente
                IconButton(onClick = onEditClick) {
                    Icon(
                        painter = painterResource(R.drawable.round_edit_24),
                        contentDescription = stringResource(R.string.edit)
                    )
                }
                // Botón para marcar como favorito o eliminar de favoritos
                IconButton(onClick = {
                    val isFavorita = componente.esFavorita
                    onFavoriteClick(componente)
                    showAddNotification = !isFavorita // Mostrar la notificación si se añade o quita de favoritos
                }) {
                    Icon(
                        painter = painterResource(if (componente.esFavorita) R.drawable.round_favorite_24 else R.drawable.round_favorite_border_24),
                        contentDescription = stringResource(R.string.favorite)
                    )
                }
                // Botón para eliminar el componente
                IconButton(onClick = {
                    onDeleteClick(componente)
                    showRemoveNotification = true // Mostrar la notificación al eliminar
                }) {
                    Icon(
                        painter = painterResource(R.drawable.round_delete_24),
                        contentDescription = stringResource(R.string.delete)
                    )
                }
            }
        }
    }

    // Snackbar para mostrar la notificación de añadido a favoritos o quitado de favoritos
    if (showAddNotification) {
        Snackbar(
            modifier = Modifier.padding(16.dp),
            action = {
                // No hay acción requerida
            }
        ) {
            // Texto de la notificación
            val notificationText = if (componente.esFavorita) stringResource(id = R.string.añadidoFavoritos) else stringResource(
                id = R.string.quitadoFavoritos
            )
            Text(notificationText)
        }
        // Ocultar la notificación después de un tiempo
        LaunchedEffect(Unit) {
            delay(3000) // Cambia este valor para ajustar la duración de la notificación
            showAddNotification = false
        }
    }

    // Snackbar para mostrar la notificación de eliminación
    if (showRemoveNotification) {
        Snackbar(
            modifier = Modifier.padding(16.dp),
            action = {
                // No hay acción requerida
            }
        ) {
            // Texto de la notificación
            Text(stringResource(id = R.string.contraseñaEliminada))
        }
        // Ocultar la notificación después de un tiempo
        LaunchedEffect(Unit) {
            delay(2000) // Cambia este valor para ajustar la duración de la notificación
            showRemoveNotification = false
        }
    }
}

