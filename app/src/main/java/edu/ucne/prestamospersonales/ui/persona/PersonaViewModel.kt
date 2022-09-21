package edu.ucne.prestamospersonales.ui.persona

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.prestamospersonales.data.repository.PersonaRepository
import edu.ucne.prestamospersonales.model.Persona
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonaViewModel @Inject constructor(
    val personaRepository: PersonaRepository
) : ViewModel() {
    var personaId by mutableStateOf(0)
    var nombres by mutableStateOf("")
    var telefono by mutableStateOf("")
    var celular by mutableStateOf("")
    var email by mutableStateOf("")
    var direccion by mutableStateOf("")
    var fechaNacimiento by mutableStateOf("")
    var ocupacionId by mutableStateOf(0)
    var balance by mutableStateOf(0.0)
    var personas = personaRepository.getAll()
        private set

    fun Guardar() {
        viewModelScope.launch {
            personaRepository.insert(
                Persona(
                    PersonaId = personaId,
                    Nombres = nombres,
                    Telefono = telefono,
                    Celular = celular,
                    Email = email,
                    Direccion = direccion,
                    FechaNacimiento = fechaNacimiento,
                    OcupacionId = ocupacionId,
                    Balance = balance
                )
            )
        }
    }
}