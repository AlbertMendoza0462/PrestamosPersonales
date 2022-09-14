package edu.ucne.prestamospersonales.ui.Persona

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.room.Index
import edu.ucne.prestamospersonales.data.AppDataBase
import edu.ucne.prestamospersonales.model.Ocupacion
import edu.ucne.prestamospersonales.model.Persona
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import java.time.Duration
import java.util.*

@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun PersonaScreen(context: Context, context1: Context) {
    val db = AppDataBase.getInstance(context)
    val scope = rememberCoroutineScope()

    var nombres by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var celular by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }
    var ocupacionId by remember { mutableStateOf(0) }

    var expandedOcupacionId = remember { mutableStateOf(false) }
    var ocupaciones = listOf("Ocupacion sin db 1", "Ocupacion sin db 2", "Ocupacion sin db 3")
    var selectedIndexOcupacionId = remember { mutableStateOf(0) }
    val interactionSourceOcupacionId = remember { MutableInteractionSource() }
    val isPressedOcupacionId: Boolean by interactionSourceOcupacionId.collectIsPressedAsState()
    if (isPressedOcupacionId) expandedOcupacionId.value = true
//    var ocupaciones = db.ocupacionDao.getAll()

    val year: Int
    val month: Int
    val day: Int
    val calendar = Calendar.getInstance()
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()
    val date = remember { mutableStateOf("") }
    val datepickerDialog = DatePickerDialog(
        context1,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            date.value = "$dayOfMonth/" + (month + 1) + "/$year"
            fechaNacimiento = date.value
        }, year, month, day
    )

    val interactionSourceFechaNacimiento = remember { MutableInteractionSource() }
    val isPressedFechaNacimiento: Boolean by interactionSourceFechaNacimiento.collectIsPressedAsState()
    if (isPressedFechaNacimiento) datepickerDialog.show()

    var haveNombresError = false
    var haveTelefonoError = false
    var haveCelularError = false
    var haveEmailError = false
    var haveDireccionError = false
    var haveFechaError = false

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
                    text = "Registro de Personas",
                    fontSize = 30.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(
                            Alignment.CenterHorizontally
                        )
                )
                Spacer(modifier = Modifier.height(30.dp))
                OutlinedTextField(
                    isError = haveNombresError,
                    value = nombres,
                    onValueChange = { nombres = it },
                    label = { Text(text = "Nombres") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    isError = haveTelefonoError,
                    value = telefono,
                    onValueChange = { telefono = it },
                    label = { Text(text = "Telefono") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    isError = haveCelularError,
                    value = celular,
                    onValueChange = { celular = it },
                    label = { Text(text = "Celular") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    isError = haveEmailError,
                    value = email,
                    onValueChange = { email = it },
                    label = { Text(text = "Email") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    isError = haveDireccionError,
                    value = direccion,
                    onValueChange = { direccion = it },
                    label = { Text(text = "Dirección") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    isError = haveFechaError,
                    readOnly = true,
                    value = date.value,
                    onValueChange = { },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Fecha de Nacimiento") },
                    interactionSource = interactionSourceFechaNacimiento
                )

                Column() {
                    OutlinedTextField(
                        readOnly = true,
                        value = ocupaciones[selectedIndexOcupacionId.value],
                        onValueChange = { },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(text = "Ocupación") },
                        interactionSource = interactionSourceOcupacionId
                    )

                    DropdownMenu(
                        expanded = expandedOcupacionId.value,
                        onDismissRequest = { expandedOcupacionId.value = false },
                        modifier = Modifier
                            .shadow(elevation = 2.dp)
                            .fillMaxWidth()
                    ) {
                        ocupaciones.forEachIndexed { index, s ->
                            DropdownMenuItem(onClick = {
                                selectedIndexOcupacionId.value = index
                                expandedOcupacionId.value = false
                                ocupacionId = selectedIndexOcupacionId.value
                            }) {
                                Text(text = s)
                            }
                        }
                    }
                }

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
                        haveNombresError = false
                        haveTelefonoError = false
                        haveCelularError = false
                        haveEmailError = false
                        haveDireccionError = false
                        haveFechaError = false

                        if (nombres.isBlank()) {
                            Toast.makeText(
                                context1,
                                "Nombres no puede  estar vacío",
                                Toast.LENGTH_LONG
                            ).show()
                            haveNombresError = true
                        } else if (!nombres.all { it.isLetter() || it.equals(' ') }) {
                            Toast.makeText(
                                context1,
                                "Nombres solo puede contener letras",
                                Toast.LENGTH_LONG
                            ).show()
                            haveNombresError = true
                        } else if (telefono.isBlank()) {
                            Toast.makeText(
                                context1,
                                "Telefono no puede  estar vacío",
                                Toast.LENGTH_LONG
                            ).show()
                            haveTelefonoError = true
                        } else if (!telefono.all { it.isDigit() || it.equals('(') || it.equals(')') || it.equals('-') || it.equals(' ') || it.equals('+') }) {
                            Toast.makeText(
                                context1,
                                "Telefono solo debe tener numeros, parentesis y guiones",
                                Toast.LENGTH_LONG
                            ).show()
                            haveTelefonoError = true
                        } else if (!(telefono.length > 9)) {
                            Toast.makeText(
                                context1,
                                "Telefono debe tener más de 9 dígitos",
                                Toast.LENGTH_LONG
                            ).show()
                            haveTelefonoError = true
                        } else if (celular.isBlank()) {
                            Toast.makeText(
                                context1,
                                "Celular no puede  estar vacío",
                                Toast.LENGTH_LONG
                            ).show()
                            haveCelularError = true
                        } else if (!celular.all { it.isDigit() || it.equals('(') || it.equals(')') || it.equals('-') || it.equals(' ') || it.equals('+') }) {
                            Toast.makeText(
                                context1,
                                "Celular solo debe tener numeros",
                                Toast.LENGTH_LONG
                            ).show()
                            haveCelularError = true
                        } else if (!(celular.length > 9)) {
                            Toast.makeText(
                                context1,
                                "Celular debe tener más de 9 dígitos",
                                Toast.LENGTH_LONG
                            ).show()
                            haveCelularError = true

                        } else if (email.isBlank()) {
                            Toast.makeText(
                                context1,
                                "Email no puede  estar vacío",
                                Toast.LENGTH_LONG
                            ).show()
                            haveEmailError = true
                        } else if (!email.any { it.equals('@') } || !email.any { it.equals('.') }) {
                            Toast.makeText(
                                context1,
                                "Email inválido",
                                Toast.LENGTH_LONG
                            ).show()
                            haveEmailError = true
                        } else if (direccion.isBlank()) {
                            Toast.makeText(
                                context1,
                                "Direccion no puede  estar vacía",
                                Toast.LENGTH_LONG
                            ).show()
                            haveDireccionError = true
                        } else if (fechaNacimiento.isBlank()) {
                            Toast.makeText(
                                context1,
                                "Fecha de nacimiento no puede  estar vacía",
                                Toast.LENGTH_LONG
                            ).show()
                            haveFechaError = true
                        } else {
                            Toast.makeText(
                                context1,
                                "Bien, formulario válido!",
                                Toast.LENGTH_LONG
                            ).show()
                            scope.launch {
                                db.personaDao.insertPersona(
                                    Persona(
                                        Nombres = nombres,
                                        Telefono = telefono,
                                        Celular = celular,
                                        Email = email,
                                        Direccion = direccion,
                                        FechaNacimiento = fechaNacimiento,
                                        OcupacionId = ocupacionId
                                    )
                                )
                            }
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