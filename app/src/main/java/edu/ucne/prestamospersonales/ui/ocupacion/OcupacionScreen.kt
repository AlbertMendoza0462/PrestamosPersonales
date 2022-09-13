package edu.ucne.prestamospersonales.ui.ocupacion

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.ucne.prestamospersonales.data.AppDataBase
import edu.ucne.prestamospersonales.model.Ocupacion
import kotlinx.coroutines.launch

@Composable
fun OcupacionScreen(context: Context) {
    val db = AppDataBase.getInstance(context)
    val scope = rememberCoroutineScope()
    var descripcion by remember { mutableStateOf("") }
    var salario by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Row() {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
            ) {
                Spacer(modifier = Modifier.height(100.dp))
                Text(
                    text = "Registro de Ocupaciones",
                    fontSize = 30.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(
                            Alignment.CenterHorizontally
                        )
                )
                Spacer(modifier = Modifier.height(30.dp))
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text(text = "Descripción") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = salario,
                    onValueChange = {
                        try {
                            if (!it.equals("")) {
                                var num = it.toDouble()
                            }
                            salario = it
                        } catch (e: NumberFormatException) { salario = salario}
                    },
                    label = { Text(text = "Salario") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                ) {
                    Button(contentPadding = PaddingValues(
                        start = 20.dp,
                        top = 12.dp,
                        end = 20.dp,
                        bottom = 12.dp
                    ), onClick = {
                        scope.launch {
                            db.ocupacionDao.insertOcupacion(
                                Ocupacion(
                                    Descripcion = descripcion,
                                    Salario = salario.toDouble()
                                )
                            )
                        }
                    }) {
                        Icon(
                            Icons.Filled.Done,
                            contentDescription = "Agregar",
                            modifier = Modifier.size(20.dp)
                        )
                        Text(text = "Agregar", fontSize = 20.sp)
                    }
                }
            }
        }
    }
}

