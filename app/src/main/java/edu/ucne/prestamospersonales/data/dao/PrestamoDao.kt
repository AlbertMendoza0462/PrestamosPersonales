package edu.ucne.prestamospersonales.data.dao;


import androidx.room.*;
import edu.ucne.prestamospersonales.model.Prestamo;
import kotlinx.coroutines.flow.Flow;

@Dao
interface PrestamoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrestamo(prestamo: Prestamo)

//    @Update
//    suspend fun updatePrestamo(prestamo: Prestamo)

    @Delete
    suspend fun deletePrestamo(prestamo: Prestamo)

    @Query("SELECT * FROM Prestamos WHERE PrestamoId = :PrestamoId LIMIT 1")
    fun getPrestamo(PrestamoId: Int): Flow<Prestamo>

    @Query("SELECT * FROM Prestamos")
    fun getAll(): Flow<List<Prestamo>>
}
