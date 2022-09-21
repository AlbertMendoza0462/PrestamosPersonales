package edu.ucne.prestamospersonales.data.dao;


import androidx.room.*;
import edu.ucne.prestamospersonales.model.Ocupacion;
import edu.ucne.prestamospersonales.model.Persona
import kotlinx.coroutines.flow.Flow;

@Dao
interface PersonaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPersona(persona: Persona)

//    @Update
//    suspend fun updatePersona(persona: Persona)

    @Delete
    suspend fun deletePersona(persona: Persona)

    @Query("SELECT * FROM Personas WHERE PersonaId = :PersonaId")
    fun getPersona(PersonaId: Int): Flow<Persona>

    @Query("SELECT * FROM Personas")
    fun getAll(): Flow<List<Persona>>
}
