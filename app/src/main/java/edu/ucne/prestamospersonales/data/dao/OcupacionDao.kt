package edu.ucne.prestamospersonales.data.dao;


import androidx.room.*;
import edu.ucne.prestamospersonales.model.Ocupacion;
import kotlinx.coroutines.flow.Flow;

@Dao
interface OcupacionDao {
    @Insert
    suspend fun insertOcupacion(ocupacion: Ocupacion)

    @Update
    suspend fun updateOcupacion(ocupacion: Ocupacion)

    @Delete
    suspend fun deleteOcupacion(ocupacion: Ocupacion)

    @Query("SELECT * FROM Ocupaciones WHERE OcupacionId = :OcupacionId")
    fun getOcupacion(OcupacionId: Int): Flow<Ocupacion>

    @Query("SELECT * FROM Ocupaciones")
    fun getAll(): Flow<List<Ocupacion>>
}
