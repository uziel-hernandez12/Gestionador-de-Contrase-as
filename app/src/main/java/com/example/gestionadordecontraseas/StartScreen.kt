package com.example.gestionadordecontraseas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Pantalla de inicio
 */

@Composable
fun StartScreen(navController: androidx.navigation.NavHostController) {
    // Fondo de pantalla
    BackgroundImage()

    // Contenedor principal que ocupa todo el tamaño disponible
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Texto en la parte superior
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(
                text = stringResource(id = R.string.tituloApp),
                color = Color.Black,
                fontSize = 48.sp,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        // Texto justo debajo del centro vertical
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                text = stringResource(id = R.string.presentacionApp),
                color = Color.Black,
                fontSize = 28.sp,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 148.dp),
                textAlign = TextAlign.Center
            )
        }

        // Botón en la parte inferior
        Button(
            onClick = { navController.navigate("login") }, // Navegar a la pantalla de inicio de sesión al hacer clic
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 32.dp
                )
                .align(Alignment.BottomCenter),
            shape = RectangleShape
        ) {
            Text(text = stringResource(id = R.string.entrar)) // Texto del botón
        }
    }
}

/**
 * Fondo de la pantalla de inicio
 */
@Composable
fun BackgroundImage() {
    // Utiliza la función painterResource() para cargar el recurso de imagen
    val image = painterResource(id = R.drawable.backgroud_inicio)

    // Crea un diseño de tipo Box para contener la imagen y la capa semitransparente
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Imagen de fondo
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(), // La imagen ocupa todo el tamaño disponible
            contentScale = ContentScale.Crop // Escala la imagen para llenar el espacio, recortando si es necesario
        )
        // Capa semitransparente para dar un efecto visual
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = 0.5f)) // Ajusta la transparencia aquí
        )
    }
}