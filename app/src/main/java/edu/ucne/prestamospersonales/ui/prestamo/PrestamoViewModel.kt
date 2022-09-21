package edu.ucne.prestamospersonales.ui.prestamo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.prestamospersonales.data.repository.PersonaRepository
import edu.ucne.prestamospersonales.model.Prestamo
import edu.ucne.prestamospersonales.data.repository.PrestamoRepository
import edu.ucne.prestamospersonales.model.Persona
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrestamoViewModel @Inject constructor(
    val prestamoRepository: PrestamoRepository,
    val personaRepository: PersonaRepository
) : ViewModel() {
    var prestamoId by mutableStateOf(0)
    var fecha by mutableStateOf("")
    var vence by mutableStateOf("")
    var personaId by mutableStateOf(0)
    var concepto by mutableStateOf("")
    var monto by mutableStateOf(0.0)
    var balance by mutableStateOf(0.0)
    var prestamos = prestamoRepository.getAll()
        private set

    fun Guardar() {
        viewModelScope.launch {
            prestamoRepository.insert(
                Prestamo(
                    PrestamoId = prestamoId,
                    Fecha = fecha,
                    Vence = vence,
                    PersonaId = personaId,
                    Concepto = concepto,
                    Monto = monto,
                    Balance = balance
                )
            )

            personaRepository.find(personaId).collect {
                personaRepository.insert(
                    Persona(
                        PersonaId = it.PersonaId,
                        Nombres = it.Nombres,
                        Telefono = it.Telefono,
                        Celular = it.Celular,
                        Email = it.Email,
                        Direccion = it.Direccion,
                        FechaNacimiento = it.FechaNacimiento,
                        OcupacionId = it.OcupacionId,
                        Balance = balance
                    )
                )
            }

        }
    }
}