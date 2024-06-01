package com.example.gestionadordecontraseas

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.gestionadordecontraseas.data.ComponentViewModel
import com.example.gestionadordecontraseas.data.Componente
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavHostController) {

    Scaffold(
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
        bottomBar = { BottomBar(navController) },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(30.dp),
                verticalArrangement = Arrangement.Top
            ) {
                item {

                    Spacer(modifier = Modifier.height(15.dp))
                    // Foto debajo del texto
                    Image(
                        painter = painterResource(R.drawable.fotomia),
                        contentDescription = stringResource(id = R.string.fotografiaMia),
                        modifier = Modifier
                            .size(128.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Gray, CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = stringResource(id = R.string.descripcion)
                        .trimIndent(),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Justify
                    )

                    Spacer(modifier = Modifier.height(25.dp))
                }
            }
        }
    )
}
