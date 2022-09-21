package edu.ucne.prestamospersonales.ui.persona

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import edu.ucne.prestamospersonales.data.AppDataBase
import edu.ucne.prestamospersonales.model.Persona
import edu.ucne.prestamospersonales.ui.prestamo.PrestamoViewModel
import kotlinx.coroutines.launch
import java.util.*

@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun PersonaScreen(
    navHostController: NavHostController,
    viewModel: PersonaViewModel = hiltViewModel()
) {
    val context1 = LocalContext.current
    val scope = rememberCoroutineScope()

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
            viewModel.fechaNacimiento = date.value
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
                    value = viewModel.nombres,
                    onValueChange = { viewModel.nombres = it },
                    label = { Text(text = "Nombres") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    isError = haveTelefonoError,
                    value = viewModel.telefono,
                    onValueChange = { viewModel.telefono = it },
                    label = { Text(text = "Telefono") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    isError = haveCelularError,
                    value = viewModel.celular,
                    onValueChange = { viewModel.celular = it },
                    label = { Text(text = "Celular") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    isError = haveEmailError,
                    value = viewModel.email,
                    onValueChange = { viewModel.email = it },
                    label = { Text(text = "Email") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    isError = haveDireccionError,
                    value = viewModel.direccion,
                    onValueChange = { viewModel.direccion = it },
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
                                viewModel.ocupacionId = selectedIndexOcupacionId.value
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

                        if (viewModel.nombres.isBlank()) {
                            Toast.makeText(
                                context1,
                                "Nombres no puede  estar vacío",
                                Toast.LENGTH_LONG
                            ).show()
                            haveNombresError = true
                        } else if (!viewModel.nombres.all { it.isLetter() || it.equals(' ') }) {
                            Toast.makeText(
                                context1,
                                "Nombres solo puede contener letras",
                                Toast.LENGTH_LONG
                            ).show()
                            haveNombresError = true
                        } else if (viewModel.telefono.isBlank()) {
                            Toast.makeText(
                                context1,
                                "Telefono no puede  estar vacío",
                                Toast.LENGTH_LONG
                            ).show()
                            haveTelefonoError = true
                        } else if (!viewModel.telefono.all { it.isDigit() || it.equals('(') || it.equals(')') || it.equals('-') || it.equals(' ') || it.equals('+') }) {
                            Toast.makeText(
                                context1,
                                "Telefono solo debe tener numeros, parentesis y guiones",
                                Toast.LENGTH_LONG
                            ).show()
                            haveTelefonoError = true
                        } else if (!(viewModel.telefono.length > 9)) {
                            Toast.makeText(
                                context1,
                                "Telefono debe tener más de 9 dígitos",
                                Toast.LENGTH_LONG
                            ).show()
                            haveTelefonoError = true
                        } else if (viewModel.celular.isBlank()) {
                            Toast.makeText(
                                context1,
                                "Celular no puede  estar vacío",
                                Toast.LENGTH_LONG
                            ).show()
                            haveCelularError = true
                        } else if (!viewModel.celular.all { it.isDigit() || it.equals('(') || it.equals(')') || it.equals('-') || it.equals(' ') || it.equals('+') }) {
                            Toast.makeText(
                                context1,
                                "Celular solo debe tener numeros",
                                Toast.LENGTH_LONG
                            ).show()
                            haveCelularError = true
                        } else if (!(viewModel.celular.length > 9)) {
                            Toast.makeText(
                                context1,
                                "Celular debe tener más de 9 dígitos",
                                Toast.LENGTH_LONG
                            ).show()
                            haveCelularError = true

                        } else if (viewModel.email.isBlank()) {
                            Toast.makeText(
                                context1,
                                "Email no puede  estar vacío",
                                Toast.LENGTH_LONG
                            ).show()
                            haveEmailError = true
                        } else if (!viewModel.email.any { it.equals('@') } || !viewModel.email.any { it.equals('.') }) {
                            Toast.makeText(
                                context1,
                                "Email inválido",
                                Toast.LENGTH_LONG
                            ).show()
                            haveEmailError = true
                        } else if (viewModel.direccion.isBlank()) {
                            Toast.makeText(
                                context1,
                                "Direccion no puede  estar vacía",
                                Toast.LENGTH_LONG
                            ).show()
                            haveDireccionError = true
                        } else if (viewModel.fechaNacimiento.isBlank()) {
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
                            viewModel.Guardar()
                            navHostController.navigateUp()
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