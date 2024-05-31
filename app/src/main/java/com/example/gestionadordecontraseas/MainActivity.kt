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
import androidx.compose.material3.CenterAlignedTopAppBar
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

class ComponentViewModel : ViewModel() {
    private val _componentes = MutableStateFlow<List<Componente>>(emptyList())
    val componentes: StateFlow<List<Componente>> = _componentes

    fun setComponentes(componentes: List<Componente>) {
        _componentes.value = componentes
    }

    fun updateComponentePassword(componenteId: String, newPassword: String) {
        _componentes.value = _componentes.value.map { componente ->
            if (componente.nombre == componenteId) {
                componente.copy(contraseña = newPassword)
            } else {
                componente
            }
        }
    }
}

class MainActivity : ComponentActivity() {
    private val componentViewModel: ComponentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa la lista de componentes
        componentViewModel.setComponentes(componentes)

        setContent {
            GestionadorDeContraseñasTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "start") {
                    composable("start") { StartScreen(navController) }
                    composable("login") { LoginScreen(navController) }
                    composable("home") { HomeScreen(navController, componentViewModel) }
                    composable("favorites") { FavoriteScreen(navController, componentViewModel) }
                    composable("updatePassword/{componenteId}") { backStackEntry ->
                        val componenteId = backStackEntry.arguments?.getString("componenteId") ?: ""
                        UpdatePasswordScreen(
                            componentViewModel = componentViewModel,
                            componenteId = componenteId,
                            onCloseClick = { navController.popBackStack() },
                            onSaveClick = { newPassword ->
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


@Composable
fun StartScreen(navController: androidx.navigation.NavHostController) {
    BackgroundImage()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Texto superior
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
            onClick = { navController.navigate("login") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 32.dp
                )
                .align(Alignment.BottomCenter),
            shape = RectangleShape
        ) {
            Text(text = stringResource(id = R.string.entrar))
        }
    }
}
@Composable
fun BackgroundImage() {
    // Use the painterResource() function to load the image resource
    val image = painterResource(id = R.drawable.backgroud_inicio)

    // Create a Box layout to contain the image
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Imagen de fondo
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        // Capa semitransparente
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = 0.5f))  // Ajusta la transparencia aquí
        )

    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: androidx.navigation.NavHostController) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var showErrorMessage by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            // Barra superior con el título de la aplicación
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.tituloApp),
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 1.dp, bottom = 48.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.bienvenido),
                    fontSize = 28.sp,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                TextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text(stringResource(id = R.string.username)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(24.dp))

                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(stringResource(id = R.string.password)) },
                    singleLine = true,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
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
                // Mensaje de credenciales incorrectas
                if (showErrorMessage) {
                    Text(
                        text = stringResource(id = R.string.mensajeError),
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                Button(
                    onClick = {
                        if (username == "admin" && password == "admin") {
                            // Usuario y contraseña correctos
                            showErrorMessage = false
                            navController.navigate("home")
                        } else {
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


@Composable
fun ComponenteCard(
    componente: Componente,
    onFavoriteClick: (Componente) -> Unit,
    onDeleteClick: (Componente) -> Unit,
    onEditClick: () -> Unit
) {
    var showPassword by remember { mutableStateOf(false) }
    var showAddNotification by remember { mutableStateOf(false) }
    var showRemoveNotification by remember { mutableStateOf(false) }

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
                Image(
                    painter = painterResource(id = componente.imageResourceId),
                    contentDescription = componente.nombre,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = componente.nombre,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(
                        painter = painterResource(if (showPassword) R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24),
                        contentDescription = stringResource(R.string.visibility)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (showPassword) componente.contraseña else "••••••••",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = onEditClick) {
                    Icon(
                        painter = painterResource(R.drawable.round_edit_24),
                        contentDescription = stringResource(R.string.edit)
                    )
                }
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
            val notificationText = if (componente.esFavorita) "Añadido a favoritos" else "Quitado de favoritos"
            Text(notificationText) // Texto del Snackbar
        }
        LaunchedEffect(Unit) {
            // Ocultar la notificación después de un tiempo
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
            Text("Contraseña eliminada") // Texto del Snackbar
        }
        LaunchedEffect(Unit) {
            // Ocultar la notificación después de un tiempo
            delay(2000) // Cambia este valor para ajustar la duración de la notificación
            showRemoveNotification = false
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, componentViewModel: ComponentViewModel) {
    val componentes by componentViewModel.componentes.collectAsState()
    var isSearching by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }

    val filteredComponentes = if (searchText.isEmpty()) {
        componentes
    } else {
        componentes.filter { it.nombre.contains(searchText, ignoreCase = true) }
    }

    fun toggleFavorite(componente: Componente) {
        componentViewModel.setComponentes(componentes.map {
            if (it == componente) it.copy(esFavorita = !it.esFavorita) else it
        })
    }

    fun deleteComponente(componente: Componente) {
        componentViewModel.setComponentes(componentes.filter { it != componente })
    }

    Scaffold(
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
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredComponentes) { componente ->
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



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(navController: NavHostController, componentViewModel: ComponentViewModel) {
    val componentes by componentViewModel.componentes.collectAsState()
    var isSearching by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }

    val filteredComponentes = if (searchText.isEmpty()) {
        componentes.filter { it.esFavorita }
    } else {
        componentes.filter { it.esFavorita && it.nombre.contains(searchText, ignoreCase = true) }
    }

    fun deleteComponente(componente: Componente) {
        componentViewModel.setComponentes(componentes.filter { it != componente })
    }

    Scaffold(
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
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredComponentes) { componente ->
                ComponenteCard(
                    componente,
                    onFavoriteClick = {},
                    onDeleteClick = { deleteComponente(it) },
                    onEditClick = { navController.navigate("updatePassword/${componente.nombre}") }

                )
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePasswordScreen(
    componentViewModel: ComponentViewModel,
    componenteId: String,
    onCloseClick: () -> Unit,
    onSaveClick: (String) -> Unit
) {
    val componentes by componentViewModel.componentes.collectAsState()
    val componente = componentes.find { it.nombre == componenteId }
    var showErrorMessage by remember { mutableStateOf(false) }

    if (componente != null) {
        var newPassword by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }
        var showNewPassword by remember { mutableStateOf(false) }
        var showConfirmPassword by remember { mutableStateOf(false) }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Actualizar Contraseña") },
                    navigationIcon = {
                        IconButton(onClick = onCloseClick) {
                            Icon(
                                painter = painterResource(id = R.drawable.round_cancel_24),
                                contentDescription = "Cerrar"
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
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
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
                    PasswordField(
                        label = "Nueva contraseña",
                        password = newPassword,
                        onPasswordChange = { newPassword = it },
                        showPassword = showNewPassword,
                        onVisibilityChange = { showNewPassword = !showNewPassword }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    PasswordField(
                        label = "Confirmar contraseña",
                        password = confirmPassword,
                        onPasswordChange = { confirmPassword = it },
                        showPassword = showConfirmPassword,
                        onVisibilityChange = { showConfirmPassword = !showConfirmPassword }
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    if (showErrorMessage) {
                        Text(
                            text = stringResource(id = R.string.contraseñasNoCoinciden),
                            color = Color.Red,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                    Button(
                        onClick = {
                            if (newPassword == confirmPassword && newPassword.isNotEmpty()) {
                                onSaveClick(newPassword)
                            }else {
                                showErrorMessage = true

                            }
                        },
                        enabled = newPassword.isNotEmpty() && confirmPassword.isNotEmpty()
                    ) {
                        Text(text = "Guardar")
                    }
                }
            }
        )
    }
}
@Composable
fun PasswordField(
    label: String,
    password: String,
    onPasswordChange: (String) -> Unit,
    showPassword: Boolean,
    onVisibilityChange: () -> Unit
) {
    Column {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = onVisibilityChange) {
                    Icon(
                        painter = painterResource(if (showPassword) R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24),
                        contentDescription = if (showPassword) "Hide password" else "Show password"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
@Preview(showBackground = true)
@Composable
fun  StartScreenPreview() {
    GestionadorDeContraseñasTheme {
        val navController = rememberNavController()
        StartScreen(navController)
    }
}

/**
 * BottomBar
 */
@Composable
fun BottomBar(navController: androidx.navigation.NavHostController, ) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val previousArgs = navBackStackEntry?.arguments
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {navController.navigate("favorites")}) {
                Icon(
                    imageVector = Icons.Default.Favorite, // Reemplaza esto con el icono que prefieras
                    contentDescription = stringResource(id = R.string.favorite),
                    modifier = Modifier.size(36.dp) // Tamaño del icono ajustado a 36dp
                )
            }
            IconButton(onClick =  { navController.navigate("home") }) {
                Icon(
                    imageVector = Icons.Default.Home, // Reemplaza esto con el icono que prefieras
                    contentDescription = stringResource(id = R.string.home),
                    modifier = Modifier.size(36.dp) // Tamaño del icono ajustado a 36dp
                )
            }
            IconButton(onClick = { /* Acción del cuarto icono (Salir) */ }) {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = stringResource(id = R.string.exit),
                    modifier = Modifier.size(36.dp) // Tamaño del icono ajustado a 36dp
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    isSearching: Boolean,
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    onCancelClick: () -> Unit,
    idTitulo: Int
) {
    TopAppBar(
        title = {
            if (isSearching) {
                TextField(
                    value = searchText,
                    onValueChange = onSearchTextChange,
                    placeholder = { Text(text = stringResource(id = R.string.search_placeholder)) },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                )
            } else {
                Text(
                    text = stringResource(id = idTitulo),
                    fontWeight = FontWeight.Bold
                )
            }
        },
        actions = {
            if (isSearching) {
                IconButton(onClick = onCancelClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.round_cancel_24),
                        contentDescription = stringResource(id = R.string.cancel)
                    )
                }
            } else {
                IconButton(onClick = onSearchClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_search_24),
                        contentDescription = stringResource(id = R.string.search)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}


