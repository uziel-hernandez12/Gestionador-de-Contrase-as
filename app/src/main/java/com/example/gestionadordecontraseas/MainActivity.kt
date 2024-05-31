package com.example.gestionadordecontraseas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.gestionadordecontraseas.data.Componente
import com.example.gestionadordecontraseas.data.componentes
import com.example.gestionadordecontraseas.ui.theme.ui.theme.GestionadorDeContraseñasTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import java.util.Locale

class ComponentViewModel : ViewModel() {
    // MutableStateFlow para almacenar y emitir una lista mutable de Componente
    private val _componentes = MutableStateFlow<List<Componente>>(emptyList())

    // StateFlow expuesto para observar cambios en la lista de Componente
    val componentes: StateFlow<List<Componente>> = _componentes

    // Método para establecer la lista de componentes
    fun setComponentes(componentes: List<Componente>) {
        _componentes.value = componentes
    }

    // Método para actualizar la contraseña de un componente dado su ID
    fun updateComponentePassword(componenteId: String, newPassword: String) {
        _componentes.value = _componentes.value.map { componente ->
            // Si el nombre del componente coincide con el ID proporcionado, se actualiza su contraseña
            if (componente.nombre == componenteId) {
                componente.copy(contraseña = newPassword)
            } else {
                // Si no coincide, se deja el componente sin cambios
                componente
            }
        }
    }
}

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

    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Scaffold que proporciona la estructura básica de la pantalla
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "") },
                actions = {
                    Box {
                        IconButton(onClick = { expanded = true }) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_settings_24), // Reemplaza con el id de tu icono de engrane
                                contentDescription = stringResource(id = R.string.configuracion)
                            )
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
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


/**
 * Pantalla que se muestra al iniciar sesion
 */
@Composable
fun HomeScreen(navController: NavHostController, componentViewModel: ComponentViewModel) {
    // Observa el flujo de componentes del ViewModel y actualiza la UI cuando cambia
    val componentes by componentViewModel.componentes.collectAsState()

    // Estado para controlar la búsqueda
    var isSearching by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }

    // Filtrar componentes según el texto de búsqueda
    val filteredComponentes = if (searchText.isEmpty()) {
        componentes
    } else {
        componentes.filter { it.nombre.contains(searchText, ignoreCase = true) }
    }

    // Función para alternar el estado de favorito de un componente
    fun toggleFavorite(componente: Componente) {
        componentViewModel.setComponentes(componentes.map {
            if (it == componente) it.copy(esFavorita = !it.esFavorita) else it
        })
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
                R.string.contraseñas
            )
        },
        // Barra inferior con botones de navegación
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->
        // Columna vertical con lista de componentes
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier.fillMaxSize()
        ) {
            // Mostrar cada componente en la lista
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


@Preview(showBackground = true)  // Anotación para mostrar una vista previa en el diseñador
@Composable
fun StartScreenPreview() {
    GestionadorDeContraseñasTheme {  // Aplicar el tema de la aplicación
        val navController = rememberNavController()  // Recordar el NavController para la navegación
        StartScreen(navController)  // Mostrar la pantalla de inicio en la vista previa
    }
}


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
            // Icono y acción para realizar una acción específica (salir, por ejemplo)
            IconButton(onClick = { /* Acción del cuarto icono (Salir) */ }) {
                Icon(
                    imageVector = Icons.Default.ExitToApp,  // Icono de salir
                    contentDescription = stringResource(id = R.string.exit),  // Descripción del contenido
                    modifier = Modifier.size(36.dp)  // Tamaño del icono
                )
            }
        }
    }
}

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


