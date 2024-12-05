package com.example.gestionadordecontraseas

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Pantalla de login
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: androidx.navigation.NavHostController) {
    // Variables para almacenar el nombre de usuario, contraseña, visibilidad de la contraseña y estado de mensaje de error
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var showErrorMessage by remember { mutableStateOf(false) }

    // Variable para controlar la expansión del menú desplegable
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Scaffold que proporciona la estructura básica de la pantalla
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "") },
                actions = {
                    Box {
                        // Botón de configuración
                        IconButton(onClick = { expanded = true }) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_settings_24), // Reemplaza con el id de tu icono de engrane
                                contentDescription = stringResource(id = R.string.configuracion)
                            )
                        }
                        // Menú desplegable para seleccionar el idioma
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(onClick = {
                                setLocale(context, "es")
                                expanded = false
                            }, text = { Text("Español") },
                                leadingIcon = {
                                    Image(
                                        painter = painterResource(id = R.drawable.espa_ol_mexico24),
                                        contentDescription = null,
                                        modifier = Modifier.size(24.dp)
                                    )
                                })
                            DropdownMenuItem(onClick = {
                                setLocale(context, "en")
                                expanded = false
                            }, text = { Text("English") },
                                leadingIcon = {
                                    Image(
                                        painter = painterResource(id = R.drawable.ingles_eu24),
                                        contentDescription = null,
                                        modifier = Modifier.size(24.dp)
                                    )
                                })
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        // Contenedor principal que ocupa todo el tamaño disponible
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.TopCenter
        ) {
            // Columna para organizar los elementos verticalmente
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 48.dp)
            ) {
                // Imagen del logo más grande
                Image(
                    painter = painterResource(id = R.drawable.logo_app_removebg_preview),
                    contentDescription = stringResource(id = R.string.logo),
                    modifier = Modifier
                        .size(300.dp)  // Ajusta el tamaño según sea necesario
                        .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 24.dp)
                )

                // Texto de bienvenida
                Text(
                    text = stringResource(id = R.string.bienvenido),
                    fontSize = 28.sp,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Campo de texto para el nombre de usuario
                TextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text(stringResource(id = R.string.username)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(24.dp))

                // Campo de texto para la contraseña
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(stringResource(id = R.string.password)) },
                    singleLine = true,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        // Icono para cambiar la visibilidad de la contraseña
                        val image = if (passwordVisible)
                            painterResource(id = R.drawable.baseline_visibility_24)
                        else
                            painterResource(id = R.drawable.baseline_visibility_off_24)

                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(painter = image, contentDescription = null)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Mensaje de error si las credenciales son incorrectas
                if (showErrorMessage) {
                    Text(
                        text = stringResource(id = R.string.mensajeError),
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                // Botón de inicio de sesión
                Button(
                    onClick = {
                        if (username == "admin" && password == "admin") {
                            // Usuario y contraseña correctos
                            showErrorMessage = false
                            navController.navigate("home")
                        } else {
                            // Mostrar mensaje de error si las credenciales son incorrectas
                            showErrorMessage = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(id = R.string.login))
                }
            }
        }
    }
}
