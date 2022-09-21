package edu.ucne.prestamospersonales.ui.prestamo

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import edu.ucne.prestamospersonales.utils.Screen
import java.lang.reflect.Modifier
import androidx.compose.foundation.lazy.items

@Composable
fun ConsultaPrestamoScreen(
    navHostController: NavHostController,
    viewModel: PrestamoViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Consulta de Prestamos") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navHostController.navigate(Screen.PrestamoScreen.route)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }

    ) {
        Column(
            //modifier = Modifier
            //.padding(it)
            //.padding(8.dp)
        ) {

            Button(onClick = { navHostController.navigate(Screen.PrestamoScreen.route) }) {
                Text(text = "Nuevo Prestamo")
            }
            val lista = viewModel.prestamos.collectAsState(initial = emptyList())

            LazyColumn(/*modifier = Modifier.fillMaxWidth()*/) {
                items(lista.value) { prestamo ->
                    Row() {
                        Text(text = prestamo.Concepto)
                    }
                }
            }
        }
    }
}