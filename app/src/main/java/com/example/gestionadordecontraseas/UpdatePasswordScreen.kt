package com.example.gestionadordecontraseas

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestionadordecontraseas.data.ComponentViewModel

/**
 * Muestra la pantalla para actualizar una contraseña
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePasswordScreen(
    componentViewModel: ComponentViewModel,
    componenteId: String,
    onCloseClick: () -> Unit,
    onSaveClick: (String) -> Unit
) {
    // Observa el flujo de componentes del ViewModel y actualiza la UI cuando cambia
    val componentes by componentViewModel.componentes.collectAsState()

    // Encuentra el componente correspondiente al ID proporcionado
    val componente = componentes.find { it.nombre == componenteId }

    // Estado para controlar la visibilidad del mensaje de error
    var showErrorMessage by remember { mutableStateOf(false) }

    // Si el componente existe, muestra la pantalla de actualización de contraseña
    if (componente != null) {
        // Estado para la nueva contraseña
        var newPassword by remember { mutableStateOf("") }

        // Estado para la confirmación de la nueva contraseña
        var confirmPassword by remember { mutableStateOf("") }

        // Estado para controlar la visibilidad de la nueva contraseña
        var showNewPassword by remember { mutableStateOf(false) }

        // Estado para controlar la visibilidad de la confirmación de la nueva contraseña
        var showConfirmPassword by remember { mutableStateOf(false) }

        // Diseño de la pantalla con Scaffold
        Scaffold(
            // Barra superior con título y botón de cerrar
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.actualizarContraseña)) },
                    navigationIcon = {
                        IconButton(onClick = onCloseClick) {
                            Icon(
                                painter = painterResource(id = R.drawable.round_cancel_24),
                                contentDescription = stringResource(id = R.string.cerrar)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            },
            // Contenido principal de la pantalla
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Mostrar nombre e imagen del componente
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = componente.imageResourceId),
                            contentDescription = componente.nombre,
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = componente.nombre,
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 18.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    // Campo de entrada de la nueva contraseña
                    PasswordField(
                        label = R.string.nuevaContraseña,
                        password = newPassword,
                        onPasswordChange = { newPassword = it },
                        showPassword = showNewPassword,
                        onVisibilityChange = { showNewPassword = !showNewPassword }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    // Campo de entrada para confirmar la nueva contraseña
                    PasswordField(
                        label = R.string.confirmarContraseña,
                        password = confirmPassword,
                        onPasswordChange = { confirmPassword = it },
                        showPassword = showConfirmPassword,
                        onVisibilityChange = { showConfirmPassword = !showConfirmPassword }
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    // Mostrar mensaje de error si las contraseñas no coinciden
                    if (showErrorMessage) {
                        Text(
                            text = stringResource(id = R.string.contraseñasNoCoinciden),
                            color = Color.Red,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                    // Botón para guardar la nueva contraseña
                    Button(
                        onClick = {
                            if (newPassword == confirmPassword && newPassword.isNotEmpty()) {
                                onSaveClick(newPassword)
                            } else {
                                showErrorMessage = true
                            }
                        },
                        enabled = newPassword.isNotEmpty() && confirmPassword.isNotEmpty()
                    ) {
                        Text(text = stringResource(id = R.string.guardar))
                    }
                }
            }
        )
    }
}

/**
 * Contiene el funcionamiento de las cajas de texto para actualizar la contraseña
 */
@Composable
fun PasswordField(
    label: Int,  // Etiqueta del campo de contraseña
    password: String,  // Contraseña actual
    onPasswordChange: (String) -> Unit,  // Función para manejar cambios en la contraseña
    showPassword: Boolean,  // Indica si se debe mostrar la contraseña
    onVisibilityChange: () -> Unit  // Función para cambiar la visibilidad de la contraseña
) {
    // Columna que contiene el campo de contraseña
    Column {
        // Etiqueta del campo de contraseña
        Text(
            text = stringResource(id = label),  // Se obtiene el texto de la etiqueta desde los recursos
            style = MaterialTheme.typography.bodyMedium  // Estilo de texto para la etiqueta
        )
        // Campo de texto con contorno para la contraseña
        OutlinedTextField(
            value = password,  // Valor de la contraseña
            onValueChange = onPasswordChange,  // Función para manejar cambios en la contraseña
            // Transformación visual para mostrar u ocultar la contraseña
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            // Icono que muestra u oculta la contraseña
            trailingIcon = {
                IconButton(onClick = onVisibilityChange) {
                    // Selecciona el ícono basado en si se debe mostrar o no la contraseña
                    Icon(
                        painter = painterResource(if (showPassword) R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24),
                        // Descripción del contenido del ícono basada en si se muestra u oculta la contraseña
                        contentDescription = if (showPassword) stringResource(id = R.string.esconderPassword) else stringResource(
                            id = R.string.mostrarPassword
                        )
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()  // Modificador para que el campo de texto ocupe todo el ancho disponible
        )
    }
}
