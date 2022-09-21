package edu.ucne.prestamospersonales.ui.prestamo

import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.hilt.navigation.compose.hiltViewModel
import edu.ucne.prestamospersonales.utils.Screen
import java.util.*

@Composable
fun PrestamoScreen(
    navHostController: NavHostController,
    viewModel: PrestamoViewModel = hiltViewModel()
) {
    val context1 = LocalContext.current

    var haveFechaError = false
    var haveVenceError = false

    var year: Int
    var month: Int
    var day: Int
    var calendar = Calendar.getInstance()
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()
    val date1 = remember { mutableStateOf("") }
    val datepickerDialog1 = DatePickerDialog(
        context1,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            date1.value = "$dayOfMonth/" + (month + 1) + "/$year"
            viewModel.fecha = date1.value
        }, year, month, day
    )

    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()
    val date2 = remember { mutableStateOf("") }
    val datepickerDialog2 = DatePickerDialog(
        context1,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            date2.value = "$dayOfMonth/" + (month + 1) + "/$year"
            viewModel.vence = date2.value
        }, year, month, day
    )

    val interactionSourceFecha = remember { MutableInteractionSource() }
    val isPressedFecha: Boolean by interactionSourceFecha.collectIsPressedAsState()
    if (isPressedFecha) datepickerDialog1.show()

    val interactionSourceVence = remember { MutableInteractionSource() }
    val isPressedVence: Boolean by interactionSourceVence.collectIsPressedAsState()
    if (isPressedVence) datepickerDialog2.show()

    Column(modifier = Modifier.padding(8.dp)) {
        OutlinedTextField(
            isError = haveFechaError,
            readOnly = true,
            value = date1.value,
            onValueChange = { },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Fecha") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null
                )
            },
            interactionSource = interactionSourceFecha
        )

        OutlinedTextField(
            isError = haveVenceError,
            readOnly = true,
            value = date2.value,
            onValueChange = { },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Vence") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null
                )
            },
            interactionSource = interactionSourceVence
        )

        OutlinedTextField(
            value = viewModel.personaId.toString(),
            onValueChange = { viewModel.personaId = it.toInt() },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "PersonaId")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null
                )
            }
        )

        OutlinedTextField(
            value = viewModel.concepto,
            onValueChange = { viewModel.concepto = it },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Concepto")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null
                )
            }
        )

        OutlinedTextField(
            value = viewModel.monto.toString(),
            onValueChange = { viewModel.monto = it.toDouble() },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Monto")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = null
                )
            }
        )

        OutlinedTextField(
            value = viewModel.balance.toString(),
            onValueChange = { viewModel.balance = it.toDouble() },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Balance")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = null
                )
            }
        )

        OutlinedButton(onClick = {
            if (viewModel.fecha.isBlank()) {
                Toast.makeText(
                    context1,
                    "Fecha no puede  estar vacío",
                    Toast.LENGTH_LONG
                ).show()
                haveFechaError = true
            } else if (viewModel.vence.isBlank()) {
                Toast.makeText(
                    context1,
                    "Vence no puede  estar vacío",
                    Toast.LENGTH_LONG
                ).show()
                haveVenceError = true
            } else if (viewModel.monto <= 0) {
                Toast.makeText(
                    context1,
                    "monto no puede  estar vacío",
                    Toast.LENGTH_LONG
                ).show()
                haveVenceError = true
            } else {
                viewModel.Guardar()
                navHostController.navigate(Screen.ConsultaPrestamoScreen.route)

            }
        }) {
            Text(text = "Guardar")
        }

        OutlinedButton(onClick = {
            navHostController.navigate(Screen.ConsultaPrestamoScreen.route)
        }) {
            Text(text = "Consulta de Prestamos")
        }

        OutlinedButton(onClick = {
            navHostController.navigate(Screen.PersonaScreen.route)
        }) {
            Text(text = "Registro de personas")
        }
    }
}