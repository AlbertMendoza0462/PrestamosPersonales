package edu.ucne.prestamospersonales.data.repository

import edu.ucne.prestamospersonales.data.dao.PrestamoDao
import edu.ucne.prestamospersonales.model.Prestamo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PrestamoRepository @Inject constructor(
    val prestamoDao:PrestamoDao
) {

    suspend fun insert(prestamo :Prestamo){
        prestamoDao.insertPrestamo(prestamo)
    }

    suspend fun delete(prestamo : Prestamo){
        prestamoDao.deletePrestamo(prestamo)
    }

    fun find(prestamoId: Int):Flow<Prestamo> {
        return prestamoDao.getPrestamo(prestamoId)
    }

    fun getAll(): Flow<List<Prestamo>>{
        return prestamoDao.getAll()
    }
}
