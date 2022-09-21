package edu.ucne.prestamospersonales.data.repository

import edu.ucne.prestamospersonales.data.dao.PersonaDao
import edu.ucne.prestamospersonales.model.Persona
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PersonaRepository @Inject constructor(
    val personaDao:PersonaDao
) {

    suspend fun insert(persona :Persona){
        personaDao.insertPersona(persona)
    }

    suspend fun delete(persona : Persona){
        personaDao.deletePersona(persona)
    }

    fun find(personaId: Int):Flow<Persona> {
        return personaDao.getPersona(personaId)
    }

    fun getAll(): Flow<List<Persona>>{
        return personaDao.getAll()
    }
}
