package com.example.gestionadordecontraseas

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavHostController) {

    // Scaffold que proporciona la estructura básica de la pantalla
    Scaffold(
        // Barra superior
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,  // Color de fondo de la barra superior
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,  // Color del contenido del título
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,  // Color del contenido del icono de navegación
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer  // Color del contenido del icono de acción
                ),
                title = {
                    Text(
                        text = stringResource(id = R.string.acercaDe),  // Título obtenido del recurso de cadena
                        fontWeight = FontWeight.Bold  // Estilo de fuente para el título
                    )
                },
            )
        },
        // Barra inferior
        bottomBar = { BottomBar(navController) },
        // Contenido principal de la pantalla
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)  // Padding interno del Scaffold
                    .fillMaxSize()  // Ocupa todo el tamaño disponible
                    .padding(30.dp),  // Padding adicional
                verticalArrangement = Arrangement.Top  // Alineación vertical al principio
            ) {
                item {
                    Spacer(modifier = Modifier.height(15.dp))  // Espacio en blanco

                    // Imagen de perfil
                    Image(
                        painter = painterResource(R.drawable.fotomia),
                        contentDescription = stringResource(id = R.string.fotografiaMia),  // Descripción de la imagen
                        modifier = Modifier
                            .size(128.dp)  // Tamaño de la imagen
                            .clip(CircleShape)  // Forma circular
                            .border(2.dp, Color.Gray, CircleShape),  // Borde gris
                        contentScale = ContentScale.Crop  // Escalado de contenido
                    )

                    Spacer(modifier = Modifier.height(16.dp))  // Espacio en blanco

                    // Descripción del usuario
                    Text(
                        text = stringResource(id = R.string.descripcion).trimIndent(),  // Texto obtenido del recurso de cadena
                        fontSize = 16.sp,  // Tamaño de fuente
                        textAlign = TextAlign.Justify  // Alineación del texto justificada
                    )

                    Spacer(modifier = Modifier.height(25.dp))  // Espacio en blanco
                }
            }
        }
    )
}
